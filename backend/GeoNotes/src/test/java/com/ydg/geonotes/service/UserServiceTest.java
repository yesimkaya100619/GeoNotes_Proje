package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.User;
import com.ydg.geonotes.repository.UserRepository;
import com.ydg.geonotes.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveUser(){
        User user= new User();
        user.setUsername("newuser");
        user.setEmail("newuser@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser, "Kaydedilen kullanıcı null olmamalı");
        assertEquals("newuser", savedUser.getUsername());

        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void testGetUserById_NotFound() {
        // Repository boş dönerse (Optional.empty)
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User found = userService.getUserById(99L);

        // Sonucun null olduğunu doğrula (Hocanın kodundaki orElse(null) kısmı burayı çalıştırır)
        assertNull(found);
        verify(userRepository, times(1)).findById(99L);
    }

}