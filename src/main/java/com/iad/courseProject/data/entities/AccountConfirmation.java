package com.iad.courseProject.data.entities;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class AccountConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String confirmationId;

    @Column(nullable = false)
    private Timestamp creationDate;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmationId() {
        return confirmationId;
    }
    public void setConfirmationId(String confirmationId) {
        this.confirmationId = confirmationId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public AccountConfirmation(User user, String confirmationId, Timestamp creationDate) {
        this.user = user;
        this.confirmationId = confirmationId;
        this.creationDate = creationDate;
    }

    public AccountConfirmation() {
    }

}
