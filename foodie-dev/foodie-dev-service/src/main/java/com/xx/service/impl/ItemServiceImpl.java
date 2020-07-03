package com.xx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xx.enums.CommentLevel;
import com.xx.mapper.*;
import com.xx.pojo.*;
import com.xx.pojo.vo.CommentLevelCountsVO;
import com.xx.pojo.vo.ItemCommentVO;
import com.xx.pojo.vo.SearchItemVO;
import com.xx.service.ItemService;
import com.xx.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    // search is not primary key, that is why
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemSpecExample = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemSpecExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(itemSpecExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        //??? selectOne vs select
        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + badCounts + normalCounts;
        CommentLevelCountsVO countsVO =  new CommentLevelCountsVO();
        countsVO.setGoodCounts(goodCounts);
        countsVO.setBadCounts(badCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setTotalCounts(totalCounts);
        return countsVO;
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map =  new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);

        /**
         * page
         */
        PageHelper.startPage(page, pageSize);
        return setterPagedGrid(list, page);
//        return list;
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map =  new HashMap<>();
        map.put("keywords", keywords);
        List<SearchItemVO> list = itemsMapperCustom.searchItems(map);
        PageHelper.startPage(page, pageSize);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map =  new HashMap<>();
        map.put("catId", catId);
        List<SearchItemVO> list = itemsMapperCustom.searchItemsByThirdCat(map);
        PageHelper.startPage(page, pageSize);
        return setterPagedGrid(list, page);
    }

    private Integer getCommentCounts(String itemId, Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if(level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }
    private PagedGridResult setterPagedGrid(List<?> list, Integer page){
        //config page
        PageInfo<?> pagedList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setTotal(pagedList.getPages());
        grid.setRows(list);
        grid.setRecords(pagedList.getTotal());
        return grid;
    }


}
