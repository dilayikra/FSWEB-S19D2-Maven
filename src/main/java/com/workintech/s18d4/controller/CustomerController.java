package com.workintech.s18d4.controller;

import com.workintech.s18d4.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workintech/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerResponse> responseList = new ArrayList<>();
        for (Customer c : customers) {
            // CustomerResponse'a 6. parametre (address) eklendi
            responseList.add(new CustomerResponse(c.getId(), c.getFirstName(), c.getLastName(),
                    c.getEmail(), c.getSalary(),
                    c.getAddress() != null ? c.getAddress().getCity() : null));
        }
        return responseList;
    }

    @GetMapping("/{id}")
    public CustomerResponse find(@PathVariable int id) { // int uyumu sağlandı
        Customer c = customerService.find(id);
        return new CustomerResponse(c.getId(), c.getFirstName(), c.getLastName(),
                c.getEmail(), c.getSalary(),
                c.getAddress() != null ? c.getAddress().getCity() : null);
    }

    @PostMapping
    public CustomerResponse save(@RequestBody Customer customer) {
        Customer saved = customerService.save(customer);
        return new CustomerResponse(saved.getId(), saved.getFirstName(), saved.getLastName(),
                saved.getEmail(), saved.getSalary(),
                saved.getAddress() != null ? saved.getAddress().getCity() : null);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable int id, @RequestBody Customer customer) { // int uyumu sağlandı
        Customer updated = customerService.update(id, customer);
        return new CustomerResponse(updated.getId(), updated.getFirstName(), updated.getLastName(),
                updated.getEmail(), updated.getSalary(),
                updated.getAddress() != null ? updated.getAddress().getCity() : null);
    }

    @DeleteMapping("/{id}")
    public CustomerResponse delete(@PathVariable int id) { // int uyumu sağlandı
        Customer deleted = customerService.delete(id);
        return new CustomerResponse(deleted.getId(), deleted.getFirstName(), deleted.getLastName(),
                deleted.getEmail(), deleted.getSalary(),
                deleted.getAddress() != null ? deleted.getAddress().getCity() : null);
    }
}