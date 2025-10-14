package be.pxl.services.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Employee {
    private Long Id;
    private Long organisationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

}
