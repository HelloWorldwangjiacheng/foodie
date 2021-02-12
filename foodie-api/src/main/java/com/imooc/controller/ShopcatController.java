package com.imooc.controller;

import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
import com.imooc.common.utils.MD5Utils;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口controller", tags = {"购物车接口相关api"})
@RestController
@RequestMapping("/shopcart")
public class ShopcatController {

    final static Logger logger = LoggerFactory.getLogger(ShopcatController.class);


    /**
     *
     * @param userId
     * @param shopcartBO
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "添加商品至购物车", notes = "添加商品至购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response){

        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("");
        }

        logger.info(shopcartBO.toString());

        // TODO 用户登录的情况下， 添加商品至购物车， 后端之后会使用redis进行购物车的存储

        return IMOOCJSONResult.ok();
    }

}
