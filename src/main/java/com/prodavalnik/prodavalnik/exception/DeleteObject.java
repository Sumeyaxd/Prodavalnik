package com.prodavalnik.prodavalnik.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class DeleteObject extends RuntimeException {

    public DeleteObject(String message) {
        super(message);
    }

}
