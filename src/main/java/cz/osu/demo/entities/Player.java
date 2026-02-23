package cz.osu.demo.entities;

import jakarta.persistence.*;
//import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "currency")
    private Integer currency;

    @Column(name = "email", length = 100)
    private String email;

    public Player() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer age) {
        this.currency = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
