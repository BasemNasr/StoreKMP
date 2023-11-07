package presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.bn.store.kmp.MR
import dev.icerock.moko.resources.compose.painterResource
import presentation.theme.DarkPurple
import presentation.theme.blackTextColor
import presentation.theme.gray2
import presentation.theme.grayTextColor


@Composable
fun AppText(
    text: String = "Text Here",
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(
//        fontFamily = boldFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    color: Color = blackTextColor
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
    )
}


@Composable
fun AnnotatedClickableText(
    modifier: Modifier,
    baseText: String? = "first ",
    baseTextColor: Color = blackTextColor,
    annotatedTextTag: String = "Annotated",
    annotatedText: String = "annotated",
    annotatedTextStyle: SpanStyle = SpanStyle(
        color = DarkPurple,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
//        fontFamily = boldFont
    ), onClicked: () -> Unit = {}
) {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = baseTextColor, fontSize = 13.sp,
//                fontFamily = semiBoldFont
            )
        ) {
            append(baseText)
        }
        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = annotatedTextTag,// provide tag which will then be provided when you click the text
            annotation = annotatedText
        )
        //add text with your different color/style
        withStyle(
            style = annotatedTextStyle
        ) {
            append(annotatedText)
        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        onClick = { offset ->
            if (annotatedText.getStringAnnotations(
                    tag = annotatedTextTag,// tag which you used in the buildAnnotatedString
                    start = offset,
                    end = offset
                ).isNotEmpty()
            ) {
                annotatedText.getStringAnnotations(
                    tag = annotatedTextTag,// tag which you used in the buildAnnotatedString
                    start = offset,
                    end = offset
                )[0].let { annotation ->
                    //do your stuff when it gets clicked
                    onClicked()
//                    Log.d("Clicked", annotation.item)
                }
            }


        }
    )
}


@Composable
fun AppTextField(
    modifier: Modifier,
    value: String?,
    hintLabel: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hintColor: Color = grayTextColor,
    showingIndicator: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    error: String? = null,
    interactionSource: MutableInteractionSource? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChanged: (text: String) -> Unit = {},
) {
    Column {
        var passwordVisible by rememberSaveable { mutableStateOf(visualTransformation == PasswordVisualTransformation()) }

        TextField(
            readOnly = readOnly,
            enabled = enabled,
            value = value ?: "",
            keyboardOptions = keyboardOptions,
            visualTransformation = if (visualTransformation == PasswordVisualTransformation() && passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = {
                onValueChanged.invoke(it)
            },
            interactionSource = interactionSource ?: MutableInteractionSource(),
            label = { Text(hintLabel, color = hintColor) },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = if (showingIndicator) blackTextColor else Color.Transparent,
                unfocusedIndicatorColor = if (showingIndicator) grayTextColor else Color.Transparent,
                disabledIndicatorColor = if (!enabled) grayTextColor else Color.Transparent,
            ),
            textStyle = TextStyle(
                color = blackTextColor,
                fontWeight = FontWeight.Bold,
//                fontFamily = regularFont,
                fontSize = 14.sp
            ),
            modifier = modifier,
            trailingIcon = {
                if (visualTransformation == PasswordVisualTransformation()) {
                    val image = if (passwordVisible)
                        MR.images.visibility
                    else MR.images.visibility_off

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = painterResource(image) , description)
                    }
                } else if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        )

        if (error != null) {
            Text(
                text = error,
                modifier = modifier,
                color = MaterialTheme.colors.error
            )
        }
    }


}


@Composable
fun AppTextField(
    modifier: Modifier,
    value: String,
    error: String? = null,
    hintLabel: String,
    placeHolder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hintColor: Color = grayTextColor,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    showingIndicator: Boolean = true,
    trailingIconAction: @Composable (() -> Unit)? = null,
    onValueChanged: (text: String) -> Unit = {}
) {

    var passwordVisible by rememberSaveable { mutableStateOf(visualTransformation == PasswordVisualTransformation()) }

    Column {
        TextField(
            value = value,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (visualTransformation == PasswordVisualTransformation() && passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = {
                onValueChanged.invoke(it)
            },
            label = {
                Text(hintLabel, color = hintColor)
            },
            maxLines = maxLines,
            placeholder = {
                Text(placeHolder, color = grayTextColor)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = if (showingIndicator) grayTextColor else Color.Transparent,
                focusedIndicatorColor = if (showingIndicator) gray2 else Color.Transparent,
            ),
            singleLine = singleLine,
            textStyle = TextStyle(
                color = blackTextColor,
                fontWeight = FontWeight.Bold,
//                fontFamily = regularFont,
                fontSize = 16.sp
            ),
            modifier = modifier,
            trailingIcon = {
                if (visualTransformation == PasswordVisualTransformation()) {
                    val image = if (passwordVisible)
                        MR.images.visibility
                    else MR.images.visibility_off
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = painterResource(image) , description)
                    }
                } else if (trailingIconAction !== null) {
                    trailingIconAction()
                }

            })

        if (error != null) {
            Text(
                text = error,
                modifier = modifier,
                color = MaterialTheme.colors.error
            )
        }
    }
}
