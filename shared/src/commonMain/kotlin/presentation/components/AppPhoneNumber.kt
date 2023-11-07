package presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.theme.grayTextColor


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppPhoneNumber(
    modifier: Modifier = Modifier,
    countryImageUrl: String? = null,
    countryCode: String = "+20",
    label: String = "phone",
    labelColor: Color = Color.DarkGray,
    value: String = "",
    error: String? = null,
    trailingAction: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hintColor: Color = grayTextColor,
    onClicked: () -> Unit = {},
    onValueChanged: (text: String) -> Unit = {},
) {
    Column(modifier = modifier)
    {

//        AppText(
//            modifier = Modifier
//                .padding(start = 8.dp, top = 1.dp, bottom = 1.dp)
//                .fillMaxWidth(),
//            text = label, style = TextStyle(
////                fontFamily = regularFont,
//                fontWeight = FontWeight.Normal, fontSize = 13.sp
//            ), color = labelColor
//        )
//
//        Row(
//            modifier = Modifier
//                .padding(start = 8.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            if (countryImageUrl == null) {
//                Image(
//                    painterResource("flag.xml"), modifier = Modifier
//                        .padding(top = 8.dp)
//                        .width(24.dp)
//                        .height(24.dp),
//                    contentDescription = "country_flag"
//                )
//            } else {
//                KamelImage(
//                    asyncPainterResource(countryImageUrl),
//                    "country_flag",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
//                )
//            }
//
//
//            AppText(
//                modifier = Modifier.padding(start = 3.dp, end = 4.dp, top = 8.dp),
//                text = countryCode,
//                style = TextStyle(
////                    fontFamily = regularFont,
//                    fontWeight = FontWeight.Normal, fontSize = 11.sp
//                ),
//                color = blackTextColor
//            )
//
//            Image(
//                modifier = Modifier
//                    .padding(top = 10.dp, end = 3.dp)
//                    .clickable { onClicked() },
//                painter = painterResource("ic_arrow_down.xml"),
//                contentDescription = "openSheet"
//            )
//
//            AppTextField(
//                modifier = Modifier
//                    .height(60.dp)
//                    .fillMaxWidth()
//                    .testTag("phone_number"),
//                value = value,
//                hintColor = blackTextColor,
//                keyboardOptions = KeyboardOptions(
//                    capitalization = KeyboardCapitalization.None,
//                    autoCorrect = true,
//                    keyboardType = KeyboardType.Number,
//                ),
//                singleLine = true,
//                maxLines = 1,
//                hintLabel = "",
//                showingIndicator = false,
//                onValueChanged = onValueChanged,
//                trailingIconAction = {
//                    if (trailingAction != null) {
//                        trailingAction()
//                    }
//                },
//            )
//        }
//
//
//
//        if (error != null) {
//            Text(
//                text = error,
//                color = MaterialTheme.colors.error
//            )
//        }
//        Spacer(
//            modifier = Modifier
//                .padding(top = 2.dp, bottom = 2.dp, start = 4.dp, end = 4.dp)
//                .height(1.dp)
//                .fillMaxWidth()
//                .background(gray2)
//        )


    }

}