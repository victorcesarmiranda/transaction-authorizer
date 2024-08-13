package br.com.victorcesarmiranda.transactionauthorizer.service.merchant;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantService {

    public static final String WHAT_IS_THE_MCC = """
        Classifique qual é o MCC do estabelecimento.
        As opções de MMC são FOOD, MEAL e CASH.
        Caso não saiba, retorne NONE.
        
        PADARIA DO ZE               SAO PAULO BR -> MEAL
        MERCEARIA 3 IRMAOS          SAO PAULO BR -> FOOD
        UBER TRIP                   SAO PAULO BR -> CASH
        UBER EATS                   SAO PAULO BR -> MEAL
        PAG*JoseDaSilva          RIO DE JANEI BR -> CASH
        GIASSI SUPERMERCADO        VILA VELHA BR -> FOOD
        PICPAY*BILHETEUNICO           GOIANIA BR -> CASH
        DON GIUSEPPE                SAO PAULO BR -> MEAL
        FORT ATACADISTA              SAO JOSE BR -> FOOD
        UNO PARK ESTACIONAMENT   RIO DE JANEI BR -> CASH
        IFD*51.478.149 ANA CAR      JOINVILLE BR -> MEAL
        KOMPRAO KOCH ATACADIST      JOINVILLE BR -> FOOD
        PG *TON UAIII SHAWAR        SAO PAULO BR -> MEAL
        PANVEL FARMACIAS         PORTO ALEGRE BR -> CASH
        IFD*NONO CARLIN PIZZAR      JOINVILLE BR -> MEAL
        UBC COMERCIO E UTILIDA      JOINVILLE BR -> CASH
        {merchant} ->
        """;

    private final OpenAiChatClient chatClient;

    public String getPaymentMethodByMerchant(String merchant) {
        try {
            PromptTemplate template = new PromptTemplate(WHAT_IS_THE_MCC);
            template.add("merchant", merchant);
            return chatClient.call(template.create().getContents());
        } catch (Exception e) {
            return "NONE";
        }
    }
}
