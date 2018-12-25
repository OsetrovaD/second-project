package com.osetrova.project.service.userservice;

import com.osetrova.project.converter.userconverter.UserToUserDetailsConverter;
import com.osetrova.project.converter.userconverter.UserToUserInfoConverter;
import com.osetrova.project.converter.userconverter.UserToUserInfoForAdminConverter;
import com.osetrova.project.dto.userdto.AdminSalaryAndIdDto;
import com.osetrova.project.dto.userdto.RegisterUserDto;
import com.osetrova.project.dto.userdto.UserInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.UserInfoForAdminDto;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.servicedto.UserChangeDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "users")
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserToUserDetailsConverter converter;
    private final UserToUserInfoForAdminConverter userForAdminConverter;
    private final UserToUserInfoConverter userInfoConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(converter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public User save(RegisterUserDto userInfo) {
        User newUser = new SimpleUser(userInfo.getLogin(), passwordEncoder.encode(userInfo.getPassword()), userInfo.getFirstName(),
                                    userInfo.getLastName(), userInfo.getPhoneNumber(), userInfo.getAddress(),
                                    userInfo.getEmail(), Role.USER, LocalDate.now());

        return userRepository.save(newUser);
    }

    @Override
    public UserInfoDto getUserInfo(String login) {
        return userRepository.findByLogin(login)
                .map(userInfoConverter::convert)
                .orElseThrow(DaoException::new);
    }

    @Override
    public UserInfoDto getUserInfo(Long id) {
        return userRepository.findById(id)
                .map(userInfoConverter::convert)
                .orElseThrow(DaoException::new);
    }

    @Override
    public List<UserInfoForAdminDto> findAllByRoles(List<Role> roles) {
        return userRepository.findAllByRoleInOrderById(roles).stream()
                .map(userForAdminConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int changeRoleToUser(Long userId) {
        userRepository.deleteAdminDetails(userId);
        userRepository.addSimpleUserDetails(userId, LocalDate.now());

        return userRepository.changeUserRole(userId, Role.USER);
    }

    @Transactional
    @Override
    public int changeRoleToAdmin(AdminSalaryAndIdDto adminDetails) {
        userRepository.deleteSimpleUserDetails(adminDetails.getId());
        userRepository.addAdminDetails(adminDetails.getId(), adminDetails.getSalary());

        return userRepository.changeUserRole(adminDetails.getId(), Role.ADMIN);
    }

    @Transactional
    @Override
    public int changeSalary(AdminSalaryAndIdDto adminDetails) {
        return userRepository.changeSalary(adminDetails.getId(), adminDetails.getSalary());
    }

    @Transactional
    @Override
    public User changeData(UserChangeDataDto userInfo, String login) {
        User user = userRepository.findByLogin(login).orElseThrow(DaoException::new);
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setAddress(userInfo.getAddress());

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public int changeFirstName(String login, String firstName) {
        return userRepository.changeFirstName(login, firstName);
    }

    @Transactional
    @Override
    public int changeLastName(String login, String lastName) {
        return userRepository.changeLastName(login, lastName);
    }

    @Transactional
    @Override
    public int changeEmail(String login, String email) {
        return userRepository.changeEmail(login, email);
    }

    @Transactional
    @Override
    public int changePhoneNumber(String login, String phoneNumber) {
        return userRepository.changePhoneNumber(login, phoneNumber);
    }

    @Transactional
    @Override
    public int changeAddress(String login, String address) {
        return userRepository.changeAddress(login, address);
    }

    @Transactional
    @Override
    public int changeLastVisitDate(String login) {
        return userRepository.changeLastVisitDate(login, LocalDate.now());
    }
}
