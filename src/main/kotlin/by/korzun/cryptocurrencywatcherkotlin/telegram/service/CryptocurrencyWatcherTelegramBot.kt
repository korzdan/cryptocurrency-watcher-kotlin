package by.korzun.cryptocurrencywatcherkotlin.telegram.service

import by.korzun.cryptocurrencywatcherkotlin.cryptocurrency.service.CryptocurrencyService
import by.korzun.cryptocurrencywatcherkotlin.telegram.config.BotConfig
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class CryptocurrencyWatcherTelegramBot(
    val botConfig: BotConfig,
    val cryptocurrencyService: CryptocurrencyService
) : TelegramLongPollingBot() {

  override fun getBotToken() = botConfig.token

  override fun getBotUsername() = botConfig.botName

  override fun onUpdateReceived(update: Update?) {
    when (update?.message?.text) {
      "/price" -> cryptocurrencyPriceRequested(update)
      cryptocurrencyService.getCryptocurrencyBySymbol(update?.message?.text)
    }
  }

  private fun cryptocurrencyPriceRequested(update: Update?) {
    sendMessage(update?.message?.chatId ?: 0, "Введите, пожалуйста, символ криптовалюты.")
  }

  private fun sendMessage(chatId: Long, textToSend: String) {
    val message = SendMessage()
    message.chatId = chatId.toString()
    message.text = textToSend
    execute(message)
  }
}
