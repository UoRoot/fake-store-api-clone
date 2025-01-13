package com.fakestoreapi.clone.application.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSaveDTO {

    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
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
        @JsonProperty("long")
        private String lon;
    }
}