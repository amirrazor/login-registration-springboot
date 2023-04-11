package com.example.registration.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with the email %s was not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                        new UsernameNotFoundException
                                (String.format(USER_NOT_FOUND, email)));
    }

    public User signUpUser(User user) {
       boolean userExists =  userRepository.
               findByEmail(user.getEmail()).
               isPresent();
       if (userExists) {
          throw new IllegalStateException
                  ("This E-mail already exists! please log in instead, or use another E-mail.");
       }

       String encodedPwd =  bCryptPasswordEncoder.encode(user.getPassword());

       user.setPassword(encodedPwd);

        userRepository.save(user);

        return user;
    }
}
