package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username",sqlContainer::getUsername);
        registry.add("spring.datasource.password",sqlContainer::getPassword);
    }

    @BeforeEach
    void cleanDatabase() {
        employeeRepository.deleteAll();
    }
    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("jan")
                .position("student")
                .build();

        String employeeString = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1, employeeRepository.findAll().size());



    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee emp1 = employeeRepository.save(Employee.builder()
                .age(30).name("Alice").position("Developer")
                .departmentId(1L).organizationId(1L).build());
        Employee emp2 = employeeRepository.save(Employee.builder()
                .age(28).name("Bob").position("Tester")
                .departmentId(2L).organizationId(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    public void testFindEmployeeById() throws Exception {
        Employee emp = employeeRepository.save(Employee.builder()
                .age(35).name("Charlie").position("Manager")
                .departmentId(3L).organizationId(2L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + emp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.position").value("Manager"));
    }

    @Test
    public void testFindEmployeesByDepartment() throws Exception {
        Employee emp1 = employeeRepository.save(Employee.builder()
                .age(40).name("Diana").position("HR")
                .departmentId(10L).organizationId(1L).build());
        Employee emp2 = employeeRepository.save(Employee.builder()
                .age(22).name("Eve").position("Intern")
                .departmentId(10L).organizationId(1L).build());
        employeeRepository.save(Employee.builder()
                .age(29).name("Frank").position("Engineer")
                .departmentId(11L).organizationId(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].departmentId").value(10))
                .andExpect(jsonPath("$[1].departmentId").value(10));
    }

    @Test
    public void testFindEmployeesByOrganization() throws Exception {
        Employee emp1 = employeeRepository.save(Employee.builder()
                .age(27).name("George").position("Analyst")
                .departmentId(1L).organizationId(100L).build());
        Employee emp2 = employeeRepository.save(Employee.builder()
                .age(33).name("Helen").position("Consultant")
                .departmentId(2L).organizationId(100L).build());
        employeeRepository.save(Employee.builder()
                .age(45).name("Ian").position("Architect")
                .departmentId(3L).organizationId(101L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].organizationId").value(100))
                .andExpect(jsonPath("$[1].organizationId").value(100));
    }


}
