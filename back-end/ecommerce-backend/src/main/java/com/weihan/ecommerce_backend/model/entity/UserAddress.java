package com.weihan.ecommerce_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "receiver", nullable = false, length = 20)
    private String receiver;

    @Column(name = "contact", nullable = false, length = 20)
    private String contact;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_default", nullable = false)
    private Byte isDefault;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}