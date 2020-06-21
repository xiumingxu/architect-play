package com.xx.service;

import com.xx.pojo.Users;
import com.xx.pojo.bo.UserBO;

public interface UserService {
    /**
     * Validate whether user is there
     */
    public boolean queryUsernameIsExist(String username);
    /**
     * Add a user
     */
    public Users createUser(UserBO user);

}
