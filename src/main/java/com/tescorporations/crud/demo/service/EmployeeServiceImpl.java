package com.tescorporations.crud.demo.service;

import com.tescorporations.crud.demo.dao.EmployeeDAO;
import com.tescorporations.crud.demo.dao.EmployeeRepository;
import com.tescorporations.crud.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    //private EmployeeDAO employeeDAO;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository){
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId); // JPA usa Optional cuando no encuentra el objeto
        Employee theEmployee = null;
        if(result.isPresent()){
            theEmployee = result.get();
        }
        return theEmployee;
    }

    //@Transactional ----- la anotacion no es necesaria cunado se usa jpaRepository
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    //@Transactional ----- la anotacion no es necesaria cunado se usa jpaRepository
    @Override
    public void deletById(int theId) {
        employeeRepository.deleteById(theId);
    }
}
