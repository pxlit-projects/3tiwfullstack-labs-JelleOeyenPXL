package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Embeddable
public class Department {


    private Long id;

    private Long organizationId;
    private String name;
    private List<Employee> employees;
    private String position;


}
