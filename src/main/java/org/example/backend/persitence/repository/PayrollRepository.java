package org.example.backend.persitence.repository;

import jakarta.validation.constraints.NotBlank;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.Payroll;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, UUID> {

//    @Query("SELECT p.employee.employeeId, p.bonus FROM Payroll p WHERE p.employee.employeeId = :employeeId")
//    List<Payroll> findPayrollByEmployeeId(@Param("employeeId") String employeeId);
//
//    List<Payroll> id(UUID id);
}
