package com.example.ui.screens.health

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.HealthPrimaryLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaternalHealthScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val maternal by viewModel.maternalHealth.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Maternal & Prenatal Health",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF0F172A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
                .testTag("maternal_health_screen"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Patient & Pregnancy Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Top row: Patient Name + Gestational Age + Active Status Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f, fill = false)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFE0F2F1),
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = Color(0xFF006D77),
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = maternal?.motherName ?: "Sunita Das",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Gestational Age: ${maternal?.gestationalWeeks ?: 24} Weeks (Trimester 2)",
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF006D77)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Horizontal "Active Pregnancy Care" Badge (No character wrapping)
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFF006D77),
                                shadowElevation = 0.5.dp
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Active Pregnancy Care",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        softWrap = false
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Pregnancy Clinical Summary Grid: EDD, Risk Factors, Vitals
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Estimated Due Date Box
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF0FDF4),
                                border = BorderStroke(1.dp, Color(0xFFDCFCE7)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarToday,
                                            contentDescription = null,
                                            tint = Color(0xFF15803D),
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Estimated Due Date (EDD)",
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF166534)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = maternal?.expectedDueDate ?: "Nov 15, 2026",
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF14532D)
                                    )
                                }
                            }

                            // High-Risk Factors Box
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF8FAFC),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Shield,
                                            contentDescription = null,
                                            tint = Color(0xFF006D77),
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "High-Risk Factors",
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF64748B)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = maternal?.highRiskFactors ?: "None - Normal Pregnancy",
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                            }
                        }

                        // Clinical Vitals Row: Blood Pressure, Hemoglobin
                        if (maternal != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF8FAFC),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Text("Blood Pressure: ", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(maternal!!.bloodPressure, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    }
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF8FAFC),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Text("Hemoglobin: ", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(maternal!!.hemoglobin, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2. Prenatal Checkups (ANC Schedule) Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Section Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFE0F2F1),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MedicalServices,
                                        contentDescription = null,
                                        tint = Color(0xFF006D77),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Prenatal Checkups (ANC Schedule)",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "National Guidelines for Antenatal Care",
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Timeline Items
                        AncScheduleItem(
                            stepNumber = "ANC 1",
                            timelinePeriod = "1st Trimester",
                            focusArea = "Registration & Hemoglobin",
                            statusText = "Completed",
                            statusType = AncStatusType.COMPLETED,
                            isLast = false
                        )

                        AncScheduleItem(
                            stepNumber = "ANC 2",
                            timelinePeriod = "14-26 Weeks",
                            focusArea = "Tetanus & USG Anomaly",
                            statusText = "Completed",
                            statusType = AncStatusType.COMPLETED,
                            isLast = false
                        )

                        AncScheduleItem(
                            stepNumber = "ANC 3",
                            timelinePeriod = "28-34 Weeks",
                            focusArea = "Blood Pressure & Growth",
                            statusText = "Due in 4 Weeks",
                            statusType = AncStatusType.DUE_SOON,
                            isLast = false
                        )

                        AncScheduleItem(
                            stepNumber = "ANC 4",
                            timelinePeriod = "36+ Weeks",
                            focusArea = "Delivery Planning & Hospital Referral",
                            statusText = "Upcoming",
                            statusType = AncStatusType.UPCOMING,
                            isLast = true
                        )
                    }
                }
            }
        }
    }
}

enum class AncStatusType {
    COMPLETED,
    DUE_SOON,
    UPCOMING
}

@Composable
private fun AncScheduleItem(
    stepNumber: String,
    timelinePeriod: String,
    focusArea: String,
    statusText: String,
    statusType: AncStatusType,
    isLast: Boolean
) {
    val statusBgColor = when (statusType) {
        AncStatusType.COMPLETED -> Color(0xFFE8F5E9)
        AncStatusType.DUE_SOON -> Color(0xFFFFFBEB)
        AncStatusType.UPCOMING -> Color(0xFFF1F5F9)
    }

    val statusTextColor = when (statusType) {
        AncStatusType.COMPLETED -> Color(0xFF15803D)
        AncStatusType.DUE_SOON -> Color(0xFFB45309)
        AncStatusType.UPCOMING -> Color(0xFF64748B)
    }

    val indicatorColor = when (statusType) {
        AncStatusType.COMPLETED -> Color(0xFF16A34A)
        AncStatusType.DUE_SOON -> Color(0xFFD97706)
        AncStatusType.UPCOMING -> Color(0xFF94A3B8)
    }

    val indicatorIcon: ImageVector = when (statusType) {
        AncStatusType.COMPLETED -> Icons.Default.CheckCircle
        AncStatusType.DUE_SOON -> Icons.Default.Schedule
        AncStatusType.UPCOMING -> Icons.Default.RadioButtonUnchecked
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left status icon
        Icon(
            imageVector = indicatorIcon,
            contentDescription = null,
            tint = indicatorColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Center content: Step name, timeline period & focus area
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stepNumber,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "($timelinePeriod)",
                    fontSize = 11.5.sp,
                    color = Color(0xFF64748B)
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = focusArea,
                fontSize = 12.sp,
                color = Color(0xFF334155),
                lineHeight = 15.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right status badge pill
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = statusBgColor,
            border = BorderStroke(0.5.dp, indicatorColor.copy(alpha = 0.4f))
        ) {
            Text(
                text = statusText,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                color = statusTextColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }

    if (!isLast) {
        HorizontalDivider(
            color = Color(0xFFF1F5F9),
            thickness = 0.8.dp,
            modifier = Modifier.padding(start = 30.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildHealthScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val child by viewModel.childHealth.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Child Health & Immunization", fontWeight = FontWeight.Bold) },
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
                .testTag("child_health_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(child?.childName ?: "Aarav Roy", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("DOB: ${child?.dob ?: "2025-10-10"} • Gender: ${child?.gender ?: "Male"}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Birth Weight: ${child?.birthWeightKg ?: 3.1} kg • Current Weight: ${child?.currentWeightKg ?: 7.4} kg", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                        Text("Vaccinations Due: ${child?.vaccinationsDue ?: "Pentavalent-3, IPV-2"}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("National Immunization Schedule (UIP)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("• Birth: BCG, OPV 0, Hep B (Given)", color = SuccessGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("• 6 Weeks: Pentavalent-1, Rota-1, fIPV-1, PCV-1 (Given)", color = SuccessGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("• 10 Weeks: Pentavalent-2, Rota-2 (Given)", color = SuccessGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("• 14 Weeks: Pentavalent-3, Rota-3, fIPV-2, PCV-2 (Given)", color = SuccessGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("• 9 Months: MR-1, PCV Booster, Vit A (Due Next Month)", color = WarningAmber, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronicCareScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chronicList by viewModel.chronicCare.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chronic Disease Management", fontWeight = FontWeight.Bold) },
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
                .testTag("chronic_care_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
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
                        Icon(Icons.Default.MonitorHeart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Longitudinal tracker for Hypertension, Type 2 Diabetes, COPD, and Cardiac conditions with target thresholds and routine screening reminders.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            items(chronicList) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(item.conditionName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SuccessGreen.copy(alpha = 0.15f)
                            ) {
                                Text("Active Monitoring", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, modifier = Modifier.padding(4.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Latest Reading: ${item.lastReading}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = HealthPrimaryLight)
                        Text("Target Threshold: ${item.targetMetrics}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Last Physician Review: ${item.lastCheckupDate}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
