package com.example.UserWallet.exceptions;

public class UserNotFoundException extends Exception {

    private String message;

    /**
     *
     * @param message
     */
    public UserNotFoundException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
