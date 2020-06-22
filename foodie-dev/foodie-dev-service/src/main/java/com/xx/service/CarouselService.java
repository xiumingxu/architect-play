package com.xx.service;

import com.xx.pojo.Carousel;
import com.xx.pojo.Users;
import com.xx.pojo.bo.UserBO;

import java.util.List;

public interface CarouselService {
    /**
     * param: isShow
     */
    public List<Carousel> queryAll(int isShow);

}
