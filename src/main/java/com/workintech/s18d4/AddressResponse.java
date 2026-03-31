package com.workintech.s18d4;

public record AddressResponse(
        Integer id,
        String street,
        Integer no,
        String city,
        String country,
        String description
) {
}