package com.tescorporations.crud.demo.rest;

import com.tescorporations.crud.demo.dao.EmployeeDAO;
import com.tescorporations.crud.demo.entity.Employee;
import com.tescorporations.crud.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee employee = employeeService.findById(employeeId);
        if(employee == null){
            throw new RuntimeException("Employee not found with id: " + employeeId);
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee setEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0);
        Employee employee = employeeService.save(theEmployee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee employee = employeeService.save(theEmployee);
        return employee;
    }

    @DeleteMapping("/employees/{theEmployeeId}")
    public String deteleEmployee(@PathVariable int theEmployeeId) {
        Employee employee = employeeService.findById(theEmployeeId);
        if(employee == null){
            throw new RuntimeException("Employee id not found " + theEmployeeId);
        }
        employeeService.deletById(theEmployeeId);
        return "Deleted employee with id - " + theEmployeeId;
    }
}
