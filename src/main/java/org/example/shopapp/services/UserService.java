package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.components.JwtTokenUtil;
import org.example.shopapp.dtos.UserDTO;
import org.example.shopapp.entities.Role;
import org.example.shopapp.entities.User;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.reposistories.RoleRepository;
import org.example.shopapp.services.serviceImpl.UserServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.shopapp.reposistories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        // check exist phone number
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        // convert from userDTO to user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookId(userDTO.getFacebookId())
                .googleId(userDTO.getGoogleId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRole(role);

        if(userDTO.getFacebookId() == 0 || userDTO.getGoogleId() == 0) {
            String password = userDTO.getPassword();
            // security password
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }

        return userRepository.save(newUser);
    }


    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        // security part
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phoneNumber or password");
        }
        User user = optionalUser.get();

        // check password
        if(user.getFacebookId() == 0 && user.getGoogleId() == 0) {
            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid phoneNumber or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password);
        // authenticate java spring sec
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(user);
    }
}
