# MiniBank 🏦

Welcome to my little demo app of a REST API built with `Groovy + Micronauts!` and stored in H2 Data Base

Prerequisites `JDK 17`

Installation: Clone this repository, in terminal / cmd go to the main folder and run:
#### *In Windows these commands are executed in PowerShell and the rest in cmd
```shell
./gradlew run
```
#### To restart the app and delete the database simply Crt + C in the running task and rerun another see the command
For the tests:
```shell
./gradlew test
```
##### The test results can be found at:
/Users/YourName/mini_bank_main/build/reports/tests/test

In a new terminal while the application is running in the Background you can execute these commands (GET them through the browser you can also execute them through the URLs)
### Remember that the `ids` change if you execute these statements in a different order

## Functions of the application (Terminal on macOS, for Windows in the next section):

User registration

```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Fran"}' http://localhost:8080/users
```
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"user_name":"Luis"}' http://localhost:8080/users
```

Account creation (wallet)

```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets
```
```shell 
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_name":"Luis_Wallet", "amount": "0", "owner_id": "2"}' http://localhost:8080/wallets
```

Making a money deposit

http://localhost:8080/transactions/discover/3
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"0", "wallet_destination": "3", "quantity": "50"}' http://localhost:8080/transactions
```


Account visualization (wallet) --> Balance and movements (transactions)

Account Balance

http://localhost:8080/wallets/list

http://localhost:8080/wallets/3
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/wallets/3
```

Movements of that account as origin or destination

http://localhost:8080/transactions/discover/3
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/transactions/discover/3
```

Transfer from account A to account B

```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"3", "wallet_destination": "4", "quantity": "25"}' http://localhost:8080/transactions
```
```shell
curl -X POST -H "Content-Type: application/json" \
    -d '{"wallet_source":"4", "wallet_destination": "3", "quantity": "10"}' http://localhost:8080/transactions
```


** Bonus **

Display User

http://localhost:8080/users/list

http://localhost:8080/users/1
```shell
curl -X GET -H "Content-Type: application/json" \
  http://localhost:8080/users/1
```

Update user 

```shell
 curl -X PUT -H "Content-Type: application/json" \
    -d '{"user_id":"1", "user_name":"Antonio"}' http://localhost:8080/users
```

Update Wallet

```shell
curl -X PUT -H "Content-Type: application/json" \
    -d '{"wallet_id":"3", "wallet_name":"Fran_Wallet", "amount": "0", "owner_id": "1"}' http://localhost:8080/wallets
```

---

## Functions of the application (cmd in Windows):

In a new terminal while the application is running in the Background you can execute these commands (GET them through the browser you can also execute them through the URLs)

User registration

```shell
curl -X POST -H "Content-Type: application/json" -d "{""user_name"":""Fran""}" http://localhost:8080/users
```
```shell
curl -X POST -H "Content-Type: application/json" -d "{""user_name"":""Luis""}" http://localhost:8080/users
```



Account creation (wallet)
```shell
curl -X POST -H "Content-Type: application/json" -d "{""wallet_name"":""Fran_Wallet"", ""amount"": ""0"", ""owner_id"": ""1""}" http://localhost:8080/wallets
```
```shell 
curl -X POST -H "Content-Type: application/json" -d "{""wallet_name"":""Luis_Wallet"", ""amount"": ""0"", ""owner_id"": ""2""}" http://localhost:8080/wallets
```


Making a money deposit
```shell
curl -X POST -H "Content-Type: application/json" -d "{""wallet_source"":""0"", ""wallet_destination"": ""3"", ""quantity"": ""50""}" http://localhost:8080/transactions
```

Account visualization (wallet) --> Balance and movements (transactions)

Account Balance

http://localhost:8080/wallets/list

http://localhost:8080/wallets/3
```shell
curl -X GET -H "Content-Type: application/json" http://localhost:8080/wallets/3
```

Movements of that account as origin or destination

http://localhost:8080/transactions/discover/3
```shell
curl -X GET -H "Content-Type: application/json" http://localhost:8080/transactions/discover/3
```

Transfer from account A to account B
```shell
curl -X POST -H "Content-Type: application/json" -d "{""wallet_source"":""3"", ""wallet_destination"": ""4"", ""quantity"": ""25""}" http://localhost:8080/transactions
```
```shell
curl -X POST -H "Content-Type: application/json" -d "{""wallet_source"":""4"", ""wallet_destination"": ""3"", ""quantity"": ""10""}" http://localhost:8080/transactions
```


** Bonus **

Display User

http://localhost:8080/users/list

http://localhost:8080/users/1
```shell
curl -X GET -H "Content-Type: application/json" http://localhost:8080/users/1
```

Update user 
```shell
 curl -X PUT -H "Content-Type: application/json" -d "{""user_id"":""1"", ""user_name"":""Antonio""}" http://localhost:8080/users
```

Update wallet
```shell
curl -X PUT -H "Content-Type: application/json" -d "{""wallet_id"":""3"", ""wallet_name"":""Fran_Wallet"", ""amount"": ""0"", ""owner_id"": ""1""}" http://localhost:8080/wallets
```

