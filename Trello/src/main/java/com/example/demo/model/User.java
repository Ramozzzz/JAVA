package com.example.demo.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="s_user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rowId;
    @NotNull
    private String login;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private String middleName;
    @NotNull
    private String passwordHash;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "developer")
    private Set<Task> developerTask;

    @OneToMany(mappedBy = "analyst")
    private Set<Task> analystTask;

    @OneToMany(mappedBy = "tester")
    private Set<Task> testerTask;

}
