# 💰 FinSight | Gestão de Investimentos

Aplicação desktop desenvolvida em JavaFX para controle de ativos financeiros e registro de operações de compra, com foco em **arquitetura limpa, integridade de dados e rastreabilidade**.

---

## 📌 Sobre o Projeto

O **FinSight** foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento backend com Java, integração com banco de dados e construção de interfaces desktop, simulando um sistema real de gestão de investimentos.

- Integração entre **Java (JavaFX)** e **SQL (MariaDB)**
- Aplicação de padrões de arquitetura (**MVC e DAO**)
- Garantia de **integridade referencial e consistência dos dados**

A aplicação permite gerenciar uma carteira de investimentos (como ações e FIIs), mantendo um histórico completo de operações e um sistema de **auditoria automática via banco de dados**.

---

## 📸 Demonstração

### Dashboard
![Dashboard](./assets/dashboard.png)

### Ativos
![Ativos](./assets/ativos.png)

### Compras
![Compras](./assets/compras.png)

### Auditoria
![Auditoria](./assets/auditoria.png)

🎥 **Vídeo de demonstração:**  
👉 [Assista ao funcionamento do sistema](./assets/demo.mp4)

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **JavaFX** (com CSS customizado e Dark Mode)
- **MariaDB / MySQL**
- **JDBC**
- **Git & GitHub**

---

## ✨ Funcionalidades

- ✔ CRUD completo de ativos (ticker, nome, tipo)
- ✔ Registro e gerenciamento de compras
- ✔ Validação de dados (valores inválidos, datas futuras, etc)
- ✔ Dashboard com resumo financeiro
- ✔ Interface moderna em Dark Mode
- ✔ Busca dinâmica de dados

- ## 📚 Aprendizados

Durante o desenvolvimento deste projeto, aprendi:

- Aplicar bindings no JavaFX para reduzir código imperativo
- Trabalhar com BigDecimal corretamente
- Estruturar um CRUD com separação de responsabilidades
- Utilizar triggers no banco para auditoria automática
- Evoluir o código aplicando princípios SOLID na prática

### 🧠 Diferenciais técnicos

- Uso de **Triggers** para automação de regras no banco
- Implementação de **Procedures** para centralização de lógica SQL
- Utilização de **Cursores** para processamento de dados
- Sistema de **log de auditoria** para rastreamento de alterações


## 💡 Decisões técnicas

- Uso de JavaFX com bindings para manter a UI reativa
- Separação em camadas (View / Controller / DAO)
- Aplicação inicial do princípio de inversão de dependência
- Banco relacional com uso de triggers para auditoria
---

## 🗄️ Banco de Dados

O sistema utiliza recursos avançados do MariaDB para garantir robustez:

- **Chaves estrangeiras (FK)** para integridade referencial  
- **Triggers** para auditoria automática  
- **Tabela de logs (audit_log)** registrando inserções e atualizações  
- **Procedures e cursores** para operações mais complexas  

---

## 📁 Estrutura do Projeto
FinSight/

├── src/

│ ├── controller/ # Regras de negócio

│ ├── dao/ # Acesso a dados (JDBC)

│ ├── model/ # Entidades e validações

│ └── view/ # Interface JavaFX

├── resources/

│ ├── config/ # Configuração do banco (.properties)

│ └── css/ # Estilos da interface

├── sql/ # Scripts do banco de dados

└── README.md

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 17 ou superior  
- MariaDB ou MySQL instalado  

### Passo a passo

1. Clone o repositório:

```bash
git clone https://github.com/AmadeusBertoline/FinSight.git
````
Configure o banco de dados executando os scripts da pasta:

/sql

Crie o arquivo de configuração:

resources/config/db.properties

Adicione suas credenciais (verifique a disponibilidade da porta 3306):

db.url=jdbc:mariadb://localhost:3306/db_investimentos
db.user=seu_usuario
db.password=sua_senha

Execute a aplicação pela sua IDE
🔒 Segurança e Boas Práticas
🔐 Credenciais protegidas via .properties (não versionado)
🧱 Integridade garantida com constraints e FKs
📜 Auditoria completa via triggers e tabela de logs
🧠 Separação clara de responsabilidades (MVC)


```md
👨‍💻 **Autor**

Amadeus Bertoline da Silva  
🔗 LinkedIn: https://www.linkedin.com/in/amadeus-bertoline-8432a6194/
