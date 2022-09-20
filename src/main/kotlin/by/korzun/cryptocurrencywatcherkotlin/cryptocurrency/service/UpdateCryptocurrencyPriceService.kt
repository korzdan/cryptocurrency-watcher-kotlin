package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository.MongoCryptocurrencyRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UpdateCryptocurrencyPriceService(
  private val cryptocurrencyRepository: MongoCryptocurrencyRepository,
  private val logger: Logger = LoggerFactory.getLogger(UpdateCryptocurrencyPriceService::class.java)
) {

  private val coinLoreApiUrl = "https://api.coinlore.net/api/ticker/?id="

  @Async
  @Scheduled(fixedRate = 3000)
  fun updateCryptocurrencyPrices() {
    val coinLoreApi = getUrlOfGettingInfoAboutAllCoins()
    logger.info(coinLoreApi)

    val monoFromListOfCryptocurrencies = WebClient
      .create(coinLoreApi)
      .get()
      .retrieve()
      .bodyToMono(object : ParameterizedTypeReference<List<Cryptocurrency>>() {})

    monoFromListOfCryptocurrencies.flatMap { list ->
      Mono.fromCallable {
        list.map { cryptocurrency ->
          cryptocurrencyRepository.save(cryptocurrency)
        }
      }
    }.subscribe()
  }

  private fun getUrlOfGettingInfoAboutAllCoins(): String {
    val fluxOfCoinIds = cryptocurrencyRepository.getAllCoinIds()
    val urlWithAllIds = StringBuffer(coinLoreApiUrl)

    val listOfMono = fluxOfCoinIds
      .collectList()
      .flatMap { list ->
        Mono.fromCallable {
          list.map { coinDto ->
            coinDto.coinId
          }
        }
      }

    listOfMono.map { list ->
      for (id in list) {
        urlWithAllIds.append("$id,")
        logger.info(urlWithAllIds.toString())
      }
    }.subscribe()

    return String(urlWithAllIds)
  }
}
