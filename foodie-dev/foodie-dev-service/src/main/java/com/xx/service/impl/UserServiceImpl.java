package com.xx.service.impl;

import com.xx.enums.Sex;
import com.xx.pojo.bo.UserBO;
import com.xx.service.UserService;
import com.xx.mapper.UsersMapper;
import com.xx.pojo.Users;
import com.xx.utils.DateUtil;
import com.xx.utils.MD5Utils;
import org.n3r.idworker.Sid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    @Autowired
    public UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        //构建条件
        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);

        return result == null? false: true;
    }

    @Override
    public Users createUser(UserBO userBO) {
        String usrId = sid.nextShort();
        Users user = new Users();
        user.setId(usrId);

        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(userBO.getUsername());
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setFace(USER_FACE);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        user.setSex(Sex.secret.type);
        usersMapper.insert(user);
        return user;
    }

    @Override
    public Users queryUserForLogin(String username, String password) {


        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(userExample);

        return result;
    }
}
