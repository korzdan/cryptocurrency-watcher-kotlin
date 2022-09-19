package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service.CryptocurrencyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cryptocurrencies")
class CryptocurrencyController(
  private val cryptocurrencyService: CryptocurrencyService
) {

  @GetMapping("/{id}")
  fun getCryptocurrencyByCoinId(@PathVariable id: Int) = cryptocurrencyService.getCryptocurrencyByCoinId(id)

  @PostMapping
  fun saveNewCryptocurrency(@RequestParam symbol: String, @RequestParam id: Int) =
    cryptocurrencyService.registerNewCryptocurrency(id, symbol)
}
