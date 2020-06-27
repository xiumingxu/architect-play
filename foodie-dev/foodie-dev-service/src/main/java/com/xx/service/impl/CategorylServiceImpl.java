package com.xx.service.impl;

import com.xx.mapper.CategoryMapper;
import com.xx.pojo.Category;
import com.xx.service.CategoryService;
import com.xx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategorylServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * Validate whether user is there
     */
    @Override
    public List<Category> queryAllRootLevelCategory(){
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }
}
