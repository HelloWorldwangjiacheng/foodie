package com.imooc.service.impl;


import com.imooc.common.enums.Sex;
import com.imooc.common.enums.YesOrNo;
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

    /**
     * 修改地址
     * @param addressBO
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);

        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.delete(address);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1. 查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list  = userAddressMapper.select(queryAddress);
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setId(addressId);
        singleAddress.setUserId(userId);

        return userAddressMapper.selectOne(singleAddress);
    }
}
