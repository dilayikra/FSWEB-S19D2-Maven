package com.workintech.s18d4.controller;

import com.workintech.s18d4.AccountResponse;
import com.workintech.s18d4.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workintech/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountResponse find(@PathVariable int id) {
        return convertToResponse(accountService.find(id));
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable int customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        return convertToResponse(accountService.save(account));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable int customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        return convertToResponse(accountService.save(account));
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable int id) {
        return convertToResponse(accountService.delete(id));
    }

    private AccountResponse convertToResponse(Account account) {
        Customer c = account.getCustomer();
        CustomerResponse customerResponse = null;
        if (c != null) {
            customerResponse = new CustomerResponse(
                    c.getId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getEmail(),
                    c.getSalary(),
                    c.getAddress() != null ? c.getAddress().getCity() : null
            );
        }
        return new AccountResponse(
                account.getId(),
                account.getAccountName(),
                account.getMoneyAmount(),
                customerResponse
        );
    }
}
