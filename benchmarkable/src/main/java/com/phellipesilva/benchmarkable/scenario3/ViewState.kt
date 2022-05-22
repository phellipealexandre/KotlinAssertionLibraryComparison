package com.phellipesilva.benchmarkable.scenario3

data class ViewState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val users: List<String> = listOf()
)
