package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface MongoCryptocurrencyRepository
  : ReactiveCrudRepository<Cryptocurrency, String> {

  fun getCryptocurrencyByCoinId(id: Int): Mono<Cryptocurrency>
}
