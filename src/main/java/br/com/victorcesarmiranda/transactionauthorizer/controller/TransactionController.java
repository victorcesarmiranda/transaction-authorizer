package br.com.victorcesarmiranda.transactionauthorizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcesarmiranda.transactionauthorizer.dto.ResponseDto;
import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import br.com.victorcesarmiranda.transactionauthorizer.enums.TransactionResult;
import br.com.victorcesarmiranda.transactionauthorizer.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseDto> processTransaction(@RequestBody @Valid TransactionDto dto) {
        transactionService.processTransaction(dto);
        return ResponseEntity.ok(new ResponseDto(TransactionResult.APPROVED.getCode()));
    }
}
