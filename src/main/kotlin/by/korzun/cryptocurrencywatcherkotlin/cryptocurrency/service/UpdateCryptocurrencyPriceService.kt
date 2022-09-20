package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.dto.CoinLoreDTO
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository.MongoCryptocurrencyRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UpdateCryptocurrencyPriceService(
  private val cryptocurrencyRepository: MongoCryptocurrencyRepository,
) {

  private val coinLoreApi = "https://api.coinlore.net/api/ticker/?id="
  private val logger: Logger = LoggerFactory.getLogger(UpdateCryptocurrencyPriceService::class.java)

  @Async
  @Scheduled(fixedRate = 4000)
  fun updateCryptocurrencyPrices() {
    appendCoinIdsToUrl().subscribe { url ->
      WebClient
        .create(url)
        .get()
        .retrieve()
        .bodyToFlux(CoinLoreDTO::class.java).flatMap { coinLoreDto ->
          cryptocurrencyRepository.getCryptocurrencyBySymbol(coinLoreDto.symbol).flatMap { cryptoToUpdate ->
            cryptoToUpdate.price = coinLoreDto.price_usd
            cryptocurrencyRepository.save(cryptoToUpdate).map {
              logger.info(it.toString())
            }
          }
        }.subscribe()
    }
  }

  private fun appendCoinIdsToUrl(): Mono<String> {
    var coinLoreApiUrl = StringBuffer(coinLoreApi)
    return getListOfCoinIds().doOnNext { list ->
      list.map { id ->
        coinLoreApiUrl.append("$id,")
      }
    }.map { String(coinLoreApiUrl.deleteCharAt(coinLoreApiUrl.length - 1)) }
  }

  private fun getListOfCoinIds(): Mono<List<Int>> {
    val fluxOfCoinIds = cryptocurrencyRepository.getAllCoinIds()
    return fluxOfCoinIds
      .collectList()
      .flatMap { list ->
        Mono.fromCallable {
          list.map { coinDto ->
            coinDto.coinId
          }
        }
      }
  }
}
