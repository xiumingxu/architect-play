package com.xx.service.impl;

import com.xx.mapper.CarouselMapper;
import com.xx.mapper.UserAddressMapper;
import com.xx.pojo.Carousel;
import com.xx.pojo.UserAddress;
import com.xx.service.AddressService;
import com.xx.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> queryAll(String userId){
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        List<UserAddress> result = userAddressMapper.selectByExample(ua);
        return result;
    }
}
