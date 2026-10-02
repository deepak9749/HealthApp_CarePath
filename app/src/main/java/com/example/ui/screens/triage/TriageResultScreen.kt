package com.example.ui.screens.triage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.RiskLevel
import com.example.ui.SmartHealthViewModel
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriageResultScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val assessment by viewModel.latestAssessmentResult.collectAsStateWithLifecycle()
    val hospitalsWithDist by viewModel.filteredHospitalsWithDistance.collectAsStateWithLifecycle()
    val currentLocationName by viewModel.currentLocationName.collectAsStateWithLifecycle()

    val nearestEmergency = hospitalsWithDist.firstOrNull { it.hospital.emergencyAvailable }

    val riskLevel = assessment?.riskLevel ?: RiskLevel.MODERATE

    val riskColor = when (riskLevel) {
        RiskLevel.EMERGENCY -> EmergencyRed
        RiskLevel.HIGH -> Color(0xFFD84315)
        RiskLevel.MODERATE -> WarningAmber
        RiskLevel.LOW -> SuccessGreen
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Triage Assessment Result", fontWeight = FontWeight.Bold) },
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
                .testTag("triage_result_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Risk Level Header Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = riskColor.copy(alpha = 0.12f)),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(riskColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (riskLevel) {
                                    RiskLevel.EMERGENCY -> Icons.Default.Emergency
                                    RiskLevel.HIGH -> Icons.Default.Warning
                                    RiskLevel.MODERATE -> Icons.Default.Info
                                    RiskLevel.LOW -> Icons.Default.CheckCircle
                                },
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "RISK LEVEL: ${riskLevel.name}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = riskColor,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = when (riskLevel) {
                                RiskLevel.EMERGENCY -> "Immediate Emergency Care Required"
                                RiskLevel.HIGH -> "Urgent Medical Evaluation Recommended"
                                RiskLevel.MODERATE -> "Doctor Consultation Advised within 24 Hours"
                                RiskLevel.LOW -> "Mild Symptoms — General Home Care & Monitoring"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // AI Safety Disclaimer (Mandatory)
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Medical Disclaimer: Based on the symptom inputs provided, this is an AI-assisted triage evaluation designed for informational guidance only. It is NOT a definitive diagnosis. If you experience severe distress or life-threatening symptoms, contact emergency services immediately.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Summary & Clinical Recommendation
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Clinical Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = assessment?.summary ?: "Assessment completed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Recommended Next Steps",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = assessment?.recommendation ?: "Please follow physician guidance.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Recommended Specialty: ${assessment?.recommendedSpecialization}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            // Immediate Escalation Actions (if Emergency or High)
            if (riskLevel == RiskLevel.EMERGENCY || riskLevel == RiskLevel.HIGH) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Emergency Escalation Actions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = EmergencyRed
                        )

                        Button(
                            onClick = { onNavigateTo(Screen.EmergencyCenter.route) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(Icons.Default.Emergency, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Open Emergency Center (SOS / 108 Call)", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                viewModel.requestAmbulance(
                                    pickupAddress = currentLocationName,
                                    destinationHospital = nearestEmergency?.hospital,
                                    onDispatched = { onNavigateTo(Screen.Ambulance.route) }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(Icons.Default.AirportShuttle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dispatch ICU Ambulance to Location", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Standard Care Actions (Book Doctor, Follow up)
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Clinical Care Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedButton(
                        onClick = { onNavigateTo(Screen.Doctors.route) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Book Doctor Appointment (OPD / Video)", fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = { onNavigateTo(Screen.Hospitals.route) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.LocalHospital, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Find Nearby Hospitals & Live Beds", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
