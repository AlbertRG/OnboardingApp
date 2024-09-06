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
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.altatec.myapplication.MainActivity
import com.altatec.myapplication.R
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.ui.viewmodel.HomeViewModel
import com.altatec.myapplication.utils.CameraUtils
import com.altatec.myapplication.utils.LocationUtils
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    cameraUtils: CameraUtils,
    locationUtils: LocationUtils,
    context: Context
) {

    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var birthdayError by remember { mutableStateOf(false) }
    var birthdaySuppText by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf(false) }
    var addressSuppText by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }
    var phoneSuppText by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    val hobbySuppText by remember { mutableStateOf("Optional") }
    val focusManager = LocalFocusManager.current

    var showDatePicker by remember { mutableStateOf(false) }
    var showMapsInfo by remember { mutableStateOf(false) }
    var reverseGeolocation by remember { mutableStateOf("") }

    val location = homeViewModel.location.value

    fun resetFields() {
        name = ""
        birthday = ""
        birthdaySuppText = ""
        birthdayError = false
        address = ""
        addressSuppText = ""
        addressError = false
        phone = ""
        phoneSuppText = ""
        phoneError = false
        hobby = ""
    }

    fun areAllFieldsFilled(): Boolean =
        name.isNotBlank() && birthday.isNotBlank()
                && address.isNotBlank() && phone.isNotBlank()

    fun areNoErrors(): Boolean =
        !birthdayError && !addressError && !phoneError

    fun isValidPhone(phone: String): Boolean =
        phone.isEmpty() || phone.length == 10

    fun isValidBirthday(birthday: String): Boolean {
        return birthday != "No date selected"
    }

    fun isValidAddress(address: String): Boolean {
        return address != "Address not found"
    }

    var toastMessage by remember { mutableStateOf("") }

    fun showLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

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

    val defaultDrawable = ContextCompat.getDrawable(context, R.drawable.baseline_person_72)
    val defaultBitmap = defaultDrawable?.let { drawableToBitmap(it) } ?: Bitmap.createBitmap(
        1,
        1,
        Bitmap.Config.ARGB_8888
    )
    var photoBitmap by remember { mutableStateOf(defaultBitmap) }
    var photoPreview by remember { mutableStateOf(defaultBitmap.asImageBitmap()) }
    var wasPhotoTaken by remember { mutableStateOf(false) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let { photo ->
                photoPreview = photo.asImageBitmap()
                photoBitmap = photo
                wasPhotoTaken = true
            }
        }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[Manifest.permission.CAMERA] == true) {
                cameraLauncher.launch()
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
        reverseGeolocation = location?.let {
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
                    cameraLauncher.launch()
                } else {
                    requestCameraPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA
                        )
                    )
                }
            },
            modifier = Modifier
                .size(160.dp)
                .padding(8.dp)
        ) {
            if (wasPhotoTaken) {
                Image(
                    bitmap = photoPreview,
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(150.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp)
                )
            }
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
                .fillMaxWidth(),
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
                        contentDescription = "Calendar"
                    )
                }
            },
            supportingText = {
                Text(
                    text = when {
                        birthday.isEmpty() || birthday == "No date selected" ->
                            "Please click on the icon to select a birthday"

                        else ->
                            ""
                    }
                )
            },
            isError = if (isValidBirthday(birthday)) {
                birthdayError = false
                false
            } else {
                birthdayError = true
                true
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
                        if (locationUtils.hasLocationPermission(context)) {
                            getAddress()
                            showMapsInfo = true
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
                        contentDescription = "Address"
                    )
                }
            },
            supportingText = {
                Text(
                    text = if (address != "") {
                        ""
                    } else {
                        "Please click on the icon to select a address"
                    }
                )
            },
            isError = if (isValidAddress(address)) {
                addressError = false
                false
            } else {
                addressSuppText = "Sorry we could not found your address location"
                addressError = true
                true
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
                    contentDescription = "Phone"
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
                    contentDescription = "Hobby"
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
                    val contact =
                        Contact(
                            id = 0L, photo = photoBitmap, name = name, birthday = birthday,
                            address = address, phone = phone, hobby = hobby
                        )
                    homeViewModel.insertContact(contact)
                    toastMessage = "Contact added successfully"
                    resetFields()
                    wasPhotoTaken = false
                    photoBitmap = defaultBitmap
                    photoPreview = defaultBitmap.asImageBitmap()
                    focusManager.clearFocus()
                } catch (e: Exception) {
                    toastMessage = "Something went wrong"
                }
                showLongToast(toastMessage)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            enabled = areAllFieldsFilled() && areNoErrors()
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
    if (showMapsInfo) {
        MapsInfoDialog(
            location = reverseGeolocation,
            onDismiss = { showMapsInfo = false },
            onAccept = { address = it }
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

@Composable
fun MapsInfoDialog(
    location: String,
    onDismiss: () -> Unit,
    onAccept: (String) -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.maps))
    var address by remember { mutableStateOf(location) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Address Info",
                    modifier = Modifier
                        .padding(end = 55.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Cancel",
                        tint = Color.Black
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier.size(160.dp),
                )
                Text("We have found that your current address based on your location is:")
                OutlinedTextField(
                    value = address,
                    onValueChange = { newText ->
                        address = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Address")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                //TODO Open GoogleMap composable
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_map_24),
                                contentDescription = "Open Google Maps"
                            )
                        }
                    },
                    supportingText = {
                        Text(text = "You can edit this field or click the icon to open Google Maps")
                    },
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            onAccept(address)
                            onDismiss()
                        }
                    ) {
                        Text("Accept")
                    }
                }
            }
        }
    }
}

@Composable
fun MyGoogleMaps(location: LatLng) {

    val properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.SATELLITE
            )
        )
    }

    val location = LatLng(0.0, 0.0)

    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        properties = properties
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MapsDialogPrev() {
    AppTheme {
        MapsInfoDialog("GeorReverse Address", {}, {})
    }
}