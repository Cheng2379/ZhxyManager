package com.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhxy.pojo.Admin;
import com.zhxy.pojo.LoginForm;

public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAllAdmin(Page<Admin> page,String adminName);
}
