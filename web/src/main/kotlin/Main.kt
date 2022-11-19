/*
 * Created by Nils Druyen on 12-27-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

import de.nilsdruyen.compose.SampleList
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody

fun main() {
    renderComposableInBody {
        H1 {
            Text("Welcome to compose hangover")
        }
        SampleList()
    }
}