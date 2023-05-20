package com.example.currencyconvertor.domain.statesandevents

sealed class Events {
    data class FROM_SELECTED(val selected : String) : Events()
    data class TO_SELECTED(val selected: String) : Events()
   object CONVERT : Events()

    data class AMOUNT_ENTER(val amount: String) : Events()
}