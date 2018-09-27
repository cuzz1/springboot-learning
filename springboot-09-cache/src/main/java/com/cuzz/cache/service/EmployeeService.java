package com.cuzz.cache.service;

import com.cuzz.cache.bean.Employee;
import com.cuzz.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;

/**
 * @Author: cuzz
 * @Date: 2018/9/26 16:08
 * @Description:
 */
@CacheConfig(cacheNames = "emp", cacheManager = "employeeCacheManager")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

//    @Cacheable(cacheNames = "emp", keyGenerator = "myKeyGenerator")
    @Cacheable(cacheNames = "emp")
    public Employee getEmployee(Integer id) {
        System.out.println("----> 查询" + id + "号员工");
        return employeeMapper.getEmployeeById(id);
    }

    @CachePut(value = {"emp"}, key = "#result.id")
    public Employee updateEmployee(Employee employee) {
        System.out.println("---->updateEmployee"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value = "emp",key = "#id")
    public  void  deleteEmployee(Integer id){
        System.out.println("---->删除的employee的id是: "+id);
    }


    @Caching(
        cacheable = {
            @Cacheable(value = "emp",key = "#lastName")
        },
        put = {
            @CachePut(value = "emp",key = "#result.id"),
            @CachePut(value = "emp",key = "#result.gender")
        }
    )
    public Employee getEmployeeByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
