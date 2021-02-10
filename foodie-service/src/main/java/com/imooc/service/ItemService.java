package com.imooc.service;

import com.imooc.common.utils.PagedGridResult;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id查询商品
     * @param itemId 商品id
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId 商品id
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId 商品id
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId 商品id
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据id查询商品的评价等级数量
     * @param itemId 商品id
     * @return
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 分页查询商品评论
     * @param itemId 商品id
     * @param level 评价等级
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品
     * @param keywords 关键词
     * @param sort 排序方式
     * @param page 当前页码
     * @param pageSize 页内偏移
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品
     * @param catId 三级分类
     * @param sort 排序方式
     * @param page 当前页码
     * @param pageSize 页内偏移
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);
}
