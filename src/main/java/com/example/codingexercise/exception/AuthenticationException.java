package com.example.codingexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown UnAuthenticatedException.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
