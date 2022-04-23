package com.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 班级
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@TableName("tb_clazz")
public class Clazz {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;             //班级Id
    private String name;            //班级名称
    private Integer number;         //班级人数
    private String introducation;   //班级介绍
    private String headmaster;      //班主任姓名
    private String email;           //班主任邮箱
    private String telephone;       //班主任电话
    private String gradeName;       //班级所属年级

}
