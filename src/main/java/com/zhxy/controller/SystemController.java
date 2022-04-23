package com.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhxy.pojo.Admin;
import com.zhxy.pojo.LoginForm;
import com.zhxy.pojo.Student;
import com.zhxy.pojo.Teacher;
import com.zhxy.service.AdminService;
import com.zhxy.service.StudentService;
import com.zhxy.service.TeacherService;
import com.zhxy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Value("${newfile-location}")
    private String filePath;
    @Value("${response-path}")
    private String responsePath;

    /**
     * 获取验证码
     */
    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取图片
        BufferedImage image = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域,为验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录,附带Token,验证码，登录信息
     */
    @ApiOperation("登录,验证Token,验证码,登录信息,用户类型")
    @PostMapping("/login")
    public Result login(
            @ApiParam("登录提交信息的Form表单") @RequestBody LoginForm loginForm, HttpServletRequest request) {
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || sessionVerifiCode == null) {
            return Result.fail().message("验证码已失效,请点击图片刷新重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码有误,请点击图片刷新后重试!");
        }
        //从session域中移除现有验证码
        session.removeAttribute("verifiCode");

        //分用户类型进行校验
        Map<String, Object> map = new HashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin != null) {
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (student != null) {
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null) {
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.ok(map);
    }

    /**
     * 登录检查Token是否过期，解析Token，判断用户类型,响应UserId
     */
    @ApiOperation("通过Token口令获取当前登录的用户信息")
    @GetMapping("/getInfo")
    public Result getInfoByToken(
            @ApiParam("Token口令") @RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        //判断token是否过期
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token解析用户id和类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }

        return Result.ok(map);
    }

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile) {
        //随机生成文件id名称
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //获取原文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //获取文件后缀
        int fileSuffix = originalFilename.lastIndexOf(".");
        //拼接组成新文件
        String newFiledName = uuid.concat(originalFilename.substring(fileSuffix));
        //保存文件
        String newFilePath = filePath.concat(newFiledName);
        try {
            multipartFile.transferTo(new File(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应文件路径
        String path = responsePath.concat(newFiledName);
        return Result.ok(path);
    }

    /**
     * 修改个人密码
     */
    @ApiOperation("更新用户密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @RequestHeader("token") String token,
            @PathVariable String oldPwd,
            @PathVariable String newPwd){

        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.fail().message("token已失效,请重新登录!");
        }
        //获取用户id和类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id",userId.intValue());
                queryWrapper.eq("password",oldPwd);
                Admin admin = adminService.getOne(queryWrapper);
                if (admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误,请重新输入!");
                }
                break;
            case 2:
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id",userId.intValue());
                queryWrapper2.eq("password",oldPwd);
                Student student = studentService.getOne(queryWrapper2);
                if (student != null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("原密码有误,请重新输入!");
                }
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id",userId.intValue());
                queryWrapper3.eq("password",oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if (teacher != null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码有误,请重新输入!");
                }
                break;
        }
        return Result.ok();
    }

}
