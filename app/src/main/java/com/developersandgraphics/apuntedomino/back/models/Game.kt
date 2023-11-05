package com.developersandgraphics.apuntedomino.back.models

data class Game(
    var total_score: Int = 200,
    var games_scores: ArrayList<Score> = ArrayList(),
    var isPlay: Boolean = false,
    var id_games: Int = 0,
    var status: String = "INICIADO",
    var team_a: String = "",
    var team_b: String = ""
)
