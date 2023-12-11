package presentation.screens.product


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.model.Category
import data.model.Product
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.screens.main.MainViewModel


class ProductDetailsScreen(
    private val product: Product,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        productDetailsContent(navigator)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun productDetailsContent(
        navigator: Navigator? = null,
        viewModel: MainViewModel = koinInject()
    ) {
        detailsContent(navigator,product)
    }


}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun detailsContent(
    navigator: Navigator?=null,
    product: Product,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = { DetailTopBar(navigator = navigator,product) },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    KamelImage(
                        asyncPainterResource(product.images[0]),
                        product.description,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(all = 12.dp),
                        onFailure = {
                            Image(
                                modifier = Modifier,
                                contentScale = ContentScale.Fit,
                                painter = painterResource("not_found.png"),
                                contentDescription = null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailProductText(product = product)
                    Spacer(modifier = Modifier.height(40.dp))
                    DetailBottomBar(product = product)
            }

        }
    }
}
@Composable
fun DetailBottomBar(product: Product) {
    Row(
        modifier = Modifier.padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Price",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "$ " + product.price,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
//                if (isProductOnCart) {
//                    viewModel.removeFromCartProduct(product)
//                } else {
//                    viewModel.addToCartProduct(product)
//                }
            },
//            border = if (isProductOnCart) BorderStroke(1.dp, Color.Black) else null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Add To Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


@Composable
fun DetailProductText(product: Product) {
    Text(
        text = product.title,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    DetailRatingSection(product = product)
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Description",
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = product.description,
        fontSize = 14.sp,
        color = Color.Gray,
        fontWeight = FontWeight.Medium
    )
}


@Composable
fun DetailRatingSection(product: Product){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Rating Icon",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = product.rate.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "(${product.count}) Reviews",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun DetailTopBar(navigator: Navigator?=null,product: Product) {
    TopAppBar(
        title = { Text(text = "${product.title}") },
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
