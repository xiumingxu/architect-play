package com.xx.controller;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.xx.pojo.Users;
import com.xx.pojo.bo.UserBO;
import com.xx.service.UserService;
import com.xx.utils.CookieUtils;
import com.xx.utils.JSONResult;
import com.xx.utils.JsonUtils;
import com.xx.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public JSONResult register(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response

    ){
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
        // 4. 实现注册
        Users userResult = userService.createUser(userBO);
        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return JSONResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "Userlogin", notes = "User login", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
         // throw the exception from md5
        // 是为了设置 cookie 的值, 才拿来 request and response
        String userName = userBO.getUsername();
        String password = userBO.getPassword();

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password))
            return JSONResult.errorMsg("Username or password cannot be null");

        //implement login: search for user and password
        // password need to be decoded
        Users result = userService.queryUserForLogin(userName, MD5Utils.getMD5Str(password));
        if(result == null)
            return JSONResult.errorMsg("User or password is not correct");
        result =  setNullProperty(result);
        // 在服务端设置把所有的东西放到 cookie 里
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(result), true);

        return JSONResult.ok(result);
    }

}
