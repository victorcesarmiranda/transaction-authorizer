package br.com.victorcesarmiranda.transactionauthorizer.service.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private MerchantService merchantService;

    @Test
    void getPaymentMethodByMerchant_ReturnsCorrectClassification_WhenMerchantExists() {
        String merchant = "PADARIA DO ZE";
        String expectedClassification = "MEAL";

        ChatClient.ChatClientPromptRequest chatClientPromptRequest = BDDMockito.mock(ChatClient.ChatClientPromptRequest.class);
        ChatClient.ChatClientRequest.CallPromptResponseSpec callPromptResponseSpec = BDDMockito.mock(ChatClient.ChatClientRequest.CallPromptResponseSpec.class);

        given(chatClient.prompt(any())).willReturn(chatClientPromptRequest);
        given(chatClientPromptRequest.call()).willReturn(callPromptResponseSpec);
        given(callPromptResponseSpec.content()).willReturn(expectedClassification);

        String actualClassification = merchantService.getPaymentMethodByMerchant(merchant);

        assertEquals(expectedClassification, actualClassification);
    }

    @Test
    void getPaymentMethodByMerchant_ReturnsEmptyString_WhenChatClientThrowsException() {
        String merchant = "PADARIA DO ZE";
        given(chatClient.prompt(any())).willThrow(new RuntimeException());

        String actualClassification = merchantService.getPaymentMethodByMerchant(merchant);

        assertEquals("", actualClassification);
    }
}