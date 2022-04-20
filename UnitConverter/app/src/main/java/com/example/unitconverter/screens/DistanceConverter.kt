package com.example.unitconverter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.R
import com.example.unitconverter.screens.Screen.Companion.screens
import com.example.unitconverter.viewmodels.DistanceViewModel



@Composable
fun DistanceConverter() {
    val viewModel: DistanceViewModel = viewModel()
    val strMeter = stringResource(id = R.string.meter)
    val strMile = stringResource(id = R.string.mile)
    val currentValue = viewModel.distance.observeAsState(viewModel.distance.value ?: "")
    val scale = viewModel.scale.observeAsState(viewModel.scale.value ?: R.string.meter)
    var result by rememberSaveable { mutableStateOf("") }

    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getDistanceAsFloat().isNaN())
    }
    val calc = {
        val temp = viewModel.convert()
        result = if (temp.isNaN())
            ""
        else
            "$temp${
                if (scale.value == R.string.mile)
                    strMeter
                else strMile
            }"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DistanceTextField(
            distance = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            callback = calc,
            viewModel = viewModel
        )
        DistanceScaleButtonGroup(
            selected = scale,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { resId: Int ->
            viewModel.setScale(resId)
        }
        Button(
            onClick = calc,
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun DistanceTextField(
    distance: State<String>,
    modifier: Modifier = Modifier,
    callback: () -> Unit,
    viewModel: DistanceViewModel
) {
    TextField(
        value = distance.value,
        onValueChange = {
            viewModel.setDistance(it)
        },
        placeholder = {
            Text(text = "distance")
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            callback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun DistanceScaleButtonGroup(
    selected: State<Int>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val sel = selected.value
    Row(modifier = modifier) {
        DistanceRadioButton(
            selected = sel == R.string.meter,
            resId = R.string.meter,
            onClick = onClick
        )
        DistanceRadioButton(
            selected = sel == R.string.mile,
            resId = R.string.mile,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )

    }
}

@Composable
fun DistanceRadioButton(
    selected: Boolean,
    resId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick(resId)
            }
        )
        Text(
            text = stringResource(resId),
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}
