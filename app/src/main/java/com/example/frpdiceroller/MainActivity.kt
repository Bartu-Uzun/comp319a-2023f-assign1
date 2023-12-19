package com.example.frpdiceroller

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frpdiceroller.ui.theme.FRPDiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FRPDiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FRPDiceRollerApp()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FRPDiceRollerApp() {

    var diceList = remember { mutableStateListOf<Dice>() }
    Scaffold (
        bottomBar = { MyBottomAppBar(diceList) },
        topBar = {
            TopAppBar(
                title = { Text(text = "FRP Dice Roller") },
                actions = {
                    OutlinedButton(
                        onClick = { Roll(diceList) }
                    ) {
                        Text("Roll!")
                        
                    }
                }
            )
        }

    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            FlowRow (
                modifier = Modifier
                    .padding(8.dp)
            ){
                diceList.forEach {dice ->
                    DiceCard(
                        dice = dice,
                        diceList = diceList
                    )
                }

            }

        }

    }


}

@Composable
fun DiceCard(dice: Dice, diceList: MutableList<Dice>) {
    // val diceVal by remember { mutableStateOf(dice.faceCount)}
    val drawableDiceName = "d" + dice.faceCount + "_" + dice.currentVal

    val imageResourceId = GetResourceIdFromName(drawableDiceName)

    Card (
        modifier = Modifier
            .padding(10.dp)
            .size(80.dp, 80.dp)
            .wrapContentSize()
            .clickable(
                onClick = { diceList.remove(dice) }
            )
    ){
        Image(
            painter = painterResource(id = imageResourceId ?: R.drawable.d6_6),
            contentDescription = dice.currentVal.toString(),
            modifier = Modifier
                .fillMaxSize())

    }
}

@Composable
fun MyBottomAppBar(diceList: MutableList<Dice>) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = {
                    diceList.add(Dice(4))
                          },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d4_4),
                    contentDescription = "add a d4"
                )

            }
            IconButton(
                onClick = {
                    diceList.add(Dice(6))
                          },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d6_6),
                    contentDescription = "add a d6"
                )

            }
            IconButton(
                onClick = {
                    diceList.add(Dice(8))
                          },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d8_8),
                    contentDescription = "add a d8"
                )

            }
            IconButton(
                onClick = {
                    diceList.add(Dice(10))
                },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d10_10),
                    contentDescription = "add a d10"
                )

            }
            IconButton(
                onClick = {
                    diceList.add(Dice(12))
                          },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d12_12),
                    contentDescription = "add a d12"
                )

            }
            IconButton(
                onClick = {
                    diceList.add(Dice(20))
                },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d20_20),
                    contentDescription = "add a d20"
                )

            }


        }
    )

}

fun Roll(diceList :MutableList<Dice>) {
    var result = 0
    val newDiceList = mutableStateListOf<Dice>()
    diceList.forEach {dice ->
        val newVal = (1..dice.faceCount).random()
        newDiceList.add(dice.copy(currentVal = newVal))
        result += newVal
    }

    for (i in 0 until diceList.size) {
        diceList[i] = newDiceList[i]
    }
}

@Preview
@Composable
fun FRPAppScreen() {
    FRPDiceRollerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FRPDiceRollerApp()
        }
    }
}

fun GetResourceIdFromName(drawableDiceName: String, drawableMap: Map<String, Int> = imageResourceMap): Int? {
    return drawableMap[drawableDiceName]
}