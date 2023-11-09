package presentation.screens.main.taps.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.screens.main.taps.profile.components.ProfileSectionCard
import presentation.theme.gray2


object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)

            return remember {
                TabOptions(
                    index = 3u,
                    title = "Profile",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Profile",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Black,

                    ),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Card(modifier = Modifier.size(80.dp), shape = CircleShape) {
                    KamelImage(
                        asyncPainterResource("https://i.ibb.co/cyP8x9m/my-passport-photo.jpg"),
                        contentDescription = "profile_pic"
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Mohamed Sakr",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "sagr3272@gmail.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            color = gray2,
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier.weight(1f))


                }



                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .clickable {
                        }
                        .size(24.dp),
                    tint = Color.Black,
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            Column(
                Modifier.verticalScroll(rememberScrollState())

            ) {
                ProfileSectionCard({ }, title = "My Orders") {
                }

                ProfileSectionCard({ }, title = "Cart") {

                }

                ProfileSectionCard(
                    { },
                    title = "Settings"
                ) {

                }
                ProfileSectionCard(
                    {
                    },
                    color = Color.Red,
                    withLine = false,
                    title = "Logout"
                ) {
                }

            }


        }
    }


}