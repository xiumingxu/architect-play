package com.xx.pojo.vo;

import java.util.List;

/**
 * the lastest items vo
 */
public class NewItemsVO {
//     <id column="rootCatId"  property="rootCatId" />
//    <result column="rootCatName"  property="rootCatName" />
//    <result column="slogan"  property="slogan" />
//    <result column="catImage"  property="catImage" />
//    <result column="bgColor"  property="bgColor" />
//    <result column="itemId"  property="itemId" />
//    <result column="itemName"  property="itemName" />
//    <result column="itemUrl"  property="itemUrl" />
//    <result column="createTime"  pr
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private List<SimpleItemVO> simpleItemList;


    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVO> getSimpleItemList() {
        return simpleItemList;
    }

    public void setSimpleItemList(List<SimpleItemVO> simpleItemList) {
        this.simpleItemList = simpleItemList;
    }

}
