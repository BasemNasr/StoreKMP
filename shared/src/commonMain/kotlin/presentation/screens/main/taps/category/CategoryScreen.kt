package presentation.screens.main.taps.category


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import data.network.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import presentation.components.CategoryCardTag
import presentation.components.Gap
import presentation.components.LoadingAnimation3
import presentation.screens.category.SelectedCategoryScreen
import presentation.screens.main.taps.home.HomeStateIntent
import presentation.screens.main.taps.home.HomeViewModel

object CategoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        mainContent(navigator)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun mainContent(
        navigator: Navigator? = null,
    ) {
        val viewModel: CategoriesViewModel = koinInject()

        val categories = viewModel.categories.collectAsState()

        /*val userToken = viewModel?.userToken?.value
        viewModel.setStateEvent(MainStateIntent.GettingToken)
        Text("Hello Your Token is : $userToken")*/

        val snackState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()

        SnackbarHost(hostState = snackState, Modifier)

        fun launchSnackBar(message: String) {
            snackScope.launch { snackState.showSnackbar(message) }
        }

        categories.value.let { result ->
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
                    CategoryContent(
                        navigator,
                        result.result
                    )
                }

                null -> {}
            }
        }


    }


    @Composable
    private fun CategoryContent(navigator: Navigator?,categories: List<Category>) {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFF9F9F9))
                .padding(16.dp)
        ) {
            Text(
                "Choose category", style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF9B9B9B),
                    textAlign = TextAlign.Center,
                )
            )
            Gap(10.dp)
            LazyColumn {
                items(categories.size) {
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier.background(
                            color = androidx.compose.ui.graphics.Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ).clickable {
                            navigator?.push(SelectedCategoryScreen(categories[it]))
                        }
                            .clip(
                                shape = RoundedCornerShape(10.dp),
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.height(100.dp).clip(
                                shape = RoundedCornerShape(10.dp),
                            ).background(color = Color.White, shape = RoundedCornerShape(10.dp))
                                .clip(
                                    shape = RoundedCornerShape(10.dp),
                                )
                        ) {
                            Text(
                                categories[it].name?:"",
                                modifier = Modifier.weight(1f).padding(start = 16.dp),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 22.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF222222),
                                )
                            )
                            KamelImage(
                                asyncPainterResource(categories[it].image?:""),
                                categories[it].name,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Gap(10.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

}