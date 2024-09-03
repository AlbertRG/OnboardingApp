package com.altatec.myapplication.ui.view

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.altatec.myapplication.MainActivity
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.ui.viewmodel.HomeViewModel
import com.altatec.myapplication.utils.CameraUtils
import com.altatec.myapplication.utils.LocationUtils
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun HomeScreen(
    cameraUtils: CameraUtils,
    locationUtils: LocationUtils,
    context: Context
) {

    val homeViewModel: HomeViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("Select Date") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }
    var phoneSuppText by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    val hobbySuppText by remember { mutableStateOf("Optional") }
    val nameFocusRequester = remember { FocusRequester() }

    var showDatePicker by remember { mutableStateOf(false) }

    val location = homeViewModel.location.value

    fun resetFields() {
        name = ""
        birthday = ""
        address = ""
        phone = ""
        phoneSuppText = ""
        phoneError = false
        hobby = ""
    }

    fun areAllFieldsFilled(): Boolean =
        name.isNotBlank() && birthday.isNotBlank()
                && address.isNotBlank() && phone.isNotBlank()

    fun isValidPhone(phone: String): Boolean =
        phone.isEmpty() || phone.length == 10

    fun calculateAge(birthday: String, pattern: String = "yyyy-MM-dd"): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val birthDate = LocalDate.parse(birthday, formatter)
            val currentDate = LocalDate.now()
            val age = Period.between(birthDate, currentDate).years
            age.toString()
        } catch (e: DateTimeParseException) {
            "Error"
        }
    }

    fun takePhoto() {
        //TODO Take photo
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {

    }

    var toastMessage by remember { mutableStateOf("") }

    fun showLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[Manifest.permission.CAMERA] == true) {
                takePhoto()
                //toastMessage = "Camera permission is already acquire"
            } else {
                val rationaleRequired =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context as MainActivity,
                        Manifest.permission.CAMERA
                    )
                toastMessage = if (rationaleRequired) {
                    "Camera permission is required to take photos"
                } else {
                    "Please enable camera permission in Android Settings"
                }
            }
            showLongToast(toastMessage)
        }
    )

    fun formatDate(milliseconds: Long?): String {
        return milliseconds?.let {
            val instant = Instant.ofEpochMilli(it)
            val zoneId = ZoneId.systemDefault()
            val localDate = instant.atZone(zoneId).toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
            localDate.format(formatter)
        } ?: "No date selected"
    }

    fun getLocation() {
        locationUtils.requestLocationUpdates(homeViewModel)
    }

    fun getAddress() {
        locationUtils.requestLocationUpdates(homeViewModel)
        address = location?.let {
            locationUtils.reverseGeocodeLocation(location)
        } ?: "Address not found"
    }

    LaunchedEffect(Unit) {
        getLocation()
    }

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                getAddress()
            } else {
                val rationaleRequired =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context as MainActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )

                toastMessage = if (rationaleRequired) {
                    "Location permission is required to get your address"
                } else {
                    "Please enable location permission in Android Settings"
                }
            }
            showLongToast(toastMessage)
        }
    )

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap: Bitmap =
            if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
            } else {
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = {
                if (cameraUtils.hasCameraPermission(context)) {
                    takePhoto()
                } else {
                    requestCameraPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA
                        )
                    )
                }
            },
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_add_a_photo_48),
                contentDescription = "Photo",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { newText ->
                if (newText.length <= 15) {
                    name = newText
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(nameFocusRequester),
            label = {
                Text(text = "Name")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Name"
                )
            },
            supportingText = {
                Row {
                    Spacer(Modifier.weight(1f))
                    Text("${name.length}/15")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1
        )
        OutlinedTextField(
            value = birthday,
            onValueChange = { newText ->
                birthday = newText
            },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            label = {
                Text(text = "Birthday")
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        showDatePicker = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Email"
                    )
                }
            },
            supportingText = {
                Text(text = "")
            },
            maxLines = 1,
        )
        OutlinedTextField(
            value = address,
            onValueChange = { newText ->
                address = newText
            },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            label = {
                Text(text = "Address")
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        address = "click" //TODO
                        if (locationUtils.hasLocationPermission(context)) {
                            getAddress()
                        } else {
                            requestLocationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = "Email"
                    )
                }
            },
            supportingText = {
                Text(text = "")
            },
            maxLines = 1
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { newText ->
                if (newText.length <= 10) {
                    phone = newText
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Phone")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Password"
                )
            },
            supportingText = {
                Row {
                    Spacer(Modifier.weight(1f))
                    Text("${phone.length}/10")
                }
            },
            isError = if (isValidPhone(phone)) {
                phoneSuppText = ""
                phoneError = false
                false
            } else {
                phoneSuppText = "Please enter a 10 digits phone"
                phoneError = true
                true
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            maxLines = 1,
        )
        OutlinedTextField(
            value = hobby,
            onValueChange = { newText ->
                hobby = newText
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Hobby")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Password"
                )
            },
            supportingText = {
                Text(text = hobbySuppText)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
        )
        Button(
            onClick = {
                try {
                    /*val contact =
                        Contact(id = 0L, photo = "Ss", name = name, birthday = birthday,
                            address = address, phone = phone, hobby = hobbies)
                    homeViewModel.insertUser(user)*/ //TODO
                    //snackMessage = "Contact Added Successfully"
                    resetFields()
                    nameFocusRequester.requestFocus()
                } catch (e: Exception) {
                    //snackMessage = "Something went wrong"
                }
                //showSnackbar(snackMessage)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            enabled = areAllFieldsFilled()
        ) {
            Text(text = "Add Contact")
        }
    }
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { date ->
                birthday = formatDate(date)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomePrev() {
    AppTheme {
        val context = LocalContext.current
        val cameraUtils = CameraUtils(context)
        val locationUtils = LocationUtils(context)
        HomeScreen(cameraUtils, locationUtils, context)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DatePickerPrev() {
    AppTheme {
        DatePickerModal({}, {})
    }
}