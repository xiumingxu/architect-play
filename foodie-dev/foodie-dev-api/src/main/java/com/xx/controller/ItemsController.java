package com.xx.controller;


import com.xx.pojo.Items;
import com.xx.pojo.ItemsImg;
import com.xx.pojo.ItemsParam;
import com.xx.pojo.ItemsSpec;
import com.xx.pojo.vo.CommentLevelCountsVO;
import com.xx.pojo.vo.ItemInfoVO;
import com.xx.service.ItemService;
import com.xx.utils.JSONResult;
import com.xx.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="item details page", tags={"item details page"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value="Search for item details", httpMethod="GET")
    @GetMapping("/info/{itemId}")
    public JSONResult getItemDetails(
            @ApiParam(name="itemId", required = true)
            @PathVariable String itemId){
        if(StringUtils.isBlank(itemId))
            return JSONResult.errorMsg("Item Id cannot be blank");
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParams = itemService.queryItemParam(itemId);
        // return a VO, without any try catch
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemParams(itemsParams);

        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value="Search for item comment counts", httpMethod="GET")
    @GetMapping("/comments")
    public JSONResult getItemCommentsCounts(
            @ApiParam(name="itemId", required = true)
            @RequestParam String itemId,
            @ApiParam(name="level", required = false)
            @RequestParam Integer level,
            @ApiParam(name="page", required = false, value = "current page")
            @RequestParam Integer page,
            @ApiParam(name="pageSize", required = false)
            @RequestParam Integer pageSize
            ){
        //这里就被看了, 不需要
        if(StringUtils.isBlank(itemId))
            return JSONResult.errorMsg("Item Id cannot be blank");
        if(page == null)
            page = 1;
        // 可以找个通用化参数的 controller
        if(pageSize == null)
            pageSize = COMMENT_PAGE_SIZE;
        PagedGridResult pagedComments = itemService.queryPagedComments(itemId, level, page, pageSize);

        return JSONResult.ok(pagedComments);
    }

    @ApiOperation(value="Search for item comments", httpMethod="GET")
    @GetMapping("/commentLevel")
    public JSONResult getItemCommentsCounts(
            @ApiParam(name="itemId", required = true)
            @RequestParam String itemId){
        if(StringUtils.isBlank(itemId))
            return JSONResult.errorMsg("Item Id cannot be blank");
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return JSONResult.ok(countsVO);
    }

    @ApiOperation(value="Search for items list", httpMethod="GET")
    @GetMapping("/search")
    public JSONResult getItemCommentsCounts(
            @ApiParam(name="keywords", required = true)
            @RequestParam String keywords,
            @ApiParam(name="sort", required = false)
            @RequestParam String sort,
            @ApiParam(name="page", required = false, value = "current page")
            @RequestParam Integer page,
            @ApiParam(name="pageSize", required = false)
            @RequestParam Integer pageSize
    ){

        //by default, cannot search
        if(StringUtils.isBlank(keywords))
            return JSONResult.errorMsg("Item Id cannot be blank");
        if(page == null)
            page = 1;
        // 可以找个通用化参数的 controller
        if(pageSize == null)
            pageSize = SEARCH_ITEM_PAGE_SIZE;

        PagedGridResult list = itemService.searchItems(keywords, sort, page, pageSize);

        return JSONResult.ok(list);
    }

    @ApiOperation(value="Search for items list by category", httpMethod="GET")
    @GetMapping("/catItems")
    public JSONResult getItemCommentsCounts(
            @ApiParam(name="catId", required = true)
            @RequestParam Integer catId,
            @ApiParam(name="sort", required = false)
            @RequestParam String sort,
            @ApiParam(name="page", required = false, value = "current page")
            @RequestParam Integer page,
            @ApiParam(name="pageSize", required = false)
            @RequestParam Integer pageSize
    ){

        //by default, cannot search
        if(catId == null)
            return JSONResult.errorMsg("Category Id cannot be blank");
        if(page == null)
            page = 1;
        // 可以找个通用化参数的 controller
        if(pageSize == null)
            pageSize = SEARCH_ITEM_PAGE_SIZE;

        PagedGridResult list = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);

        return JSONResult.ok(list);
    }
}
