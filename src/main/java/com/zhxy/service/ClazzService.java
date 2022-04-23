package com.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhxy.pojo.Clazz;

import java.util.List;

public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> getClazzByOr(Page<Clazz> pageParam, Clazz clazz);

    List<Clazz> getClazzs();
}
