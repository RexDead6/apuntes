package com.developersandgraphics.apuntedomino.back.services

import com.developersandgraphics.apuntedomino.back.models.Game
import com.developersandgraphics.apuntedomino.back.models.NewGame
import com.developersandgraphics.apuntedomino.back.models.NewScore
import com.developersandgraphics.apuntedomino.back.models.ResponseClient
import com.developersandgraphics.apuntedomino.back.models.Score
import com.developersandgraphics.apuntedomino.back.models.UpdateGame
import com.developersandgraphics.apuntedomino.back.models.UpdateScore
import com.developersandgraphics.apuntedomino.back.network.RequestInterface
import com.developersandgraphics.apuntedomino.back.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeServices{
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun doNewGame(team1: String, team2: String, total_score: Int): ResponseClient<Game>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RequestInterface::class.java).insertNewGame(NewGame(
                team1, team2, total_score
            ))
            response.body()!!
        }
    }

    suspend fun doCurrentGame(id_games: String): ResponseClient<Game>{
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(RequestInterface::class.java).getCurrentGame(id_games)
            response.body()!!
        }
    }

    suspend fun doNewScore(score1: Int, score2: Int, id_games: Int) : ResponseClient<Score>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RequestInterface::class.java).insertNewScore(NewScore(
                score1, score2, id_games
            ))
            response.body()!!
        }
    }

    suspend fun doUpdateScore(id_game_score: Int): ResponseClient<Score>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RequestInterface::class.java).updateScore(UpdateScore(id_game_score))
            response.body()!!
        }
    }

    suspend fun doUpdateGame(id_games: Int, status: String): ResponseClient<Void> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RequestInterface::class.java).updateGame(UpdateGame(id_games, status))
            response.body()!!
        }
    }
}