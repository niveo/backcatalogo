# Sistema de Estudo Catalogo de Produtos

Esse sistema tem o intuito de mapear os produtos em um catalogo com suas cordenadas X e Y levando esses dados para um aplicativo android onde o cliente pode clicar no catalogo e detalhar o produto ou mesmo lançar ele em um pedido.

## Notas
Esse é um sistema composto em 3 partes
* [BackEnd](https://github.com/niveo/backcatalogo) Java Spring Boot
* [FrontEnd](https://github.com/niveo/froncatalogo) Angular
* [Aplicativo](https://github.com/niveo/appcatalogo) Android


### Banco de Dados
Inicie uma instância do banco mysql com o docker alterando as variáveis *CHANGE*
```
sudo docker run  -d -p 3306:3306 \ 
-e MYSQL_ROOT_PASSWORD=CHANGE \ 
-e MYSQL_DATABASE=CATALOGO -e MYSQL_USER=CHANGE \
-e MYSQL_PASSWORD=CHANGE mysql:latest
```

## Variaveis para ambiente em debug clonado
Informe as variáveis abaixo no eclipse ou altere em application.properties
- MYSQL_USER
- MYSQL_PASSWORD
- DIRETORIO_BASE_CATALOGO *Diretorio onde todo conteudo sera criado

#### Java
Java 18 requerido

#### Porta Aplicação
Mudar a porta que por padrão é 8080 inclua -Dserver.port=8081

## Testar a aplicação
```
java -DDIRETORIO_BASE_CATALOGO=CHANGE -DMYSQL_USER=CHANGE -DMYSQL_PASSWORD=CHANGE -jar backcatalogo-0.0.1-SNAPSHOT.jar
```

Entre com http://localhost:8080/ para visualizar a aplicação

O sistema criara alguns dados automaticamente para exemplificação.

