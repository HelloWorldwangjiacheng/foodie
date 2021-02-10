package com.imooc.service.impl;


import com.imooc.common.enums.Sex;
import com.imooc.common.utils.DateUtil;
import com.imooc.common.utils.MD5Utils;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private final static String USER_FACE = "https://pics4.baidu.com/feed/2cf5e0fe9925bc3115866b4d45385ab6c913707e.png?token=fa40800a2de2d3ed2e92b29f4d36eca0&s=7028A1F2D20E14EFE3093B70030080F4";

    /**
     * 判断用户名是否存在
     * @param name 用户名
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExists(String name) {
        Example usersExample = new Example(Users.class);
        Example.Criteria usersExampleCriteria = usersExample.createCriteria();
        usersExampleCriteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(usersExample);
        return (users != null &&  users.size() == 1);
    }

    /**
     * 注册
     * @param userBO 接受信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users createUser(UserBO userBO) {

        // 全局唯一
        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(userBO.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1993-01-01"));
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        int insert = usersMapper.insert(user);

        return user;
    }

    /**
     * 查找登录用户
     * @param username 用户名
     * @param password 密码
     * @return 用户User
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example usersExample = new Example(Users.class);
        Example.Criteria usersExampleCriteria = usersExample.createCriteria();

        usersExampleCriteria.andEqualTo("username", username);
        usersExampleCriteria.andEqualTo("password", password);

        return usersMapper.selectOneByExample(usersExample);
    }


}
