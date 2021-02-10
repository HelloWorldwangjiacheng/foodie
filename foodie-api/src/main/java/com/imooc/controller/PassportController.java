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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private UserService userService;

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @ApiOperation(value = "判断用户名是否存在", notes = "判断用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExists(@RequestParam String username){
        if (StringUtils.isEmpty(username)){
            return IMOOCJSONResult.errorMap("用户名不能为空！");
        }

        boolean b = userService.queryUsernameIsExists(username);
        if (b){
            return IMOOCJSONResult.errorMsg("用户名不能重复！");
        }
        return IMOOCJSONResult.ok();
    }


    /**
     * 用户注册
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response){

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(confirmPassword)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空！");
        }

        if (password.length() < 6 || confirmPassword.length() < 6){
            return IMOOCJSONResult.errorMsg("密码长度必须大于5");
        }

        if (!password.equals(confirmPassword)){
            return IMOOCJSONResult.errorMsg("两次密码不一致！");
        }



        Users user = userService.createUser(userBO);

        Users users = setNullProperty(user);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        return IMOOCJSONResult.ok(user);
    }


    /**
     * 用户退出登录
     * @return
     */
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response){

        // 清除cookie
        CookieUtils.deleteCookie(request,response,"user");

        // TODO 用户退出登录，需要清空购物车

        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();

    }


    /**
     * 用户登录
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response){

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空！");
        }


        Users user = null;
        try {
            user = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            return IMOOCJSONResult.errorMsg(e.getMessage());
        }
        if (user == null){
            return IMOOCJSONResult.errorMsg("用户名或密码不正确！！！");
        }

        Users users = setNullProperty(user);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);

        return IMOOCJSONResult.ok(users);
    }


    private Users setNullProperty(Users user){
        user.setPassword(null);
        user.setRealname(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
        return user;
    }
}
