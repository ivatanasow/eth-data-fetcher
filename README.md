# ETHEREUM-FETCHER

## Running the application

```bash
## Build docker image 
docker build -t limeapi .
## Run the container
docker run limeapi -d

or

## Run the app with gradle replacing "value" with actual values 
ETH_NODE_URL=value DB_CONNECTION_URL=value JWT_SECRET=value ./gradlew run
```

## API

### Fetch transactions

#### Request

`GET /lime/eth?transactionHashes`

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

`GET /lime/all`

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

`POST /lime/authenticate`

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

`GET /lime/my`

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

