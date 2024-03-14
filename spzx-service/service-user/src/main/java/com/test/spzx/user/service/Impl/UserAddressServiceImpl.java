package com.test.spzx.user.service.Impl;

import com.test.spzx.model.entity.user.UserAddress;
import com.test.spzx.user.mapper.UserAddressMapper;
import com.test.spzx.user.service.UserAddressService;
import com.test.spzx.util.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findByUserId(userId);

    }

    @Override
    public UserAddress getById(Long id) {

        return userAddressMapper.getById(id);    }
}
