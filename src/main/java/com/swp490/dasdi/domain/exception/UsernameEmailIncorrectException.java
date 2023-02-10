package com.swp490.dasdi.domain.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UsernameEmailIncorrectException extends RuntimeException {
    private String message;
}
