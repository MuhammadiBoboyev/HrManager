package uz.pdp.lesson_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.pdp.lesson_5.entity.Task;
import uz.pdp.lesson_5.entity.User;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.repository.RoleRepository;
import uz.pdp.lesson_5.repository.TaskRepository;
import uz.pdp.lesson_5.repository.UserRepository;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    @Lazy
    JavaMailSender javaMailSender;

    @Autowired
    TaskRepository taskRepository;

    public ApiResponse task(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getToWhomeId());
        if (!optionalUser.isPresent()) return new ApiResponse("employee not found", false);
        Optional<User> optionalUser1 = userRepository.findById(taskDto.getFromWhomeId());
        if (!optionalUser1.isPresent()) return new ApiResponse("employee not found", false);
        Task task = new Task();
        if (optionalUser1.get().getRole().contains(("ROLE_DIRECTOR"))) {
            task.setFromWhome(optionalUser1.get());
            task.setToWhome(optionalUser.get());
        } else if (optionalUser1.get().getRole().contains("ROLE_HR_MANAGER") && (optionalUser.get().getRole().contains("ROLE_EMPLOYER"))) {
            task.setFromWhome(optionalUser1.get());
            task.setToWhome(optionalUser.get());
        } else {
            return new ApiResponse("you are not permission this request", false);
        }
        task.setName(taskDto.getName());
        task.setComment(taskDto.getComment());
        task.setLifeTime(taskDto.getLifeTime());
        taskRepository.save(task);
        sendEmail(optionalUser1.get().getEmail(), optionalUser.get().getEmail(), task.getName(), task.getComment());
        return new ApiResponse("success saved this task and send message email to employee", true);
    }

    public List<Task> getTaskByEmployeeId(UUID id) {
        return taskRepository.findAllByToWhomeId(id);
    }

    public ApiResponse successTask(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found", false);
        Task task = optionalTask.get();
        task.setSuccess(!task.isSuccess());
        taskRepository.save(task);
        boolean b = sendEmail(task.getToWhome().getEmail(), task.getFromWhome().getEmail(), task.getName() + " \n task id = " + task.getId(), "this task is success");
        return new ApiResponse("task success completed", true);
    }

    public boolean sendEmail(String sendFrom, String sendTo, String name, String comment) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(sendFrom);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setSubject(name);
            mimeMessageHelper.setText(comment, true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
