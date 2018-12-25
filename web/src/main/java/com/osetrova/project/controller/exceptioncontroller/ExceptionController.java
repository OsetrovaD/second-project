package com.osetrova.project.controller.exceptioncontroller;

import com.osetrova.project.exception.DaoException;
import com.osetrova.project.exception.FileNotFoundInDatabaseException;
import com.osetrova.project.exception.ItemAlreadyInBasketException;
import com.osetrova.project.exception.TheSameUserException;
import com.osetrova.project.exception.WrongDeliveryDateException;
import com.osetrova.project.exception.WrongNumberOfElementException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.ui.Model;
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

    @ExceptionHandler(FileNotFoundInDatabaseException.class)
    public String catchFileNotFoundInDatabaseFoundException(Model model, FileNotFoundInDatabaseException ex) {
        model.addAttribute("searchName", ex.getMessage());

        return "file-not-found-in-database-exception";
    }

    @ExceptionHandler(TheSameUserException.class)
    public String catchDataIntegrityViolationException() {
        return "the-same-user-exception";
    }

    @ExceptionHandler(WrongDeliveryDateException.class)
    public String catchWrongDeliveryDateException() {
        return "wrong-date-delivery-exception";
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public String catchOptimisticLockException() {
        return "optimistic-lock-exception";
    }

    @ExceptionHandler(DaoException.class)
    public String catchDaoException() {
        return "dao-exception";
    }

    @ExceptionHandler(ItemAlreadyInBasketException.class)
    public String catchItemAlreadyInBasketException() {
        return "item-already-in-basket-exception";
    }

    @ExceptionHandler(WrongNumberOfElementException.class)
    public String catchWrongNumberOfElementException() {
        return "wrong-number-of-element-exception";
    }

    @ExceptionHandler(NullPointerException.class)
    public String catchNullPointerException() {
        return "null-pointer-exception";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String catchConstraintViolationException() {
        return "constraint-violation-exception";
    }
}
