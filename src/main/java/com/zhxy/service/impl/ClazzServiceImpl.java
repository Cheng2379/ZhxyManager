package com.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhxy.mapper.ClazzMapper;
import com.zhxy.pojo.Clazz;
import com.zhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzByOr(Page<Clazz> pageParam, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGradeName();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("grade_name",gradeName);
        }
        String name = clazz.getName();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
