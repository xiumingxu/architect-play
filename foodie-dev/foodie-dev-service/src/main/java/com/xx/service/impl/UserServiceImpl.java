package com.xx.service.impl;

import com.xx.service.UserService;
import com.xx.mapper.UsersMapper;
import com.xx.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
//        usersMapper.
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        //构建条件
        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);

        return result == null? false: true;
    }
}
