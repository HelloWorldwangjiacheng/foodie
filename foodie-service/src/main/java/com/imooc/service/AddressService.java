package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.UserBO;

import java.util.List;

public interface AddressService {

    /**
     * 根据用户id来查询列表
     * @param userId 用户id
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);
}
