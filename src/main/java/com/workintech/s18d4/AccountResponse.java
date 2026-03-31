package com.workintech.s18d4;

public record AccountResponse(
        Integer id,
        String accountName,
        Double moneyAmount,
        CustomerResponse customerResponse
) {
}
