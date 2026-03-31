package com.workintech.s18d4.service;

import com.workintech.s18d4.repository.AddressRepository;
import com.workintech.s18d4.entity.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address find(int id) { // long -> int yapıldı
        Optional<Address> addressOptional = addressRepository.findById((long) id);
        if (addressOptional.isPresent()) {
            return addressOptional.get();
        }
        throw new RuntimeException("Address not found with id: " + id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(int id, Address address) { // long -> int yapıldı
        Address existingAddress = find(id);
        address.setId(id);
        return addressRepository.save(address);
    }

    @Override
    public Address delete(int id) { // long -> int yapıldı
        Address address = find(id);
        addressRepository.delete(address);
        return address;
    }
}