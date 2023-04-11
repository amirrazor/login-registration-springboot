package com.example.registration.registration;


import com.example.registration.user.User;
import com.example.registration.user.UserRole;
import com.example.registration.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        Boolean isValidEmail = emailValidator.test(request.getUsername());
        if (!isValidEmail) {
            throw new IllegalStateException("Invalid email you entered there!");
        }

        User savedUser = userService.signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getUsername(),
                        request.getPassword(),
                        UserRole.USER
                )
        );

        if (savedUser != null) {
            return "User registered successfully.";
        } else {
            return "This E-mail already exists! Please log in instead, or use another E-mail.";
        }
    }
}