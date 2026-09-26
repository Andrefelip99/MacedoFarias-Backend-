# 🍫 Macedo Farias — Sistema de Catálogo para Confeitaria

> Aplicação web desenvolvida para uma confeitaria, com catálogo de produtos, gerenciamento administrativo e integração com serviços externos para armazenamento de imagens e banco de dados.

## 📌 Sobre o projeto

O **Macedo Farias** é uma aplicação web desenvolvida com o objetivo de disponibilizar um catálogo digital de produtos de confeitaria, permitindo que os clientes visualizem os produtos de forma rápida e encontrem informações como descrição, preço, categoria e imagens.

O projeto também possui uma área administrativa responsável pelo gerenciamento dos produtos cadastrados.

A aplicação foi desenvolvida utilizando uma arquitetura separando **frontend e backend**, permitindo que cada parte evolua de forma independente.

---

## 🖥️ Funcionalidades

### 👤 Área pública

* Visualização do catálogo de produtos
* Exibição de imagens dos produtos
* Informações de preço e descrição
* Organização por categorias
* Pesquisa de produtos
* Filtros
* Acesso ao link de contato/pedido
* Interface responsiva para dispositivos móveis

### 🔐 Área administrativa

* Autenticação de administrador
* Cadastro de produtos
* Edição de produtos
* Exclusão de produtos
* Gerenciamento de categorias
* Upload e gerenciamento das imagens dos produtos

---

## 🏗️ Arquitetura

O projeto foi desenvolvido utilizando uma arquitetura separada em duas aplicações:

```text
                    ┌─────────────────────┐
                    │      Cliente        │
                    │    Navegador Web    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     Frontend        │
                    │      Vue.js         │
                    └──────────┬──────────┘
                               │ HTTP
                               ▼
                    ┌─────────────────────┐
                    │      Backend        │
                    │   Spring Boot      │
                    │       Java          │
                    └──────┬───────┬──────┘
                           │       │
                ┌──────────┘       └──────────┐
                ▼                             ▼
       ┌────────────────┐             ┌────────────────┐
       │    PostgreSQL  │             │   Cloudinary   │
       │      Neon      │             │    Imagens     │
       └────────────────┘             └────────────────┘
```

---

## 🚀 Tecnologias utilizadas

### Backend

* ☕ Java
* 🌱 Spring Boot
* Spring Data JPA
* Spring Web
* Spring Security
* PostgreSQL
* Maven

### Frontend

* 🟢 Vue.js
* JavaScript
* HTML5
* CSS3

### Interface e animações

* GSAP
* Lenis
* Three.js / recursos 3D
* Motion para Vue

### Serviços

* ☁️ Cloudinary — armazenamento das imagens
* 🐘 Neon — banco de dados PostgreSQL
* 🚀 Vercel — hospedagem do frontend
* 🚀 Render — hospedagem do backend

---

## 🗄️ Modelo de Produto

Os produtos possuem informações como:

```text
Produto
├── id
├── title
├── price
├── description
├── imageUrl
├── imageUrl2
├── imageUrl3
├── link
├── category
└── createdAt
```

O campo de preço utiliza `BigDecimal`, garantindo maior precisão para valores monetários.

---

## 📸 Gerenciamento de imagens

As imagens dos produtos não são armazenadas diretamente no banco de dados.

O fluxo utilizado é:

```text
Upload da imagem
       ↓
Backend Spring Boot
       ↓
Cloudinary
       ↓
URL da imagem
       ↓
Banco de dados
```

Dessa forma, o banco mantém apenas as referências das imagens, enquanto os arquivos são gerenciados pelo Cloudinary.

---

## ⚡ Desempenho do catálogo

Um dos objetivos do projeto foi evitar que o usuário precisasse esperar o backend estar disponível para conseguir visualizar o catálogo.

Para isso, o frontend utiliza estratégias de **cache dos produtos**, permitindo que informações previamente carregadas possam ser apresentadas rapidamente.

O frontend pode atualizar os dados posteriormente através da API.

```text
Usuário
   ↓
Frontend
   ↓
Cache local ──────► Exibição rápida
   │
   └──────────────► API
                       ↓
                   Backend
                       ↓
                    Banco
```

Essa abordagem também reduz a dependência do tempo de resposta inicial do servidor.

---

## 🔒 Segurança

A aplicação possui uma área administrativa separada da área pública.

O fluxo de autenticação permite restringir operações de gerenciamento de produtos, enquanto o catálogo permanece disponível para os visitantes.

As operações administrativas são realizadas através da API protegida do backend.

---

## 📱 Responsividade

A interface foi desenvolvida pensando também em dispositivos móveis, considerando:

* Smartphones
* Tablets
* Notebooks
* Monitores maiores

Os componentes visuais e animações foram adaptados para diferentes tamanhos de tela.

---

## 🎨 Interface

O projeto busca utilizar uma identidade visual relacionada ao segmento de confeitaria, combinando elementos visuais, imagens dos produtos e animações para criar uma experiência mais interativa.

Além do catálogo tradicional, foram utilizados recursos de animação e elementos visuais em 3D para evitar uma apresentação completamente estática.

---

## 📂 Estrutura do projeto

### Backend

```text
backend/
└── src/
    └── main/
        ├── java/
        │   └── com.example.confeitariaMacedoFarias/
        │       ├── controllers/
        │       ├── services/
        │       ├── repositories/
        │       ├── entities/
        │       ├── dto/
        │       └── configurations/
        │
        └── resources/
            └── application.properties
```

### Frontend

```text
frontend/
├── src/
│   ├── components/
│   ├── views/
│   ├── router/
│   ├── assets/
│   └── services/
│
├── public/
└── package.json
```

---

## ⚙️ Executando o projeto

### Backend

Clone o repositório:

```bash
git clone SEU_REPOSITORIO_BACKEND
```

Entre na pasta:

```bash
cd backend
```

Execute o projeto com Maven:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

### Frontend

Instale as dependências:

```bash
npm install
```

Execute o projeto:

```bash
npm run dev
```

---

## 🔑 Variáveis de ambiente

O projeto utiliza variáveis de ambiente para informações sensíveis e configurações externas.

Exemplo:

```env
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=

JWT_SECRET=
```

> Os valores reais dessas variáveis não devem ser versionados no GitHub.

---

## 🌐 Deploy

A aplicação foi estruturada para trabalhar com serviços de hospedagem separados:

```text
Frontend
   │
   ▼
Vercel
   │
   │ HTTP
   ▼
Backend
   │
   ├── Render
   │
   ├── Neon PostgreSQL
   │
   └── Cloudinary
```

---

## 🎯 Objetivos do projeto

Além de funcionar como um sistema para catálogo de produtos, o projeto foi desenvolvido como uma oportunidade prática para aplicar conhecimentos de desenvolvimento web.

Entre os principais objetivos estão:

* Praticar desenvolvimento de APIs REST
* Aplicar conceitos de Spring Boot
* Trabalhar com persistência de dados
* Utilizar autenticação e autorização
* Integrar serviços externos
* Trabalhar com upload de imagens
* Desenvolver uma interface moderna com Vue.js
* Trabalhar com animações e interação
* Implementar estratégias de cache
* Realizar deploy de aplicações
* Trabalhar com frontend e backend de forma independente

---

## 🧠 Conceitos aplicados

Durante o desenvolvimento foram utilizados conceitos como:

* API REST
* MVC
* DTO
* Repository Pattern
* Service Layer
* Injeção de Dependências
* ORM / JPA
* Autenticação
* Autorização
* JWT
* CRUD
* HTTP
* CORS
* Banco de dados relacional
* Upload de arquivos
* Cloud Storage
* Cache no frontend
* Variáveis de ambiente
* Deploy
* TDD

---

## 📚 Aprendizados

O desenvolvimento do Macedo Farias proporcionou experiência prática principalmente na integração entre diferentes tecnologias.

O projeto também permitiu trabalhar com situações encontradas em aplicações reais, como gerenciamento de imagens externas, comunicação entre frontend e backend, autenticação, persistência de dados e problemas relacionados ao deploy de aplicações.

---

## 👨‍💻 Desenvolvedor

**André Felipe**

Estudante de **Análise e Desenvolvimento de Sistemas**, com foco em desenvolvimento backend utilizando Java e Spring Boot.

### Tecnologias de interesse

```text
Java
Spring Boot
Spring Security
JPA / Hibernate
PostgreSQL
Vue.js
JavaScript
Git
GitHub
REST APIs
Docker
```

---

## ⭐ Projeto

Se este projeto foi útil ou interessante para você, considere deixar uma ⭐ no repositório.

---
