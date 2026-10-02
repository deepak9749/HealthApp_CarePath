package com.example.ui.screens.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.DoctorEntity
import com.example.data.model.ConsultationType
import com.example.ui.SmartHealthViewModel
import com.example.ui.navigation.Screen
import com.example.ui.theme.HealthPrimaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSearchScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val doctors by viewModel.doctors.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    var selectedSpecialization by remember { mutableStateOf<String?>(null) }
    var selectedDoctorForBooking by remember { mutableStateOf<DoctorEntity?>(null) }

    val specializations = listOf("Cardiology", "Neurology", "General Medicine", "Pulmonology", "Orthopedics", "Pediatrics", "Obstetrics & Gynecology", "Emergency Medicine")

    val filteredDoctors = doctors.filter { doc ->
        val matchesSpecialty = selectedSpecialization == null || doc.specialization.contains(selectedSpecialization!!, ignoreCase = true)
        val matchesQuery = searchQuery.isBlank() || doc.name.contains(searchQuery, ignoreCase = true) || doc.hospitalName.contains(searchQuery, ignoreCase = true)
        matchesSpecialty && matchesQuery
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Doctors & Teleconsult", fontWeight = FontWeight.Bold) },
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
                .testTag("doctor_search_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search Input
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by doctor name or hospital...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Specialty Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = selectedSpecialization == null,
                            onClick = { selectedSpecialization = null },
                            label = { Text("All Specialties") }
                        )
                    }
                    items(specializations) { spec ->
                        val isSelected = selectedSpecialization == spec
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSpecialization = if (isSelected) null else spec },
                            label = { Text(spec) }
                        )
                    }
                }
            }

            // Results count
            item {
                Text(
                    text = "${filteredDoctors.size} Verified Physicians Available",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(filteredDoctors) { doc ->
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("${doc.specialization} • ${doc.qualification}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                                Text("${doc.hospitalName} • ${doc.experienceYears} yrs exp", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("${doc.rating}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("OPD: ${doc.opdTimings}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Fee: ${doc.consultationFee}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = HealthPrimaryLight)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (doc.availableForTeleconsult) {
                                OutlinedButton(
                                    onClick = { onNavigateTo(Screen.Teleconsult.route) },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.VideoCall, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Video Consult", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }

                            Button(
                                onClick = { selectedDoctorForBooking = doc },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1.2f)
                            ) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Book OPD Slot", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    if (selectedDoctorForBooking != null) {
        val doc = selectedDoctorForBooking!!
        var patientName by remember { mutableStateOf("Patient User") }
        var selectedDate by remember { mutableStateOf("Tomorrow (10:30 AM)") }
        var consultType by remember { mutableStateOf(ConsultationType.IN_PERSON) }
        var notes by remember { mutableStateOf("Follow-up consultation for health review.") }

        AlertDialog(
            onDismissRequest = { selectedDoctorForBooking = null },
            title = { Text("Book Appointment with ${doc.name}", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Hospital: ${doc.hospitalName}", fontWeight = FontWeight.SemiBold)
                    Text("Specialization: ${doc.specialization}", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = patientName,
                        onValueChange = { patientName = it },
                        label = { Text("Patient Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { selectedDate = it },
                        label = { Text("Date & Time Slot") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Symptoms / Reason for Visit") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.bookAppointment(
                            patientName = patientName,
                            doctor = doc,
                            date = selectedDate,
                            timeSlot = selectedDate,
                            type = consultType,
                            notes = notes,
                            onSuccess = {
                                selectedDoctorForBooking = null
                                onNavigateTo(Screen.QueueStatus.route)
                            }
                        )
                    }
                ) {
                    Text("Confirm Appointment")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedDoctorForBooking = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
