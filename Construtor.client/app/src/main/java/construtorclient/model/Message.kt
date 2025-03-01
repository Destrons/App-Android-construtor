package com.construtorclient.model

data class Message(
    val remetenteId: String = "",
    val destinatarioId: String = "",
    val mensagem: String = "",
    val timestamp: Long = System.currentTimeMillis()
)