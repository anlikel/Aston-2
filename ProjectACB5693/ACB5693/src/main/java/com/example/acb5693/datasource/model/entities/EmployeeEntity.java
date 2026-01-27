package com.example.acb5693.datasource.model.entities;

import static com.example.acb5693.datasource.model.util.RegExp.PHONE_NUMBER_PATTERN;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Column(name = "first_name", length = 40, nullable = false)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    @NotBlank
    private String lastName;

    @Column(name = "middle_name", length = 40)
    private String middleName;

    @Column(
        name = "phone_number",
        length = 12,
        nullable = false,
        unique = true,
        columnDefinition = "bpchar(12)")
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "Некорректный формат номера телефона")
    @NotBlank
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    @NotNull
    @Past
    private LocalDate birthDate;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    @NotNull
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}

