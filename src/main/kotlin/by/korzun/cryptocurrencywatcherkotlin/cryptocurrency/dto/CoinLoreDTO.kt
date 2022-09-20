package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.dto

data class CoinLoreDTO(
  val symbol: String,
  val price_usd: Double
) {
  override fun toString(): String {
    return "CoinLoreDTO(symbol='$symbol', price=$price_usd)"
  }
}
