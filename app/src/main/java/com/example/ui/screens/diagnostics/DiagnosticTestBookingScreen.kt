package com.example.ui.screens.diagnostics

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.DiagnosticBookingEntity
import com.example.data.local.DiagnosticTestEntity
import com.example.data.model.DiagnosticBookingStatus
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.HealthPrimaryLight
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class FacilityCategory {
    ALL,
    GOVERNMENT,
    PRIVATE
}

data class DiagnosticFacility(
    val id: String,
    val name: String,
    val category: FacilityCategory,
    val phone: String,
    val address: String,
    val priceNote: String
)

val defaultDiagnosticFacilities = listOf(
    // Government Facilities
    DiagnosticFacility(
        id = "gov_1",
        name = "Government General Hospital",
        category = FacilityCategory.GOVERNMENT,
        phone = "033-2223-1000",
        address = "College Street, Kolkata",
        priceNote = "Subsidized Govt. Rate"
    ),
    DiagnosticFacility(
        id = "gov_2",
        name = "District Hospital Diagnostic Centre",
        category = FacilityCategory.GOVERNMENT,
        phone = "033-2475-2000",
        address = "Barasat Road, North 24 Parganas",
        priceNote = "Govt. Subsidized"
    ),
    DiagnosticFacility(
        id = "gov_3",
        name = "Government Medical College & Hospital",
        category = FacilityCategory.GOVERNMENT,
        phone = "033-2255-3000",
        address = "88 College Street, Kolkata",
        priceNote = "Free for BPL / Subsidized"
    ),
    DiagnosticFacility(
        id = "gov_4",
        name = "State Diagnostic Centre",
        category = FacilityCategory.GOVERNMENT,
        phone = "033-2334-4000",
        address = "Salt Lake Sector 1, Kolkata",
        priceNote = "Standard Govt. Rates"
    ),
    DiagnosticFacility(
        id = "gov_5",
        name = "Sonarpur Rural Hospital Lab",
        category = FacilityCategory.GOVERNMENT,
        phone = "033-2434-5678",
        address = "Station Road, Sonarpur",
        priceNote = "Free (NHM Supported)"
    ),

    // Private Facilities
    DiagnosticFacility(
        id = "pvt_1",
        name = "Peerless Diagnostic Centre",
        category = FacilityCategory.PRIVATE,
        phone = "033-4011-1222",
        address = "360 Panchasayar, Kolkata",
        priceNote = "NABL Accredited"
    ),
    DiagnosticFacility(
        id = "pvt_2",
        name = "AMRI Diagnostic Centre",
        category = FacilityCategory.PRIVATE,
        phone = "033-6680-0000",
        address = "Dhakuria, Kolkata",
        priceNote = "NABH & NABL Lab"
    ),
    DiagnosticFacility(
        id = "pvt_3",
        name = "Apollo Diagnostics",
        category = FacilityCategory.PRIVATE,
        phone = "033-2320-3040",
        address = "Canal Circular Road, Kolkata",
        priceNote = "Premium Diagnostic Unit"
    ),
    DiagnosticFacility(
        id = "pvt_4",
        name = "Suraksha Diagnostic Centre",
        category = FacilityCategory.PRIVATE,
        phone = "033-6619-1000",
        address = "EM Bypass, Kasba, Kolkata",
        priceNote = "Home Collection Available"
    ),
    DiagnosticFacility(
        id = "pvt_5",
        name = "Dr. Lal PathLabs",
        category = FacilityCategory.PRIVATE,
        phone = "011-3988-5050",
        address = "Garia Main Road, Kolkata",
        priceNote = "Digital Fast Reports"
    ),
    DiagnosticFacility(
        id = "pvt_6",
        name = "Ruby General Hospital Lab",
        category = FacilityCategory.PRIVATE,
        phone = "033-3987-1800",
        address = "Kasba Golpark, Kolkata",
        priceNote = "24x7 Diagnostic Unit"
    )
)

fun getTestPreparationAdvice(test: DiagnosticTestEntity): List<String> {
    val name = test.name.lowercase()
    val prep = test.prepInstructions.lowercase()

    return when {
        name.contains("cbc") || name.contains("complete blood count") -> listOf(
            "No special fasting is usually required.",
            "Take your regular medicines unless your doctor has advised otherwise.",
            "Stay normally hydrated before sample collection."
        )
        name.contains("fasting blood sugar") || name.contains("fbs") -> listOf(
            "Fast for approximately 8–10 hours before the test.",
            "Water is generally allowed.",
            "Avoid food, sugary drinks and other caloric beverages during the fasting period."
        )
        name.contains("hba1c") -> listOf(
            "Fasting is generally not required.",
            "Follow your doctor's instructions regarding medications and meals."
        )
        name.contains("ecg") || name.contains("electrocardiogram") -> listOf(
            "Avoid heavy exercise immediately before the test.",
            "Wear comfortable clothing.",
            "Inform the technician about relevant medications or cardiac symptoms."
        )
        name.contains("x-ray") || name.contains("chest x-ray") -> listOf(
            "Follow the diagnostic centre's instructions.",
            "Remove metal objects or jewellery around the chest area when requested.",
            "Inform the technician if pregnancy is possible."
        )
        name.contains("mri") || name.contains("magnetic resonance") -> listOf(
            "Inform the centre about implants, pacemakers, metal fragments or other medical devices.",
            "Follow the centre's instructions regarding food and medication."
        )
        name.contains("echocardiography") || name.contains("echo") -> listOf(
            "Wear comfortable two-piece clothing.",
            "No specific fasting required.",
            "Continue your regular cardiac medications unless instructed otherwise."
        )
        name.contains("ct scan") || name.contains("computed tomography") -> listOf(
            "Inform technician if pregnant or wearing implants.",
            "Fasting for 4 hours may be advised if IV contrast is used.",
            "Drink plenty of water afterwards to flush contrast."
        )
        name.contains("ultrasound") || name.contains("usg") -> listOf(
            "For abdominal ultrasound: Fasting for 6–8 hours prior is usually required.",
            "For pelvic scans: Drink 32 oz of water 1 hour prior to maintain full bladder.",
            "Wear comfortable clothing."
        )
        prep.isNotBlank() -> listOf(
            test.prepInstructions,
            "Stay normally hydrated and follow your doctor's specific recommendations."
        )
        else -> listOf(
            "No special preparation is generally required.",
            "Stay normally hydrated and follow your doctor's instructions."
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosticTestBookingScreen(
    viewModel: SmartHealthViewModel,
    testId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val allTests by viewModel.diagnosticTests.collectAsStateWithLifecycle()

    // Find the selected test or create a sensible fallback if loading
    val selectedTest = remember(allTests, testId) {
        allTests.find { it.id == testId } ?: DiagnosticTestEntity(
            id = testId,
            name = when (testId) {
                "diag_1" -> "Complete Blood Count (CBC) with ESR"
                "diag_2" -> "12-Lead Electrocardiogram (ECG)"
                "diag_3" -> "Digital Chest X-Ray (PA View)"
                "diag_4" -> "Fasting Blood Sugar (FBS) & HbA1c"
                "diag_5" -> "2D Echocardiography with Color Doppler"
                "diag_6" -> "CT Scan - Brain (Plain)"
                else -> "Diagnostic Test"
            },
            category = "Clinical Diagnostics",
            description = "Comprehensive laboratory test and diagnostic examination.",
            prepInstructions = "Follow standard preparation guidelines.",
            approxPrice = "₹250 - ₹500",
            turnAroundTime = "Same Day"
        )
    }

    // Facility Filter State: ALL, GOVERNMENT, PRIVATE
    var facilityFilter by rememberSaveable { mutableStateOf(FacilityCategory.ALL) }

    val filteredFacilities = remember(facilityFilter) {
        when (facilityFilter) {
            FacilityCategory.ALL -> defaultDiagnosticFacilities
            FacilityCategory.GOVERNMENT -> defaultDiagnosticFacilities.filter { it.category == FacilityCategory.GOVERNMENT }
            FacilityCategory.PRIVATE -> defaultDiagnosticFacilities.filter { it.category == FacilityCategory.PRIVATE }
        }
    }

    var selectedFacility by remember { mutableStateOf<DiagnosticFacility?>(null) }
    var facilityDropdownExpanded by remember { mutableStateOf(false) }

    // Date selection
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    // Prescription file selection
    var prescriptionUri by remember { mutableStateOf<Uri?>(null) }
    var prescriptionFileName by rememberSaveable { mutableStateOf<String?>(null) }
    var cameraTempFile by remember { mutableStateOf<File?>(null) }
    var cameraTempUri by remember { mutableStateOf<Uri?>(null) }

    // Auth prompt dialog state
    var showAuthPromptDialog by remember { mutableStateOf(false) }

    // Status Timeline Modal
    var showStatusModal by remember { mutableStateOf(false) }

    // Latest Booking for this test
    val latestBookingFromDb by viewModel.getLatestBookingForTest(selectedTest.id).collectAsStateWithLifecycle(null)
    var activeBooking by remember { mutableStateOf<DiagnosticBookingEntity?>(null) }

    LaunchedEffect(latestBookingFromDb) {
        if (latestBookingFromDb != null) {
            activeBooking = latestBookingFromDb
        }
    }

    // Auto progression timer (1 minute from requestCreatedAt)
    LaunchedEffect(activeBooking?.bookingId, activeBooking?.status) {
        val booking = activeBooking ?: return@LaunchedEffect
        if (booking.status != DiagnosticBookingStatus.CONFIRMED) {
            val elapsed = System.currentTimeMillis() - booking.requestCreatedAt
            val remaining = 60_000L - elapsed
            if (remaining > 0) {
                delay(remaining)
            }
            viewModel.updateDiagnosticBookingStatus(booking.bookingId, DiagnosticBookingStatus.CONFIRMED)
            activeBooking = booking.copy(status = DiagnosticBookingStatus.CONFIRMED)
        }
    }

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {}

            prescriptionUri = uri
            prescriptionFileName = getFileNameFromUri(context, uri)
            Toast.makeText(context, "Prescription attached: $prescriptionFileName", Toast.LENGTH_SHORT).show()
        }
    }

    // Camera Picture Launcher
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraTempUri != null) {
            prescriptionUri = cameraTempUri
            prescriptionFileName = cameraTempFile?.name ?: "Prescription_Photo_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
            Toast.makeText(context, "Prescription photo attached", Toast.LENGTH_SHORT).show()
        }
    }

    // Camera Permission Launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                val data = createCameraTempFile(context)
                if (data != null) {
                    cameraTempFile = data.first
                    cameraTempUri = data.second
                    takePictureLauncher.launch(data.second)
                } else {
                    Toast.makeText(context, "Could not initialize camera file", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Camera error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission is required to capture prescriptions", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedTest.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
                .testTag("diagnostic_test_booking_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SECTION 1: SELECTED TEST INFO CARD
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE0F2F1),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Biotech,
                                    contentDescription = null,
                                    tint = HealthPrimaryLight,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedTest.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFFF1F5F9)
                                ) {
                                    Text(
                                        text = selectedTest.category,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HealthPrimaryLight,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Est: ${selectedTest.turnAroundTime}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                        Text(
                            text = selectedTest.approxPrice,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00796B)
                        )
                    }
                }
            }

            // SECTION 2: GOVERNMENT / PRIVATE / ALL (Pill / Oval Chips)
            item {
                Column {
                    Text(
                        text = "Facility Type",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(
                            FacilityCategory.ALL to "All",
                            FacilityCategory.GOVERNMENT to "Government",
                            FacilityCategory.PRIVATE to "Private"
                        ).forEach { (cat, label) ->
                            val isSelected = facilityFilter == cat
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) HealthPrimaryLight else Color.White,
                                border = BorderStroke(1.dp, if (isSelected) HealthPrimaryLight else Color(0xFFCBD5E1)),
                                modifier = Modifier
                                    .clickable {
                                        facilityFilter = cat
                                        // Reset or keep facility if still matches
                                        if (selectedFacility != null && cat != FacilityCategory.ALL && selectedFacility?.category != cat) {
                                            selectedFacility = null
                                        }
                                    }
                                    .testTag("filter_facility_${label.lowercase()}")
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF334155),
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // SECTION 3: SELECT LAB / CLINIC DROPDOWN
            item {
                Column {
                    Text(
                        text = "Select Lab / Clinic",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedCard(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { facilityDropdownExpanded = true }
                                .testTag("select_facility_dropdown")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (selectedFacility != null) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = selectedFacility!!.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0F172A)
                                        )
                                        Text(
                                            text = "${if (selectedFacility!!.category == FacilityCategory.GOVERNMENT) "Government" else "Private"} • ${selectedFacility!!.priceNote}",
                                            fontSize = 11.5.sp,
                                            color = HealthPrimaryLight
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "Select a diagnostic centre",
                                        fontSize = 14.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Open dropdown",
                                    tint = Color(0xFF64748B)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = facilityDropdownExpanded,
                            onDismissRequest = { facilityDropdownExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            filteredFacilities.forEach { fac ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = fac.name,
                                                fontSize = 13.5.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "${if (fac.category == FacilityCategory.GOVERNMENT) "Govt" else "Private"} • ${fac.address} • ${fac.priceNote}",
                                                fontSize = 11.sp,
                                                color = Color(0xFF64748B)
                                            )
                                        }
                                    },
                                    onClick = {
                                        selectedFacility = fac
                                        facilityDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // SECTION 4: TEST-SPECIFIC PREPARATION / ADVICE BOX
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                    border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFFEF3C7),
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MedicalInformation,
                                        contentDescription = null,
                                        tint = Color(0xFFD97706),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CarePath Advice • Test Preparation",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val prepTips = remember(selectedTest) { getTestPreparationAdvice(selectedTest) }
                        prepTips.forEach { tip ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("• ", fontSize = 12.sp, color = Color(0xFFB45309), fontWeight = FontWeight.Bold)
                                Text(
                                    text = tip,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = Color(0xFF78350F)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Educational advice only; follow any specific instructions from your doctor or the lab.",
                            fontSize = 10.5.sp,
                            color = Color(0xFFB45309),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }

            // SECTION 5: CALL + DATE SELECTION
            item {
                Column {
                    Text(
                        text = "Appointment & Facility Contact",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Call Button
                        Button(
                            onClick = {
                                if (selectedFacility != null) {
                                    viewModel.dialPhoneNumber(context, selectedFacility!!.phone)
                                } else {
                                    Toast.makeText(context, "Please select a lab or clinic first to call", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0284C7)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("facility_call_button")
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedFacility != null) "Call Lab" else "Call",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Select Date Button
                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (selectedDate.isNotEmpty()) HealthPrimaryLight else Color(0xFFCBD5E1)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selectedDate.isNotEmpty()) Color(0xFFE0F2F1) else Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("select_date_button")
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = "Select Date",
                                tint = if (selectedDate.isNotEmpty()) HealthPrimaryLight else Color(0xFF475569),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedDate.isNotEmpty()) selectedDate else "Select Date",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedDate.isNotEmpty()) HealthPrimaryLight else Color(0xFF334155),
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // SECTION 6: PRESCRIPTION UPLOAD
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.UploadFile,
                                contentDescription = null,
                                tint = HealthPrimaryLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Upload Prescription (Optional)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        }
                        Text(
                            text = "Attach existing doctor prescription to expedite lab registration",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = { filePickerLauncher.launch("*/*") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("upload_prescription_device")
                            ) {
                                Icon(Icons.Default.FolderOpen, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Device", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    val hasCamPerm = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED

                                    if (hasCamPerm) {
                                        try {
                                            val data = createCameraTempFile(context)
                                            if (data != null) {
                                                cameraTempFile = data.first
                                                cameraTempUri = data.second
                                                takePictureLauncher.launch(data.second)
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Camera error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("upload_prescription_camera")
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Camera", fontSize = 12.sp)
                            }
                        }

                        if (prescriptionFileName != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFE0F2F1),
                                border = BorderStroke(1.dp, Color(0xFF80CBC4)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Column {
                                            Text(
                                                text = "Prescription Attached",
                                                fontSize = 11.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF004D40)
                                            )
                                            Text(
                                                text = prescriptionFileName!!,
                                                fontSize = 11.sp,
                                                color = Color(0xFF006D77),
                                                maxLines = 1
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            prescriptionUri = null
                                            prescriptionFileName = null
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Remove file", modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // SECTION 7: BOOK BUTTON OR STATUS SECTION
            item {
                if (activeBooking == null) {
                    // BOOK Button
                    Button(
                        onClick = {
                            // Validation 1: User must be authenticated
                            if (currentUser == null) {
                                showAuthPromptDialog = true
                                return@Button
                            }

                            // Validation 2: Lab / clinic selected
                            if (selectedFacility == null) {
                                Toast.makeText(context, "Please select a lab or clinic.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Validation 3: Date selected
                            if (selectedDate.isBlank()) {
                                Toast.makeText(context, "Please select a test date.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Create the booking
                            val newBooking = DiagnosticBookingEntity(
                                bookingId = "CP-DIAG-${(10000..99999).random()}",
                                userId = currentUser?.id,
                                patientName = currentUser?.name ?: "Patient",
                                testId = selectedTest.id,
                                testName = selectedTest.name,
                                facilityId = selectedFacility!!.id,
                                facilityName = selectedFacility!!.name,
                                facilityType = if (selectedFacility!!.category == FacilityCategory.GOVERNMENT) "Government" else "Private",
                                phoneNumber = selectedFacility!!.phone,
                                bookingDate = selectedDate,
                                prescriptionUri = prescriptionUri?.toString(),
                                prescriptionFileName = prescriptionFileName,
                                requestCreatedAt = System.currentTimeMillis(),
                                status = DiagnosticBookingStatus.WAITING_FOR_CONFIRMATION
                            )

                            viewModel.createDiagnosticBooking(newBooking)
                            activeBooking = newBooking
                            Toast.makeText(context, "Booking requested successfully!", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HealthPrimaryLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("diagnostic_book_button")
                    ) {
                        Text(
                            text = "BOOK",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    // ACTIVE BOOKING STATUS SECTION (Clickable!)
                    val booking = activeBooking!!
                    val isConfirmed = booking.status == DiagnosticBookingStatus.CONFIRMED

                    val bannerBg by animateColorAsState(
                        targetValue = if (isConfirmed) Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
                        animationSpec = tween(600),
                        label = "status_bg"
                    )
                    val bannerBorder by animateColorAsState(
                        targetValue = if (isConfirmed) Color(0xFF86EFAC) else Color(0xFFFDE68A),
                        animationSpec = tween(600),
                        label = "status_border"
                    )
                    val bannerText by animateColorAsState(
                        targetValue = if (isConfirmed) Color(0xFF15803D) else Color(0xFFB45309),
                        animationSpec = tween(600),
                        label = "status_text"
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = bannerBg,
                        border = BorderStroke(1.dp, bannerBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showStatusModal = true }
                            .testTag("booking_status_banner")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isConfirmed) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = bannerText,
                                        modifier = Modifier.size(22.dp)
                                    )
                                } else {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.5.dp,
                                        color = bannerText
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isConfirmed) "✓ Confirmed" else "Waiting for Confirmation...",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = bannerText
                                    )
                                    Text(
                                        text = "Ref: ${booking.bookingId} • Tap to view status tracker",
                                        fontSize = 11.sp,
                                        color = bannerText.copy(alpha = 0.8f)
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "View tracker",
                                tint = bannerText
                            )
                        }
                    }

                    // DOWNLOAD REPORT BUTTON - ONLY AFTER CONFIRMATION!
                    if (isConfirmed) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                DiagnosticReportPdfGenerator.generateAndOpenReport(context, booking)
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("download_report_button")
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Download Report",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    // Material 3 Date Picker Dialog
    if (showDatePicker) {
        val todayStart = remember {
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis() + 86400000L,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    // Do not allow past dates
                    return utcTimeMillis >= todayStart
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            selectedDate = sdf.format(Date(millis))
                        }
                        showDatePicker = false
                    },
                    modifier = Modifier.testTag("date_picker_confirm")
                ) {
                    Text("OK", fontWeight = FontWeight.Bold, color = HealthPrimaryLight)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Auth Required Dialog for Guest Users
    if (showAuthPromptDialog) {
        AlertDialog(
            onDismissRequest = { showAuthPromptDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = HealthPrimaryLight,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text("Create Account / Sign In", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "You need an account to book a diagnostic test and track your booking status.",
                    fontSize = 13.5.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAuthPromptDialog = false
                        viewModel.setLoginDialogOpen(true)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HealthPrimaryLight),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("auth_prompt_login_button")
                ) {
                    Text("Create Account / Sign In")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showAuthPromptDialog = false },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // Booking Status Vertical Stepper Modal
    if (showStatusModal && activeBooking != null) {
        val booking = activeBooking!!
        val isConfirmed = booking.status == DiagnosticBookingStatus.CONFIRMED

        AlertDialog(
            onDismissRequest = { showStatusModal = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = null,
                        tint = HealthPrimaryLight,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Booking Status",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Ref: ${booking.bookingId} • ${booking.testName}",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Step 1: Request Submitted
                    TimelineStepItem(
                        title = "Request Submitted",
                        description = "Test booking details sent to diagnostic facility.",
                        status = TimelineStepState.COMPLETED
                    )

                    TimelineConnector()

                    // Step 2: Waiting for Confirmation
                    TimelineStepItem(
                        title = "Waiting for Confirmation",
                        description = if (isConfirmed) "Facility verified and accepted your slot." else "Facility is checking technician and slot availability...",
                        status = if (isConfirmed) TimelineStepState.COMPLETED else TimelineStepState.ACTIVE
                    )

                    TimelineConnector()

                    // Step 3: Confirmed
                    TimelineStepItem(
                        title = "Confirmed",
                        description = if (isConfirmed) "Slot confirmed for ${booking.bookingDate}. Please visit facility on scheduled date." else "Confirmation pending from facility.",
                        status = if (isConfirmed) TimelineStepState.COMPLETED else TimelineStepState.PENDING
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF8FAFC),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Facility: ${booking.facilityName} (${booking.facilityType})", fontSize = 11.sp, color = Color(0xFF334155))
                            Text("Date: ${booking.bookingDate}", fontSize = 11.sp, color = Color(0xFF334155))
                            Text("Patient: ${booking.patientName}", fontSize = 11.sp, color = Color(0xFF334155))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showStatusModal = false }) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

enum class TimelineStepState {
    COMPLETED,
    ACTIVE,
    PENDING
}

@Composable
fun TimelineStepItem(
    title: String,
    description: String,
    status: TimelineStepState
) {
    Row(verticalAlignment = Alignment.Top) {
        when (status) {
            TimelineStepState.COMPLETED -> {
                Surface(shape = CircleShape, color = SuccessGreen, modifier = Modifier.size(24.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
            TimelineStepState.ACTIVE -> {
                Surface(shape = CircleShape, color = Color(0xFFF59E0B), modifier = Modifier.size(24.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White))
                    }
                }
            }
            TimelineStepState.PENDING -> {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(2.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier.size(24.dp)
                ) {}
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (status != TimelineStepState.PENDING) FontWeight.Bold else FontWeight.Medium,
                color = when (status) {
                    TimelineStepState.COMPLETED -> Color(0xFF15803D)
                    TimelineStepState.ACTIVE -> Color(0xFFB45309)
                    TimelineStepState.PENDING -> Color(0xFF94A3B8)
                }
            )
            Text(
                text = description,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                color = if (status != TimelineStepState.PENDING) Color(0xFF475569) else Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
fun TimelineConnector() {
    Row {
        Box(
            modifier = Modifier
                .padding(start = 11.dp)
                .width(2.dp)
                .height(20.dp)
                .background(Color(0xFFCBD5E1))
        )
    }
}

private fun getFileNameFromUri(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = it.getString(index)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "Prescription_${System.currentTimeMillis()}"
}

private fun createCameraTempFile(context: Context): Pair<File, Uri>? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "Prescription_${timeStamp}_"
        val storageDir = File(context.cacheDir, "camera_photos").apply { mkdirs() }
        val imageFile = File.createTempFile(fileName, ".jpg", storageDir)
        val authority = "${context.applicationContext.packageName}.fileprovider"
        val uri = FileProvider.getUriForFile(context, authority, imageFile)
        Pair(imageFile, uri)
    } catch (_: Exception) {
        null
    }
}
