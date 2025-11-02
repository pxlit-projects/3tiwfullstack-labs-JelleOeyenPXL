package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService{

    private final DepartmentRepository departmentRepository;

    private DepartmentResponse mapToDepartmentResponse(Department department){
        return DepartmentResponse.builder()
                .name(department.getName())
                .organizationId(department.getOrganizationId())
                .position(department.getPosition())
                .employees(department.getEmployees())
                .build();
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
            Department department = Department.builder()
                    .name(departmentRequest.getName())
                    .organizationId(departmentRequest.getOrganizationId())
                    .position(departmentRequest.getPosition())
                    .employees(departmentRequest.getEmployees())
                    .build();
            departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::mapToDepartmentResponse).orElseThrow();
    }

    @Override
    public List<DepartmentResponse> getDepartmentByOrganizationId(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentByOrganizationIdWithEmployees(Long organizationId) {
        return departmentRepository.findByOrganizationIdWithEmployees(organizationId).stream().map(this::mapToDepartmentResponse).toList();
    }


}
