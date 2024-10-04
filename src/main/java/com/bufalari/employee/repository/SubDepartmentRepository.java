package com.bufalari.employee.repository;


import com.bufalari.employee.entity.SubDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDepartmentRepository extends JpaRepository<SubDepartmentEntity, Long> {
}
