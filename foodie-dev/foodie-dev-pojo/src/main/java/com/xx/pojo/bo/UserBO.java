package com.xx.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象BO", description = "客户端的东西被编程这个 entity中")
public class UserBO {
    @ApiModelProperty(value="username", name="username", example="XX", required = true )
    private String username;
    @ApiModelProperty(value="password", name="password", example="XXxxx", required = true)
    private String password;
    @ApiModelProperty(value="confirmPassword", name="confirmPassword", example="XXxxx", required = false)
    private String confirmPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
