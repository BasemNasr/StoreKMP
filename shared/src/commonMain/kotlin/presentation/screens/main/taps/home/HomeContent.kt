package presentation.screens.main.taps.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Category
import data.model.Product
import data.model.Rating
import presentation.screens.main.composable.ProductCard


@Composable
fun HomeContent() {
    LazyColumn(modifier = Modifier.padding(8.dp))
    {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Sale", style = TextStyle(
                        fontSize = 34.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF222222),
                    )
                )
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "View all", style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF222222),
                        )

                )
            }
        }
        item {
            LazyRow {
                items(52) {
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
        item {
            Text(
                "New", style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF222222),
                )
            )
        }
        item {
            LazyRow {
                items(52) {
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
                            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
                            images = listOf("https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"),
                            price = 22.3f,
                            rating = Rating(count = 259, rate = 4.1),
                            title = "Mens Casual Premium Slim Fit T-Shirts ",
                            creationAt = "",
                            updatedAt = ""
                        )
                    ) {}
                }
            }
        }
    }

}