package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface IEmployeeService {


    List<EmployeeResponse> getAllEmployees();

    void addEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> findByDepartment(Long departmentId);

    List<EmployeeResponse> findByOrganization(Long organizationId);
}
