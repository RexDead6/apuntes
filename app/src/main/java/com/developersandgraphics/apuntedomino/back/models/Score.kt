package com.developersandgraphics.apuntedomino.back.models

data class Score(
    val score_a: Int,
    val score_b: Int,
    var status: String,
    val id_games_scores: Int
)
