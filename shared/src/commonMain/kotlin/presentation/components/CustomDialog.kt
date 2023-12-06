package presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.DarkPurple
import presentation.theme.Gold
import presentation.theme.yellow
import utils.AppStrings

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomDialogSheet(
    title: String,
    buttonText: String,
    message: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onReject() },
    ) {
        CustomDialog(title, buttonText, message, onAccept, onReject)
    }

}

@Composable
fun CustomDialog(
    title: String,
    buttonText: String,
    message: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(21.dp))
            Box(
                modifier = Modifier
                    .width(134.dp)
                    .height(5.dp)
                    .background(
                        color = Color(0xFFD0D0DA),
                        shape = RoundedCornerShape(size = 100.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Red,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = message,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row {

                Button(
                    onClick = { onReject() },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .border(
                            width = 1.dp,
                            color = DarkPurple,
                            shape = RoundedCornerShape(size = 12.dp)
                        ).height(60.dp)
                    ,

                    shape = RoundedCornerShape(size = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = DarkGray,
                        backgroundColor = Red,
                    ),
                ) {
                    Text(text = AppStrings.cancel.stringValue, style = TextStyle(color = Color.White))
                }
                Spacer(
                    modifier = Modifier.width(15.dp),
                )

                Button(
                    onClick = { onAccept() },
                    shape = RoundedCornerShape(size = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Gold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = AppStrings.yes.stringValue + "! $buttonText",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

