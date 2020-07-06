package com.xx.service;

import com.xx.pojo.Carousel;
import com.xx.pojo.UserAddress;

import java.util.List;

public interface AddressService {
    /**
     * param: isShow
     */
    public List<UserAddress> queryAll(String userId);
}
