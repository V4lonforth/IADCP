package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.AccessLevel;
import com.iad.courseProject.data.entities.types.AccountStatus;
import com.sun.istack.internal.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="RegisteredUser")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private AccessLevel accessLevel;

    @Column(nullable = false)
    private AccountStatus accountStatus;

    @Column(nullable = false)
    private boolean receivingNotifications;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Region> regions;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isReceivingNotifications() {
        return receivingNotifications;
    }
    public void setReceivingNotifications(boolean receivingNotifications) {
        this.receivingNotifications = receivingNotifications;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Region> getRegions() {
        return regions;
    }
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }
    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String access : accessLevel.getLevels()) {
            authorities.add(new SimpleGrantedAuthority(access));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String email, String password, AccessLevel accessLevel, AccountStatus accountStatus) {
        this.username = username;
        this.email = email;
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.accessLevel = accessLevel;
        this.accountStatus = accountStatus;
        receivingNotifications = false;
    }

    public User() {
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
