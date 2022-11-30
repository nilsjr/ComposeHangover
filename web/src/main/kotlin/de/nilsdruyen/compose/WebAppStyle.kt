package de.nilsdruyen.compose

import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width

object WebAppStyle : StyleSheet() {

    init {
        "html, body" style {
            width(100.percent)
            height(100.percent)
            margin(0.px)
            background("#212121")
        }
    }
}