package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.dto.CoinIDDTO
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface MongoCryptocurrencyRepository
  : ReactiveCrudRepository<Cryptocurrency, String> {

  fun getCryptocurrencyByCoinId(id: Int): Mono<Cryptocurrency>
  fun getCryptocurrencyBySymbol(symbol: String): Mono<Cryptocurrency>
  @Query(value = "{}", fields = "{coinId: 1, _id: 0}")
  fun getAllCoinIds(): Flux<CoinIDDTO>
}
