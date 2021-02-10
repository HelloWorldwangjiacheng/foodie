package com.imooc.controller;

import com.imooc.common.enums.YesOrNo;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "轮播图列表展示", notes = "轮播图列表展示", httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel(){

        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

        return IMOOCJSONResult.ok(carousels);
    }


    @ApiOperation(value = "获取商品(一级分类)", notes = "获取商品(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats(){

        List<Category> categories = categoryService.queryAllRootLevelCat();

        return IMOOCJSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类信息", notes = "获取商品子分类信息", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true) @PathVariable Integer rootCatId){

        // 虽然上面的swagger2 有做参数必传校验，但是我们自己必须要校验，因为会有爬虫或者黑客恶意攻击
        if (rootCatId == null){
            return IMOOCJSONResult.errorMsg("分类不存在！");
        }

        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(subCatList);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(@ApiParam(name = "rootCatId", value = "一级分类id", required = true) @PathVariable Integer rootCatId){

        // 虽然上面的swagger2 有做参数必传校验，但是我们自己必须要校验，因为会有爬虫或者黑客恶意攻击
        if (rootCatId == null){
            return IMOOCJSONResult.errorMsg("分类不存在！");
        }

        List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(sixNewItemsLazy);
    }
}
