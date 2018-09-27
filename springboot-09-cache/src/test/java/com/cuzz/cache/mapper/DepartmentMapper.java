package com.cuzz.cache.mapper;

import com.cuzz.cache.bean.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: cuzz
 * @Date: 2018/9/26 16:03
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentMapper {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void test() {
        Employee employee = employeeMapper.getEmployeeById(1);
        System.out.println(employee);
    }


}
