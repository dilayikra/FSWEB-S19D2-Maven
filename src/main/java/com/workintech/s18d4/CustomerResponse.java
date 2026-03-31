package com.workintech.s18d4;

public record CustomerResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Double salary,
        String address // Test muhtemelen bunu bekliyor
) {
}
