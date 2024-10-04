package com.bufalari.employee.controller;

import com.bufalari.employee.entity.CompanyEntity;
import com.bufalari.employee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyEntity> getCompanyById(@PathVariable Long id) {
        Optional<CompanyEntity> company = companyService.findCompanyById(id);
        // If company exists, return it; otherwise, return 404
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompanyEntity createCompany(@RequestBody CompanyEntity companyEntity) {
        return companyService.save(companyEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable Long id) {
        Optional<CompanyEntity> company = companyService.findCompanyById(id);
        if (company.isPresent()) {
            companyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
