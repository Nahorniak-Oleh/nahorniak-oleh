package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.Role;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.UserStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(
        name = "User.countAllByRole",
        query = "SELECT COUNT(u) " +
                "FROM User u " +
                "WHERE u.role= ?1"
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String street;

    private String country;

    private String city;

    private double balance;

}
