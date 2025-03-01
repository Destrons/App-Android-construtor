package com.construtorclient.model

data class Recompensa(
    val pontos: Int = 0,
    val nivel: String = "Iniciante",
    val premiosDisponiveis: List<String> = listOf()
)