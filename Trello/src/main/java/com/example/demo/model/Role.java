package com.example.demo.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="s_role")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rowId;
    @NotNull
    private String name;


    @ManyToMany(fetch=FetchType.EAGER,mappedBy = "roles")
    private Set<User> users;
}
