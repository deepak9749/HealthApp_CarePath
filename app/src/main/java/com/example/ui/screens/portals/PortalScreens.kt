package com.example.ui.screens.portals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.BedAvailabilityEntity
import com.example.data.local.HospitalEntity
import com.example.data.model.RiskLevel
import com.example.data.model.SyncStatus
import com.example.data.model.UrgencyLevel
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.HealthPrimaryLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthWorkerPortalScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val followUps by viewModel.followUps.collectAsStateWithLifecycle()
    val allHospitals by viewModel.allHospitals.collectAsStateWithLifecycle()
    var showReferralDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frontline Worker (ASHA / ANM)", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showReferralDialog = true }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "New Referral")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showReferralDialog = true },
                containerColor = HealthPrimaryLight,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate Field Referral", fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("health_worker_portal_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Sonarpur PHC Sub-Centre • Worker: Sunita Ghosh", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Assigned Ward: Sonarpur Rural & Narendrapur • 342 Households Registered", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            item {
                Text("High-Risk Follow-Up Reminders (${followUps.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(followUps) { fu ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(fu.patientName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (fu.riskLevel == RiskLevel.EMERGENCY || fu.riskLevel == RiskLevel.HIGH) EmergencyRed.copy(alpha = 0.15f) else WarningAmber.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${fu.riskLevel.name} RISK",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (fu.riskLevel == RiskLevel.EMERGENCY || fu.riskLevel == RiskLevel.HIGH) EmergencyRed else WarningAmber,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Purpose: ${fu.purpose}", style = MaterialTheme.typography.bodyMedium)
                        Text("Referred to: ${fu.hospitalName} (${fu.doctorName})", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        Text("Due Date: ${fu.dueDate}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }

    if (showReferralDialog) {
        var patientName by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("45") }
        var gender by remember { mutableStateOf("Female") }
        var selectedHosp by remember { mutableStateOf(allHospitals.firstOrNull()) }
        var spec by remember { mutableStateOf("Cardiology") }
        var summary by remember { mutableStateOf("Exertional chest discomfort and BP 160/100 mmHg.") }

        AlertDialog(
            onDismissRequest = { showReferralDialog = false },
            title = { Text("Generate Inter-Facility Referral", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(value = patientName, onValueChange = { patientName = it }, label = { Text("Patient Name") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = spec, onValueChange = { spec = it }, label = { Text("Specialty Required") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Clinical Triage Summary") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (patientName.isNotBlank() && selectedHosp != null) {
                            viewModel.createReferral(
                                patientName = patientName,
                                patientAge = age.toIntOrNull() ?: 40,
                                patientGender = gender,
                                fromFacility = "Sonarpur PHC Centre",
                                toHospital = selectedHosp!!,
                                specialization = spec,
                                clinicalSummary = summary,
                                urgency = UrgencyLevel.URGENT,
                                healthWorker = "Sunita Ghosh (ASHA)"
                            )
                            showReferralDialog = false
                        }
                    }
                ) {
                    Text("Transmit Referral")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReferralDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalStaffPortalScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allBeds by viewModel.allBedAvailabilities.collectAsStateWithLifecycle()
    val allHospitals by viewModel.allHospitals.collectAsStateWithLifecycle()

    val myHospitalId = "hosp_peerless"
    val myHospital = allHospitals.find { it.id == myHospitalId }
    val myBeds = allBeds.filter { it.hospitalId == myHospitalId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hospital Bed & Queue Manager", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("hospital_staff_portal_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(myHospital?.name ?: "Peerless Hospital & B.K. Roy Research Centre", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Live Staff Portal • Bed Allocation Desk • API Live Sync Online", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            item {
                Text("Manage Live Bed Availability", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(myBeds) { bed ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(bed.bedTypeName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Total: ${bed.total} • Reserved: ${bed.reserved}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (bed.available > 0) {
                                        viewModel.updateBedAvailability(bed, bed.available - 1)
                                    }
                                }
                            ) {
                                Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease")
                            }

                            Text("${bed.available}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = HealthPrimaryLight)

                            IconButton(
                                onClick = {
                                    if (bed.available < bed.total) {
                                        viewModel.updateBedAvailability(bed, bed.available + 1)
                                    }
                                }
                            ) {
                                Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovernmentAdminScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hospitals by viewModel.allHospitals.collectAsStateWithLifecycle()
    val syncLogs by viewModel.apiSyncLogs.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    val filteredSyncLogs = remember(syncLogs, searchQuery) {
        if (searchQuery.isBlank()) {
            syncLogs
        } else {
            val query = searchQuery.trim().lowercase()
            syncLogs.filter {
                it.hospitalName.lowercase().contains(query) ||
                it.apiId.lowercase().contains(query)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Govt Public Health Directorate",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("government_admin_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header / Regional Health Monitoring Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = "Government Seal",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Regional Health Monitoring",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Sonarpur / South 24 Parganas Health District",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Department of Health & Family Welfare • Government of West Bengal",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Key District Metrics (KPI Cards)
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val hospCount = if (hospitals.isNotEmpty()) hospitals.size.toString() else "31"
                    GovtMetricCard(
                        label = "Monitored Hospitals",
                        value = hospCount,
                        icon = Icons.Default.LocalHospital,
                        iconTint = HealthPrimaryLight,
                        modifier = Modifier.weight(1f)
                    )
                    GovtMetricCard(
                        label = "Avg Emergency Response",
                        value = "9.2 min",
                        icon = Icons.Default.Speed,
                        iconTint = Color(0xFF00897B),
                        modifier = Modifier.weight(1f)
                    )
                    GovtMetricCard(
                        label = "Referral Completion",
                        value = "94.8%",
                        icon = Icons.Default.CheckCircle,
                        iconTint = SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Live Hospital Data Integration Section Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Live Hospital Data Integration & API Sync",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = SuccessGreen.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Live Sync",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }
                }
            }

            // Quick Search Box
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search Hospital or API Source...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // API Sync Log Items
            if (filteredSyncLogs.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (searchQuery.isNotEmpty()) "No matching hospital APIs found" else "No hospital API sync records available",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(filteredSyncLogs, key = { it.id }) { log ->
                    GovtHospitalApiCard(log = log)
                }
            }
        }
    }
}

@Composable
fun GovtMetricCard(
    label: String,
    value: String,
    icon: ImageVector? = null,
    iconTint: Color = HealthPrimaryLight,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconTint.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun GovtHospitalApiCard(
    log: com.example.data.local.ApiSyncLogEntity,
    modifier: Modifier = Modifier
) {
    val relativeTime = remember(log.syncTime) {
        val diff = System.currentTimeMillis() - log.syncTime
        val mins = diff / (60 * 1000)
        when {
            mins < 1 -> "Just now"
            mins == 1L -> "1 min ago"
            mins < 60 -> "$mins mins ago"
            else -> {
                val hours = mins / 60
                if (hours == 1L) "1 hr ago" else "$hours hrs ago"
            }
        }
    }

    val (statusLabel, statusColor, statusBg) = when (log.status) {
        SyncStatus.SUCCESS -> Triple("🟢 Connected (200 OK)", SuccessGreen, SuccessGreen.copy(alpha = 0.12f))
        SyncStatus.PARTIAL -> Triple("🟡 Partial Sync", WarningAmber, WarningAmber.copy(alpha = 0.12f))
        SyncStatus.TIMEOUT -> Triple("🟡 Timeout / Delayed", WarningAmber, WarningAmber.copy(alpha = 0.12f))
        SyncStatus.FAILED -> Triple("🔴 Offline / Error", EmergencyRed, EmergencyRed.copy(alpha = 0.12f))
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header Row: Hospital Icon + Name + Status Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "Hospital",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = log.hospitalName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = statusBg,
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = statusLabel,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                thickness = 0.8.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Metadata Row: API Source • Records Synced • Last Sync
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // API Source
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "API:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = log.apiId,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                        )
                    }
                }

                // Records Count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${log.recordsUpdated} records",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Last Sync
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = relativeTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Error Message (if any)
            if (!log.errorMessage.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = EmergencyRed.copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, EmergencyRed.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = EmergencyRed,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = log.errorMessage,
                            style = MaterialTheme.typography.labelSmall,
                            color = EmergencyRed
                        )
                    }
                }
            }
        }
    }
}
