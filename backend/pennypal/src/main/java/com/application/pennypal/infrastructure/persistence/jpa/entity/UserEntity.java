package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.user.valueObject.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    private String userId;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(length = 10)
    @Setter
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    /// As I defined role using Set.of() method I can't edit the roles. No setter here.
    private Set<Roles> roles;

    @Column(nullable = false)
    @Setter
    private boolean active;

    @Column(nullable = false)
    @Setter
    private boolean verified;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column()
    @Setter
    private String profileURL;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    /// constructor
    public UserEntity(
            String userId,
            String name,
            String email,
            String password,
            String phone,
            Set<Roles> roles,
            boolean active,
            boolean verified,
            String profileURL
            ){
        this.userId = userId;
        this.name = name;
        this.email =email;
        this.password = password;
        this.phone = phone;
        this.roles = roles;
        this.active = active;
        this.verified = verified;
        this.profileURL= profileURL;
    }

    //Relations defined

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecurringTransactionLogEntity> recurringLogs;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    private List<GoalEntity> goals;
}
