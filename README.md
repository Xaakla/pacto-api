# API - Pacto Soluções (Documentação)

## Visão Geral

Esta API foi desenvolvida como parte de um processo seletivo da **Pacto Soluções** e atua como o backend para um sistema de gerenciamento de vendas. A API integra-se com a **CIELO** para processar pagamentos e reembolsos via cartão de crédito, proporcionando ao usuário uma experiência segura e eficiente para a realização dessas operações.

## Requisitos

Antes de prosseguir com a execução do projeto, certifique-se de que o ambiente de desenvolvimento atenda aos seguintes requisitos:

- **Java 17 ou superior**: Com a variável de ambiente `JAVA_HOME` devidamente configurada.
- **Git**: Para clonar o repositório e gerenciar o controle de versão.
- **PostgreSQL 14 ou superior**: Para gerenciamento do banco de dados.

## Instruções para Configuração Local

### 1. Clonar o Repositório

Para obter o código-fonte da API, execute o seguinte comando no terminal:

```bash
git clone https://github.com/Xaakla/pacto-api.git
```

Caso prefira, também é possível fazer o download do projeto em formato ZIP diretamente pelo link do repositório.

### 2. Configurar o Banco de Dados

Verifique se você possui um banco de dados PostgreSQL ativo com o nome pacto. Caso ainda não tenha criado o banco, siga as instruções abaixo:

1. No terminal, conecte-se ao PostgreSQL com o seguinte comando:

   ```bash
   psql -U postgres
   ```

2. Insira a senha do usuário postgres (o valor padrão é postgres, salvo se houver sido alterado).

3. Crie o banco de dados utilizando o comando SQL:

   ```sql
   CREATE DATABASE pacto;
   ```

### 3. Iniciar a Aplicação

Com o banco de dados configurado, navegue até o diretório do projeto clonado e, no terminal, execute o seguinte comando para iniciar a aplicação:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível localmente na porta 8080.

## Simulação de Pagamentos com Cartão de Crédito

Para realizar a simulação de transações com cartão de crédito utilizando a API da **Cielo**, é importante seguir algumas regras específicas para garantir a validação do cartão e o comportamento correto da simulação.

### Regras de Simulação de Cartão

1. **Número do Cartão**:
   - O número do cartão deve começar com o dígito **1**.  
     Exemplo: `1XXX XXXX XXXX XXXX`.


2. **Status da Transação**:  
   O status do pagamento será determinado pelo último dígito do número do cartão de crédito:
   - **Aprovado**: O último dígito deve ser **1** ou **4**.  
     Exemplo: `1XXX XXXX XXXX XXX4`.
   - **Falha**: O último dígito deve ser **2**, **3**, **5**, **7**, ou **8**.  
     Exemplo: `1XXX XXXX XXXX XXX3`.
   - **Pendente**: O último dígito deve ser **6**.  
     Exemplo: `1XXX XXXX XXXX XXX6`.
   - **Aleatório**: O último dígito deve ser **9**.  
     Exemplo: `1XXX XXXX XXXX XXX9`.


3. **Nome do Titular**:
   - O nome no cartão deve conter, no máximo, **25 caracteres**.


4. **Data de Vencimento**:
   - A data de vencimento deve ser válida.  
     Exemplo: `12/2030`.


5. **Código de Segurança (CVV)**:
   - O CVV deve ser um número aleatório de **3 dígitos**.  
     Exemplo: `222`.


## Observações

- A integração com a API da CIELO exige chaves e credenciais específicas, que devem ser configuradas de acordo com a documentação fornecida pela CIELO.
- Certifique-se de que as configurações de conexão com o banco de dados PostgreSQL estejam devidamente definidas no arquivo de propriedades da aplicação (application.properties).