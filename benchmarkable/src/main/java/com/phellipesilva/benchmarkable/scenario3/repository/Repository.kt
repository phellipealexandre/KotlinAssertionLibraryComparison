package com.phellipesilva.benchmarkable.scenario3.repository

interface Repository {

    suspend fun getUsers(): List<String>

}