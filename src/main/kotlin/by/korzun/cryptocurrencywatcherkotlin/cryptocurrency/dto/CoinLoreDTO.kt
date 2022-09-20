package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CoinLoreDTO(
  val symbol: String,
  @JsonProperty("price_usd")
  val priceUSD: Double
)
