package com.cuzz.springboot.mapper;


import com.cuzz.springboot.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper 或者 @MapperScan 将接口扫描装配到容器中
 */
public interface EmployeeMapper {

    Employee getEmpById(Integer id);

    void insertEmp(Employee employee);
}
