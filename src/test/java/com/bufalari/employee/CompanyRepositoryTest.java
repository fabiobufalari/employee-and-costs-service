package com.bufalari.employee;

import com.bufalari.employee.entity.CompanyEntity;
import com.bufalari.employee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void testSaveAndFindCompany() {
        CompanyEntity company = new CompanyEntity("Bufalari Corp", "123 Main St", "Halifax", "Canada");

        // Salva a CompanyEntity, persistindo automaticamente o endereço associado.
        company = companyRepository.save(company);

        CompanyEntity foundCompany = companyRepository.findById(company.getId()).orElse(null);

        assertThat(foundCompany).isNotNull();
        assertThat(foundCompany.getName()).isEqualTo(company.getName());
    }


    @Test
    void testDeleteCompany() {
        CompanyEntity company = new CompanyEntity("Bufalari Corp", "123 Main St", "Halifax", "Canada");

        // Salva a CompanyEntity, persistindo automaticamente o endereço associado.
        company = companyRepository.save(company);

        companyRepository.deleteById(company.getId());

        // Verifica se a empresa e o endereço foram excluídos.
        assertThat(companyRepository.findById(company.getId()).isEmpty()).isTrue();
    }
}
