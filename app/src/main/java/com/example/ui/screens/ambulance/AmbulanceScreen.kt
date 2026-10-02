package com.example.ui.screens.ambulance

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
import com.example.data.model.AmbulanceStatus
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmbulanceScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activeAmbulance by viewModel.activeAmbulanceRequest.collectAsStateWithLifecycle()
    val currentLocationName by viewModel.currentLocationName.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ambulance Dispatch Tracker", fontWeight = FontWeight.Bold) },
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
                .testTag("ambulance_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (activeAmbulance == null) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("No Active Ambulance Request", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Request an ambulance from the Emergency Center or Hospital Detail screen.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    viewModel.requestAmbulance(
                                        pickupAddress = currentLocationName,
                                        destinationHospital = null,
                                        onDispatched = {}
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed)
                            ) {
                                Text("Dispatch Emergency Ambulance")
                            }
                        }
                    }
                }
            } else {
                val req = activeAmbulance!!

                // Status Banner
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = EmergencyRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("STATUS: ${req.status.name}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                                        Text("ETA: ${req.etaMinutes} Minutes Remaining", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
                                    }
                                }
                            }
                        }
                    }
                }

                // Progress Stepper: REQUESTED -> ACCEPTED -> DISPATCHED -> ARRIVING -> PICKED_UP -> COMPLETED
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Dispatch Progression", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(14.dp))

                            val steps = listOf(
                                AmbulanceStatus.REQUESTED,
                                AmbulanceStatus.ACCEPTED,
                                AmbulanceStatus.DISPATCHED,
                                AmbulanceStatus.ARRIVING,
                                AmbulanceStatus.PICKED_UP,
                                AmbulanceStatus.COMPLETED
                            )

                            val currentIndex = steps.indexOf(req.status)

                            steps.forEachIndexed { index, step ->
                                val isDone = index <= currentIndex
                                val isCurrent = index == currentIndex

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isDone) (if (isCurrent) EmergencyRed else SuccessGreen)
                                                else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isDone) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = step.name.replace("_", " "),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isDone) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // Vehicle & Driver Details
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Vehicle & Paramedic Crew", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text("Vehicle: ${req.vehicleNumber} (ALS - Advanced Cardiac Life Support)", fontWeight = FontWeight.SemiBold)
                            Text("Provider: ${req.providerName}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Equipment: High-Flow O2, Defibrillator, Syringe Infusion Pump, Stretcher", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Pickup Location: ${req.pickupAddress}", fontWeight = FontWeight.SemiBold)
                            Text("Destination Hospital: ${req.destinationName}", fontWeight = FontWeight.SemiBold, color = EmergencyRed)

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { viewModel.dialPhoneNumber(context, "108") },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Call Driver")
                                }

                                OutlinedButton(
                                    onClick = {
                                        // Advance status simulation
                                        val nextStatus = when (req.status) {
                                            AmbulanceStatus.REQUESTED -> AmbulanceStatus.ACCEPTED
                                            AmbulanceStatus.ACCEPTED -> AmbulanceStatus.DISPATCHED
                                            AmbulanceStatus.DISPATCHED -> AmbulanceStatus.ARRIVING
                                            AmbulanceStatus.ARRIVING -> AmbulanceStatus.ARRIVED
                                            AmbulanceStatus.ARRIVED -> AmbulanceStatus.PICKED_UP
                                            AmbulanceStatus.PICKED_UP -> AmbulanceStatus.COMPLETED
                                            AmbulanceStatus.COMPLETED -> AmbulanceStatus.DISPATCHED
                                            AmbulanceStatus.CANCELLED -> AmbulanceStatus.REQUESTED
                                        }
                                        viewModel.updateAmbulanceStatus(req, nextStatus)
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1.2f)
                                ) {
                                    Text("Simulate Next Step", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
