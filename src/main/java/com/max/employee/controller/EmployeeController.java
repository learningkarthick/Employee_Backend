package com.max.employee.controller;

import com.max.employee.model.Employee;
import com.max.employee.service.EmployeeCsvService;
import com.max.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeCsvService employeeCsvService;

     @Autowired
     EmployeeService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return service.save(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return service.save(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/generate")
    public String generateReport() throws IOException {
        return employeeCsvService.generateAndUploadCsv();
    }
}