package com.phellipesilva.benchmarkable.scenario3.repository

import kotlinx.coroutines.delay

class FakeRepository : Repository {

    private var users: List<String> = listOf()
    private var shouldReturnError: Boolean = false

    override suspend fun getUsers(): List<String> {
        delay(100)
        if (shouldReturnError) {
            throw Exception()
        }

        return users
    }

    fun setUsersResponse(users: List<String>) {
        this.users = users
    }

    fun enforceErrorOnFetchingOperation() {
        shouldReturnError = true
    }
}