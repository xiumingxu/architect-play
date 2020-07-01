package com.xx.mapper;

import com.xx.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

// 被 itemservice 去实现
public interface ItemsMapperCustom {
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);
}