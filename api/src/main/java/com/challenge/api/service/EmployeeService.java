package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    // map data structure to store employees
    private final Map<UUID, Employee> employees = new HashMap<>();

    public EmployeeService() {
        createMockEmployees();
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    // Get employee by UUID
    public Employee getEmployeeByUuid(UUID uuid) {
        return employees.get(uuid);
    }

    public Employee createEmployee(Object requestBody) {
        // logic to validate the request body
        if (requestBody == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        // seperate request body into key-value pairs
        Map<String, Object> requestMap = (Map<String, Object>) requestBody;

        // firstname and lastname are required fields
        String firstName = (String) requestMap.get("firstName");
        String lastName = (String) requestMap.get("lastName");

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        Integer salary = (Integer) requestMap.get("salary");
        Integer age = (Integer) requestMap.get("age");
        String jobTitle = (String) requestMap.get("jobTitle");
        String email = (String) requestMap.get("email");

        // logic to validate optional fields
        if (salary != null && salary < 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }

        if (age != null && (age < 18 || age > 100)) {
            throw new IllegalArgumentException("Age must be between 18 and 100");
        }

        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Email must be valid");
        }

        // create new employee object
        EmployeeImpl newEmployee = new EmployeeImpl();
        newEmployee.setUuid(UUID.randomUUID());
        newEmployee.setFirstName(firstName.trim());
        newEmployee.setLastName(lastName.trim());
        newEmployee.setFullName(newEmployee.getFirstName() + " " + newEmployee.getLastName());
        newEmployee.setSalary(salary != null ? salary : 50000);
        newEmployee.setAge(age != null ? age : 25);
        newEmployee.setJobTitle(jobTitle != null ? jobTitle : "Employee");
        newEmployee.setEmail(email != null ? email : "employee@company.com");
        newEmployee.setContractHireDate(Instant.now());
        newEmployee.setContractTerminationDate(null);

        // place employee into employees map
        employees.put(newEmployee.getUuid(), newEmployee);

        return newEmployee;
    }

    // Create mock employees for testing
    private void createMockEmployees() {
        EmployeeImpl emp1 = new EmployeeImpl();
        emp1.setUuid(UUID.randomUUID());
        emp1.setFirstName("Devan");
        emp1.setLastName("Parekh");
        emp1.setFullName("Devan Parekh");
        emp1.setSalary(750000000);
        emp1.setAge(20);
        emp1.setJobTitle("Software Engineer");
        emp1.setEmail("dev.par@devpar.com");
        emp1.setContractHireDate(null);

        EmployeeImpl emp2 = new EmployeeImpl();
        emp2.setUuid(UUID.randomUUID());
        emp2.setFirstName("Jane");
        emp2.setLastName("Smith");
        emp2.setFullName("Jane Smith");
        emp2.setSalary(85000);
        emp2.setAge(28);
        emp2.setJobTitle("Senior Developer");
        emp2.setEmail("jane.smith@company.com");
        emp2.setContractHireDate(null);

        employees.put(emp1.getUuid(), emp1);
        employees.put(emp2.getUuid(), emp2);
    }
}
