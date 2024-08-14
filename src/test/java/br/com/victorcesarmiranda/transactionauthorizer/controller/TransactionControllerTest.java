package br.com.victorcesarmiranda.transactionauthorizer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import br.com.victorcesarmiranda.transactionauthorizer.dto.ResponseDto;
import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import br.com.victorcesarmiranda.transactionauthorizer.enums.TransactionResult;
import br.com.victorcesarmiranda.transactionauthorizer.service.transaction.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService service;

    @MockBean
    private ChatClient chatClient;

    @Autowired
    private JacksonTester<TransactionDto> jsonTransactionDto;

    @Autowired
    private JacksonTester<ResponseDto> jsonResponseDto;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:14"
    );


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void processTransaction_ValidTransactionDto_ReturnsApprovedCode() throws Exception {
        var transactionDto = new TransactionDto("123456", BigDecimal.valueOf(100), "5812", "PADARIA DO ZE               SAO PAULO BR");
        var expectedResponse = new ResponseDto(TransactionResult.APPROVED.getCode());

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransactionDto.write(transactionDto).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponseDto.write(expectedResponse).getJson()));
    }

    @Test
    void processTransaction_InsufficientFoodBalanceAndSufficientCashBalance_ReturnsApprovedCode() throws Exception {
        var transactionDto = new TransactionDto("123456", BigDecimal.valueOf(250), "5812", "PADARIA DO ZE               SAO PAULO BR");
        var expectedResponse = new ResponseDto(TransactionResult.APPROVED.getCode());

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransactionDto.write(transactionDto).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponseDto.write(expectedResponse).getJson()));
    }

    @Test
    void processTransaction_InsufficientMealAndCashBalance_ReturnsRejectedCode() throws Exception {
        var transactionDto = new TransactionDto("123456", BigDecimal.valueOf(450), "5812", "PADARIA DO ZE               SAO PAULO BR");
        var expectedResponse = new ResponseDto(TransactionResult.REJECTED.getCode());

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransactionDto.write(transactionDto).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponseDto.write(expectedResponse).getJson()));
    }

    @Test
    void processTransaction_AccountNotFound_ReturnsTransactionResultError() throws Exception {
        var transactionDto = new TransactionDto("18613", BigDecimal.valueOf(450), "5812", "PADARIA DO ZE               SAO PAULO BR");
        var expectedResponse = new ResponseDto(TransactionResult.ERROR.getCode());

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransactionDto.write(transactionDto).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponseDto.write(expectedResponse).getJson()));
    }

}