
package com.microshop.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){super(message);}
}
