package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.UserDTO;
import org.example.shopapp.entities.User;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

public interface UserServiceImpl {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password); // trả về token kit khi login
}
