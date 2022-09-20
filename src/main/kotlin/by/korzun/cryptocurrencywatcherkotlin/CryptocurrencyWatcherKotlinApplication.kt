package by.korzun.cryptocurrencywatcherkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CryptocurrencyWatcherKotlinApplication

fun main(args: Array<String>) {
	runApplication<CryptocurrencyWatcherKotlinApplication>(*args)
}
