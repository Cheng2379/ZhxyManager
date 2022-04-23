package com.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhxy.pojo.Student;
import com.zhxy.service.StudentService;
import com.zhxy.utils.MD5;
import com.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("分页条件带查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询的当前页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的单页条目数") @PathVariable Integer pageSize,
            @ApiParam("分页查询的条件") Student student) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage = studentService.getStudentByOpr(page, student);
        return Result.ok(iPage);
    }

    @ApiOperation("添加学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("JSON格式的Student对象") @RequestBody Student student) {
        Integer id = student.getId();
        if (id == null || id == 0) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("删除单个和多个学生")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("需要删除的所有Student信息id的JSON集合") @RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }

}
