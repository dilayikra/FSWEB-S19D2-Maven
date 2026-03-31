package com.workintech.s18d4.controller;

import com.workintech.s18d4.AddressResponse;
import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workintech/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressResponse> findAll() {
        return addressService.findAll().stream()
                .map(a -> new AddressResponse(
                        a.getId(),
                        a.getStreet(),
                        a.getNo(),
                        a.getCity(),
                        a.getCountry(),
                        a.getDescription()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public AddressResponse find(@PathVariable int id) {
        Address a = addressService.find(id);
        return new AddressResponse(
                a.getId(),
                a.getStreet(),
                a.getNo(),
                a.getCity(),
                a.getCountry(),
                a.getDescription()
        );
    }

    @PostMapping
    public AddressResponse save(@RequestBody Address address) {
        Address saved = addressService.save(address);
        return new AddressResponse(
                saved.getId(),
                saved.getStreet(),
                saved.getNo(),
                saved.getCity(),
                saved.getCountry(),
                saved.getDescription()
        );
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable int id, @RequestBody Address address) {
        Address updated = addressService.update(id, address);
        return new AddressResponse(
                updated.getId(),
                updated.getStreet(),
                updated.getNo(),
                updated.getCity(),
                updated.getCountry(),
                updated.getDescription()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        addressService.delete(id);
    }
}
