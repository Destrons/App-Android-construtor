package com.construtorclient.model

data class Contrato(
    val clienteId: String = "",
    val prestadorId: String = "",
    val status: String = "pendente",
    val valor: Double = 0.0,
    val dataAssinatura: Long = System.currentTimeMillis()
)
