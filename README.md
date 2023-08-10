
1. Registro usuario

curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Fran"}' http://localhost:8080/users

curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Luis"}' http://localhost:8080/users


2. Creación de cuenta (wallet)

curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets
    
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Luis_Wallet", "amount": "0", "owner_id": "2"}' http://localhost:8080/wallets


3. Realización de depósito de dinero

curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"0", "wallet_destination": "3", "quantity": "50"}' http://localhost:8080/transactions


4. Visualización de cuenta (wallet) --> Balance y movimientos (transactions)

4.1 Balance de la cuenta

http://localhost:8080/wallets/list

http://localhost:8080/wallets/3

curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/wallets/3


4.2 Movimiento de esa cuenta como origen o destino

http://localhost:8080/transactions/discover/3

curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/transactions/discover/3


5. Transferencia de una cuenta A a una cuenta B

curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"3", "wallet_destination": "4", "quantity": "25"}' http://localhost:8080/transactions

curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"4", "wallet_destination": "3", "quantity": "10"}' http://localhost:8080/transactions



** Extra **

Visualización de usuario

http://localhost:8080/users/1

curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/users/1


Actualizacion de usuario

 curl -X PUT -H "Content-Type: application/json" \
    -d '{"user_id":"1", "user_name":"Antonio"}' http://localhost:8080/users


Actualizacion de wallet

curl -X PUT -H "Content-Type: application/json" \
    -d '{"wallet_id":"3", "wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets

