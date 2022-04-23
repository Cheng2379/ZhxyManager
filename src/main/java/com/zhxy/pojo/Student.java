package com.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 学生
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@TableName("tb_student")
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String sno;
    private String name;
    private Character gender = '男';//默认值
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String introducation;
    private String portraitPath;    //存储头像的项目路径
    private String clazzName;       //班级名称

}
