package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService{

    private final OrganizationRepository organizationRepository;

    private OrganizationResponse mapToOrganizationResponse(Organization organization){
        return OrganizationResponse.builder()
                .name(organization.getName())
                .id(organization.getId())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .departments(organization.getDepartments())
                .build();
    }

}
