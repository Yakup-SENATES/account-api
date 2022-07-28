package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.CreateAccountRequest;
import com.example.demo.dto.converter.AccountDtoConverter;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;
    private final Clock clock;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter accountDtoConverter, Clock clock) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
        this.clock = clock;
    }

    public AccountDto createAccount(CreateAccountRequest request ) {

        Customer customer = customerService.findCustomerById(request.getCustomerId());
        Account account = new Account(customer,
                request.getInitialCredit(),
                LocalDateTime.now()
                );

        if (request.getInitialCredit().compareTo(BigDecimal.ZERO)>0) {
            Transaction transaction = new Transaction(
                    request.getInitialCredit(),
                    account
            );
            Transaction savedTransaction = new Transaction(request.getInitialCredit(), account);
        }
        return accountDtoConverter.convert(accountRepository.save(account));
    }
    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(instant, Clock.systemDefaultZone().getZone());
    }
}
