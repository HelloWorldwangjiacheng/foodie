package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param name 用户名
     * @return
     */
    public boolean queryUsernameIsExists(String name);

    /**
     * 创建用户（注册）
     * @param userBO 接受信息
     * @return 返回Users对象
     */
    public Users createUser(UserBO userBO);

//    /**
//     * 用户退出登录
//     * @return
//     */
//    public Users logout();

    /**
     * 搜索登录用户
     * @param username 用户名
     * @param password 密码
     * @return 用户User
     */
    public Users queryUserForLogin(String username, String password);
}
