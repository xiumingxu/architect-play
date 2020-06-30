package com.xx.service;

import com.xx.pojo.Items;
import com.xx.pojo.ItemsImg;
import com.xx.pojo.ItemsParam;
import com.xx.pojo.ItemsSpec;
import com.xx.pojo.vo.CommentLevelCountsVO;
import com.xx.pojo.vo.ItemCommentVO;

import java.util.List;

/**
    For project item details
 */
public interface ItemService {
    /**
     * Query details by product Id
     * @param id
     * @return
     */
    public Items queryItemById(String id);

    /**
     * Search for item image list by item id
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * Search for item specification list by item id
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * Search for item parameters, such as big, small
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * Search for item comments by item Id
     */
    public CommentLevelCountsVO queryCommentCounts(String itemID);

    /**
     * Get item comments
     */
    public List<ItemCommentVO> queryPagedComments(String itemId, Integer level);

}
