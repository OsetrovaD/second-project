package com.osetrova.project.controller.exceptioncontroller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NumberFormatException.class)
    public String catchNumberFormatException() {
        return "number-format-exception";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String catchNoHandlerFoundException() {
        return "not-found-exception";
    }
}
