package com.jpmc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USERS")
public class User {

    @Id
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", initialValue = 10, allocationSize = 1)
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

}
