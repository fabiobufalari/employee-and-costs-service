package com.bufalari.employee.service;

import com.bufalari.employee.entity.CompanyEntity;
import com.bufalari.employee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Optional<CompanyEntity> findCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public CompanyEntity save(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyEntity findByIdOrThrow(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
}
