package com.osetrova.project.service.userservice;

import com.osetrova.project.converter.UserToUserDetailsConverter;
import com.osetrova.project.jparepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserToUserDetailsConverter converter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserToUserDetailsConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(converter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
