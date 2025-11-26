package org.example.backend.persitence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="employees")
@Entity
public class Employees {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String employeeId;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Payroll> payroll =new ArrayList<>();

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(length = 15)
    private String phone;

    @Column(length=100,nullable = false)
    private String email;
    @Column(length=100)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank
    @Column(length = 100,nullable = false)
    private String tax;

    @Column(name = "date_of_birth")
    private LocalDate dataBirth;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(length = 50)
    private String position;
    @Column(length = 100)
    private String role_company;
    @Column(length = 100)
    @NotBlank
    private String bankinfor;
    @Column(length = 50)
    private String department;
    @Column(length = 100,nullable = false)
    private Integer salary;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 15)
    private String emergencyContactPhone;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt = LocalDate.now();


    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }

    public enum Gender {
        male,
        female,
        other
    }
}
