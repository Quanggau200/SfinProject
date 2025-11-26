package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.PayrollDto;
import org.example.backend.dto.reponse.EmployeeReponse;
import org.example.backend.dto.reponse.Payrollreponse;
import org.example.backend.dto.request.PayrollRequest;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.Payroll;
import org.example.backend.persitence.repository.EmployeesRepository;
import org.example.backend.persitence.repository.PayrollRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("sfinvietnam/employees/payroll")
public class PayrollService {
//    private final PayrollRepository payrollRepository;
//    private final ResourceTransactionManager resourceTransactionManager;
//    private final EmployeesRepository employeesRepository;
//
//
//    public Page<PayrollDto> getAllPayrolls(Pageable pageable){
//        Page<Payroll> payrollPage= payrollRepository.findAll(pageable);
//        return payrollPage.map(payroll -> PayrollDto.builder()
//                .id(payroll.getId())
//                .bonus(payroll.getBonus())
//                .basic_salary(payroll.getBasicSalary())
//                .employeeId(payroll.getEmployee().getEmployeeId())
//                .employeeName(payroll.getStatus())
//                .build());
//    }
//
//    public Payrollreponse createPayrollForEmployee(PayrollRequest payrollRequest){
//        log.info("in method createPayrollForEmployee");
//        if(payrollRequest==null)
//        {
//            throw new IllegalArgumentException("payrollRequest is null");
//        }
//        if(payrollRepository.findPayrollByEmployeeId(payrollRequest.getEmployeeId()).isEmpty())
//        {
//            throw new IllegalArgumentException("employee_id is exits");
//        }
//        Employees emp=employeesRepository.findByEmployeeId(payrollRequest.getEmployeeId())
//                .orElseThrow(()->new IllegalArgumentException("employee_id not exits"));
//        Payroll payroll= Payroll.builder().
//                employee(emp)
//                .basicSalary(payrollRequest.getSalary())
//                .bonus(payrollRequest.getBonus())
//        .build();
//        payrollRepository.save(payroll);
//        Payrollreponse response= new Payrollreponse();
//        response.setId(payroll.getId());
//        response.setBonus(payroll.getBonus());
//        response.setSalary(payroll.getBasicSalary());
//        response.setStatus(payroll.getStatus());
//        return response;
//    }

}
