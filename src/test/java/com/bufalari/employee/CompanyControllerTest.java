package com.bufalari.employee;

import com.bufalari.employee.entity.CompanyEntity;
import com.bufalari.employee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void testGetCompanyById() throws Exception {
        CompanyEntity company = new CompanyEntity();
        company.setId(1L);
        company.setName("Company 1");

        // Mock the service call
        when(companyService.findCompanyById(1L)).thenReturn(Optional.of(company));

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company 1"));
    }

    @Test
    public void testGetCompanyById_NotFound() throws Exception {
        // Mock the service call with an empty optional
        when(companyService.findCompanyById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isNotFound());
    }
}
