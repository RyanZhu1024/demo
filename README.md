# Vurb-Exercise

## run and test
`1. git clone`

`2. ./gradlew bootRun`

test link: [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/7c8f8db5b46b3e72a28a)

> handling timeout is located in `DeckService` `fetchCardsByDeckId`

> pageToken is handled by using JWT, which is implemented in `TokenService`

> to see the difference between optimized deck detail and unoptimized one, use `optimize` param when send request, which is set to 1 as optimized default and 0 denotes unoptimized.

## Notes
1. I assume that all the urls to be requestd are valid. All decks and cards exist. Since it's a prototype, 404 error is not handled.
2. I created 100 decks in the in-mem sql, so the estimatedResult will alwasy stay at 100
