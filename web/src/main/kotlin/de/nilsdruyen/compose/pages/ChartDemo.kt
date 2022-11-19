package de.nilsdruyen.compose.pages

import ArcElement
import BarController
import BarElement
import BubbleController
import CategoryScale
import Chart
import Decimation
import DoughnutController
import Filler
import Legend
import LineController
import LineElement
import LinearScale
import LogarithmicScale
import PieController
import PointElement
import PolarAreaController
import RadarController
import RadialLinearScale
import ScatterController
import SubTitle
import TimeScale
import TimeSeriesScale
import Title
import Tooltip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import de.nilsdruyen.compose.pages.chart.Type
import org.jetbrains.compose.web.dom.Canvas
import kotlin.random.Random

inline fun <T : Any> jso(): T = js("({})")

inline fun <T : Any> jso(builder: T.() -> Unit): T = jso<T>().apply(builder)

@Composable
fun ChartDemo(){
    Chart.register(
        ArcElement,
        LineElement,
        BarElement,
        PointElement,
        BarController,
        BubbleController,
        DoughnutController,
        LineController,
        PieController,
        PolarAreaController,
        RadarController,
        ScatterController,
        CategoryScale,
        LinearScale,
        LogarithmicScale,
        RadialLinearScale,
        TimeScale,
        TimeSeriesScale,
        Decimation,
        Filler,
        Legend,
        Title,
        Tooltip,
        SubTitle
    )

    val data = List(6) { Random.nextInt(6) }

    Canvas {
        DisposableEffect(data) {
            val chart = Chart(scopeElement, jso {
                type = Type.bar
                this.data = jso {
                    labels = arrayOf("Red", "Blue", "Yellow", "Green", "Purple", "Orange")
                    datasets = arrayOf(jso {
                        label = "# of Votes"
                        this.data = data.toTypedArray()
                        backgroundColor = arrayOf(
                            "rgba(255, 99, 132, 0.2)",
                            "rgba(54, 162, 235, 0.2)",
                            "rgba(255, 206, 86, 0.2)",
                            "rgba(75, 192, 192, 0.2)",
                            "rgba(153, 102, 255, 0.2)",
                            "rgba(255, 159, 64, 0.2)"
                        )
                        borderColor = arrayOf(
                            "rgba(255, 99, 132, 1)",
                            "rgba(54, 162, 235, 1)",
                            "rgba(255, 206, 86, 1)",
                            "rgba(75, 192, 192, 1)",
                            "rgba(153, 102, 255, 1)",
                            "rgba(255, 159, 64, 1)"
                        )
                        borderWidth = 1
                    })
                }
            })
            onDispose {
                chart.destroy()
            }
        }
    }
}