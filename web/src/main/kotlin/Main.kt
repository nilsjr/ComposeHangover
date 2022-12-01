/*
 * Created by Nils Druyen on 12-27-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.nilsdruyen.compose.SampleList
import de.nilsdruyen.compose.WebAppStyle
import de.nilsdruyen.compose.common.api.ClientApi
import kotlinx.coroutines.flow.collect
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
    renderComposableInBody {
        var color by remember { mutableStateOf("ffffff") }

        LaunchedEffect(Unit) {
            ClientApi.observeColor().collect {
                println("update color: $it")
                color = it
            }
        }

        Style(WebAppStyle)

        Div({
            style {
                width(100.percent)
                height(100.percent)
                background("#$color")
            }
        }) {
            H1 {
                Text("Welcome to compose hangover")
            }
            H2 {
                Text("color: $color")
            }
        }
    }
}