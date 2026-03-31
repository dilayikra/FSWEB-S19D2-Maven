package com.workintech.s18d4;

import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.repository.AccountRepository;
import com.workintech.s18d4.repository.AddressRepository;
import com.workintech.s18d4.repository.CustomerRepository;
import com.workintech.s18d4.service.AccountServiceImpl;
import com.workintech.s18d4.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MainTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private CustomerRepository mockCustomerRepository;

    private AccountServiceImpl accountService;
    private CustomerServiceImpl customerService;

    // Test verileri
    private Address sampleAddress;
    private Customer sampleCustomer;
    private Account sampleAccount;

    private Account sampleAccountForService;
    private Customer sampleCustomerForService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // --- 1. ENTITY TESTLERİ İÇİN SETUP (ID SETLEMİYORUZ!) ---
        sampleAddress = new Address();
        sampleAddress.setStreet("Main Street");
        sampleAddress.setNo(100);
        sampleAddress.setCity("Bursa");
        sampleAddress.setCountry("Turkey");

        sampleCustomer = new Customer();
        sampleCustomer.setFirstName("Dilay");
        sampleCustomer.setLastName("Ikra");
        sampleCustomer.setEmail("dilay@test.com");
        sampleCustomer.setSalary(5000.0);
        sampleCustomer.setAddress(sampleAddress);

        sampleAccount = new Account();
        sampleAccount.setAccountName("Savings");
        sampleAccount.setMoneyAmount(1500.0);
        sampleAccount.setCustomer(sampleCustomer);

        // --- 2. REPOSITORY TESTLERİ İÇİN VERİTABANINA KAYIT ---
        // Detached entity hatasını önlemek için sırayla persist ediyoruz
        entityManager.persist(sampleAddress);
        entityManager.persist(sampleCustomer);
        entityManager.persist(sampleAccount);
        entityManager.flush();

        // --- 3. SERVICE TESTLERİ İÇİN MOCK SETUP ---
        sampleAccountForService = new Account();
        sampleAccountForService.setId(1); // Mock için manuel ID verebiliriz (DB'ye gitmiyor)
        sampleAccountForService.setAccountName("Service Account");

        sampleCustomerForService = new Customer();
        sampleCustomerForService.setId(1);
        sampleCustomerForService.setFirstName("Mock");

        accountService = new AccountServiceImpl(mockAccountRepository);
        customerService = new CustomerServiceImpl(mockCustomerRepository);
    }

    @Test
    @DisplayName("Account Entity Test")
    void testAccountProperties() {
        assertNotNull(sampleAccount.getId()); // ID veritabanı tarafından verildi
        assertEquals("Savings", sampleAccount.getAccountName());
    }

    @Test
    @DisplayName("Address Entity Test")
    void testAddressProperties() {
        assertNotNull(sampleAddress.getId());
        assertEquals("Bursa", sampleAddress.getCity());
    }

    @Test
    @DisplayName("AccountService::find")
    void testFindAccount_AccountService() {
        when(mockAccountRepository.findById(any())).thenReturn(Optional.of(sampleAccountForService));
        Account result = accountService.find(1);
        assertNotNull(result);
        assertEquals("Service Account", result.getAccountName());
    }

    @Test
    @DisplayName("CustomerService::find")
    void testFindCustomer() {
        when(mockCustomerRepository.findById(any())).thenReturn(Optional.of(sampleCustomerForService));
        Customer result = customerService.find(1);
        assertNotNull(result);
        assertEquals("Mock", result.getFirstName());
    }

    @Test
    @DisplayName("CustomerService::delete")
    void testDeleteCustomerService() {
        when(mockCustomerRepository.findById(any())).thenReturn(Optional.of(sampleCustomerForService));
        doNothing().when(mockCustomerRepository).delete(sampleCustomerForService);
        Customer deletedCustomer = customerService.delete(1);
        assertNotNull(deletedCustomer);
        verify(mockCustomerRepository, times(1)).delete(sampleCustomerForService);
    }
 }
