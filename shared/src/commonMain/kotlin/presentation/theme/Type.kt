package presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource

// Set of Material typography styles to start with


//val fontFamily: FontFamily = fontFamilyResource(MR.fonts.)

//val somarBoldFont = fontFamilyResource(
//    MR.fonts.som
//)
//val somarSemiBold = FontFamily(
//    Font(R.font.somar_semibold)
//)
//val somarRegular = FontFamily(
//    Font(R.font.somar_regular)
//)

val Typography = Typography(
    body1 = TextStyle(
//        fontFamily = somarRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    body2 = TextStyle(
//        fontFamily = somarRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    h5 = TextStyle(
        color = Color.Black,
//        fontFamily = somarBoldFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
    ),
    h4 = TextStyle(
        color = textColorSemiBlack,
//        fontFamily = somarSemiBold,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    ),
    h3 = TextStyle(
        color = textColorSemiBlack,
//        fontFamily = somarRegular,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    ),
    h2 = TextStyle(
        color = textColorSemiBlack,
//        fontFamily = somarRegular,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)