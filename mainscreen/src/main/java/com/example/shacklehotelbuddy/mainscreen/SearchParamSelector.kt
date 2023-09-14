package com.example.shacklehotelbuddy.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shacklehotelbuddy.theme.ShackleHotelBuddyTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.example.shacklehotelbuddy.strings.R as stringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchParamSelector(
    modifier: Modifier = Modifier,
    state: SearchParamSelectorState,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        var datePickerShowing by rememberSaveable {
            mutableStateOf(false)
        }
        val dateFormatter = remember {
            DateTimeFormatter.ofPattern("dd / MM / yyyy")
        }
        if (datePickerShowing) {
            val dateRangePickerState = rememberDateRangePickerState()
            DatePickerDialog(
                onDismissRequest = { datePickerShowing = false },
                confirmButton = {
                    TextButton(
                        enabled = dateRangePickerState.selectedEndDateMillis != null && dateRangePickerState.selectedStartDateMillis != null,
                        onClick = {
                            datePickerShowing = false
                            state.checkInDate =
                                Instant.ofEpochMilli(dateRangePickerState.selectedStartDateMillis!!)
                                    .atZone(ZoneId.of("UTC")).toLocalDate()
                            state.checkOutDate =
                                Instant.ofEpochMilli(dateRangePickerState.selectedEndDateMillis!!)
                                    .atZone(ZoneId.of("UTC")).toLocalDate()
                        },
                    ) { Text(stringResource(stringR.string.ok)) }
                }
            ) {
                DateRangePicker(
                    state = dateRangePickerState,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Column {
            SearchParamRow(
                modifier = Modifier.clickable(
                    onClick = { datePickerShowing = true }
                ),
                icon = {
                    Icon(
                        painterResource(id = R.drawable.event_upcoming),
                        contentDescription = stringResource(
                            id = stringR.string.search_screen_check_in_date
                        )
                    )
                },
                title = { Text(stringResource(stringR.string.search_screen_check_in_date)) },
                value = {
                    val text = if (state.checkInDate == null) {
                        stringResource(stringR.string.search_screen_date_placeholder)
                    } else {
                        dateFormatter.format(state.checkInDate)
                    }
                    Text(text)
                }
            )
            Divider()
            SearchParamRow(
                modifier = Modifier.clickable(
                    onClick = { datePickerShowing = true }
                ),
                icon = {
                    Icon(
                        painterResource(id = R.drawable.event_available),
                        contentDescription = stringResource(
                            id = stringR.string.search_screen_check_out_date
                        )
                    )
                },
                title = { Text(stringResource(stringR.string.search_screen_check_out_date)) },
                value = {
                    val text = if (state.checkOutDate == null) {
                        stringResource(stringR.string.search_screen_date_placeholder)
                    } else {
                        dateFormatter.format(state.checkOutDate)
                    }
                    Text(text)
                }
            )
            Divider()
            SearchParamRow(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.person),
                        contentDescription = stringResource(
                            id = stringR.string.search_screen_adults
                        )
                    )
                },
                title = { Text(stringResource(stringR.string.search_screen_adults)) },
                value = {
                    BasicTextField(
                        value = state.adults,
                        onValueChange = {
                            val number = it.text.toIntOrNull()
                            val newTextFieldValue = if (number != null && number <= 0) {
                                TextFieldValue("1")
                            } else {
                                it
                            }
                            state.adults = newTextFieldValue
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(LocalContentColor.current),
                        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            )
            Divider()
            SearchParamRow(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.supervisor_account),
                        contentDescription = stringResource(
                            id = stringR.string.search_screen_children
                        )
                    )
                },
                title = { Text(stringResource(stringR.string.search_screen_children)) },
                value = {
                    BasicTextField(
                        value = state.children,
                        onValueChange = {
                            val number = it.text.toIntOrNull()
                            val newTextFieldValue = if (number != null && number <= 0) {
                                TextFieldValue("0")
                            } else {
                                it
                            }
                            state.children = newTextFieldValue
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(LocalContentColor.current),
                        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            )
        }
    }
}

@Composable
private fun SearchParamRow(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    value: @Composable () -> Unit,
) {
    Row(modifier = modifier.height(IntrinsicSize.Max)) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(8.dp))
            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                title()
            }
        }
        Divider(
            Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                value()
            }
        }
    }
}

@Stable
internal class SearchParamSelectorState(
    initialCheckInDate: LocalDate? = null,
    initialCheckOutDate: LocalDate? = null,
    initialAdults: TextFieldValue? = null,
    initialChildren: TextFieldValue? = null,
) {
    var checkInDate: LocalDate? by mutableStateOf(initialCheckInDate)
    var checkOutDate: LocalDate? by mutableStateOf(initialCheckOutDate)
    var adults: TextFieldValue by mutableStateOf(initialAdults ?: TextFieldValue("1"))
    var children: TextFieldValue by mutableStateOf(initialChildren ?: TextFieldValue("0"))

    fun toSearchParams(): SearchParams? {
        val safeInDate = checkInDate
        val safeOutDate = checkOutDate
        val adultsInt = adults.text.toIntOrNull()
        val childrenInt = children.text.toIntOrNull()
        return if (safeInDate != null &&
            safeOutDate != null &&
            adultsInt != null &&
            adultsInt > 0 &&
            childrenInt != null &&
            childrenInt >= 0
        ) {
            SearchParams(
                checkInDate = safeInDate,
                checkOutDate = safeOutDate,
                adultsNumber = adultsInt,
                childrenNumber = childrenInt,
            )
        } else {
            null
        }
    }

    companion object {
        val Saver: Saver<SearchParamSelectorState, *> = listSaver(
            save = {
                listOf(
                    it.checkInDate,
                    it.checkOutDate,
                    with(TextFieldValue.Saver) { save(it.adults) },
                    with(TextFieldValue.Saver) { save(it.children) }
                )
            },
            restore = { list ->
                SearchParamSelectorState(
                    list[0] as LocalDate?,
                    list[1] as LocalDate?,
                    TextFieldValue.Saver.restore(list[2]!!),
                    TextFieldValue.Saver.restore(list[3]!!),
                )
            }
        )
    }
}

@Composable
internal fun rememberSearchParamSelectorState(): SearchParamSelectorState =
    rememberSaveable(saver = SearchParamSelectorState.Saver) {
        SearchParamSelectorState()
    }

@Preview
@Composable
private fun SearchParamPreviewLight() {
    ShackleHotelBuddyTheme(
        isDarkTheme = false
    ) {
        SearchParamSelector(
            state = SearchParamSelectorState()
        )
    }
}

@Preview
@Composable
private fun SearchParamPreviewDark() {
    ShackleHotelBuddyTheme(
        isDarkTheme = true
    ) {
        SearchParamSelector(
            state = SearchParamSelectorState()
        )
    }
}
