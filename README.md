
## Para testar a API

### Acordando a criança

docker-compose up --build



### Observe que a ordem é importanto, porque o UUID da inserção vai ser usado para o soft delete e para recuperar o cupom do BD

### Inserir um cupom
```shell
curl --location 'http://localhost:8080/coupon' \
--header 'Content-Type: application/json' \
--data '{
  "code": "123456",
  "description": "Cupom de desconto especial",
  "discountValue": 0.6,
  "expirationDate": "2027-12-31T23:59:59",
  "published": true
}'
```

### Soft delete de um cumpom
```shell
curl --location --request DELETE 'http://localhost:8080/coupon/d24e0381-70a5-44bd-8696-888ebe41db26'
```
### Criar um novo cumpom
```shell
curl --location 'http://localhost:8080/coupon/d24e0381-70a5-44bd-8696-888ebe41db26'
```


