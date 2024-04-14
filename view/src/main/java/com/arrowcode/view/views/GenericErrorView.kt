package com.arrowcode.view.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun GenericErrorViewNoActionAvailable(
    errorImage: ImageVector = Icons.Default.Clear,
    errorMessage: String,
) {
    GenericErrorView(
        errorImage = errorImage,
        errorMessage = errorMessage
    )
}

@Composable
fun GenericErrorViewWithActionAvailable(
    errorImage: ImageVector = Icons.Default.Clear,
    errorMessage: String,
    buttonActionText: String,
    onErrorAction: (() -> Unit)?,
) {
    GenericErrorView(
        errorImage = errorImage,
        errorMessage = errorMessage,
        buttonActionText = buttonActionText,
        onErrorAction = onErrorAction,
    )
}

@Composable
private fun GenericErrorView(
    errorImage: ImageVector = Icons.Default.Clear,
    errorMessage: String,
    buttonActionText: String = "",
    onErrorAction: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 128.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        shape = RoundedCornerShape(16.dp),
                    ),
                imageVector = errorImage,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onError),
                contentDescription = "error_icon",
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 64.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                text = errorMessage,
                textAlign = TextAlign.Center
            )
        }

        if (onErrorAction != null) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
                    .padding(horizontal = 32.dp)
                    .align(Alignment.BottomCenter),
                onClick = { onErrorAction() }) {
                Text(text = buttonActionText)
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun GenericErrorViewWithActionAvailablePreview() {
    GenericErrorViewWithActionAvailable(
        errorMessage = "Error Error Error Error Error Error Error Error",
        buttonActionText = "action",
        onErrorAction = {})
}

@Composable
@Preview(showBackground = true)
fun GenericErrorViewNoActionAvailablePreview() {
    GenericErrorViewNoActionAvailable(
        errorMessage = "Error Error Error Error Error Error Error Error"
    )
}