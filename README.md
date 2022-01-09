# tqi_evolution_backend_2021

## Solução: 
Minha sugestão para o caso apresentado é uma API-REST com Spring Boot, pois poderá ser consumida facilmente por qualquer cliente. Pensei neste modelo porque acredito que seria "relativamente" fácil implementar uma solução front-end para consumí-la usando, por exemplo, HTML, CSS e Ajax.

## Funcionamento:

    1 - possui em endpoint onde o cliente poderá se cadastrar no sistema da empresa de empréstimo. Não há nenhuma restrição a este endpoint, bastando apenas que sejam enviados, no formato json, todos os campos necessários para cadastro;

    2 - a partir do cadastro, o cliente precisará obrigatoriamente chamar o endpoint de login. Essa autenticação é feita com o email e senha do usuário, e uma vez identificado no banco de dados, será retornado um token jwt, contendo o nome do cliente e uma validade de 10 minutos;

    3 - uma vez que o cliente possui seu token de acesso, poderá consumir os outros endpoints, seja para editar seus dados, ou solicitar empréstimos, ou consultar todos os seus empréstimos, ou consultar os dados de determinado empréstimo. Esse token deverá ser enviado no cabeçalho de cada requisição que for efetuada;

    4 - além disso, para cada solicitação de empréstimo, a api verifica se atende as regras determinadas, que são: a) máximo de 60 parcelas; e, b)vencimento da primeira parcela estar no máximo a três meses contados a partir da data de solicitação;

    5 - se o cliente tentar acessar sem informar o token, o sistema recusará a solicitação, devolvendo uma mensagem no corpo da resposta alertando que deve ser feito um login para obter um token;

    6 - semelhante ao item anterior, quando o token expira (após 10 minutos), o ssitema recusa todas as requisições e devolve uma mensagem no corpo da resposta alertando que deve ser feito um login para obter um token válido.

## Sugestão de melhoria:

Uma melhoria que pode ser implementada, é criar endpoints para que os administradores do sistema possam excluir determinados clientes ou inativá-los e, também, possam gerenciar as solicitações de empréstimo (deferindo ou indeferindo-as).
 

 ## Tecnologias e ferramentas utilizadas:

    **Java:** a api foi implementa utilizando a linguagem Java, por meio da IDE InteliJ;

    **Spring Boot:** utilizei esse framework dada a facilidade de montar a estrutura do projeto inicial e adicição de novos start's se for o caso. Como gerenciador de dependências optei pelo Maven.

    **Postgres** como banco de dados;

    **Docker** para subir um container do PostgresSql, facilitando a configuração do ambiente. Após baixar as imagens do Postgres e pgadmin4, rodei os seguinte comandos no terminal:

    Criando uma rede para comunicação entres os container's:

    js  docker network create --driver bridge postgres-network

    
    Criando o container postgres: 

    js docker run --name marcos -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=docker --network=postgres-network -p 7777:5432 -V /tmp/database:/var/lib/postgresql/data postgres


    Criando o container para o pgadmin4, pois dessa forma fica mais para acompanhar tanto os comandos DDL, quanto os comandos DML:

    docker run --name pgadmin --network=postgres-network -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=seu_email_aqui@gmail.com" -e "PGADMIN_DEFAULT_PASSWORD=docker" -d dpage/pgadmin4








    
