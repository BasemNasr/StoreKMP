package presentation.screens.main.taps.category


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.components.Gap

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

        /*val userToken = viewModel?.userToken?.value
        viewModel.setStateEvent(MainStateIntent.GettingToken)
        Text("Hello Your Token is : $userToken")*/

        CategoryContent(
            listOf(
                Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                ), Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                ), Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                ), Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                ), Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                ), Category(
                    creationAt = "",
                    id = 0,
                    image = "https://i.ibb.co/gyYBhDB/e012b7c12e4b2a5bc61b8683ec894a9b.png",
                    name = "Jeans",
                    updatedAt = ""
                )
            )
        )

    }


    @Composable
    private fun CategoryContent(categories: List<Category>) {
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
                        )
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