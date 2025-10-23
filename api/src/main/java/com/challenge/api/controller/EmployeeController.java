package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    // Autowired dependency injection for the EmployeeService, no need to instantiate it
    @Autowired
    private EmployeeService employeeService;

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee models as necessary.
     * @return One or more Employees.
     */
    @GetMapping
    // return a response entity with list of employes so correct status code is returned
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee = employeeService.getEmployeeByUuid(uuid);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer.
     * @param requestBody hint!
     * @return Newly created Employee
     */
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Object requestBody) {
        try {
            Employee employee = employeeService.createEmployee(requestBody);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }
}
