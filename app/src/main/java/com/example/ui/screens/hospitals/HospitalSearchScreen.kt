package com.example.ui.screens.hospitals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.HospitalType
import com.example.ui.SmartHealthViewModel
import com.example.ui.components.HospitalCard
import com.example.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalSearchScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hospitalsWithDist by viewModel.filteredHospitalsWithDistance.collectAsStateWithLifecycle()
    val savedHospitals by viewModel.savedHospitals.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()

    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hospitals & Live Beds (30+)", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterSheet = true }) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        }
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
                .testTag("hospital_search_screen"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Search Input Field
            item {
                OutlinedTextField(
                    value = filterState.query,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    placeholder = { Text("Search by name, area (e.g. Sonarpur, Garia, Peerless)") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (filterState.query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Quick Filter Chips Row
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // All
                    item {
                        FilterChip(
                            selected = filterState.type == null && !filterState.requireIcu && !filterState.requireEmergencyBed,
                            onClick = { viewModel.resetFilters() },
                            label = { Text("All (${hospitalsWithDist.size})") }
                        )
                    }
                    // Govt Hospitals
                    item {
                        FilterChip(
                            selected = filterState.type == HospitalType.GOVERNMENT,
                            onClick = {
                                viewModel.updateFilter(
                                    filterState.copy(type = if (filterState.type == HospitalType.GOVERNMENT) null else HospitalType.GOVERNMENT)
                                )
                            },
                            label = { Text("Government Only") }
                        )
                    }
                    // Private Hospitals
                    item {
                        FilterChip(
                            selected = filterState.type == HospitalType.PRIVATE,
                            onClick = {
                                viewModel.updateFilter(
                                    filterState.copy(type = if (filterState.type == HospitalType.PRIVATE) null else HospitalType.PRIVATE)
                                )
                            },
                            label = { Text("Private Only") }
                        )
                    }
                    // ICU Beds Available
                    item {
                        FilterChip(
                            selected = filterState.requireIcu,
                            onClick = {
                                viewModel.updateFilter(filterState.copy(requireIcu = !filterState.requireIcu))
                            },
                            label = { Text("ICU Available") },
                            leadingIcon = { Icon(Icons.Default.LocalHospital, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        )
                    }
                    // Emergency Beds
                    item {
                        FilterChip(
                            selected = filterState.requireEmergencyBed,
                            onClick = {
                                viewModel.updateFilter(filterState.copy(requireEmergencyBed = !filterState.requireEmergencyBed))
                            },
                            label = { Text("Emergency Beds") },
                            leadingIcon = { Icon(Icons.Default.Emergency, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        )
                    }
                }
            }

            // Results count label
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${hospitalsWithDist.size} Facilities Found (Sorted by Distance)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(hospitalsWithDist) { hospWithDist ->
                val isSaved = savedHospitals.any { it.hospitalId == hospWithDist.hospital.id }
                HospitalCard(
                    hospitalWithDist = hospWithDist,
                    isSaved = isSaved,
                    onCardClick = { onNavigateTo(Screen.HospitalDetail.createRoute(hospWithDist.hospital.id)) },
                    onCallClick = { viewModel.dialPhoneNumber(context, hospWithDist.hospital.phone) },
                    onNavigateClick = { viewModel.navigateToHospital(context, hospWithDist.hospital) },
                    onAmbulanceClick = { onNavigateTo(Screen.Ambulance.route) },
                    onBookClick = { onNavigateTo(Screen.HospitalDetail.createRoute(hospWithDist.hospital.id)) },
                    onToggleSave = { viewModel.toggleSaveHospital(hospWithDist.hospital.id, isSaved) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("Filter Facilities", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Maximum Distance (${filterState.maxDistanceKm.toInt()} km)", fontWeight = FontWeight.Bold)
                Slider(
                    value = filterState.maxDistanceKm.toFloat(),
                    onValueChange = { viewModel.updateFilter(filterState.copy(maxDistanceKm = it.toDouble())) },
                    valueRange = 5f..50f,
                    steps = 8
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterState.requireIcu,
                        onCheckedChange = { viewModel.updateFilter(filterState.copy(requireIcu = it)) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Only show hospitals with live ICU beds available")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterState.requireEmergencyBed,
                        onCheckedChange = { viewModel.updateFilter(filterState.copy(requireEmergencyBed = it)) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Only show hospitals with emergency triage beds")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.resetFilters()
                            showFilterSheet = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reset")
                    }
                    Button(
                        onClick = { showFilterSheet = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Apply Filters")
                    }
                }
            }
        }
    }
}
