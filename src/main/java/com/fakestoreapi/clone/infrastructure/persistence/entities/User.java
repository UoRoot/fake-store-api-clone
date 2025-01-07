package com.fakestoreapi.clone.infrastructure.persistence.entities;

import com.fakestoreapi.clone.infrastructure.persistence.entities.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Name {
        @Column(name = "firstname", nullable = false, length = 50)
        private String firstname;

        @Column(name = "lastname", nullable = false, length = 50)
        private String lastname;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        @Column(name = "city", length = 100)
        private String city;

        @Column(name = "street", length = 200)
        private String street;

        @Column(name = "number")
        private Integer number;

        @Column(name = "zipcode", length = 10)
        private String zipcode;

        @Embedded
        private Geolocation geolocation;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Geolocation {
        @Column(name = "latitude", length = 20)
        private String lat;

        @Column(name = "longitude", length = 20)
        private String lon;
    }
}
