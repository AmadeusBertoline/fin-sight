FinSight 💰 | Gestão de Investimentos
Uma aplicação desktop para controle de ativos financeiros e registro de operações de compra, desenvolvida com foco em arquitetura limpa e integridade de dados.

📌 Sobre o Projeto
O projeto foi concebido como um desafio técnico pessoal para consolidar e demonstrar proficiência na integração entre Java (JavaFX) e SQL (MariaDB), focando em padrões de projeto (DAO/MVC) e integridade referencial. O objetivo é gerenciar uma carteira de ativos (Ações, FIIs) e manter um histórico detalhado de compras, incluindo um sistema de log de auditoria para rastrear todas as alterações no banco de dados.


📸 Demonstração
Dashboard
Ativos
Compras
Vídeo de demonstração
Assista à demonstração do FinSight


🛠️ Tecnologias Utilizadas
Linguagem: Java 17

Interface Gráfica: JavaFX (com CSS customizado e Dark Mode)

Banco de Dados: MariaDB / MySQL

Persistência: JDBC (Java Database Connectivity)

Versionamento: Git & GitHub


✨ Funcionalidades
[x] CRUD de Ativos: Cadastro de tickers, nomes de empresas e tipos de ativos.

[x] Gestão de Compras: Registro de operações com validação de datas e valores.

[x] Validação Robusta: Tratamento de entradas inválidas e proteção contra datas futuras.

[x] Auditoria de Dados: Tabela de logs que registra automaticamente inserções e atualizações.

[x] Interface Responsiva: Design em Dark Mode otimizado para o usuário.


📁 Estrutura de Pastas
Plaintext
FinSight/
├── src/                 # Código fonte Java
│   ├── controller/      # Regras de negócio e controle da UI
│   ├── dao/             # Data Access Object (Persistência)
│   ├── model/           # Classes de entidade (JavaBeans)
│   └── view/            # Telas (FXML e JavaFX)
├── sql/                 # Scripts de criação e população do banco
├── resources/           # Arquivos de configuração (.properties) e CSS
└── README.md            # Documentação do projeto


🚀 Como Executar
Pré-requisitos
Java JDK 17 ou superior.

MariaDB ou MySQL instalado localmente.

Configuração
Clone o repositório:

Bash
git clone https://github.com/AmadeusBertoline/FinSight.git
Na pasta src/main/resources, você encontrará um arquivo chamado db.example.

Renomeie-o para db.properties e insira suas credenciais do banco de dados:

Properties
db.url=jdbc:mariadb://localhost:3306/db_investimentos
db.user=seu_usuario
db.password=sua_senha
Execute os scripts SQL localizados na pasta /sql para criar a estrutura das tabelas.

Execute a classe Main.java.

🔒 Segurança e Boas Práticas
Proteção de Dados: Credenciais de banco de dados são gerenciadas via arquivo .properties, que está devidamente listado no .gitignore para evitar exposição acidental.

Integridade: Uso rigoroso de Chaves Estrangeiras (FK) e Constraints para garantir que nenhuma compra fique órfã de um ativo.

Rastreabilidade: Implementação de audit_log para conformidade, registrando o histórico de operações realizadas no sistema.

Desenvolvido por Amadeus Bertoline da Silva
