package com.maxrzhe.composetodoapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.presentation.ui.theme.LARGE_PADDING

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LARGE_PADDING),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = { onDismiss() }
                    ) {
                        Text(text = stringResource(R.string.cancel_button))
                    }
                    Spacer(modifier = Modifier.width(LARGE_PADDING))
                    Button(
                        onClick = { onConfirm() }
                    ) {
                        Text(text = stringResource(R.string.confirm_button))
                    }
                }
            },
        )
    }
}

@Composable
@Preview
fun DisplayAlertDialogPreview() {
    DisplayAlertDialog(
        title = stringResource(R.string.delete_task_alert_title, "Some Title"),
        message = stringResource(R.string.delete_task_message, "Some Title"),
        openDialog = true,
        onDismiss = { },
        onConfirm = { })
}