package com.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhxy.pojo.Teacher;
import com.zhxy.service.TeacherService;
import com.zhxy.utils.MD5;
import com.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("分页条件带查询教师信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("分页查询的当前页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的单页条目数") @PathVariable Integer pageSize,
            @ApiParam("分页查询的条件") Teacher teacher){
        Page<Teacher> page = new Page<>(pageNo,pageSize);
        IPage<Teacher> iPage = teacherService.getTeachers(page,teacher);
        return Result.ok(iPage);
    }

    @ApiOperation("添加教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("JSON格式的Teacher对象") @RequestBody Teacher teacher){
        Integer id = teacher.getId();
        if (id == null || id == 0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("删除单个和多个教师")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("需要删除的所有Teacher信息id的JSON集合") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}
