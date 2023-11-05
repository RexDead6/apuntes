package com.developersandgraphics.apuntedomino.back.models

data class UpdateScore(
    val id_games_scores: Int, val status: String = "INACTIVO"
)
