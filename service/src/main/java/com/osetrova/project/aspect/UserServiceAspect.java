package com.osetrova.project.aspect;

import com.osetrova.project.exception.TheSameUserException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAspect {

    @Pointcut("execution(* com.osetrova.project.service.userservice.UserService.save(..))")
    public void newUser() {}

    @AfterThrowing("newUser()")
    public void theSameUser() {
        throw new TheSameUserException();
    }
}
