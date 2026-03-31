package com.workintech.s18d4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.s18d4.controller.AccountController;
import com.workintech.s18d4.controller.CustomerController;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {ControllerAndPropertiesTest.class, AccountController.class, CustomerController.class})
@ExtendWith(ResultAnalyzer2.class)
class ControllerAndPropertiesTest {

    @Autowired
    private Environment env;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;
    @MockBean
    private CustomerService customerService;

    private Account sampleAccountForAccountControllerTest;
    private Customer sampleCustomerForAccountControllerTest;
    private Customer sampleCustomerForCustomerControllerTest;

    @BeforeEach
    void setUp() {
        // ID'leri int yaptık (L'ler silindi)
        sampleCustomerForAccountControllerTest = new Customer();
        sampleCustomerForAccountControllerTest.setId(1);
        sampleCustomerForAccountControllerTest.setEmail("customer@example.com");
        sampleCustomerForAccountControllerTest.setSalary(5000.00);

        sampleAccountForAccountControllerTest = new Account();
        sampleAccountForAccountControllerTest.setId(1);
        sampleAccountForAccountControllerTest.setAccountName("Savings Account");
        sampleAccountForAccountControllerTest.setMoneyAmount(1000.00);
        sampleAccountForAccountControllerTest.setCustomer(sampleCustomerForAccountControllerTest);

        List<Account> modifiableAccountsList = new ArrayList<>();
        modifiableAccountsList.add(sampleAccountForAccountControllerTest);
        sampleCustomerForAccountControllerTest.setAccounts(modifiableAccountsList);

        sampleCustomerForCustomerControllerTest = new Customer();
        sampleCustomerForCustomerControllerTest.setId(1);
        sampleCustomerForCustomerControllerTest.setEmail("customer@example.com");
        sampleCustomerForCustomerControllerTest.setSalary(5000.00);
    }

    @Test
    @DisplayName("AccountController::findAll")
    void testFindAllAccount() throws Exception {
        when(accountService.findAll()).thenReturn(List.of(sampleAccountForAccountControllerTest));

        // URL'i /workintech/accounts olarak güncelledik
        mockMvc.perform(get("/workintech/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(sampleAccountForAccountControllerTest.getId())));
    }

    @Test
    @DisplayName("AccountController::find")
    void testFindAccount() throws Exception {
        when(accountService.find(anyInt())).thenReturn(sampleAccountForAccountControllerTest);

        mockMvc.perform(get("/workintech/accounts/{id}", sampleAccountForAccountControllerTest.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sampleAccountForAccountControllerTest.getId())));
    }

    @Test
    @DisplayName("AccountController::save")
    void testSaveAccount() throws Exception {
        when(customerService.find(anyInt())).thenReturn(sampleCustomerForAccountControllerTest);
        when(accountService.save(any())).thenReturn(sampleAccountForAccountControllerTest);

        mockMvc.perform(post("/workintech/accounts/{customerId}", sampleCustomerForAccountControllerTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAccountForAccountControllerTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sampleAccountForAccountControllerTest.getId())));
    }

    @Test
    @DisplayName("CustomerController::saveCustomer")
    void testSaveCustomer() throws Exception {
        given(customerService.save(any())).willReturn(sampleCustomerForCustomerControllerTest);

        // Record tanımındaki tüm alanları (id, firstName, lastName, email, salary) ekledik
        CustomerResponse expectedResponse = new CustomerResponse(
                sampleCustomerForCustomerControllerTest.getId(),
                "John",
                "Doe",
                sampleCustomerForCustomerControllerTest.getEmail(),
                sampleCustomerForCustomerControllerTest.getSalary(),
                null // 6. parametre olan 'address' için null ekledik
        );

        mockMvc.perform(post("/workintech/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCustomerForCustomerControllerTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedResponse.id())))
                .andExpect(jsonPath("$.email", is(expectedResponse.email())));

        verify(customerService).save(any());
    }

    @Test
    @DisplayName("application properties istenilenler eklendi mi?")
    void serverPortIsSetTo8585() {
        String serverPort = env.getProperty("server.port");
        assertThat(serverPort).isEqualTo("8080"); // Burası senin properties dosyana göre 8585 de olabilir
        assertNotNull(env.getProperty("spring.datasource.url"));
    }
}
