package com.example.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import kotlinx.coroutines.delay

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val key = LocalSoftwareKeyboardController.current
            ModalBottomSheetLayout(sheetState = sheetState,
                sheetContent = { repeat(20) { Text(text = "$it") } }) {
                Column {
                    var otpResponse by remember {
                        mutableStateOf<Boolean?>(
                            null
                        )
                    }
                    if (otpResponse == false) {
                        LaunchedEffect(key1 = Unit, block = {
                            delay(180)
                            otpResponse = true
                        })
                    }
                    if (otpResponse == true) {
                        LaunchedEffect(key1 = Unit, block = { sheetState.show() })
                    }
                    Column {
                        var string by remember { mutableStateOf("") }
                        TextField(value = string, onValueChange = { string = it })
                        Button(onClick = {
                            key?.hide()
                            otpResponse = false
                        }) { Text(text = "TEST") }
                    }
                }
            }
        }
    }

}