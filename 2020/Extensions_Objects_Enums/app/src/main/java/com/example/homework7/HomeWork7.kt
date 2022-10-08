package com.example.homework7 

fun main() {

    val realWallet = Wallets.RealWalletClass()

    val virtualWallet = Wallets.VirtualWalletClass()

    realWallet.addMoneyToRealWallet(Currencies.RUR, 500)
    realWallet.addMoneyToRealWallet(Currencies.EUR, 300000)
    realWallet.addMoneyToRealWallet(Currencies.USD, 220000)

    virtualWallet.addMoneyToVirtualWallet(Currencies.RUR, 600.0)
    virtualWallet.addMoneyToVirtualWallet(Currencies.EUR, 900000.9)
    virtualWallet.addMoneyToVirtualWallet(Currencies.USD, 120010.5)

    if (realWallet.equals(virtualWallet)) println("wallets are the same")
    else println("wallets aren't the same")

    println("Virtual wallet contain ${virtualWallet.moneyInUSD().toFloat()} USD")
    println("Real wallet contain ${realWallet.moneyInUSD().toFloat()} USD")

}

fun Currencies.checkingCurrencies(): Boolean {
    return if (this == Currencies.nationalCurrency)
        return true
    else false
}

fun Currencies.convertToUSD(amount: Double): Double {
    return when (this) {
        Currencies.EUR -> amount / CurrencyConverter.USDEUR
        Currencies.RUR -> amount / CurrencyConverter.USDRUR
        Currencies.USD -> amount / CurrencyConverter.USDUSD
    }
}

