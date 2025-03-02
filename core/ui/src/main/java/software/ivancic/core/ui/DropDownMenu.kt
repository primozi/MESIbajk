package software.ivancic.core.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun <T> DropDownMenu(
    selectedItem: T?,
    availableItems: List<T>,
    onItemSelected: (T) -> Unit,
    selectedItemComposable: @Composable (T?) -> Unit = { Text(text = it.toString()) },
    listItemComposable: @Composable (T) -> Unit = { Text(text = it.toString()) },
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    BackHandler(enabled = expanded) {
        expanded = false
    }

    Box(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            selectedItemComposable(selectedItem)

            Icon(
                painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            availableItems.forEach { item ->
                DropdownMenuItem(
                    text = { listItemComposable(item) },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DropDownMenuComponentPreview() {
    MaterialTheme {
        DropDownMenu(
            selectedItem = "Selected Item",
            availableItems = listOf("Item 1", "Item 2", "Item 3"),
            onItemSelected = { },
        )
    }
}
