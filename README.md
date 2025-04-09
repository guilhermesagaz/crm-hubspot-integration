# CRM HubSpot Integration (Spring Boot 3.4.4 + Java 21)

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-brightgreen)
![License](https://img.shields.io/badge/License-MIT-green)

Aplicação Spring Boot 3.4.4 com Java 21 para integração avançada com a API do HubSpot, incluindo autenticação OAuth 2.0, gerenciamento de contatos e webhooks.

---

## 🚀 Tecnologias Principais

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Cloud OpenFeign** (Client HTTP declarativo)
- **Google Guava** (rate limiting)
- **H2 Database** (Dev)

---

## 📋 Pré-requisitos

- **JDK 21** ([Amazon Corretto](https://aws.amazon.com/corretto/) ou [OpenJDK](https://openjdk.org/projects/jdk/21/))
- **Maven 3.9+**
- **Conta de desenvolvedor** no [HubSpot](https://developers.hubspot.com/)

---

## 🛠️ Configuração Rápida

### 1. Clone o repositório
```bash
git clone https://github.com/guilhermesagaz/crm-hubspot-integration.git
cd crm-hubspot

## 🛠️ Configuração

### 2. Configure o ambiente
Crie um arquivo `.env` na raiz:

```ini
# HubSpot OAuth
HUBSPOT_CLIENT_ID=seu-client-id
HUBSPOT_CLIENT_SECRET=seu-client-secret
HUBSPOT_REDIRECT_URI=https://f866-177-223-76-7.ngrok-free.app/api/auth/callback
```

---

## ✨ Funcionalidades

- Geração da URL de autorização OAuth2.
- Processamento do callback com o authorization code.
- Criação de contatos no CRM via API.
- Processamento de eventos via Webhooks (ex: contact.creation).
- Respeito ao rate limit da HubSpot (110 reqs / 10 segundos).

---

## 🚀 Como Executar

### ⚙️ Configuração

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/crm-hubspot.git
cd crm-hubspot
```

2. Configure as variáveis de ambiente necessárias:

Você pode criar um arquivo `.env` na raiz ou configurar diretamente no seu sistema. As variáveis obrigatórias são:

| Variável                  | Descrição                          | Obrigatório |
|---------------------------|--------------------------------------|-------------|
| `HUBSPOT_CLIENT_ID`       | ID do cliente OAuth do HubSpot      | ✅          |
| `HUBSPOT_CLIENT_SECRET`   | Segredo do cliente OAuth            | ✅          |
| `HUBSPOT_REDIRECT_URI`    | URL de callback registrada no HubSpot | ✅        |

3. Execute a aplicação:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=default
```

> A aplicação será iniciada em: `http://localhost:8080`

---

🌍 Redirecionamento e Webhook com Ngrok

Para testar localmente os fluxos de autenticação OAuth e os webhooks do HubSpot, você pode utilizar o [ngrok](https://ngrok.com/).

1. Inicie o ngrok apontando para a porta da sua aplicação (ex: 8080):

```bash
ngrok http 8080
```

2. Copie a URL gerada (ex: https://abcd1234.ngrok.io) e configure no painel de desenvolvedor do HubSpot:

- URL de Redirecionamento OAuth: https://abcd1234.ngrok.io/api/auth/callback

- URL do Webhook: https://abcd1234.ngrok.io/api/webhooks/contact-creation

---

## 🌐 Endpoints API

Está disponível arquivo para importação do Insomnia para faclitar a execução um script. Disponível no diretório `docs` arquivo `insomnia.json`.

### 🔐 Autenticação

```http
GET /api/auth/authorize-url
```

```http
GET /api/auth/callback?code={authorization_code}
```

```http
GET /api/auth/refresh-token?refreshToken={refreshToken}
```

```http
GET /api/auth/last-token
```

### 👤 Contatos (Requer autenticação)

```http
POST /api/contact
Authorization: Bearer token
Content-Type: application/json

{
  "properties": {
    "email": "exemplo@empresa.com",
    "firstname": "João",
    "lastname": "Silva"
    "phone": "999999999"
  }
}
```

```http
GET /api/contact?limit={limit}
Authorization: Bearer token
```

```http
GET /api/contact/{contactId}
Authorization: Bearer token
```

### 📩 Webhooks

```http
POST /api/webhooks/contact-creation
X-HubSpot-Signature: {signature}
Content-Type: application/json

{
  "objectId": 123,
  "eventType": "contact.creation"
}
```

---

## 📈 Controle de Rate Limit

HubSpot permite **110 requisições por 10 segundos**.

A aplicação usa `Guava RateLimiter` para garantir:

```java
RateLimiter rateLimiter = RateLimiter.create(11.0); // ~110 por 10s
rateLimiter.tryAcquire(); // bloqueia até poder continuar
```

---

## 📌 Melhorias Futuras

- 📊 Monitoramento de rate limit
- 🧪 Cobertura total com testes
