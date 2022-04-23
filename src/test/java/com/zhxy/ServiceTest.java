package com.zhxy;

import com.zhxy.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void admin(){
        System.out.println(adminService.getById(101));
    }
}
