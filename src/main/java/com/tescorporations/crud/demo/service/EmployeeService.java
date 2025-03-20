package com.tescorporations.crud.demo.service;

import com.tescorporations.crud.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee employee);
    void deletById(int theId);
}
