package br.com.victorcesarmiranda.transactionauthorizer.controller;

import org.springframework.http.ResponseEntity;

import br.com.victorcesarmiranda.transactionauthorizer.dto.ResponseDto;
import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transação", description = "API para transações")
public interface TransactionApi {

    @Operation(
            summary = "Processa a transação",
            description = "Processa a transação de acordo com as regras de negócio da aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um código informando o status do processamento da transação", content =
            @Content(examples = {
                    @ExampleObject(value = "00", name = "Transação aprovada"),
                    @ExampleObject(value = "51", name = "Transação rejeitada por falta de saldo"),
                    @ExampleObject(value = "07", name = "Erro ao processar a transação")
            }))
    })
    ResponseEntity<ResponseDto> processTransaction(TransactionDto dto);
}
