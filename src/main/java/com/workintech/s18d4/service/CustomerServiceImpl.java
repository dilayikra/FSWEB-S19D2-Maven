package com.workintech.s18d4.service;

import com.workintech.s18d4.repository.CustomerRepository;
import com.workintech.s18d4.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer find(int id) { // long -> int yapıldı
        Optional<Customer> customerOptional = customerRepository.findById((long) id);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }
        throw new RuntimeException("Customer not found with id: " + id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(int id, Customer customer) { // long -> int yapıldı
        Customer existingCustomer = find(id);
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public Customer delete(int id) { // long -> int yapıldı
        Customer customer = find(id);
        customerRepository.delete(customer);
        return customer;
    }
}
