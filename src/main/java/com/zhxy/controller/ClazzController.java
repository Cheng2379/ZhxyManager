package com.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhxy.pojo.Clazz;
import com.zhxy.service.ClazzService;
import com.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @ApiOperation("获取所有班级")
    @GetMapping("/getClazzs")
    public Result getClazz(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return Result.ok(clazzs);
    }

    @ApiOperation("分页条件带查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("分页查询的当前页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的单页条目数") @PathVariable("pageSize") Integer pagesSize,
            @ApiParam("分页查询的条件") Clazz clazz){
        Page<Clazz> page = new Page<>(pageNo,pagesSize);
        IPage<Clazz> iPage =  clazzService.getClazzByOr(page,clazz);
        return Result.ok(iPage);
    }

    @ApiOperation("添加和修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("JSON格式的Clazz对象") @RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("删除单个和多个班级")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("需要删除的所有Clazz信息id的JSON集合") @RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }



}
