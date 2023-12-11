package presentation.screens.category


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import data.model.Category
import data.model.Product
import data.network.Resource
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.components.AppSlider
import presentation.components.LoadingAnimation3
import presentation.components.ProductCard
import presentation.screens.product.DetailTopBar
import presentation.screens.product.ProductDetailsScreen

class SelectedCategoryScreen(val category: Category) : Screen {

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel: SelectedCategoryViewModel = koinInject()


        val snackState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()
        val products = viewModel.products.collectAsState()

        viewModel.setStateEvent(SelectedCategoryStateIntent.GetProducts(category.id ?: 0))

        SnackbarHost(hostState = snackState, Modifier)

        fun launchSnackBar(message: String) {
            snackScope.launch { snackState.showSnackbar(message) }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            topBar = { CategoryDetailTopBar(navigator = navigator,category) },
        ) { paddingValues ->
            Column(
                Modifier.padding(paddingValues)
            ) {
                AppSlider()

                Text(
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                    text = "Products", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF9B9B9B),
                        textAlign = TextAlign.Center,
                    )
                )

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
                                modifier = Modifier,
                                columns = GridCells.Adaptive(168.dp),
                                contentPadding = PaddingValues(
                                    start = 12.dp,
                                    top = 2.dp,
                                    end = 12.dp,
                                    bottom = 16.dp
                                ),
                                content = {
                                    items(result.result.size) { index ->
                                        val product = result.result[index]
                                        ProductCard(
                                            product
                                        ) {
                                            navigator?.push(ProductDetailsScreen(product))
                                        }
                                    }
                                }
                            )
                        }

                        null -> {}
                    }
                }
            }
        }


    }
}

@Composable
fun CategoryDetailTopBar(navigator: Navigator?=null,cateogry: Category) {
    TopAppBar(
        title = { Text(text = "${cateogry.name}") },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    navigator?.pop()
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button"
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}
