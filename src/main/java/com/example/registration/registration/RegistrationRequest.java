package com.example.registration.registration;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
}
