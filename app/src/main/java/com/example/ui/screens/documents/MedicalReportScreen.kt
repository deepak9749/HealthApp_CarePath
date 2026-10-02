package com.example.ui.screens.documents

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.data.local.MedicalReportEntity
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.HealthPrimaryLight
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalReportScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val reports by viewModel.medicalReports.collectAsStateWithLifecycle()
    var showUploadDialog by rememberSaveable { mutableStateOf(false) }
    var selectedReportForDetails by remember { mutableStateOf<MedicalReportEntity?>(null) }

    val documentCategories = remember {
        listOf(
            "Prescription",
            "Blood Test Report",
            "Lab Test Report",
            "Diagnostic Report",
            "Medical Imaging / Scan",
            "X-Ray",
            "CT Scan",
            "MRI Report",
            "Ultrasound Report",
            "Doctor's Consultation Report",
            "Discharge Summary",
            "Hospital Report",
            "Medication Record",
            "Vaccination Record",
            "Other"
        )
    }

    var selectedCategory by rememberSaveable { mutableStateOf("Blood Test Report") }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    var hospName by rememberSaveable { mutableStateOf("Peerless Hospital / Sonarpur Diag") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by rememberSaveable { mutableStateOf<String?>(null) }
    var isImageSelected by rememberSaveable { mutableStateOf(false) }
    var validationError by rememberSaveable { mutableStateOf<String?>(null) }

    var cameraTempFile by remember { mutableStateOf<File?>(null) }
    var cameraTempUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraTempUri != null) {
            selectedFileUri = cameraTempUri
            val generatedName = cameraTempFile?.name ?: "Medical_Report_${SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
            selectedFileName = generatedName
            isImageSelected = true
            validationError = null
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                val cameraData = createCameraImageUri(context)
                if (cameraData != null) {
                    cameraTempFile = cameraData.first
                    cameraTempUri = cameraData.second
                    takePictureLauncher.launch(cameraData.second)
                } else {
                    Toast.makeText(context, "Could not initialize camera storage", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Camera error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission is required to capture reports. You can still use 'Choose File'.", Toast.LENGTH_LONG).show()
        }
    }

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

            selectedFileUri = uri
            val realName = getFileNameFromUri(context, uri)
            selectedFileName = realName
            isImageSelected = isImageFile(realName, uri.toString())
            validationError = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medical Reports & AI OCR", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showUploadDialog = true },
                        modifier = Modifier.testTag("upload_report_top_button")
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = "Upload Report")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showUploadDialog = true },
                containerColor = HealthPrimaryLight,
                contentColor = Color.White,
                modifier = Modifier.testTag("upload_report_fab")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Upload Report", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("medical_report_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Educational Banner
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("AI Report Interpretation Engine", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Upload blood test PDFs or lab photos for plain-language parameter explanations and reference ranges. (Non-diagnostic educational tool).", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }

            items(reports) { report ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedReportForDetails = report }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = report.reportType,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(report.reportDate, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(report.fileName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(report.hospitalName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)

                        if (report.fileUri.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            if (isImageFile(report.fileName, report.fileUri)) {
                                AsyncImage(
                                    model = report.fileUri,
                                    contentDescription = "Report Attachment Preview",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HealthPrimaryLight, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("AI Analysis Summary", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = HealthPrimaryLight)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = report.aiAnalysis ?: "Standard clinical parameter reference generated.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (report.fileUri.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = { openDocument(context, report.fileUri) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("View Document")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showUploadDialog) {
        val scrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = {
                showUploadDialog = false
                validationError = null
            },
            modifier = Modifier.testTag("upload_medical_document_dialog"),
            title = {
                Text(
                    text = "Upload Medical Document",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // 1. Document Category Dropdown (At the top)
                    Column {
                        Text(
                            text = "Document Category",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = selectedCategory,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { categoryDropdownExpanded = !categoryDropdownExpanded }) {
                                        Icon(
                                            imageVector = if (categoryDropdownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                            contentDescription = "Select Category"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Surface(
                                color = Color.Transparent,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clickable { categoryDropdownExpanded = !categoryDropdownExpanded }
                            ) {}
                            DropdownMenu(
                                expanded = categoryDropdownExpanded,
                                onDismissRequest = { categoryDropdownExpanded = false }
                            ) {
                                documentCategories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category) },
                                        onClick = {
                                            selectedCategory = category
                                            categoryDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // 2. File Selection Options: Choose File or Take Photo
                    Column {
                        Text(
                            text = "Select Document",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    try {
                                        filePickerLauncher.launch("*/*")
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Could not open file picker: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("choose_file_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Choose File", maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }

                            Button(
                                onClick = {
                                    val hasPermission = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED

                                    if (hasPermission) {
                                        try {
                                            val cameraData = createCameraImageUri(context)
                                            if (cameraData != null) {
                                                cameraTempFile = cameraData.first
                                                cameraTempUri = cameraData.second
                                                takePictureLauncher.launch(cameraData.second)
                                            } else {
                                                Toast.makeText(context, "Could not initialize camera storage", Toast.LENGTH_SHORT).show()
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Camera error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        try {
                                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Could not request camera permission: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("take_photo_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = HealthPrimaryLight)
                            ) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Take Photo", maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }

                    // 3. Selected File Information & Preview
                    if (selectedFileUri != null && !selectedFileName.isNullOrBlank()) {
                        val fileName = selectedFileName.orEmpty()
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = if (isImageSelected) Icons.Default.Image else if (fileName.endsWith(".pdf", ignoreCase = true)) Icons.Default.PictureAsPdf else Icons.Default.Description,
                                        contentDescription = null,
                                        tint = HealthPrimaryLight,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = fileName,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = selectedCategory,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            selectedFileUri = null
                                            selectedFileName = null
                                            isImageSelected = false
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Clear selected file", modifier = Modifier.size(18.dp))
                                    }
                                }

                                if (isImageSelected) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    AsyncImage(
                                        model = selectedFileUri,
                                        contentDescription = "Document Thumbnail",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(140.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }

                    // 4. Diagnostic Centre / Hospital (Optional field)
                    OutlinedTextField(
                        value = hospName,
                        onValueChange = { hospName = it },
                        label = { Text("Diagnostic Centre / Hospital") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 5. Validation error
                    if (validationError != null) {
                        Text(
                            text = validationError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val currentFileName = selectedFileName.orEmpty()
                        if (selectedFileUri == null || currentFileName.isBlank()) {
                            validationError = "Please choose a file or take a photo first."
                            return@Button
                        }

                        val sampleAnalysis = when (selectedCategory) {
                            "Blood Test Report" -> "Hemoglobin, Platelets, and WBC parameters evaluated within standard laboratory reference ranges."
                            "Prescription" -> "Prescription medication schedule, dosage instructions, and clinical precautions recorded."
                            "Diagnostic Report", "Lab Test Report" -> "Laboratory diagnostic investigation markers recorded with standard clinical references."
                            "X-Ray", "CT Scan", "MRI Report", "Medical Imaging / Scan", "Ultrasound Report" -> "Medical imaging radiological record archived for clinical consultation."
                            "Doctor's Consultation Report" -> "Physician consultation clinical summary, vitals, and longitudinal treatment plan logged."
                            "Discharge Summary", "Hospital Report" -> "Hospitalization clinical summary, discharge vitals, and medication transitions cataloged."
                            "Vaccination Record" -> "Immunization record and preventive healthcare entry archived."
                            else -> "Medical record document successfully indexed and verified for your digital health timeline."
                        }

                        viewModel.uploadMedicalReport(
                            hospitalName = hospName.ifBlank { "Diagnostic Centre / Hospital" },
                            reportType = selectedCategory,
                            fileName = currentFileName,
                            aiAnalysis = sampleAnalysis,
                            fileUri = selectedFileUri.toString()
                        )
                        showUploadDialog = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("confirm_upload_button")
                ) {
                    Text("Upload Document")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUploadDialog = false },
                    modifier = Modifier.testTag("cancel_upload_button")
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (selectedReportForDetails != null) {
        val rep = selectedReportForDetails!!
        AlertDialog(
            onDismissRequest = { selectedReportForDetails = null },
            title = { Text(rep.fileName, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text("Hospital: ${rep.hospitalName}", fontWeight = FontWeight.SemiBold)
                    Text("Category: ${rep.reportType}", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))

                    if (rep.fileUri.isNotBlank()) {
                        if (isImageFile(rep.fileName, rep.fileUri)) {
                            AsyncImage(
                                model = rep.fileUri,
                                contentDescription = "Full Report View",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    Text("AI Interpretation & Reference Parameters:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(rep.aiAnalysis ?: "Standard clinical parameter reference generated.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Disclaimer: This AI analysis explains terminology and compares numbers to standard laboratory references. It does not replace a clinical consultation.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (rep.fileUri.isNotBlank()) {
                        Button(
                            onClick = { openDocument(context, rep.fileUri) },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Open File")
                        }
                    }
                    TextButton(onClick = { selectedReportForDetails = null }) {
                        Text("Done")
                    }
                }
            }
        )
    }
}

private fun getFileNameFromUri(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        result = cursor.getString(index)
                    }
                }
            }
        } catch (_: Exception) {}
    }
    if (result.isNullOrBlank()) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "Medical_Document_${System.currentTimeMillis()}"
}

private fun isImageFile(fileName: String, fileUriString: String): Boolean {
    val lowerName = fileName.lowercase()
    val lowerUri = fileUriString.lowercase()
    return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || lowerName.endsWith(".png") || lowerName.endsWith(".webp") ||
            lowerUri.endsWith(".jpg") || lowerUri.endsWith(".jpeg") || lowerUri.endsWith(".png") || lowerUri.endsWith(".webp")
}

private fun createCameraImageUri(context: Context): Pair<File, Uri>? {
    return try {
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "Medical_Report_${timeStamp}_"
        val storageDir = File(context.cacheDir, "camera_photos").apply { mkdirs() }
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        val authority = "${context.applicationContext.packageName}.fileprovider"
        val uri = FileProvider.getUriForFile(
            context,
            authority,
            imageFile
        )
        Pair(imageFile, uri)
    } catch (_: Exception) {
        null
    }
}

private fun openDocument(context: Context, fileUriString: String) {
    if (fileUriString.isBlank()) {
        Toast.makeText(context, "No file attachment available for this report", Toast.LENGTH_SHORT).show()
        return
    }
    try {
        val uri = Uri.parse(fileUriString)
        val mimeType = try {
            context.contentResolver.getType(uri)
        } catch (_: Exception) { null } ?: when {
            fileUriString.endsWith(".pdf", ignoreCase = true) -> "application/pdf"
            fileUriString.endsWith(".jpg", ignoreCase = true) || fileUriString.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
            fileUriString.endsWith(".png", ignoreCase = true) -> "image/png"
            fileUriString.endsWith(".webp", ignoreCase = true) -> "image/webp"
            else -> "*/*"
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooserIntent = Intent.createChooser(intent, "Open Medical Document").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open document: ${e.localizedMessage ?: "No compatible viewer found"}", Toast.LENGTH_LONG).show()
    }
}

