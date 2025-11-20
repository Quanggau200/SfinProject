package org.example.backend.persitence.repository;

import org.example.backend.persitence.entity.Payroll;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PayrollRepository extends CrudRepository<Payroll,Integer> {
    @EntityGraph(attributePaths = "employee")
    List<Payroll> findWithEmployeeByEmployee_EmployeeId(String employeeId);
}
