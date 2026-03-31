package com.workintech.s18d4.controller;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workintech/address")
@AllArgsConstructor
public class AddressController { // Sınıf adı dosya adıyla aynı yapıldı

    private final AddressService addressService;

    @GetMapping
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Address find(@PathVariable int id) { // int uyumu sağlandı
        return addressService.find(id);
    }

    @PostMapping
    public Address save(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable int id, @RequestBody Address address) { // int uyumu sağlandı
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public Address delete(@PathVariable int id) { // int uyumu sağlandı
        return addressService.delete(id);
    }
}
