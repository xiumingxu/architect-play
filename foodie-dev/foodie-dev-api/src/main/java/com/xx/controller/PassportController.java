package com.xx.controller;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.xx.pojo.bo.UserBO;
import com.xx.service.UserService;
import com.xx.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
@Api(value="register and login", tags={"used for login and register"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    /**
     * return status code
     * @return
     */
    @GetMapping("/usernameIsExisted")
    public HttpStatus usernameIsExisted(@RequestParam String username){
        if(StringUtils.isBlank(username))
            return HttpStatus.INTERNAL_SERVER_ERROR;
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist)
            return HttpStatus.INTERNAL_SERVER_ERROR;
        // not exist
        return HttpStatus.OK;
    }

    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBO userBO){
        String userName = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        if(StringUtils.isBlank(userName) ||
            StringUtils.isBlank(password) ||
            StringUtils.isBlank(confirmPassword))
            return JSONResult.errorMsg("Username or password cannot be null");
        if(password.length() < 6)
            return JSONResult.errorMsg("password length cannot be less than 6");
        if(!password.equals(confirmPassword))
            return JSONResult.errorMsg("Two passwords are not the same");
        if(userService.queryUsernameIsExist(userBO.getUsername()))
            return JSONResult.errorMsg("User name already exist");
        userService.createUser(userBO);
        return JSONResult.ok();
    }

}
