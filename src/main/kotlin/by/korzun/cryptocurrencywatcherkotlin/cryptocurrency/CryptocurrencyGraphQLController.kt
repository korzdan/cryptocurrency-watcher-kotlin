package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service.CryptocurrencyService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class CryptocurrencyGraphQLController(
  private val cryptocurrencyService: CryptocurrencyService
) {

  @QueryMapping("cryptocurrencyByCoinId")
  fun getCryptocurrencyByCoinId(@Argument coinId: Int): Mono<Cryptocurrency> {
    return cryptocurrencyService.getCryptocurrencyByCoinId(coinId);
  }

  @QueryMapping("cryptocurrencies")
  fun getAllCryptocurrencies(): Flux<Cryptocurrency> {
    return cryptocurrencyService.getCryptocurrencies()
  }

  @MutationMapping("createCryptocurrency")
  fun createCryptocurrency(@Argument symbol: String, @Argument coinId: Int): Mono<Cryptocurrency> {
    return cryptocurrencyService.registerNewCryptocurrency(coinId, symbol)
  }
}
