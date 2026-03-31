package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Address;
import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address find(int id);
    Address save(Address address);
    Address update(int id, Address address);
    Address delete(int id);
}
