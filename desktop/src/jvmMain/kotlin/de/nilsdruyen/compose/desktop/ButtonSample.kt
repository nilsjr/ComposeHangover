package de.nilsdruyen.compose.desktop

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GradientShadowButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val isButtonHovered by interactionSource.collectIsHoveredAsState()

    val scaleAnimated by animateFloatAsState(
        targetValue = if (isButtonHovered) 1f else 0.7f,
        animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
    )
    val alphaAnimated by animateFloatAsState(
        targetValue = if (isButtonHovered) 0.75f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
    )
    val blurAnimated by animateDpAsState(
        targetValue = if (isButtonHovered) 48.dp else 8.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
    )

    Box(
        modifier
            .hoverable(interactionSource)
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
    ) {
        val gradientBrush = Brush.linearGradient(
            colors = listOf(Color(0xff6171fe), Color(0xff9f6afe), Color(0xffb79dfe))
        )

        Box(
            Modifier
                .graphicsLayer {
                    scaleX = scaleAnimated
                    scaleY = scaleAnimated
                }
                .blur(radius = blurAnimated, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .drawBehind {
                    drawRoundRect(
                        brush = gradientBrush,
                        cornerRadius = CornerRadius(8.dp.toPx()),
                        alpha = alphaAnimated
                    )
                }
                .fillMaxSize()
        )
        Row(
            Modifier
                .padding(16.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick
                )
                .background(color = Color(0xff18181b), shape = RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    brush = gradientBrush,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}