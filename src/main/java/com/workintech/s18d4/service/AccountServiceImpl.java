package com.workintech.s18d4.service;

import com.workintech.s18d4.repository.AccountRepository;
import com.workintech.s18d4.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account find(int id) { // long -> int yapıldı
        Optional<Account> accountOptional = accountRepository.findById((long) id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        }
        throw new RuntimeException("Account not found with id: " + id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(int id, Account account) { // long -> int yapıldı
        Account existingAccount = find(id);
        account.setId(id);
        return accountRepository.save(account);
    }

    @Override
    public Account delete(int id) { // long -> int yapıldı
        Account account = find(id);
        accountRepository.delete(account);
        return account;
    }
}