package com.bufalari.employee;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bufalari.employee.entity.CompanyEntity;
import com.bufalari.employee.repository.CompanyRepository;
import com.bufalari.employee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void testFindCompanyById_Success() {
        CompanyEntity company = new CompanyEntity();
        company.setId(1L);
        company.setName("Test Company");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Aqui você precisa extrair o valor do Optional
        Optional<CompanyEntity> optionalCompany = companyService.findCompanyById(1L);

        // Verificando se o Optional contém um valor antes de continuar
        assertTrue(optionalCompany.isPresent());
        CompanyEntity foundCompany = optionalCompany.get();

        assertNotNull(foundCompany);
        assertEquals("Test Company", foundCompany.getName());
    }


    @Test
    public void testFindCompanyById_NotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            companyService.findCompanyById(1L);
        });
    }


    public CompanyEntity findCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

}
