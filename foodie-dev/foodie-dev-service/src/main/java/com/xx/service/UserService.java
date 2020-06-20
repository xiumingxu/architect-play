package com.xx.controller;

public interface UserService {
    /**
     * Validate whether user is there
     */
    public boolean queryUsernameIsExist(String username);
}
