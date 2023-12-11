package presentation.screens.main.taps.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.network.Resource
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.components.AppSlider
import presentation.components.CategoryCardTag
import presentation.components.LoadingAnimation3
import presentation.components.ProductCard
import presentation.screens.product.ProductDetailsScreen

class SearchScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: SearchViewModel = koinInject()
        val products = viewModel.products.collectAsState()
        val snackState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()
        val navigator: Navigator = LocalNavigator.currentOrThrow

        SnackbarHost(hostState = snackState, Modifier)

        fun launchSnackBar(message: String) {
            snackScope.launch { snackState.showSnackbar(message) }
        }

        LazyColumn {
            item {
                TextField(
                    leadingIcon = {
                        Icon(Icons.Default.Search, "Search")
                    },
                    label = {
                        Text(
                            text = "What Are you looking for ?",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color(0xFFADB3DA),
                            )
                        )
                    },
                    value = "", onValueChange = {},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color(0xFFADB3DA),
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color(0xFFEFF1F8),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(10.dp)),

                    )
            }

            item {
                products.value.let { result ->
                    when (result) {
                        is Resource.Failure -> {
                            launchSnackBar("some failures occurred")
                        }

                        Resource.Loading -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(1.0f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                LoadingAnimation3()
                            }
                        }

                        is Resource.Success -> {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 150.dp),
                                modifier = Modifier.heightIn(max = 800.dp)
                            ) {
                                items(result.result.size) { index ->
                                    val product = result.result[index]
                                    ProductCard(
                                        product
                                    ) {
                                        navigator.push(ProductDetailsScreen(product))
                                    }
                                }
                            }
                        }
                        null -> {}
                    }
                }
            }
        }
    }
}