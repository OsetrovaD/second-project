package com.osetrova.project.dao.entitydaointerface;

import com.osetrova.project.dao.BaseDao;
import com.osetrova.project.dto.LoginPasswordDto;
import com.osetrova.project.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {

    Optional<User> getByLoginAndPassword(LoginPasswordDto loginPasswordDto);
}
