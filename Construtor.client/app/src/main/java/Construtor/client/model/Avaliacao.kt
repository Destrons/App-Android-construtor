package com.Construtor.client.model

data class Avaliacao(
    val clienteId: String = "",
    val prestadorId: String = "",
    val nota: Float = 0f,
    val comentario: String = "",
    val timestamp: Long = System.currentTimeMillis()
)