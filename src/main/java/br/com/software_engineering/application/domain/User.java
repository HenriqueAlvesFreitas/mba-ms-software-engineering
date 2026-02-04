package br.com.software_engineering.application.domain;

import br.com.software_engineering.application.domain.valueObjects.Email;
import br.com.software_engineering.application.domain.valueObjects.EmailConverter;
import br.com.software_engineering.application.domain.valueObjects.Phone;
import br.com.software_engineering.application.domain.valueObjects.PhoneConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @Convert(converter = EmailConverter.class)
    @Column(unique = true)
    private Email email;

    @Convert(converter = PhoneConverter.class)
    @Column(unique = true)
    private Phone phone;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getEmail() {
        return email != null ? email.getValue() : null;
    }
    public String getPhone() {
        return phone != null ? phone.getValue() : null;
    }
}