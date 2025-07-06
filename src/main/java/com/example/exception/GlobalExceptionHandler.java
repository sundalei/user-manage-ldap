package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AttributeInUseException;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.odm.core.OdmException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NameNotFoundException.class})
    public ResponseEntity<String> handleNameNotFoundException(NameNotFoundException ex) {
        return new ResponseEntity<>("LDAP entry not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AttributeInUseException.class})
    public ResponseEntity<String> handleAttributeInUseException(AttributeInUseException ex) {
        return new ResponseEntity<>("LDAP attribute conflict: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OdmException.class)
    public ResponseEntity<String> handleOdmException(OdmException ex) {
        return new ResponseEntity<>("LDAP mapping error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommunicationException.class)
    public ResponseEntity<String> handleCommunicationException() {
        return new ResponseEntity<>("LDAP server is unavailable.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
