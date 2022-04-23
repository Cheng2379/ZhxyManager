package com.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhxy.pojo.Grade;
import com.zhxy.service.GradeService;
import com.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取所有年级信息")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    /**
     * 分页查询和搜索
     */
    @ApiOperation("分页条件带查询年级信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("分页查询的当前页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的单页条目数") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询模糊匹配的名称") String gradeName) {
        //分页查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        //通过Service层查询
        IPage<Grade> iPage = gradeService.getGradeByOr(page, gradeName);
        //封装Result对象并返回
        return Result.ok(iPage);
    }

    /**
     * 增加和修改
     */
    @ApiOperation("新增或修改Grade,有id属性是修改, 没有则为增加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON格式的Grade对象") @RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 批量删除和删除
     */
    @ApiOperation("删除单个和多个年级")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("需要删除的所有Grade信息id的JSON集合") @RequestBody List<Integer> ids) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }


}
