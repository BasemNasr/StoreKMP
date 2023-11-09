package presentation.screens.main.taps.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import data.model.Category
import data.model.Product
import data.model.Rating
import presentation.screens.main.composable.ProductCard


object SearchTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Search)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "Search",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
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
                    colors = outlinedTextFieldColors(
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
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    modifier = Modifier.heightIn(max = 800.dp)
                ) {
                    items(50) { photo ->
                        ProductCard(
                            productModel = Product(
                                id = 1,
                                category = Category(
                                    name = "men's clothing",
                                    creationAt = "",
                                    id = 0,
                                    image = "",
                                    updatedAt = "",
                                ),
                                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                                images = listOf("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"),
                                price = 109.95f,
                                rating = Rating(count = 120, rate = 3.9),
                                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                                creationAt = "",
                                updatedAt = "",

                                )
                        ) {}
                    }
                }
            }
        }
    }


}