# ETHEREUM-FETCHER

Ethereum Fetcher is an n-tier Spring Boot application that leverages Spring Data and Spring Cloud OpenFeign. It exposes RESTful endpoints, providing an API to fetch Ethereum transaction data and a mechanism to save the requested data per application user. The application is structured into multiple layers — controller, service, and persistence, resulting in a flexible, loosely coupled, and easily maintainable codebase.

## Running the application

```bash
## Build docker image 
docker build -t ethfetcher .
## Run the container
docker run ethfetcher -d

or

## Run the app with gradle replacing "value" with actual values 
ETH_NODE_URL=value DB_CONNECTION_URL=value JWT_SECRET=value ./gradlew run
```

## API

### Fetch transactions

#### Request

`GET /app/eth?transactionHashes`

#### Response

```json
{
  "transactions": [
    {
      "transactionHash": "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b",
      "transactionStatus": 1,
      "blockHash": "0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2",
      "blockNumber": 6139707,
      "from": "0xa7d9ddbe1f17865597fbd27ec712455208b6b76d",
      "to": "0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb",
      "contractAddress": null,
      "logsCount": 0,
      "input": "0x68656c6c6f21",
      "value": "0xf3dbb76162000"
    }
  ],
  "error": null
}
```

or

```json
{
  "transactions": [],
  "error": {
    "message": "Unable to get data for transactions [0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944a]"
  }
}
```

### Get all saved transactions

#### Request

`GET /app/all`

#### Response

```json
{
  "transactions": [
    {
      "transactionHash": "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b",
      "transactionStatus": 1,
      "blockHash": "0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2",
      "blockNumber": 6139707,
      "from": "0xa7d9ddbe1f17865597fbd27ec712455208b6b76d",
      "to": "0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb",
      "contractAddress": null,
      "logsCount": 0,
      "input": "0x68656c6c6f21",
      "value": "0xf3dbb76162000"
    },
    {
      ...
    }
  ],
  "error": null
}
```

### Create JWT

#### Request

`POST /app/authenticate`

```json
{
  "username": "alice",
  "password": "alice"
}
```

#### Response

```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhbGljZSIsImlhdCI6MTcyNDUwNDE2Nn0.snZfRIaqgZna5paA9HHQGJm4-cDVVYT7YZFsFAK9ZGXbA8cek3jFrAjqZQMZjTA2"
}
```

### Get transactions per JWT

#### Request

`GET /app/my`

#### Response

```json
{
  "transactions": [
    {
      "transactionHash": "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b",
      "transactionStatus": 1,
      "blockHash": "0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2",
      "blockNumber": 6139707,
      "from": "0xa7d9ddbe1f17865597fbd27ec712455208b6b76d",
      "to": "0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb",
      "contractAddress": null,
      "logsCount": 0,
      "input": "0x68656c6c6f21",
      "value": "0xf3dbb76162000"
    }
  ],
  "error": null
}
```

