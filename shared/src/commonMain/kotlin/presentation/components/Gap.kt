package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * You can use it to add space in Flex layout
 * Ex. Gap(20) , to add space with 20 dp in [Column] or [Row]
 */
@Composable
fun Gap(
    size: Dp,
) {
    var positionInParent by remember { mutableStateOf(Offset.Zero) }
    Box(modifier = Modifier
        .size(size)
        .onGloballyPositioned { coordinates ->
            positionInParent = coordinates.positionInRoot()
        }
        .then(
            Modifier
                .width(
                    if (positionInParent.x == 0f) {
                        0.dp
                    } else {
                        size
                    }
                )
                .height(
                    if (positionInParent.y == 0f) {
                        0.dp
                    } else {
                        size
                    }
                )
        ))
}