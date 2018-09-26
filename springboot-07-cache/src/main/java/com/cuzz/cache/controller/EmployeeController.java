package com.cuzz.cache.controller;

import com.cuzz.cache.bean.Employee;
import com.cuzz.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cuzz
 * @Date: 2018/9/26 16:10
 * @Description:
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/emp")
    public Employee update(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        employeeService.deleteEmployee(id);
        return "success";
    }

}
