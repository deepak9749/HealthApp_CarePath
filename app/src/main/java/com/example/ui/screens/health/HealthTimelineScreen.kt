package com.example.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.SmartHealthViewModel
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.HealthPrimaryLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthTimelineScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val assessments by viewModel.healthAssessments.collectAsStateWithLifecycle()
    val reports by viewModel.medicalReports.collectAsStateWithLifecycle()
    val prescriptions by viewModel.prescriptions.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Longitudinal Health Timeline", fontWeight = FontWeight.Bold) },
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
                .testTag("health_timeline_screen"),
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
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(Icons.Default.Timeline, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Your unified chronological health history across triage assessments, doctor appointments, prescriptions, and lab reports.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Quick Stats Row
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TimelineMetricCard("Assessments", "${assessments.size}", Icons.Default.HealthAndSafety, MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                    TimelineMetricCard("Reports", "${reports.size}", Icons.Default.Description, Color(0xFF006874), Modifier.weight(1f))
                    TimelineMetricCard("Prescriptions", "${prescriptions.size}", Icons.Default.Medication, Color(0xFF7B1FA2), Modifier.weight(1f))
                }
            }

            item {
                Text("Chronological Health Events", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            // Assessments
            items(assessments) { item ->
                TimelineEventCard(
                    title = "Digital Triage Assessment (${item.riskLevel.name})",
                    subtitle = item.summary,
                    date = "Recent",
                    tag = "Triage Assessment",
                    accentColor = when (item.riskLevel.name) {
                        "EMERGENCY" -> EmergencyRed
                        "HIGH" -> WarningAmber
                        else -> SuccessGreen
                    },
                    icon = Icons.Default.HealthAndSafety
                )
            }

            // Reports
            items(reports) { item ->
                TimelineEventCard(
                    title = "${item.fileName} (${item.reportType})",
                    subtitle = "Analyzed by AI • ${item.hospitalName}",
                    date = "Recent Report",
                    tag = "Lab Report",
                    accentColor = Color(0xFF006874),
                    icon = Icons.Default.Description
                )
            }

            // Prescriptions
            items(prescriptions) { item ->
                TimelineEventCard(
                    title = "Rx: ${item.diagnosis}",
                    subtitle = "${item.doctorName} • ${item.instructions}",
                    date = "Prescribed",
                    tag = "Prescription",
                    accentColor = Color(0xFF7B1FA2),
                    icon = Icons.Default.Medication
                )
            }
        }
    }
}

@Composable
fun TimelineMetricCard(
    label: String,
    count: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(count, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun TimelineEventCard(
    title: String,
    subtitle: String,
    date: String,
    tag: String,
    accentColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = accentColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
