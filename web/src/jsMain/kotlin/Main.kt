/*
 * Created by Nils Druyen on 12-27-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import de.nilsdruyen.compose.WebAppStyle
import de.nilsdruyen.compose.common.HangoverViewModel
import de.nilsdruyen.compose.common.data.HangoverRepositoryImpl
import de.nilsdruyen.compose.common.entities.Color
import kotlinx.coroutines.MainScope
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody

fun main() {
    val scope = MainScope()
    val viewModel = HangoverViewModel(scope, HangoverRepositoryImpl())

    renderComposableInBody {
        val state = viewModel.state.collectAsState()
        val primaryColor = remember {
            derivedStateOf {
                state.value.theme.colors[Color.PRIMARY]
            }
        }

        LaunchedEffect(Unit) {
            viewModel.observe()
        }

        Style(WebAppStyle)

        Div({
            style {
                width(100.percent)
                height(100.percent)
                background("#$primaryColor")
            }
        }) {
            H1 {
                Text("Welcome to compose hangover")
            }
            H2 {
                Text("color: $primaryColor")
            }
        }
    }
}