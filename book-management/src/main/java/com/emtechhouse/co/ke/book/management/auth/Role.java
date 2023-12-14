package com.emtechhouse.co.ke.book.management.auth;

import com.emtechhouse.co.ke.book.management.security.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},
            mappedBy = "role")
    private Set<Authority> authorities = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId")
    @JsonIgnore
    private UserAccount userAccount;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role addAuthorities(Set<Authority> authorities) {
        for (Authority authority : authorities) {
            if (authority != null) {
                this.authorities.add(authority);
                authority.setRole(this);
            }
        }
        return this;
    }
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> grantedAuthorities = this.authorities
                .stream()
                .map(authority ->
                        new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(
                new SimpleGrantedAuthority("ROLE_"+this.roleName));
        return grantedAuthorities;
    }
}