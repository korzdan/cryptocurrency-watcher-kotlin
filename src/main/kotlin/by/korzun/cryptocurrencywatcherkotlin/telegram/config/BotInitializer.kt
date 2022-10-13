package by.korzun.cryptocurrencywatcherkotlin.telegram.config

import by.korzun.cryptocurrencywatcherkotlin.telegram.service.CryptocurrencyWatcherTelegramBot
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class BotInitializer(val bot: CryptocurrencyWatcherTelegramBot) {

  @EventListener(ContextRefreshedEvent::class)
  fun init() {
    val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
    telegramBotsApi.registerBot(bot)
  }
}
