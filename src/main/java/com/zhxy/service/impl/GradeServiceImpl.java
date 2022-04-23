package com.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhxy.mapper.GradeMapper;
import com.zhxy.pojo.Grade;
import com.zhxy.service.GradeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOr(Page<Grade> pageParam, String gradeName) {
        QueryWrapper<Grade> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<Grade> getGrades() {
        return baseMapper.selectList(null);
    }
}
