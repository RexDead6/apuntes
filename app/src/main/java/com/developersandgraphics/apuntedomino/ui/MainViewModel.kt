package com.developersandgraphics.apuntedomino.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersandgraphics.apuntedomino.ApunteDominoApp
import com.developersandgraphics.apuntedomino.back.MyDataStore
import com.developersandgraphics.apuntedomino.back.models.Game
import com.developersandgraphics.apuntedomino.back.models.Score
import com.developersandgraphics.apuntedomino.back.models.Team
import com.developersandgraphics.apuntedomino.back.services.HomeServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val application: ApunteDominoApp
) : ViewModel() {

    //  Confirm Dialog

    private val _isShowCancel = MutableLiveData<Boolean>(false)
    val isShowCancel: LiveData<Boolean> = _isShowCancel

    private val _isShowFinish = MutableLiveData<Boolean>(false)
    val isShowFinish: LiveData<Boolean> = _isShowFinish

    // Progress Dialog

    private val _isProgress = MutableLiveData<Boolean>(false)
    val isProgress: LiveData<Boolean> = _isProgress

    // Dialog message
    private val _messageDialog = MutableLiveData<String>("")
    val messageDialog: LiveData<String> = _messageDialog

    // Dialog new Game
    private val _inputTeam1 = MutableLiveData<String>("")
    val inputTeam1: LiveData<String> = _inputTeam1

    private val _inputTeam2 = MutableLiveData<String>("")
    val inputTeam2: LiveData<String> = _inputTeam2

    private val _inputPoints = MutableLiveData<String>("")
    val inputPoints: LiveData<String> = _inputPoints

    private val _isButtonDialogEnabled = MutableLiveData<Boolean>(false)
    val isButtonDialogEnabled: LiveData<Boolean> = _isButtonDialogEnabled

    // Dialog add Points

    private val _isShowAddDialog = MutableLiveData<Boolean>(false)
    val isShowAddDialog: LiveData<Boolean> = _isShowAddDialog

    private val _inputPointTeam1 = MutableLiveData<String>("")
    val inputPointTeam1: LiveData<String> = _inputPointTeam1

    private val _inputPointTeam2 = MutableLiveData<String>("")
    val inputPointTeam2: LiveData<String> = _inputPointTeam2

    private val _isButtonAddEnabled = MutableLiveData<Boolean>(false)
    val isButtonAddEnabled: LiveData<Boolean> = _isButtonAddEnabled

    // In game vars

    private val _team1 = MutableLiveData<Team>(Team())
    val team1: LiveData<Team> = _team1

    private val _team2 = MutableLiveData<Team>(Team())
    val team2: LiveData<Team> = _team2

    private val _game = MutableLiveData<Game>(Game())
    val game: LiveData<Game> = _game

    init {
        val storage = MyDataStore(application.applicationContext)
        val services = HomeServices()
        viewModelScope.launch {
            val value: String = storage.getProperty(MyDataStore.ID_GAMES_KEY).first()
            if (value != ""){
                _isProgress.value = true
                val response = services.doCurrentGame(value)
                if (response.status){
                    _game.value = response.data
                    _team1.value = Team(response.data.team_a)
                    _team2.value = Team(response.data.team_b)
                    _game.value!!.isPlay = true

                    for (score in _game.value!!.games_scores){
                        if (score.status == "ACTIVO") {
                            _team1.value!!.points += score.score_a
                            _team2.value!!.points += score.score_b
                        }
                    }
                }
                _isProgress.value = false
            }
        }
    }

    fun onChangeFormRegister(nameTeam1: String, nameTeam2: String, maxPoints: String) {
        _inputTeam1.value = nameTeam1
        _inputTeam2.value = nameTeam2
        _inputPoints.value = maxPoints
        _isButtonDialogEnabled.value = _inputTeam1.value?.isNotEmpty() == true &&
                _inputTeam2.value?.isNotEmpty() == true &&
                _inputPoints.value?.isNotEmpty() == true
    }

    fun onClickDialogRegister() {
        val services = HomeServices()
        val storage = MyDataStore(application.applicationContext)
        _isProgress.value = true
        //viewModelScope.launch {
            /*val response = services.doNewGame(
                _inputTeam1.value!!,
                _inputTeam2.value!!,
                _inputPoints.value?.toInt() ?: 0
            )
            _game.value!!.isPlay = response.status*/
            //if (response.status) {
                //_game.value!!.id_games = response.data.id_games
                _game.value!!.isPlay = true
                _game.value!!.total_score = _inputPoints.value!!.toInt()
                _team1.value = Team(_inputTeam1.value!!)
                _team2.value = Team(_inputTeam2.value!!)
                _game.value!!.id_games = 1

                _inputTeam1.value = ""
                _inputTeam2.value = ""
                _inputPoints.value = ""
                //storage.saveProperty(MyDataStore.ID_GAMES_KEY, response.data.id_games.toString())
            //}
            _isProgress.value = false
        //}
    }

    fun showDialog(type:String) {
        if (type == "CANCEL")
            _isShowCancel.value = !_isShowCancel.value!!
        if (type == "FINISH")
            _isShowFinish.value = !_isShowFinish.value!!
        if (type == "ADD"){
            if (
                (_team1.value!!.points >= _game.value!!.total_score
                        || _team2.value!!.points >= _game.value!!.total_score) &&
                !_isShowAddDialog.value!!
            ){
                _messageDialog.value = "Uno de los equipos ya contiene el máximo de puntos"
                return
            }
            _isShowAddDialog.value = !_isShowAddDialog.value!!
        }
    }

    fun onChangeAddPoints(team1: String, team2: String) {
        _inputPointTeam1.value = team1
        _inputPointTeam2.value = team2
        _isButtonAddEnabled.value = team1.isNotEmpty() && team2.isNotEmpty()
    }

    fun onClickAddPoints() {
        val services = HomeServices()
        _isProgress.value = true
        //viewModelScope.launch {
            /*val response = services.doNewScore(
                _inputPointTeam1.value!!.toInt(),
                _inputPointTeam2.value!!.toInt(),
                _game.value!!.id_games
            )*/
            //if (response.status) {
                //_game.value!!.games_scores.add(response.data)
                //_team1.value!!.points += response.data.score_a
                //_team2.value!!.points += response.data.score_b

                _team1.value!!.points += _inputPointTeam1.value!!.toInt()
                _team2.value!!.points += _inputPointTeam2.value!!.toInt()
                _game.value!!.games_scores.add(Score(
                    _inputPointTeam1.value!!.toInt(),
                    _inputPointTeam2.value!!.toInt(),
                    "ACTIVO",
                    Random.nextInt()
                ))

                _inputPointTeam1.value = ""
                _inputPointTeam2.value = ""
                _isShowAddDialog.value = false
            //}
            _isProgress.value = false
        //}
    }

    fun onClickDeleteScore(score: Score) {
        val services = HomeServices()
        _isProgress.value = true
        //viewModelScope.launch {
        //    val response = services.doUpdateScore(score.id_games_scores)
        //    if (response.status) {
                _team1.value!!.points -= score.score_a
                _team2.value!!.points -= score.score_b

                var newScores: ArrayList<Score> = ArrayList()

                for (_score: Score in _game.value!!.games_scores) {
                    if (_score.id_games_scores == score.id_games_scores) {
                        _score.status = "INACTIVO"
                    }
                    newScores.add(_score)
                }
                _game.value!!.games_scores = newScores
            //}
            _isProgress.value = false
        //}
    }

    fun updateGame(status: String) {
        if (
            (_team1.value!!.points < _game.value!!.total_score &&
                    _team2.value!!.points < _game.value!!.total_score) &&
            status == "FINALIZADO"
        ) {
            _messageDialog.value = "Ningun equipo ha llegado a la puntuacion máxima"
            return
        }

        val services = HomeServices()
        val storage = MyDataStore(application.applicationContext)
        _isProgress.value = true
        //viewModelScope.launch {
        //    val response = services.doUpdateGame(_game.value!!.id_games, status)
        //    if (response.status){
                _team1.value = Team()
                _team2.value = Team()
                _game.value = Game()
                _isShowCancel.value = false
                _isShowFinish.value = false
        //        storage.saveProperty(MyDataStore.ID_GAMES_KEY, "")
        //    }
            _isProgress.value = false
        //}
    }

    fun clearMessageDialog(){
        _messageDialog.value = ""
        _isShowFinish.value = false
    }
}