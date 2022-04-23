package com.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhxy.pojo.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByOr(Page<Grade> pageParam, String gradeName);

    List<Grade> getGrades();
}
