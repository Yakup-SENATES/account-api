package com.example.demo.dto.converter;

import com.example.demo.dto.CustomerAccountDto;
import com.example.demo.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {

    private final TransactionDtoConverter transactionDtoConverter;

    public CustomerAccountDtoConverter(TransactionDtoConverter converter) {
        this.transactionDtoConverter = converter;
    }

    public CustomerAccountDto convert(Account from){

        return new CustomerAccountDto(
                Objects.requireNonNull(from.getId()),
                from.getBalance(),
                Objects.requireNonNull(from.getTransaction())
                        .stream()
                        .map(transactionDtoConverter::convert)
                        .collect(Collectors.toSet()),
                Objects.requireNonNull(from.getCreationDate())

        );
    }
}
