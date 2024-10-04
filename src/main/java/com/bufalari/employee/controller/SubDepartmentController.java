package com.bufalari.employee.controller;


import com.bufalari.employee.dto.SubDepartmentDTO;
import com.bufalari.employee.service.SubDepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-departments")
public class SubDepartmentController {

    private final SubDepartmentService subDepartmentService;

    public SubDepartmentController(SubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }

    @PostMapping
    public ResponseEntity<SubDepartmentDTO> createSubDepartment(@RequestBody SubDepartmentDTO subDepartmentDTO) {
        SubDepartmentDTO createdSubDepartment = subDepartmentService.createSubDepartment(subDepartmentDTO);
        return ResponseEntity.ok(createdSubDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubDepartmentDTO> getSubDepartmentById(@PathVariable Long id) {
        return subDepartmentService.getSubDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SubDepartmentDTO>> getAllSubDepartments() {
        List<SubDepartmentDTO> subDepartments = subDepartmentService.getAllSubDepartments();
        return ResponseEntity.ok(subDepartments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubDepartmentDTO> updateSubDepartment(@PathVariable Long id, @RequestBody SubDepartmentDTO subDepartmentDTO) {
        SubDepartmentDTO updatedSubDepartment = subDepartmentService.updateSubDepartment(id, subDepartmentDTO);
        return ResponseEntity.ok(updatedSubDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubDepartment(@PathVariable Long id) {
        subDepartmentService.deleteSubDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
