package com.bufalari.employee.service;


import com.bufalari.employee.convert.SubDepartmentConverter;
import com.bufalari.employee.dto.SubDepartmentDTO;
import com.bufalari.employee.entity.SubDepartmentEntity;
import com.bufalari.employee.repository.SubDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubDepartmentService {

    private final SubDepartmentRepository subDepartmentRepository;
    private final SubDepartmentConverter subDepartmentConverter;

    public SubDepartmentService(SubDepartmentRepository subDepartmentRepository, SubDepartmentConverter subDepartmentConverter) {
        this.subDepartmentRepository = subDepartmentRepository;
        this.subDepartmentConverter = subDepartmentConverter;
    }

    public SubDepartmentDTO createSubDepartment(SubDepartmentDTO subDepartmentDTO) {
        SubDepartmentEntity subDepartmentEntity = subDepartmentConverter.dtoToEntity(subDepartmentDTO);
        SubDepartmentEntity savedSubDepartment = subDepartmentRepository.save(subDepartmentEntity);
        return subDepartmentConverter.entityToDTO(savedSubDepartment);
    }

    public Optional<SubDepartmentDTO> getSubDepartmentById(Long id) {
        return subDepartmentRepository.findById(id).map(subDepartmentConverter::entityToDTO);
    }

    public List<SubDepartmentDTO> getAllSubDepartments() {
        return subDepartmentRepository.findAll().stream().map(subDepartmentConverter::entityToDTO).collect(Collectors.toList());
    }

    public SubDepartmentDTO updateSubDepartment(Long id, SubDepartmentDTO subDepartmentDTO) {
        if (!subDepartmentRepository.existsById(id)) {
            throw new IllegalArgumentException("SubDepartment not found");
        }
        SubDepartmentEntity subDepartmentEntity = subDepartmentConverter.dtoToEntity(subDepartmentDTO);
        subDepartmentEntity.setId(id);
        SubDepartmentEntity updatedSubDepartment = subDepartmentRepository.save(subDepartmentEntity);
        return subDepartmentConverter.entityToDTO(updatedSubDepartment);
    }

    public void deleteSubDepartment(Long id) {
        subDepartmentRepository.deleteById(id);
    }
}
