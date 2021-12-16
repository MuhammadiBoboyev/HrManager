package uz.pdp.lesson_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import uz.pdp.lesson_5.entity.Role;
import uz.pdp.lesson_5.entity.Section;
import uz.pdp.lesson_5.entity.Tourniquet;
import uz.pdp.lesson_5.entity.User;
import uz.pdp.lesson_5.entity.enums.RoleName;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.LoginDto;
import uz.pdp.lesson_5.payload.RegisterDto;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.repository.RoleRepository;
import uz.pdp.lesson_5.repository.SectionRepository;
import uz.pdp.lesson_5.repository.TourniquetRepository;
import uz.pdp.lesson_5.repository.UserRepository;
import uz.pdp.lesson_5.security.JwtProvider;

import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    @Lazy
    JavaMailSender javaMailSender;

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TourniquetRepository tourniquetRepository;

    public ApiResponse verificationEmail(String email, String emailCode, String password) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return new ApiResponse("account verification", true);
        }
        return new ApiResponse("account is already", false);
    }

    public ApiResponse registerEmployee(RegisterDto registerDto, String path) {
        Set<Role> role = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        User user = new User();
        if (path.equals("hr_manager")) {
            if (role.contains("ROLE_DIRECTOR")) {
                user.setRole(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_HR_MANAGER)));
            }
            return new ApiResponse("you are no permission this request", false);
        }
        if (path.equals("employee")) {
            if (role.contains("ROLE_HR_MANAGER") || role.contains("ROLE_DIRECTOR")) {
                user.setRole(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_EMPLOYER)));
            }
            return new ApiResponse("you are no permission this request", false);
        }
        if (userRepository.existsByEmail(registerDto.getEmail()))
            return new ApiResponse("this email is exists already", false);
        Optional<Section> optionalSection = sectionRepository.findById(registerDto.getSectionId());
        if (!optionalSection.isPresent())
            return new ApiResponse("section not found", false);
        Optional<Tourniquet> optionalTourniquet = tourniquetRepository.findById(registerDto.getTourniquetId());
        if (!optionalTourniquet.isPresent())
            return new ApiResponse("tourniquet not found", false);
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setSection(optionalSection.get());
        user.setTourniquet(optionalTourniquet.get());
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        boolean sendEmail = sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("success full registered please verification your email", true);
    }

    public ApiResponse loginUser(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User principal = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), principal.getRole());
            return new ApiResponse("token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("login or password in correct", false);
        }
    }

    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom("boboyevmuhammad99@gmail.com");
            mimeMessageHelper.setTo(sendingEmail);
            mimeMessageHelper.setSubject("Account verification");
            mimeMessageHelper.setText("<a href='http://localhost:8080/api/auth/verificationEmail/?emailCode=" + emailCode + "&email=" + sendingEmail + "'>click to verification</a>", true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
