package com.fakestoreapi.clone.application.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSaveDTO {

    @NotBlank(message = "The email is mandatory")
    @Email(message = "The email must be a valid email address")
    private String email;

    @NotBlank(message = "The username is mandatory")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "The password is mandatory")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "The password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    @NotNull(message = "The name is mandatory")
    private Name name;

    @NotNull(message = "The address is mandatory")
    private Address address;

    @NotBlank(message = "The phone number is mandatory")
    @Pattern(
        regexp = "^\\+?[1-9]\\d{1,14}$",
        message = "The phone number must be a valid international phone number"
    )
    private String phone;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Name {
        @NotBlank(message = "The first name is mandatory")
        @Size(max = 50, message = "The first name must not exceed 50 characters")
        private String firstname;

        @NotBlank(message = "The last name is mandatory")
        @Size(max = 50, message = "The last name must not exceed 50 characters")
        private String lastname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address {
        @NotBlank(message = "The city is mandatory")
        private String city;

        @NotBlank(message = "The street is mandatory")
        private String street;

        @NotNull(message = "The house number is mandatory")
        @Min(value = 1, message = "The house number must be greater than 0")
        private Integer number;

        @NotBlank(message = "The zip code is mandatory")
        @Pattern(
            regexp = "^\\d{5}(-\\d{4})?$",
            message = "The zip code must be a valid format (e.g., 12345 or 12345-6789)"
        )
        private String zipcode;

        @NotNull(message = "The geolocation is mandatory")
        private Geolocation geolocation;
    }

    @Data
    public static class Geolocation {
        @NotBlank(message = "The latitude is mandatory")
        @Pattern(
            regexp = "^-?\\d{1,2}\\.\\d+$",
            message = "The latitude must be a valid coordinate (e.g., -90.0 to 90.0)"
        )
        private String lat;

        @NotBlank(message = "The longitude is mandatory")
        @Pattern(
            regexp = "^-?\\d{1,3}\\.\\d+$",
            message = "The longitude must be a valid coordinate (e.g., -180.0 to 180.0)"
        )
        @JsonProperty("long")
        private String lon;
    }
}