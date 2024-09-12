package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.UserDTO;
import org.example.shopapp.entities.Role;
import org.example.shopapp.entities.User;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.reposistories.RoleRepository;
import org.example.shopapp.services.serviceImpl.UserServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.example.shopapp.reposistories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
            newUser.setPassword(password);
        }

        return userRepository.save(newUser);
    }


    @Override
    public String login(String phoneNumber, String password) {
        // security part
        return null;
    }
}
