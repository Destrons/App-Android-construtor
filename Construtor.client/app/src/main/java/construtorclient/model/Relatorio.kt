package com.construtorclient.model

data class Relatorio(
    val totalObras: Int = 0,
    val totalGasto: Double = 0.0,
    val obrasConcluidas: Int = 0,
    val obrasCanceladas: Int = 0,
    val prestadoresContratados: Int = 0,
    val mediaAvaliacao: Float = 0f
)