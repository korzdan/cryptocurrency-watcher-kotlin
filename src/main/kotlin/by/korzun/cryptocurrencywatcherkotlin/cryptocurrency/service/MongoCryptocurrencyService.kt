package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository.MongoCryptocurrencyRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MongoCryptocurrencyService(
  private val cryptocurrencyRepository: MongoCryptocurrencyRepository,
) : CryptocurrencyService {

  override fun getCryptocurrencyByCoinId(id: Int): Mono<Cryptocurrency> {
    return cryptocurrencyRepository.getCryptocurrencyByCoinId(id)
  }

  override fun getCryptocurrencies(): Flux<Cryptocurrency> {
    return cryptocurrencyRepository.findAll();
  }

  override fun registerNewCryptocurrency(id: Int, symbol: String): Mono<Cryptocurrency> {
    return cryptocurrencyRepository.save(Cryptocurrency(coinId = id, symbol = symbol))
  }

  override fun getCryptocurrencyBySymbol(symbol: String): Mono<Cryptocurrency> {
    return cryptocurrencyRepository.getCryptocurrencyBySymbol(symbol)
  }
}
