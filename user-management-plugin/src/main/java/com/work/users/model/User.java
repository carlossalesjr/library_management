package com.work.users.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // O nome da tabela no banco de dados
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt = LocalDateTime.now();

    // Construtor vazio é necessário para o Hibernate
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Crie os Getters e Setters para todos os campos...
    public int getId() { return user_id; }
    public void setId(int user_id) { this.user_id = user_id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
}