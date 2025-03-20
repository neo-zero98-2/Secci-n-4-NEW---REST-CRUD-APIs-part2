package com.tescorporations.crud.demo.dao;

import com.tescorporations.crud.demo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee employee);
    void deletById(int theId);
}