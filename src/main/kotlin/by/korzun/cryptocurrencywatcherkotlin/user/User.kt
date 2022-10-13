package by.korzun.cryptocurrencywatcherkotlin.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
class User(
    @Id
    var id: String? = null
)
