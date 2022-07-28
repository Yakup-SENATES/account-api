package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.converter.CustomerDtoConverter;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class CustomerServiceTest {

    private CustomerService customerService;

    private CustomerRepository customerRepository;
    private CustomerDtoConverter converter;

/*
* @BeforeEach is used to signal that the annotated method
* should be executed before each @Test, @RepeatedTest,
* @ParameterizedTest, @TestFactory, and @TestTemplate
* method in the current test class.
* */
    @BeforeEach
    public void setUp(){
        customerRepository = mock(CustomerRepository.class);
        converter = mock(CustomerDtoConverter.class);
        customerService = new CustomerService(customerRepository, converter);
    }

    @Test
    public void testFindByCustomerId_whenCustomerExists_shouldReturnCustomer(){
    //of -> Returns an unmodifiable set containing zero elements. See Unmodifiable Sets for details.
        Customer customer = new Customer("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = customerService.findCustomerById("id");

        assertEquals(result, customer);
    }


    @Test
    public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, ()-> customerService.findCustomerById("id"));
    }

    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer(){

        Customer customer = new Customer("id", "name", "surname", Set.of());

        CustomerDto customerDto = new CustomerDto("id", "name", "surname",Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById("id");

        assertEquals(result, customerDto);

    }

    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldThrowException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,()-> customerService.getCustomerById("id") );

        //convertere hiç gitmediğini de verify edelim
        verifyNoInteractions(converter);

    }





}