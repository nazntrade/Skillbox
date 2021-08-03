package com.example.homework7

sealed class Wallets    {

    class VirtualWalletClass : Wallets() {




        private var amountVirtUSD: Double = 0.0
        private var amountVirtRUR: Double = 0.0
        private var amountVirtEUR: Double = 0.0

        private var virtualWalletMap: MutableMap<Int, Double> = mutableMapOf(
            CurrencyConverter.USDEUR.toInt() to amountVirtEUR,
            CurrencyConverter.USDRUR.toInt() to amountVirtRUR,
            CurrencyConverter.USDUSD.toInt() to amountVirtUSD
        )

        fun addMoneyToVirtualWallet(currencies: Currencies, amount: Double) {
            when (currencies) {
                Currencies.RUR -> {
                    amountVirtUSD = +amount
                }
                Currencies.EUR -> {
                    amountVirtEUR = +amount
                }
                Currencies.USD -> {
                    amountVirtUSD = +amount
                }
            }
        }

        override fun moneyInUSD(): Double {
            return (amountVirtEUR / CurrencyConverter.USDEUR) +
                (amountVirtRUR / CurrencyConverter.USDRUR) + amountVirtUSD
        }
    }

    class RealWalletClass : Wallets() {

        var amountRealUSD = 0
        var amountRealRUR = 0
        var amountRealEUR = 0

        private var realWalletMap: MutableMap<Int, Int> = mutableMapOf(
            CurrencyConverter.USDEUR.toInt() to amountRealEUR,
            CurrencyConverter.USDRUR.toInt() to amountRealRUR,
            CurrencyConverter.USDUSD.toInt() to amountRealUSD
        )

        fun addMoneyToRealWallet(currencies: Currencies, amount: Int) {
            when (currencies) {
                Currencies.RUR ->
                    amountRealRUR =
                        ((amountRealRUR + amount) / CurrencyConverter.USDRUR).toInt()
                Currencies.EUR ->
                    amountRealEUR =
                        ((amountRealEUR + amount) / CurrencyConverter.USDEUR).toInt()
                Currencies.USD ->
                    amountRealUSD =
                        ((amountRealUSD + amount) / CurrencyConverter.USDUSD).toInt()
            }
        }

        override fun moneyInUSD(): Double {
            return (amountRealEUR / CurrencyConverter.USDEUR) +
                (amountRealRUR / CurrencyConverter.USDRUR) + amountRealUSD
        }
    }

    abstract fun moneyInUSD(): Double
}
