package uz.pdp.lesson_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lesson_5.entity.*;
import uz.pdp.lesson_5.entity.enums.RoleName;
import uz.pdp.lesson_5.payload.*;
import uz.pdp.lesson_5.repository.*;
import uz.pdp.lesson_5.security.JwtProvider;

import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TourniquetScannerRepository tourniquetScannerRepository;

    @Autowired
    SalaryRepository salaryRepository;


    public List<User> getEmployee() {
        Set<Role> roles = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!(roles.contains("ROLE_DIRECTOR") || roles.contains("ROLE_HR_MANAGER")))
            return null;
        return userRepository.findAllByRole(roleRepository.findAllByRoleName(RoleName.ROLE_EMPLOYER));
    }

    public ApiEmployeeDataResponse getEmployeeById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return null;
        List<Task> taskList = taskRepository.findByToWhomeAndSuccess(optionalUser.get(), true);
        List<InputOutputTourniquetScanner> scannerList = tourniquetScannerRepository.findAllByTourniquetId(optionalUser.get().getTourniquet().getId());
        return new ApiEmployeeDataResponse(optionalUser.get(), taskList, scannerList);
    }

    public ApiResponse salary(SalaryDto salaryDto) {
        Optional<User> optionalUser = userRepository.findById(salaryDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("employee not found", false);
        salaryRepository.save(new Salary(optionalUser.get(), salaryDto.getTime()));
        return new ApiResponse("success", true);
    }

    public List<Salary> getSalaryByEmployeeId(UUID id) {
        return salaryRepository.findAllByUserId(id);
    }
}
