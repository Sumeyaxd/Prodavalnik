package com.prodavalnik.prodavalnik.web;


import com.prodavalnik.prodavalnik.service.exception.BadOrderRequestException;
import com.prodavalnik.prodavalnik.service.exception.DeleteObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadOrderRequestException.class)
    public ModelAndView handleBadOrderRequestException (BadOrderRequestException bore) {

        ModelAndView modelAndView = new ModelAndView("bad-order-request");

        modelAndView.addObject("parameter", bore.getParameter());

        return modelAndView;
    }

    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(DeleteObjectException.class)
    public ModelAndView handleDeleteObjectException (DeleteObjectException doe) {

        ModelAndView modelAndView = new ModelAndView("delete-object-error");

        modelAndView.addObject("message", doe.getMessage());

        return modelAndView;
    }
}
