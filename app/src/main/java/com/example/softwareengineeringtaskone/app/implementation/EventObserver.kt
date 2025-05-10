package com.example.softwareengineeringtaskone.app.implementation

interface EventObserver {
    fun onDataChanged(source: String, data: Any)
}