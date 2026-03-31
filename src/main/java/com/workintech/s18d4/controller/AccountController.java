package com.workintech.s18d4.controller;

import com.workintech.s18d4.AccountResponse;
import com.workintech.s18d4.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workintech/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping
    public List<AccountResponse> findAll() {
        List<Account> accounts = accountService.findAll();
        List<AccountResponse> responses = new ArrayList<>();
        for (Account a : accounts) {
            responses.add(convertToResponse(a));
        }
        return responses;
    }

    @GetMapping("/{id}")
    public AccountResponse find(@PathVariable int id) { // int uyumu sağlandı
        Account account = accountService.find(id);
        return convertToResponse(account);
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable int customerId, @RequestBody Account account) { // int uyumu sağlandı
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        Account savedAccount = accountService.save(account);
        return convertToResponse(savedAccount);
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable int customerId, @RequestBody Account account) { // int uyumu sağlandı
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        Account updatedAccount = accountService.save(account);
        return convertToResponse(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable int id) { // BURASI: long -> int yapıldı
        Account deleted = accountService.delete(id);
        return convertToResponse(deleted);
    }

    // Entity -> Record dönüşümü (Hatalar burada düzeltildi)
    private AccountResponse convertToResponse(Account account) {
        Customer c = account.getCustomer();
        CustomerResponse customerResponse = null;
        if (c != null) {
            // CustomerResponse artık 6 parametre bekliyor (Address eklendi)
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
