package com.imooc.service.impl;


import com.imooc.common.enums.Sex;
import com.imooc.common.utils.DateUtil;
import com.imooc.common.utils.MD5Utils;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.AddressService;
import com.imooc.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author 王嘉程
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    /**
     * 根据用户id来查询列表
     * @param userId 用户id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = RuntimeException.class)
    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        // 1. 判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        // 2. 保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }
}
