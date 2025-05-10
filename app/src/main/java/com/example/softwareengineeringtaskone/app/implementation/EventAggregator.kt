package com.example.softwareengineeringtaskone.app.implementation

import android.util.Log

object EventAggregator {
    private val observers = mutableListOf<EventObserver>()

    fun registerObserver(observer: EventObserver) {
        observers.add(observer)
    }

    fun unregisterObserver(observer: EventObserver) {
        observers.remove(observer)
    }

    fun notifyObservers(source: String, data: Any) {
        Log.d("EventAggregator", "Оповещение наблюдателей от источника: $source")
        observers.forEach { it.onDataChanged(source, data) }
    }
}