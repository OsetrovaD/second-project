package com.osetrova.project.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NumberFormatException.class)
    public String catchNumberFormatException() {
        return "number-format-exception";
    }
}
