package presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.DarkPurple
import presentation.theme.yellow

@Composable
fun AppPrimaryButtonPreview() {
//    AppPrimaryButton(
//        modifier = Modifier
//            .padding(horizontal = 40.dp, vertical = 12.dp)
//            .width(400.dp)
//            .height(90.dp),
//        buttonText = stringResource(id = R.string.sign_in),
//        isLoading = false,
//
//        shape = RoundedCornerShape(16.dp),
//        colors = ButtonDefaults.buttonColors(
//            contentColor = yellow,
//            containerColor = DarkPurple,
//            disabledContainerColor = grayTextColor
//        ),
//        textColor = yellow,
//        style = TextStyle(
//            fontFamily = semiBoldFont,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold,
//            textAlign = TextAlign.Center
//        )
//    ) {
//        //  navController?.navigate(AuthScreens.RegisterStep1.route)
//    }

}

@Composable
fun AppPrimaryButton(
    modifier: Modifier = Modifier,
    buttonText: String = "",
    isLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = yellow,
        backgroundColor = DarkPurple
    ),
    shape: RoundedCornerShape =
        RoundedCornerShape(16.dp),
    style: TextStyle = TextStyle(
//        fontFamily = fontFamilyResource(MR.fonts.somar_bold),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    textColor: Color = yellow,
    onClicked: () -> Unit,
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(61.dp)
        .padding(top = 34.dp),
        shape = shape,
        colors = colors,
        enabled = !isLoading,
        onClick = {
            onClicked()
        }) {
        val strokeWidth = 2.dp
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(visible = isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.drawBehind {
                        drawCircle(
                            Color.White,
                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                            style = Stroke(strokeWidth.toPx())
                        )
                    },
                    color = Color.LightGray,
                    strokeWidth = strokeWidth
                )
            }

            Text(
                text = buttonText,
                modifier = Modifier.weight(1f),
                style = style,
                color = textColor,
                textAlign = TextAlign.Center,
                )
        }

    }
}


