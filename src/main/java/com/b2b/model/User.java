package com.b2b.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import com.b2b.model.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your first name")
    private String name;

    @Column(name = "contact")
    @NotEmpty(message = "*Please provide your contact")
    private String contact;

    @Column(name = "address")
    @NotEmpty(message = "*Please enter your address")
    private String address;

    @Column(name = "capabilities")
    @NotEmpty(message = "*Please enter your capabilities")
    private String capabilities;

    @Column(name = "description")
    @NotEmpty(message = "*Please enter your description")
    private String description;

    @Column(name = "website")
    @NotEmpty(message = "*Please enter your website url")
    private String website;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @Column(name = "active")
    private Boolean active;
}