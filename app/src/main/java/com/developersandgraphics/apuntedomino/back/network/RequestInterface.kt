package com.developersandgraphics.apuntedomino.back.network

import com.developersandgraphics.apuntedomino.back.models.Game
import com.developersandgraphics.apuntedomino.back.models.NewGame
import com.developersandgraphics.apuntedomino.back.models.NewScore
import com.developersandgraphics.apuntedomino.back.models.ResponseClient
import com.developersandgraphics.apuntedomino.back.models.Score
import com.developersandgraphics.apuntedomino.back.models.UpdateGame
import com.developersandgraphics.apuntedomino.back.models.UpdateScore
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RequestInterface {

    @POST("games")
    suspend fun insertNewGame(@Body newGame: NewGame): Response<ResponseClient<Game>>

    @GET("games/{games_id}")
    suspend fun getCurrentGame(@Path("games_id") games_id: String): Response<ResponseClient<Game>>

    @POST("games/score")
    suspend fun insertNewScore(@Body newScore: NewScore): Response<ResponseClient<Score>>

    @PUT("games/score/status")
    suspend fun updateScore(@Body updateScore: UpdateScore): Response<ResponseClient<Score>>

    @PUT("games/status")
    suspend fun updateGame(@Body updateGame: UpdateGame): Response<ResponseClient<Void>>
}