package by.korzun.cryptocurrencywatcherkotlin.telegram.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class BotConfig(
    @Value("\${bot.name}") var botName: String,
    @Value("\${bot.token}") var token: String
)
