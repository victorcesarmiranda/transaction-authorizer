package br.com.victorcesarmiranda.transactionauthorizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcesarmiranda.transactionauthorizer.dto.ResponseDto;
import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionRequestDto;
import br.com.victorcesarmiranda.transactionauthorizer.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

    private TransactionService service;

    @PostMapping
    public ResponseEntity<ResponseDto> processTransaction(@RequestBody @Valid TransactionRequestDto dto) {
        service.processTransaction(dto);
        return ResponseEntity.ok(new ResponseDto("00"));
    }
}
