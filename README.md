# Sistema de Gestão de Biblioteca (INF008)

Este projeto é um sistema de gestão de biblioteca desenvolvido como trabalho prático para a disciplina de Programação Orientada a Objetos (INF008) do curso de Análise e Desenvolvimento de Sistemas do IFBA.

O sistema implementa uma arquitetura de microkernel, onde as principais funcionalidades são carregadas como plugins independentes e dinâmicos. A interface gráfica foi construída com JavaFX e a persistência de dados é gerida pelo Hibernate (ORM), comunicando com uma base de dados MariaDB executada num contêiner Docker.

## Pré-requisitos

Para compilar e executar este projeto, é necessário ter o seguinte software instalado:

* Java (JDK 17 ou superior)
* Apache Maven (3.6 ou superior)
* Docker e Docker Compose
* Para utilizadores do Windows, é recomendado o uso do WSL para a execução do Docker.

## 1. Configuração do Ambiente (Base de Dados)

A base de dados MariaDB é fornecida através de um ambiente Docker. Para iniciá-la:

1.  Navegue no seu terminal para o diretório que contém os ficheiros `docker-compose.yml` fornecidos.
2.  Execute o seguinte comando para iniciar o contêiner da base de dados em segundo plano:

    ```bash
    docker-compose up -d
    ```
A base de dados estará agora a ser executada e pronta para receber conexões.

## 2. Instruções de Compilação

O projeto utiliza o Maven para gerir as dependências e o processo de compilação.

1.  Navegue no seu terminal para a pasta raiz deste projeto.
2.  Execute o seguinte comando para compilar todos os módulos e gerar o ficheiro executável `.jar`:

    ```bash
    mvn clean package
    ```
Este comando irá descarregar as dependências, compilar o código-fonte e empacotar a aplicação num único ficheiro "uber-jar" localizado em `library-kernel/target/`.

## 3. Instruções de Execução

Após a compilação ser bem-sucedida, para executar a aplicação, utilize o seguinte comando a partir da pasta raiz do projeto:

```bash
java -jar library-kernel/target/library-kernel-1.0.0.jar
```

## 4. Funcionalidades

Após a execução bem sucedida, a janela principal da aplicação será iniciada, e os plugins de funcionalidades serão carregados e exibidos no menu lateral.

Funcionalidades Implementadas
O sistema é composto por um núcleo (Kernel) e os seguintes plugins carregados dinamicamente:


Gestão de Utilizadores: CRUD completo para utilizadores do sistema (cadastro, edição, exclusão e listagem).


Gestão de Livros: CRUD completo para o acervo de livros.


Gestão de Empréstimos: Permite o registo de novos empréstimos e devoluções , validando a disponibilidade de cópias.



Relatório: Exibe um relatório de todos os livros atualmente emprestados, com as informações relevantes
