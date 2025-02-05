package com.Construtor.client.model

data class HistoricoServico(
    val servicoId: String = "",
    val prestadorId: String = "",
    val clienteId: String = "",
    val tipoServico: String = "",
    val descricao: String = "",
    val dataInicio: Long = 0,
    val dataFim: Long = 0,
    val status: String = "concluído",
    val valorFinal: Double = 0.0
)