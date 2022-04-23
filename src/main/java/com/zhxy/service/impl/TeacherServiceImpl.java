package com.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhxy.mapper.TeacherMapper;
import com.zhxy.pojo.LoginForm;
import com.zhxy.pojo.Teacher;
import com.zhxy.service.TeacherService;
import com.zhxy.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teaService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeachers(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String clazzName = teacher.getClazzName();
        if (!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name",clazzName);
        }
        String name = teacher.getName();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page,queryWrapper);
    }
}
