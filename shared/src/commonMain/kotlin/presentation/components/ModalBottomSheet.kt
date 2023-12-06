package presentation.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.launch

//region SheetState

// Possible sheet positions
enum class SheetPosition { HIDDEN, PARTIALLY_EXPANDED, EXPANDED }

@ExperimentalMaterialApi
class SheetState(
    val skipPartiallyExpanded: Boolean,
    initialPosition: SheetPosition = SheetPosition.HIDDEN,
    confirmPositionChange: (SheetPosition) -> Boolean = { true },
) {
    companion object {
        @Suppress("RemoveExplicitTypeArguments")
        fun Saver(
            skipPartiallyExpanded: Boolean,
            confirmPositionChange: (SheetPosition) -> Boolean,
        ) = Saver<SheetState, SheetPosition>(
            save = { sheetState: SheetState -> sheetState.swipeableState.currentValue },
            restore = { savedValue: SheetPosition ->
                SheetState(
                    skipPartiallyExpanded = skipPartiallyExpanded,
                    initialPosition = savedValue,
                    confirmPositionChange = confirmPositionChange
                )
            }
        )
    }

    val swipeableState: SwipeableState<SheetPosition> = SwipeableState(
        initialValue = initialPosition,
        confirmStateChange = confirmPositionChange,
    )
}

@ExperimentalMaterialApi
@Composable
fun rememberSheetState(
    skipPartiallyExpanded: Boolean = false,
    confirmPositionChange: (SheetPosition) -> Boolean = { true },
): SheetState {
    return rememberSaveable(
        skipPartiallyExpanded, confirmPositionChange,
        saver = SheetState.Saver(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmPositionChange = confirmPositionChange,
        )
    ) {
        SheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            initialPosition = SheetPosition.HIDDEN,
            confirmPositionChange = confirmPositionChange,
        )
    }
}
//endregion

//region ModalBottomSheet
private const val UNKNOWN = -1

@ExperimentalMaterialApi
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberSheetState(),
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val closeSheet: () -> Unit = {
        coroutineScope.launch { sheetState.swipeableState.animateTo(SheetPosition.HIDDEN) }
    }

    Popup(
        onDismissRequest = closeSheet,
        properties = PopupProperties(focusable = true),
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            var sheetHeightPx by remember(sheetState) { mutableStateOf(UNKNOWN) }
            val anchors: Map<Float, SheetPosition> by remember(
                sheetHeightPx,
                constraints.maxHeight,
                sheetState,
            ) {
                mutableStateOf(
                    if (sheetHeightPx == UNKNOWN) {
                        emptyMap()
                    } else {
                        buildMap {
                            put(0f, SheetPosition.EXPANDED)
                            if (constraints.maxHeight / 2 < sheetHeightPx && sheetState.skipPartiallyExpanded.not()) {
                                put(
                                    (sheetHeightPx - constraints.maxHeight / 2).toFloat(),
                                    SheetPosition.PARTIALLY_EXPANDED
                                )
                            }
                            put(sheetHeightPx.toFloat(), SheetPosition.HIDDEN)
                        }
                    }
                )
            }

            ScrimBackground(
                swipeableState = sheetState.swipeableState,
                onClick = closeSheet
            )

            Surface(
                shape = shape,
                color = color,
                contentColor = contentColor,
                border = border,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            if (offset.y < sheetState.swipeableState.offset.value) closeSheet()
                        }
                    }
                    .onPlaced { sheetHeightPx = it.size.height }
                    .let {
                        if (sheetHeightPx == UNKNOWN) {
                            it
                        } else {
                            it
                                .swipeable(
                                    state = sheetState.swipeableState,
                                    anchors = anchors,
                                    orientation = Orientation.Vertical
                                )
                                .offset {
                                    IntOffset(
                                        x = 0,
                                        y = sheetState.swipeableState.offset.value
                                            .toInt()
                                            .coerceIn(0, sheetHeightPx)
                                    )
                                }
                        }
                    }
                    .nestedScroll(rememberSheetContentNestedScrollConnection(sheetState.swipeableState))
            ) {
                content()
            }

            // Show the sheet with animation once we know it's size
            LaunchedEffect(sheetHeightPx) {
                if (sheetHeightPx != UNKNOWN && sheetState.swipeableState.currentValue == SheetPosition.HIDDEN) {
                    val target = if (anchors.containsValue(SheetPosition.PARTIALLY_EXPANDED)) {
                        SheetPosition.PARTIALLY_EXPANDED
                    } else {
                        SheetPosition.EXPANDED
                    }
                    sheetState.swipeableState.animateTo(target)
                }
            }
        }
    }

    // Prevent the sheet from being dismissed during the initial animation when the state is COLLAPSED
    var wasSheetShown by remember(sheetState) { mutableStateOf(false) }
    LaunchedEffect(sheetState.swipeableState.currentValue) {
        when (sheetState.swipeableState.currentValue) {
            SheetPosition.PARTIALLY_EXPANDED -> wasSheetShown = true
            SheetPosition.EXPANDED -> wasSheetShown = true
            SheetPosition.HIDDEN -> if (wasSheetShown) onDismissRequest()
        }
    }
}
//endregion

//region ScrimBackground
private const val SCRIM_ALPHA = 0.32f

@ExperimentalMaterialApi
@Composable
private fun ScrimBackground(
    swipeableState: SwipeableState<SheetPosition>,
    onClick: () -> Unit,
) {
    val showScrim by remember {
        derivedStateOf { swipeableState.targetValue != SheetPosition.HIDDEN }
    }
    val scrimAlpha by animateFloatAsState(targetValue = if (showScrim) SCRIM_ALPHA else 0f)
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { onClick() } }
    ) {
        drawRect(color = Color.Black, alpha = scrimAlpha)
    }
}
//endregion

//region NestedScroll
@ExperimentalMaterialApi
@Composable
private fun rememberSheetContentNestedScrollConnection(
    sheetPosition: SwipeableState<SheetPosition>,
): NestedScrollConnection = remember(sheetPosition) {
    object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            // if trying to scrolling upwards, drag the sheet first
            return if (available.y < 0 && source == NestedScrollSource.Drag) {
                sheetPosition.performDrag(available.y).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            // If child scrolled to it's end, consume the remaining scroll but check that the
            // source is Drag to ignore fling events (i.e. after flinging to the start of the list)
            return if (source == NestedScrollSource.Drag) {
                sheetPosition.performDrag(available.y).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            // If flinging upwards and sheet is not at the top - consume the event
            return if (available.y < 0 && sheetPosition.offset.value > 0f) {
                sheetPosition.animateTo(sheetPosition.targetValue)
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            sheetPosition.animateTo(sheetPosition.targetValue)
            return available
        }

        private fun Float.toOffset() = Offset(0f, this)
    }
}
//endregion

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ModalBottomSheetPreview() {
    var showSheetSmall by rememberSaveable { mutableStateOf(false) }
    var showSheetLarge by rememberSaveable { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = { showSheetSmall = true }) { Text("Show sheet (small)") }
            Button(onClick = { showSheetLarge = true }) { Text("Show sheet (full screen)") }
        }

        if (showSheetSmall) {
            ModalBottomSheet(
                onDismissRequest = { showSheetSmall = false },
                sheetState = rememberSheetState(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                SheetContent(items = 6)
            }
        }
        if (showSheetLarge) {
            ModalBottomSheet(
                onDismissRequest = { showSheetLarge = false },
                sheetState = rememberSheetState(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                SheetContent(items = 101)
            }
        }
    }
}

@Composable
private fun SheetContent(items: Int) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        repeat(times = items) { Text("Sheet item number $it") }
    }
}