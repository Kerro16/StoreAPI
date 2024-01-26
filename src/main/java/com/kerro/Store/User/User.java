package com.kerro.Store.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")

    private long id;

    @Column(name = "username", length = 255, nullable = false)
    String username;

    @Column(name = "password", length = 255, nullable = false)
    String password;

    @Column(name = "firstname", length = 255, nullable = false)
    String firstname;

    @Column(name = "lastname", length = 255, nullable = false)
    String lastname;

    @Column(name = "address", length = 255, nullable = true)
    String address;

    public User(long id, String username, String password, String firstname, String lastname, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public User(String username, String password, String firstname, String lastname, String address) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

}
