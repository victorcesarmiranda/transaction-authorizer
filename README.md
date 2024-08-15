# Autorizador de Transações

Projeto exemplo para autorizar e processar transações de cartão de crédito de benefícios.

## Rodando localmente

1. Clone o projeto.

```bash
  git clone https://github.com/victorcesarmiranda/transaction-authorizer
```

2. Entre no diretório do projeto:

```bash
  cd transaction-authorizer
```


3. No desafio L3, foi solicitado a criação de um mecanismo para substituir MCCs com base no nome do comerciante. Eu utilizei Inteligência Artificial como mecanismo para fazer esta classificação.
Para utilizá-la é preciso uma [API key da Open AI](https://platform.openai.com/api-keys) para fazer a integração com a Integeligência Artificial. (Caso não seja criada a API key a integração não funcionará e a transação será mapeada unicamente pelo MCC)


Após a criação da API key, no arquivo `docker-compose.yml` na raiz do projeto, substituir a variável OPENAI_API_KEY pela chave válida.


```bash
OPENAI_API_KEY: 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
```


4. Para rodar o projeto são necessárias duas portas livres. A 8080 para rodar o Transaction Authorizer e a 5432 para o banco de dados Postgres. Execute no terminal na raiz do projeto:

```bash
  docker-compose up --build
```


5. Após subir o projeto, acessar a URL da documentação

```bash
  http://localhost:8080/swagger-ui.html
```


6. Escolher a solicitação `POST /transaction` e apertar o botão `Try it out`

![image](https://github.com/user-attachments/assets/0471496b-0c02-497a-a135-5a2e1876bb57)


7. Foram criadas duas contas padrão para serem usadas nos testes:

| Número da Conta | Saldo Food | Saldo Meal | Saldo Cash |
| :-------------- | :--------- | :--------- | :--------- |
| 123456          | 100.00     | 200.00     | 300.00     |
| 789012          | 400.00     | 500.00     | 600.00     |
  
Preencher o Request Body com as informações da conta disponível e apertar Execute.

![image](https://github.com/user-attachments/assets/ea9bcb54-6f66-4828-9e83-53039177d288)


## Stack utilizada

**Back-end:** 
- Java 17
- Spring Boot
- Spring Retry
- Spring JPA
- Spring Validation
- Spring Web
- Spring AI Open AI
- Flyway
- Postgres DB
- JUnit
- Mockito
- Testcontainers
  

**Documentação:**
- Open API WEB
