# SD - API sem usar framework

## Sobre
 - Foi desenvolvido em Java
 - Para facilitar o mapeamento das requisições foram desenvolvidas annotations semelhantes ao que vemos nos frameworks da stack.
    ## Annotations desenvolvidas
    #### De classe
    - @RequestMapping (Mapeia uma classe como um controller que será responsável por um conjuntos de requisições feitas ao recurso que ele é responsável)
    #### De métodos
    - @GetMapping (Mapeia um método da classe anotada com @RequestMapping para mapeamento das resquisições do tipo GET)
    - @PutMapping (Mapeia um método da classe anotada com @RequestMapping para mapeamento das resquisições do tipo PUT)
    - @PostMapping (Mapeia um método da classe anotada com @RequestMapping para mapeamento das resquisições do tipo POST)
    - @PatchMapping (Mapeia um método da classe anotada com @RequestMapping para mapeamento das resquisições do tipo PATCH)
    - @DeleteMapping (Mapeia um método da classe anotada com @RequestMapping para mapeamento das resquisições do tipo DELETE)
    #### De parâmetros
    - @PathParam (Mapeia parâmetros do tipo PATH ou seja /recurso/{iddorecuso}/acao pegando e adicionando ao parâmetro o valor de iddorecuso vindo na URL)
    - @QueryParam (Mapeia parâmetros do tipo QUERY ou seja /recurso?limit=10&pagina=0 pegando e adicionando ao parâmetro o valor de limit e pagina vindo na URL)
    - @RequestBody (Mapeia os valores do body vindo na requisição para o tipo do Objeto esperado como parâmetro)
    ## Estrutura
    ```
    ├───src
    │   ├───main
    │   │   └───java
    │   │       └───sd
    │   │           └───ufpi
    │   │               ├───application
    │   │               │   ├───controllers
    │   │               │   ├───domain
    │   │               │   │   ├───dto
    │   │               │   │   ├───enums
    │   │               │   │   ├───form
    │   │               │   │   └───model
    │   │               │   ├───repository
    │   │               │   ├───routine
    │   │               │   └───service
    │   │               └───core
    │   │                   ├───database
    │   │                   ├───exceptions
    │   │                   ├───rest
    │   │                   │   ├───anotations
    │   │                   │   ├───exceptions
    │   │                   │   └───types
    │   │                   └───utils
    │   └───test
    │       └───java
    │           └───sd
    │               └───ufpi
    ```
    - Application - Contém todo o código da aplicação em sentido de API
    - Core - Contém todas as demais implementações de annotations, parses e mappers que são utilizados pela application
## COMO TESTAR
## Como rodar - BACKEND
- Necessário
- 1° Certifique-se de ter o Java intalado, no minimo na versão 17
- 2° Certifique-se de ter o Maven instalado na máquina
- 3° Tenha banco de dados Postgres instalado
- Passos
    - 1° - Crie um banco de dados com qualquer nome - apisemframe
    - 2° - Adicione as configurações do banco no arquivo apisemframe.properties que está na raiz do projeto
    - 3° - rode o comando na pasta do projeto ```$mvn clean package```
    - 4° - Execute a aplicação executando a classe principal App.java
    - 5° - Teste a aplicação usando o método GET nesse endereço ```localhost:8080/user/bem-vindo```
    - 6° - <strong>Ao rodar a aplicação as tabelas e alguns registros serão persistidos no banco de dados. Para testar o login adicione usuario: sistema e senha: 123456</strong>
## Como rodar - FRONTEND
- Necessário
- 1° Certifique-se de ter o Node instalado, no minimo na versão 16
- 2° Certifique-se de ter YARN instalado globalmente
    - Caso não tenha, execute no projeto: ```$npm install -g yarn```
- Passos
    - 1° - Na raiz do projeto execute ```$yarn install``` para baixar a as dependências
    - 2° - Execute para rodar o projeto ```$yarn dev```
    - 3° - Teste a aplicação usando esse endereço ```localhost:3000```