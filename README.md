---
marp: true
theme: default
class: invert
paginate: true
---

# Arquitetura de Software
## Meetup ADA - MELI BackEnd 2025

**Marcel Tanuri**  
abril/2025

---

## O que é Arquitetura?

> "Arquitetura de software são as decisões estruturais mais importantes sobre o sistema."  
— Ralph Johnson

---

1. Organização de componentes
2. Estilo de comunicação entre módulos
3. Padrões de escalabilidade, segurança e desempenho

---

## Por que se fala tanto em Arquitetura?

- Decisões arquiteturais são difíceis e caras de mudar
- Afetam a manutenibilidade e escalabilidade do software
- São fundamentais para sistemas modernos e distribuídos
- Conectam as necessidades do negócio com as soluções técnicas

---

## Onde a Arquitetura aparece na prática?

- **Startups**: decisões rápidas, mas com impacto duradouro
- **Empresas maduras**: arquitetura governada e documentada
- **Sistemas legados**: arquitetura emergente ou envelhecida
- **SaaS, APIs, microserviços, monolitos, apps móveis**...

---

## Livros que marcaram gerações

- _Software Architecture in Practice_ — Bass, Clements, Kazman
- _Clean Architecture_ — Robert C. Martin
- _The Art of Scalability_ — Abbott, Fisher
- _Designing Data-Intensive Applications_ — Martin Kleppmann
- _Domain-Driven Design_ — Eric Evans

---

## Estilos Arquiteturais Clássicos

- **Monolito**
- **Camadas (Layered)**
- **Cliente-Servidor**
- **Pipe and Filter**
- **Model-View-Controller (MVC)**
- **Broker**

---

## Estilos Arquiteturais Atuais

- **Microservices**
- **Event-Driven Architecture (EDA)**
- **Serverless**
- **Hexagonal (Ports & Adapters)**
- **Clean Architecture**
- **Service Mesh**
- **Modular Monolith**

---

## Estilos Arquiteturais podem se misturar

| Combinação                          | O que representa                                                   |
|-----------------------------------|-------------------------------------------------------------------|
| Microservices + EDA               | Serviços independentes que se comunicam via eventos               |
| Microservices + Clean Architecture| Cada serviço com domínio limpo e camadas bem definidas           |
| Serverless + EDA                  | Funções disparadas por eventos (ex: AWS Lambda + SQS/SNS)         |
| Hexagonal + Microservices         | Cada serviço isola dependências externas com adapters             |
| Clean + Hexagonal                 | Foco no domínio + regras de dependência invertidas               |
| Microservices + Service Mesh      | Microserviços com controle de tráfego, retries e observabilidade |

> Misturar estilos é comum e saudável, desde que cada escolha tenha propósito.

---

## Arquitetura em Camadas (Layered)

- Muito comum em aplicações Spring Boot
- Separação por responsabilidade

```plaintext
[Controller] → [Service] → [Repository]
```

### Benefícios:
- Separação de responsabilidades
- Testabilidade
- Boa para sistemas pequenos e médios

### Desafios:
- Pode virar "tudo depende de tudo"
- Escalabilidade limitada

---

## Comparativo

| Estilo         | Acoplamento | Escalabilidade | Complexidade |
|----------------|-------------|----------------|--------------|
| Monolito       | Alto        | Baixa          | Baixa        |
| Camadas        | Médio       | Média          | Média        |
| Microservices  | Baixo       | Alta           | Alta         |
| Serverless     | Muito baixo | Muito alta     | Alta         |

---

## Foco da Aula: Microservices

### Por que é relevante hoje?

- Escalabilidade independente por domínio
- Observabilidade, resiliência, deploy contínuo
- Amplamente usado em empresas modernas e cloud-native

---

## Arquitetura moderna com Microservices

- Serviços pequenos e especializados
- Independência de deploy e linguagem
- Comunicação via HTTP (REST) e Mensageria (RabbitMQ, Kafka, etc)
- Infraestrutura de suporte essencial:
  - API Gateway
  - Message Broker
  - Observabilidade (logs, métricas, tracing)

---

## API Gateway

- Porta de entrada única para todos os serviços
- Controle de autenticação, rate limit, logging
- Ex: Spring Cloud Gateway, NGINX, Kong

```plaintext
Client ───▶ API Gateway ───▶ Users Service
                          └───▶ Orders Service
```

---

## Message Broker (Fila de Mensagens)

- Comunicação assíncrona entre serviços
- Permite desacoplamento e resiliência
- Ex: RabbitMQ, Kafka

```plaintext
Users Service ───▶ [Fila: user.created] ───▶ Email Service
```

---

## Como os serviços se comunicam?

### Comunicação REST (síncrona):
```plaintext
Cliente → API Gateway → Serviço REST
```
- O Gateway redireciona com base na URL (ex: `/users` → User Service)
- Comunicação direta por HTTP

### Comunicação via broker (assíncrona):
```plaintext
User Service → [RabbitMQ] → Outro serviço consumidor
```
- Comunicação desacoplada
- Serviço A não precisa conhecer serviço B

---

## Arquitetura na prática

Vamos montar juntos:

- API Gateway
- 2 Microserviços (ex: `users`, `orders`)
- Comunicação via HTTP (REST)
- Spring Boot + Docker Compose

---

## Demonstração (Live Coding)

### Objetivo:

- Mostrar separação de responsabilidades
- Mostrar chamada entre serviços
- Demonstrar como evoluir serviços de forma independente

---

## Clean Architecture

- Proposta por Robert C. Martin (Uncle Bob)
- Organiza o sistema de forma centrada no domínio
- Regra de ouro: as dependências sempre apontam para o centro

```plaintext
[ Frameworks & Drivers ]
        ↓
[ Interface Adapters ]
        ↓
[ Application Rules ]
        ↓
[ Enterprise Rules ]
```

- Entidades e regras de negócio não conhecem nada externo
- Controladores e Repositórios são adaptadores na borda

---

## Clean Architecture na prática

### Benefícios:
- Testabilidade alta
- Independência de framework e banco
- Foco em domínio, não em tecnologia

### Aplicação com Spring Boot:
- Requer disciplina e separação de pacotes
- Camadas internas puras, interfaces implementadas externamente

### Quando usar:
- Projetos com regras de negócio importantes
- Domínios complexos e em crescimento

---

## Considerações finais

- Arquitetura não é sobre tecnologia, é sobre decisões
- Arquitetura influencia diretamente na saúde do software
- Estudar arquitetura é essencial para crescer na carreira

---

## Perguntas e Discussão
🧠💬

---

## Referências

- IEEE 1471 – Architecture Description
- Martin Fowler - Software Architecture Guide
- ThoughtWorks Technology Radar
- Livros mencionados anteriormente
