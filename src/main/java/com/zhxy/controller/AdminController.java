package com.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhxy.pojo.Admin;
import com.zhxy.service.AdminService;
import com.zhxy.utils.MD5;
import com.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员管理")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("分页条件带查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的当前页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的单页条目数") @PathVariable Integer pageSize,
            @ApiParam("管理员名字") String adminName){
        Page<Admin> page = new Page<>(pageNo,pageSize);
        IPage<Admin> iPage = adminService.getAllAdmin(page,adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("删除单个和多个管理员")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("需要删除的所有Admin信息id的JSON集合") @RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("增加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("JSON格式的Admin对象") @RequestBody Admin admin){
        Integer id = admin.getId();
        if (id == null || id == 0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

}
