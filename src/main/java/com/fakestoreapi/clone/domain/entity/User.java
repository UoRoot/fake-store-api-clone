package com.fakestoreapi.clone.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;

    @Data
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @Data
    public static class Address {
        private String city;
        private String street;
        private Integer number;
        private String zipcode;
        private Geolocation geolocation;
    }

    @Data
    public static class Geolocation {
        private String lat;
        private String lon;
    }

}
