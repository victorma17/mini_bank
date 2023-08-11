# Mini Bank 🏦

For english version: [readmeENG](https://github.com/victorma17/mini_bank_v3/blob/main/readmeENG.md)

Bienvenido a mi pequeña app demo de una API REST construida con `Groovy + Micronauts!` y almacenada en memoria H2

Requisitos previo `JDK 17`

Insatalación: Clona este repositorio,en la terminal / cmd ves a la carpeta principal y ejecuta:
### *En Windows estos comandos de ejecucion en PowerShell y el resto en cmd
```shell
./gradlew run 
```
#### Para reiniciar la app y borrar la BBDD simplemente Crt + C en la tarea corriendo y reejecutar otra ver el comando
Para los test:
```shell
./gradlew test
```
##### Los resultados de los test los puedes encontrar en:
/Users/YourName/mini_bank_main/build/reports/tests/test

En una nueva terminal mientras corre la aplicación en Background puedes ejecutar estos comandos (los GET a través del navegador también puedes ejecutarlos a través de las URL)
### Recuerda que los `ids` cambian si ejecutas estas instrucciones en un orden diferente

## Funciones de la aplicacion (Terminal en macOS, para Windows en la siguiente seccion):

1. Registro usuario

```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Fran"}' http://localhost:8080/users
```
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Luis"}' http://localhost:8080/users
```

2. Creación de cuenta (wallet)
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets
```
```shell 
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Luis_Wallet", "amount": "0", "owner_id": "2"}' http://localhost:8080/wallets
```


3. Realización de depósito de dinero
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"0", "wallet_destination": "3", "quantity": "50"}' http://localhost:8080/transactions
```

4. Visualización de cuenta (wallet) --> Balance y movimientos (transactions)

4.1 Balance de la cuenta

http://localhost:8080/wallets/list

http://localhost:8080/wallets/3
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/wallets/3
```

4.2 Movimiento de esa cuenta como origen o destino

http://localhost:8080/transactions/discover/3
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/transactions/discover/3
```

5. Transferencia de una cuenta A a una cuenta B
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"3", "wallet_destination": "4", "quantity": "25"}' http://localhost:8080/transactions
```
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"4", "wallet_destination": "3", "quantity": "10"}' http://localhost:8080/transactions
```


** Extra **

Visualización de usuario

http://localhost:8080/users/list
http://localhost:8080/users/1
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/users/1
```

Actualizacion de usuario
```shell
 curl -X PUT -H "Content-Type: application/json" \
    -d '{"user_id":"1", "user_name":"Antonio"}' http://localhost:8080/users
```

Actualizacion de wallet
```shell
curl -X PUT -H "Content-Type: application/json" \
    -d '{"wallet_id":"3", "wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets
```
---

## Funciones de la aplicacion (cmd en Windows):

En una nueva terminal mientras corre la aplicación en Background puedes ejecutar estos comandos (los GET a través del navegador también puedes ejecutarlos a través de las URL)

1. Registro usuario

```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""user_name"":""Fran""}" http://localhost:8080/users
```
```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""user_name"":""Luis""}" http://localhost:8080/users
```

2. Creación de cuenta (wallet)
```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""wallet_name"":""Fran_Wallet"", ""amount"": ""0"", ""owner_id"": ""1""}" http://localhost:8080/wallets
```
```shell 
curl -X POST -H "Content-Type: application/json" \ -d "{""wallet_name"":""Luis_Wallet"", ""amount"": ""0"", ""owner_id"": ""2""}" http://localhost:8080/wallets
```

3. Realización de depósito de dinero
```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""wallet_source"":""0"", ""wallet_destination"": ""3"", ""quantity"": ""50""}" http://localhost:8080/transactions
```

4. Visualización de cuenta (wallet) --> Balance y movimientos (transactions)

4.1 Balance de la cuenta

http://localhost:8080/wallets/list

http://localhost:8080/wallets/3
```shell
curl -X GET -H "Content-Type: application/json" \ http://localhost:8080/wallets/3
```

4.2 Movimiento de esa cuenta como origen o destino

http://localhost:8080/transactions/discover/3
```shell
curl -X GET -H "Content-Type: application/json" \ http://localhost:8080/transactions/discover/3
```

5. Transferencia de una cuenta A a una cuenta B
```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""wallet_source"":""3"", ""wallet_destination"": ""4"", ""quantity"": ""25""}" http://localhost:8080/transactions
```
```shell
curl -X POST -H "Content-Type: application/json" \ -d "{""wallet_source"":""4"", ""wallet_destination"": ""3"", ""quantity"": ""10""}" http://localhost:8080/transactions
```

** Extra **

Visualización de usuario

http://localhost:8080/users/1
```shell
curl -X GET -H "Content-Type: application/json" \ http://localhost:8080/users/1
```

Actualizacion de usuario
```shell
 curl -X PUT -H "Content-Type: application/json" \ -d "{""user_id"":""1"", ""user_name"":""Antonio""}" http://localhost:8080/users
```

Actualizacion de wallet
```shell
curl -X PUT -H "Content-Type: application/json" \ -d "{""wallet_id"":""3"", ""wallet_name"":""Fran_Wallet"", ""amount"": ""0"", ""owner_id"": ""1""}" http://localhost:8080/wallets
```
