---
marp: true
theme: default
class: invert
paginate: true
# Define variáveis CSS para consistência
style: |
  section {
    font-size: 22px; /* Um pouco maior para melhor legibilidade */
    color: var(--text-color);
    padding: 60px; /* Adiciona um pouco mais de respiro */
  }

  h1 {
    font-size: 64px; /* Mais destaque */
    text-align: center;
    border-bottom: 4px solid var(--accent-color); /* Linha de destaque para o título */
    padding-bottom: 10px;
    margin-bottom: 20px;
  }

  h2 {
    padding-bottom: 5px;
    font-size: 38px;
  }
  
  h3 {
    font-size: 34px;
    margin-top: 0;
  }

  .columns {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 30px; /* Mais espaço */
  }
  .columns-3 {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }
  .highlight {
      padding: 15px;
      border-left: 5px solid var(--accent-color);
      margin: 15px 0;
      font-style: italic;
  }
---

# Arquitetura
### **Do Monolito aos Microservices**

<br>
<br>
<br>

---

### ⚠️ O Problema: A "Bola de Lama" (Big Ball of Mud)

Com o tempo, em um projeto real, a complexidade se acumula:

* **Fragilidade:** Alterar uma UI pode quebrar uma regra de negócio central.
* **Rigidez:** Mudar a tecnologia de persistência (DB) parece impossível.
* **Dispersão:** A lógica de negócio está espalhada entre **Controllers**, **Services** e **Models**.
* **Testes Lentos:** Os testes de integração se tornam a única forma de garantir a funcionalidade.

> **Resultado:** O software fica *caro para manter* e *arriscado para alterar*.

---

### 📉 A Arquitetura Padrão (O Perigo da Dependência)

A estrutura típica em Spring é ótima para começar, mas...

**O Fluxo de Dados:** Cliente ➡️ Controller ➡️ Service ➡️ Repository ➡️ Banco de Dados.

**Qual é o problema?**

A sua **Lógica de Negócio** (`@Service`) depende **diretamente** da **Infraestrutura** (`@Repository` do Spring Data JPA).

> **A COPLAMENTO:** Se trocarmos o SQL por um MongoDB ou uma API externa, você terá que reescrever sua camada de **`@Service`**!

---

### 💡 A Ideia Central de TODAS as Arquiteturas Limpas

<br>

# SEPARE AS PREOCUPAÇÕES!

> A sua **Lógica de Negócio** (o "coração" do seu software) **não deve depender** de **detalhes de implementação** (frameworks, bancos de dados, ou APIs).

<div class="highlight">
  São os **detalhes** que devem depender do **coração**, e não o contrário!
</div>

---

### 🏰 A Consolidação: Os "Ancestrais"

A "Clean Architecture" não surgiu do nada. Ela foi uma consolidação de ideias que resolviam o mesmo problema:

* **Arquitetura Hexagonal (Ports & Adapters)** - *Alistair Cockburn*
* **Onion Architecture (Arquitetura "Cebola")** - *Jeffrey Palermo*

> **Clean Architecture** (Uncle Bob) pegou essas ideias e deu a elas um nome e um diagrama fáceis de lembrar.

---

### 🎯 Clean Architecture (Robert C. Martin)

O famoso diagrama de "alvos".



**O objetivo é o mesmo:** Isolar o núcleo de negócio para que ele seja a parte mais fácil e barata de testar e mudar.

---

### 🔑 A Regra de Ouro: A Regra da Dependência

Não importa quantas camadas você tenha. A regra é UMA:

> **As dependências do código-fonte só podem apontar para DENTRO.**

* Uma classe em um círculo **interno** não pode saber NADA sobre uma classe em um círculo **externo**.
* `UseCase` (interno) **NÃO PODE** importar `Repository` (externo).
* `Entity` (interno) **NÃO PODE** importar `Spring` (externo).

<br>
<center>
  <div style="font-size: 50px; font-weight: bold; color: var(--accent-color); padding: 10px; background-color: #ffe0b2; border-radius: 5px;">
    [Externo] ➡ [Interno]  <span style="color: red;">❌</span> <br>
    [Externo] ⬅ [Interno]  <span style="color: green;">✅</span>
  </div>
</center>

---

### 🧱 As Camadas (1/4): Entities (Entidades)

![bg right:40% 100%](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

* **O que é:** Os objetos puros do seu negócio (Ex: `Pedido`, `Cliente`).
* **Regra:** Contêm as regras de negócio mais gerais (Ex: "Um Pedido não pode ter valor negativo").
* **Java:** Classes **POJO** simples, sem anotações de framework!
    * *O `@Entity` do JPA não deve viver aqui.*

---

### 🧱 As Camadas (2/4): Use Cases (Casos de Uso)

![bg right:40% 100%](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

* **O que é:** A lógica específica da **aplicação** (o fluxo de trabalho).
* **Regra:** Orquestra a ação. Ex: O fluxo para *Criar um Pedido*.
* **Java:** Classes de serviço que dependem de **interfaces** (Portas), não de implementações concretas (Repository JPA).

---

### 🧱 As Camadas (3/4): Interface Adapters (Adaptadores)

![bg right:40% 100%](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

* **O que é:** "Tradutores" entre o mundo exterior e o Use Case.
* **Regra:** Converte dados do formato do framework para o formato do Use Case, e vice-versa.
* **Exemplos:**
    * **Controllers:** Pegam a requisição HTTP e chamam o Use Case.
    * **Repositories:** **Implementam** as interfaces de persistência definidas pelo Use Case (usando JPA, JDBC, etc.).

---

### 🧱 As Camadas (4/4): Frameworks & Drivers (Detalhes)

![bg right:40% 100%](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

* **O que é:** O mundo exterior.
* **Regra:** Todos os detalhes que você não controla.
* **Exemplos:** O Spring Boot em si, o Banco de Dados (PostgreSQL, H2), a Web (Tomcat), APIs Externas (Stripe, Kafka).

---

### 🪄 A Mágica: Inversão de Dependência (Em Java)

Como o `UseCase` (interno) fala com o `Repository` (externo) sem violar a regra?

**O Jeito Certo (Desacoplado):**
1.  O **UseCase** define uma **interface (Porta)** que ele precisa.
2.  O **Repository** (Adaptador) **implementa** essa interface.

<br>
<div class="highlight">
  O UseCase depende apenas da **"promessa"** (a Interface), não da **"realização"** (a Implementação JPA).
</div>
<br>

**Fluxo de Dependência:** UseCase ➡️ Interface (Porta) ⬅️ Implementação (Adaptador)

```java
// 1. Pacote Interno (application)
public interface IPedidoRepository {
    void salvar(Pedido pedido);
}

// 2. Pacote Interno (usecases) 
public class CriarPedidoUseCase { private final IPedidoRepository repository; 
// OK! Depende da interface // ... 
}

// 3. Pacote Externo (infrastructure) 
public class JpaPedidoRepository implements IPedidoRepository { 
  // ... Usa o Spring Data JPA aqui ... 
  public void salvar(Pedido pedido) { 
    /* ... */ 
    } 
}

```
---

## 🌐 E os Microservices? O Escopo Muda.

A regra é simples:

> **Cada Microservice é, em si, uma pequena aplicação que segue a Clean Architecture.**

* O Microservice-A é um sistema completo.
* Para o Microservice-A, a existência do Microservice-B é um **"detalhe de infraestrutura"** (camada externa).

---

### Exemplo: Serviço A (Pedidos) 📞 Serviço B (Estoque)

**Cenário:** O `CriarPedidoUseCase` (Serviço A) precisa verificar o estoque (Serviço B) antes de criar o pedido.

<br>

**Pergunta:** Como o UseCase (interno) chama uma API REST (externa) sem violar a **Regra da Dependência**?

---

### ❌ O Jeito Errado (Microservice Acoplado)


```java
// Pacote Interno (usecases) - SERVIÇO A
public class CriarPedidoUseCase {
    
    // VIOLAÇÃO!
    // Depende de uma tecnologia externa (Spring/Feign)
    private FeignEstoqueClient feignClient;

    public void execute(Pedido pedido) {
    // Acoplado! O que acontece se o Serviço B mudar de REST para Kafka?
    if (feignClient.verificarEstoque(pedido.getProductId())) {
        // ...
    }
  }

}
```

`Isso torna seu **Caso de Uso** (coração do negócio) impossível de testar sem a rede.`

---

> **Consequência:**
>
> 1.  **VIOLAÇÃO!** O coração do negócio depende de uma tecnologia externa (HTTP/Spring/Feign).
> 2.  **Impossível de testar** o UseCase sem a rede.
> 3.  Se o Serviço B mudar de REST para Kafka, o UseCase **terá que ser alterado**!

---

### O Jeito Certo (Microservice Limpo)

**Passo 1: A Porta (Interface) - Camada INTERNA**

O UseCase define o que ele precisa, através de uma interface (Porta) no seu próprio pacote.

```java
// Pacote Interno (application.ports) - SERVIÇO A
public interface IEstoqueService {
    boolean verificarDisponibilidade(String productId);
}

// Pacote Interno (usecases) - SERVIÇO A 
public class CriarPedidoUseCase { 
  // OK! Depende apenas de uma interface interna 
  private final IEstoqueService estoqueService;

public void execute(Pedido pedido) {`
    if (estoqueService.verificarDisponibilidade(pedido.getProductId())) {`
        // ...
    }
  }

}
```

---

### O Jeito Certo (Microservice Limpo)

**Passo 2: O Adaptador (Impl. HTTP) - Camada EXTERNA**

Na camada de infraestrutura, criamos o "Adaptador" que implementa a interface, usando as ferramentas externas (Feign, RestTemplate).

```java
// Pacote Externo (infrastructure.adapters) - SERVIÇO A
@Component
public class EstoqueServiceHttpClient implements IEstoqueService {
    
    // O Feign Client é um detalhe de implementação
    private final FeignEstoqueClient feignClient;

    @Override
    public boolean verificarDisponibilidade(String productId) {
        // O "adaptador" faz a tradução e chama o mundo exterior
        return feignClient.verificarEstoque(productId);
    }
}
```

---

### Benefícios Finais

Por que fazer todo esse trabalho?

1. **Testabilidade:** Você pode testar seu CriarPedidoUseCase100% em memória, com um Mock doIEstoqueService. Seus testes são **rápidos** e **confiáveis**. 2. **Manutenibilidade:** O Serviço B mudou de REST para Kafka? Você **não toca** no UseCase. Você apenas cria um novo EstoqueServiceKafkaAdapter e troca a implementação no Spring. 3. **Independência:** Seu núcleo de negócio (Use Cases) não sabe que existe Spring, Kafka, REST ou SQL. Ele só conhece as **regras de negócio**.

---

# Estrutura de Pacotes (Clean Architecture em Java/Spring)

A chave é organizar o projeto de modo que os pacotes externos (Infraestrutura) **sempre importem** os pacotes internos (Núcleo), respeitando a Regra da Dependência.

## 1. Núcleo (Camadas Internas)

Estes pacotes contêm a lógica de negócio e as definições de contratos. **Não devem ter dependências de frameworks externos (Spring, JPA, etc.)**.

---

| Pacote | Conteúdo Principal | Camada da Clean Arch |
| :--- | :--- | :--- |
| **com.app.domain** | **Entities** (Objetos de negócio puros, POJOs), Value Objects, e as regras de negócio mais gerais. | Entities |
| **com.app.application.ports** | **Interfaces** (Portas) que definem o que o Use Case precisa para persistência e serviços externos (Ex: IPedidoRepository). | Ports |
| **com.app.application.usecases** | A lógica da aplicação (**Use Cases**). Orquestra o fluxo de dados e depende apenas das interfaces em .ports. | Use Cases |

---

## 2. Infra (Camadas Externas)

Estes pacotes contêm os adaptadores que implementam os contratos definidos no **Núcleo**. **Podem depender de frameworks externos (Spring, JPA, Feign, etc.)**.

| Pacote | Conteúdo Principal | Função / Exemplo |
| :--- | :--- | :--- |
| **com.app.infrastructure.web** | Tradutores de Entrada (**Controllers**, DTOs). Lida com HTTP e chama os Use Cases. | Adapter (Primário) |
| **com.app.infrastructure.persistence** | Implementações concretas das Interfaces de Repository, utilizando **Spring Data JPA, JDBC, etc.** | Adapter (Secundário) |
| **com.app.infrastructure.external** | Adaptadores para APIs e serviços externos (**Clients Feign, Kafka Producers, gRPC clients**). | Adapter (Secundário) |

---

# Perguntas?

