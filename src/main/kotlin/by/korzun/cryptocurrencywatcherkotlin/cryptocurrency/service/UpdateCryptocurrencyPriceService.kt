package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.Cryptocurrency
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.dto.CoinLoreDTO
import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.repository.MongoCryptocurrencyRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UpdateCryptocurrencyPriceService(
  private val cryptocurrencyRepository: MongoCryptocurrencyRepository,
) {

  private val coinLoreApi = "https://api.coinlore.net/api/ticker/?id="

  @Async
  @Scheduled(fixedRate = 10000)
  fun updateCryptocurrencyPrices() {
    appendCoinIDsToURL().subscribe { url ->
      WebClient
        .create(url)
        .get()
        .retrieve()
        .bodyToFlux(CoinLoreDTO::class.java).flatMap { coinLoreDTO -> updateCryptocurrencyBySymbol(coinLoreDTO) }
        .subscribe()
    }
  }

  private fun updateCryptocurrencyBySymbol(coinLoreDto: CoinLoreDTO): Mono<Cryptocurrency> {
    return cryptocurrencyRepository.getCryptocurrencyBySymbol(coinLoreDto.symbol).flatMap { cryptoToUpdate ->
      cryptoToUpdate.price = coinLoreDto.priceUSD
      cryptocurrencyRepository.save(cryptoToUpdate)
    }
  }

  private fun appendCoinIDsToURL(): Mono<String> {
    val coinLoreApiUrl = StringBuffer(coinLoreApi)
    return getFluxOfCoinIDs().collectList().doOnNext { list ->
      list.map { id ->
        coinLoreApiUrl.append("$id,")
      }
    }.flatMap { Mono.fromCallable { deleteLastCommaFromURL(coinLoreApiUrl) } }
  }

  private fun deleteLastCommaFromURL(url: StringBuffer): String {
    val indexOfLastComma = url.length - 1
    return String(url.deleteCharAt(indexOfLastComma))
  }

  private fun getFluxOfCoinIDs(): Flux<Int> {
    val fluxOfCoinIDDTOs = cryptocurrencyRepository.getAllCoinIds()
    return fluxOfCoinIDDTOs.map { coinIdDto -> coinIdDto.coinId }
  }
}
