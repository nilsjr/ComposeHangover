package de.nilsdruyen.compose.html

import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.title

fun HTML.dashboard() {
    head {
        title("Compose hangover")
    }
    body {
        h3 {
            +"Hello"
        }
    }
}