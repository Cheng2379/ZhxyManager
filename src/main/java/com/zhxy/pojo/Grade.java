package com.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 年级及年级主任信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_grade")
public class Grade {

    //年级信息
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;                 //年级ID
    private String name;                //年级名称
    private String introducation;       //年级介绍
    //年级主任信息
    private String manager;             //年级主任姓名
    private String email;               //年级主任邮箱
    private String telephone;           //年级主任电话

}
