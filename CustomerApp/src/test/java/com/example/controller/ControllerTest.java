package com.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.util.Arrays;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest(CustomerController.class)
public class ControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private CustomerRepository customerRepository;
 
    @InjectMocks
    private CustomerController customerController;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Customer customer;
 
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setAge("30");
        customer.setAadharcardnumber(123456789012L); // long type
        customer.setAccoutnumber(9876543210L);      // long type
        customer.setEmail("john.doe@example.com");
        customer.setPhonenumber(1234567890L);       // long type
        customer.setUsername("john.doe");
        customer.setPassword("password");
        customer.setBalance(1000.0);
    }
 
    @Test
    public void testRead() throws Exception {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
 
        mockMvc.perform(get("/customer/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(customer.getName()));
    }
 
    @Test
    public void testReadByName() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
 
        mockMvc.perform(get("/customer/readname/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()));
    }
 
    @Test
    public void testAdd() throws Exception {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
 
        mockMvc.perform(post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()));
    }
 
    @Test
    public void testUpdate() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
 
        mockMvc.perform(put("/customer/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()));
    }
 
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/customer/delete/1"))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testReadBalanceById() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
 
        mockMvc.perform(get("/customer/readbalance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    }
}
 