package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CryptocurrencyService {
  fun getCryptocurrencies(): Flux<Cryptocurrency>
  fun getCryptocurrencyByCoinId(id: Int): Mono<Cryptocurrency>

  fun registerNewCryptocurrency(id: Int, symbol: String): Mono<Cryptocurrency>
}
