package com.imooc.controller;

import com.imooc.common.enums.YesOrNo;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.PagedGridResult;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult infoByItemId(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemSpecList(itemsSpecs);
        itemInfoVO.setItemParams(itemsParam);

        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评论等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "当前页码", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "业内偏移", required = false)
            @RequestParam Integer pageSize
            ){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null|| page <= 0){
            page = 1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }


    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords", value = "关键词", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序方式", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "当前页码", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "业内偏移", required = false)
            @RequestParam Integer pageSize
    ){

        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null|| page <= 0){
            page = 1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize = SEARCH_ITEM_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "分类id搜索商品列表", notes = "分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序方式", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "当前页码", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "业内偏移", required = false)
            @RequestParam Integer pageSize
    ){

        if (catId == null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null|| page <= 0){
            page = 1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize = SEARCH_ITEM_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(catId, sort, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }
}
