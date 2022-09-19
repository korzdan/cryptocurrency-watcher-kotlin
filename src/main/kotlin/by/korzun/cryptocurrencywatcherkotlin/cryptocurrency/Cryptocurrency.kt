package by.korzun.cryptocurrencywatcherkotlin.cryptocurrency

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cryptocurrency")
class Cryptocurrency(
  @Id
  var id: String? = null,
  var coinId: Int = -1,
  var symbol: String = "",
  var price: Double = .0
)
