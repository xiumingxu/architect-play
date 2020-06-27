package com.xx.mapper;

import com.xx.my.mapper.MyMapper;
import com.xx.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom extends MyMapper<Category> {

    public List getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}