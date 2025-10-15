package com.wam.lab1_maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "person")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "fname", length = 25)
    private String fname;

    @Column(name = "lname", length = 25)
    private String lname;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "credential_id")
    private Credential credential;


    // pour setter le username
    public void setUsername(String username) {
        if (this.credential == null) {
            this.credential = Credential.builder()
                    .username(username)
                    .password(null)
                    .isActive(true)
                    .build();
        } else {
            this.credential.setUsername(username);
        }
    }

    // pour setter le mot de passe
    public void setPassword(String password) {
        if (this.credential == null) {
            this.credential = Credential.builder()
                    .username(null)
                    .password(password)
                    .isActive(true)
                    .build();
        } else {
            this.credential.setPassword(password);
        }
    }

    // pour setter les roles
    public void setRole(String roleName) {
        if (roleName == null) {
            this.role = null;
            return;
        }
        this.role = Role.valueOf(roleName.toUpperCase());
    }



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role != null ? List.of(new SimpleGrantedAuthority(this.role.name())) : List.of();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.credential != null ? this.credential.getPassword() : null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.credential != null ? this.credential.getUsername() : null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.credential != null && this.credential.getIsActive();
    }
}
