package com.emtechhouse.co.ke.book.management.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Authority {
    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long authorityId;
    @Column(name = "authority_name")
    private String authorityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    @JsonIgnore
    private Role role;
    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
