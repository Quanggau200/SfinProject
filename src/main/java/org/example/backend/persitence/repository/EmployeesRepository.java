package org.example.backend.persitence.repository;

import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.Payroll;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, UUID> {

    boolean existsByEmail(String email);
    boolean existsByFullName(String fullName);
    boolean existsByPhone(String phone);



    Optional<Employees> findByEmployeeId(String employeeId);
    @EntityGraph(attributePaths = "payroll")
    @Query("""
            SELECT new org.example.backend.dto.SpecificationDTO.EmployeePayrollDTO(
                e.employeeId,
                e.fullName,
                e.role_company,
                p.basicSalary,
                p.bonus
            )
            FROM Employees e
            LEFT JOIN e.payroll p
            WHERE e.employeeId = :employeeId
            """)
    List<Employees> findByEmployeeIdWithPayrolls(@Param("employeeId")String employeeId);
}