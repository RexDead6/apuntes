package com.developersandgraphics.apuntedomino.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.developersandgraphics.apuntedomino.R
import com.developersandgraphics.apuntedomino.back.models.Game
import com.developersandgraphics.apuntedomino.back.models.Score
import com.developersandgraphics.apuntedomino.back.models.Team
import com.developersandgraphics.apuntedomino.ui.theme.ApunteDominoTheme
import com.developersandgraphics.apuntedomino.ui.theme.DarkGreen
import com.developersandgraphics.apuntedomino.ui.theme.Green
import com.developersandgraphics.apuntedomino.ui.theme.Orange
import com.developersandgraphics.apuntedomino.ui.theme.Primary
import com.developersandgraphics.apuntedomino.ui.theme.Red
import com.developersandgraphics.apuntedomino.ui.theme.Transparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApunteDominoTheme {
                HomeScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(mainViewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundHome()
            Body(mainViewModel)
        }
    }
}

@Composable
fun Body(mainViewModel: MainViewModel) {

    val game: Game by mainViewModel.game.observeAsState(initial = Game())
    val team1: Team by mainViewModel.team1.observeAsState(initial = Team())
    val team2: Team by mainViewModel.team2.observeAsState(initial = Team())
    val inputTeam1: String by mainViewModel.inputTeam1.observeAsState("")
    val inputTeam2: String by mainViewModel.inputTeam2.observeAsState("")
    val inputPoints: String by mainViewModel.inputPoints.observeAsState("")
    val isButtonDialog: Boolean by mainViewModel.isButtonDialogEnabled.observeAsState(false)
    val isShowAdd: Boolean by mainViewModel.isShowAddDialog.observeAsState(false)
    val isButtonAdd: Boolean by mainViewModel.isButtonAddEnabled.observeAsState(false)
    val inputScore1: String by mainViewModel.inputPointTeam1.observeAsState("")
    val inputScore2: String by mainViewModel.inputPointTeam2.observeAsState("")
    val messageDialog: String by mainViewModel.messageDialog.observeAsState(initial = "")
    val isShowCancel: Boolean by mainViewModel.isShowCancel.observeAsState(false)
    val isShowFinis: Boolean by mainViewModel.isShowFinish.observeAsState(false)
    val isProgress: Boolean by mainViewModel.isProgress.observeAsState(initial = false)

    LoadingDialog(isProgress)

    StartDialog(
        show = !game.isPlay,
        isButtonDialog,
        inputTeam1,
        inputTeam2,
        inputPoints,
        { s: String, s1: String, s2 ->
            mainViewModel.onChangeFormRegister(s, s1, s2)
        },
        { mainViewModel.onClickDialogRegister() }
    )

    AddDialog(
        show = isShowAdd,
        enabledButton = isButtonAdd,
        input1 = inputScore1,
        input2 = inputScore2,
        onChangeForm = { input1, input2 -> mainViewModel.onChangeAddPoints(input1, input2) },
        onClickButton = { mainViewModel.onClickAddPoints() },
        onDismiss = { mainViewModel.showDialog("ADD") }
    )

    MessageDialog(
        show = messageDialog != "",
        onClickButton = { mainViewModel.clearMessageDialog() },
        message = messageDialog
    )

    ConfirmDialog(
        show = isShowCancel,
        message = "¿seguro desea cancelar e inválidar este juego?",
        onClickAceptar = { mainViewModel.updateGame("CANCELADO") },
        onClickCancel = { mainViewModel.showDialog("CANCEL") }
    )

    ConfirmDialog(
        show = isShowFinis,
        message = "¿seguro desea finalizar este juego?",
        onClickAceptar = { mainViewModel.updateGame("FINALIZADO") },
        onClickCancel = { mainViewModel.showDialog("FINISH") }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Points(game.total_score)
        Spacer(modifier = Modifier.size(20.dp))
        Row(Modifier.fillMaxWidth()) {
            CardTeam(Modifier.weight(1f), team1, game.total_score, "1", Green)
            Spacer(modifier = Modifier.size(8.dp))
            CardTeam(Modifier.weight(1f), team2, game.total_score, "2", Orange)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Matches(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), game.games_scores,
            onDeleteScore = { mainViewModel.onClickDeleteScore(it) }
        )
        Spacer(modifier = Modifier.size(20.dp))
        Footer(Modifier, onShowDialog = { mainViewModel.showDialog(it) })
    }
}

@Composable
fun Footer(modifier: Modifier, onShowDialog: (String) -> Unit) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Transparent
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledIconButton(
                    onClick = { onShowDialog("CANCEL") },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel button",
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledIconButton(
                    onClick = { onShowDialog("ADD") },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add button",
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledIconButton(
                    onClick = { onShowDialog("FINISH") },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = DarkGreen
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Check button",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Matches(modifier: Modifier, matches: List<Score>, onDeleteScore: (Score) -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Transparent
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Puntaje",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn {
                items(matches) {
                    CardItem(score = it, onDeleteScore)
                }
            }
        }
    }
}

@Composable
fun CardItem(score: Score, onClickDelete: (Score) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = score.score_a.toString(),
            fontWeight = FontWeight.Bold,
            color = if (score.status == "ACTIVO") Color.White else Color.Gray,
            fontSize = 25.sp,
            textDecoration = if (score.status == "ACTIVO") TextDecoration.None else TextDecoration.LineThrough
        )
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
                .background(if (score.status == "ACTIVO") Color.White else Color.Gray)
        )
        Text(
            text = score.score_b.toString(),
            fontWeight = FontWeight.Bold,
            color = if (score.status == "ACTIVO") Color.White else Color.Gray,
            fontSize = 25.sp,
            textDecoration = if (score.status == "ACTIVO") TextDecoration.None else TextDecoration.LineThrough
        )
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close item",
            tint = if (score.status == "ACTIVO") Red else Color.Gray,
            modifier = Modifier.clickable {
                if (score.status == "ACTIVO") {
                    onClickDelete(score)
                }
            }
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun CardTeam(modifier: Modifier, team: Team, totalPoints: Int, number: String, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Transparent
        )
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Equipo #$number",
                fontSize = 18.sp,
                color = color,
                style = TextStyle(
                    baselineShift = BaselineShift.Superscript
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = team.name,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = "${team.points}/${totalPoints}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Points(totalPoints: Int) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Transparent
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Column(
                Modifier
                    .weight(1f)
            ) {
                Text(text = "Puntos", fontSize = 24.sp, color = Color.White)
                Text(
                    text = "$totalPoints",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    color = Color.White
                )
            }

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(text = "Equipos", fontSize = 24.sp, color = Color.White)
                Text(
                    text = "2",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BackgroundHome() {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background app",
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartDialog(
    show: Boolean,
    enabledButton: Boolean,
    input1: String,
    input2: String,
    input3: String,
    onChangeForm: (String, String, String) -> Unit,
    onClickButton: () -> Unit
) {
    //if (false) {
    if (show) {
        Dialog(onDismissRequest = { }) {
            Card {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Inicie una nueva partida!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = input1,
                        onValueChange = { onChangeForm(it, input2, input3) },
                        label = { Text(text = "Nombre Equipo #1") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        )
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    OutlinedTextField(
                        value = input2,
                        onValueChange = { onChangeForm(input1, it, input3) },
                        label = { Text(text = "Nombre Equipo #2") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        )
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    OutlinedTextField(
                        value = input3,
                        onValueChange = { onChangeForm(input1, input2, it) },
                        label = { Text(text = "Puntuación máxima") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { onClickButton() }, enabled = enabledButton) {
                        Text(text = "Crear nuevo juego")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
    show: Boolean,
    enabledButton: Boolean,
    input1: String,
    input2: String,
    onChangeForm: (String, String) -> Unit,
    onClickButton: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Card {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Agrega la puntuacion!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = input1,
                        onValueChange = { onChangeForm(it, input2) },
                        label = { Text(text = "Puntos equipo #1") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    OutlinedTextField(
                        value = input2,
                        onValueChange = { onChangeForm(input1, it) },
                        label = { Text(text = "Puntos equipo #2") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { onClickButton() }, enabled = enabledButton) {
                        Text(text = "Registrar puntuacion")
                    }
                }
            }
        }
    }
}

@Composable
fun MessageDialog(show: Boolean, message: String, onClickButton: () -> Unit) {
    if (show) {
        Dialog(onDismissRequest = { onClickButton() }) {
            Card {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextButton(onClick = { onClickButton() }) {
                        Text(text = "Aceptar")
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    show: Boolean,
    message: String,
    onClickAceptar: () -> Unit,
    onClickCancel: () -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onClickCancel() }) {
            Card {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { onClickAceptar() }) {
                            Text(text = "Aceptar")
                        }
                        TextButton(onClick = { onClickCancel() }) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog(show: Boolean) {
    if (show) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }
}