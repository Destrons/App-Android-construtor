package com.construtorclient.model

data class SuporteTicket(
    val ticketId: String = "",
    val usuarioId: String = "",
    val tipoUsuario: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val status: String = "aberto",
    val dataAbertura: Long = System.currentTimeMillis(),
    val dataFechamento: Long? = null
)