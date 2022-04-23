package com.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhxy.mapper.AdminMapper;
import com.zhxy.pojo.Admin;
import com.zhxy.pojo.LoginForm;
import com.zhxy.service.AdminService;
import com.zhxy.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("adminService")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAllAdmin(Page<Admin> page,String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)){
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page,queryWrapper);
    }


}
