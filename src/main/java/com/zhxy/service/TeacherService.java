package com.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhxy.pojo.LoginForm;
import com.zhxy.pojo.Teacher;
import org.springframework.stereotype.Repository;

public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeachers(Page<Teacher> page, Teacher teacher);
}
