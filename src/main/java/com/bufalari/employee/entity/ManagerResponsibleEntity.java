package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não mais necessário

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "managers_responsibles", indexes = {
    @Index(name = "idx_mgr_resp_code", columnList = "code", unique = true),
    @Index(name = "idx_mgr_resp_email", columnList = "email", unique = true)
})
public class ManagerResponsibleEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <<< ESTRATÉGIA UUID
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 50, unique = true, nullable = false) // Código não pode ser nulo
    private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String position;

    @Column(length = 50)
    private String phone;

    @Column(length = 255, unique = true, nullable = false) // Email não pode ser nulo
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerResponsibleEntity that = (ManagerResponsibleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}