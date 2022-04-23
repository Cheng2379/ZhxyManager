package com.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhxy.mapper.StudentMapper;
import com.zhxy.pojo.LoginForm;
import com.zhxy.pojo.Student;
import com.zhxy.service.StudentService;
import com.zhxy.utils.MD5;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("stuService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String clazzName = student.getClazzName();
        if (!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name",clazzName);
        }
        String name = student.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page,queryWrapper);
    }
}
