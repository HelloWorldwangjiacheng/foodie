package com.imooc.controller;

import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
import com.imooc.common.utils.MD5Utils;
import com.imooc.pojo.Users;
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

@Api(value = "基础", tags = {"基础"})
@RestController
@RequestMapping("/passport")
public class BaseController {

    final static Logger logger = LoggerFactory.getLogger(BaseController.class);


    public static final Integer COMMENT_PAGE_SIZE = 10;


}
