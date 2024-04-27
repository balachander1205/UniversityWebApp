package com.api.university.service;

import com.api.university.entity.UserEntity;
import com.api.university.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserEntity getUserDetails(String username) {
        return usersRepository.getUserDetails(username);
    }
}
