package com.phellipesilva.benchmarkable.scenario2.processed

sealed class ProcessedItem(val value: String) {
    class Header(value: String): ProcessedItem(value)
    class SelectableItem(value: String): ProcessedItem(value)
}