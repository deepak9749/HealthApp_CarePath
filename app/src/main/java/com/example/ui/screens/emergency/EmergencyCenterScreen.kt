package com.example.ui.screens.emergency

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.SmartHealthViewModel
import com.example.ui.components.HospitalCard
import com.example.ui.i18n.tr
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmergencyRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyCenterScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLocationName by viewModel.currentLocationName.collectAsStateWithLifecycle()
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()
    val hospitalsWithDist by viewModel.filteredHospitalsWithDistance.collectAsStateWithLifecycle()
    val emergencyContacts by viewModel.emergencyContacts.collectAsStateWithLifecycle()
    val activeAmbulance by viewModel.activeAmbulanceRequest.collectAsStateWithLifecycle()

    var showAmbulanceConfirmDialog by remember { mutableStateOf(false) }
    var showBlinkitNotInstalledDialog by remember { mutableStateOf(false) }
    var showRapidoNotInstalledDialog by remember { mutableStateOf(false) }
    var showUberNotInstalledDialog by remember { mutableStateOf(false) }

    val emergencyHospitals = hospitalsWithDist.filter { it.hospital.emergencyAvailable }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Emergency, contentDescription = null, tint = EmergencyRed)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(tr("Emergency Center (SOS)"), fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = tr("Back"))
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
                .testTag("emergency_center_screen"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Location confirmation
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = EmergencyRed, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${tr("Emergency Location")}: $currentLocationName",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Big 1-Tap SOS Helpline Actions
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = tr("Immediate SOS Helplines"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 108 National Emergency Ambulance
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = EmergencyRed),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(110.dp)
                                .clickable { viewModel.dialPhoneNumber(context, "108") }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(tr("Call 108"), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                                Text(tr("National Emergency"), style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
                            }
                        }

                        // 102 Ambulance Direct
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFC62828)),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(110.dp)
                                .clickable { viewModel.dialPhoneNumber(context, "102") }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(tr("Call 102"), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                                Text(tr("Govt Ambulance"), style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
                            }
                        }

                        // 112 Police / Disaster
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF006874)),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(110.dp)
                                .clickable { viewModel.dialPhoneNumber(context, "112") }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(tr("Call 112"), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                                Text(tr("All Helpline"), style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Request ICU-Equipped Ambulance Button
                    Button(
                        onClick = { showAmbulanceConfirmDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 56.dp)
                            .testTag("request_ambulance_sos_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.AirportShuttle,
                                contentDescription = "Ambulance",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = tr("Dispatch ICU Ambulance to My Location"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // OR Separator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                        Text(
                            text = tr("OR ALTERNATIVE TRANSPORT"),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Alternative Transport Card (Rapido & Uber Fallback)
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("alternative_transport_section")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Text(
                                text = tr("Need Alternative Transport?"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tr("If an ambulance is unavailable, you may arrange alternative transport. For life-threatening emergencies, use emergency medical services whenever possible."),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))



                            // Blinkit Ambulance Fallback Button
                            Button(
                                onClick = {
                                    EmergencyTransportLauncher.launchBlinkit(
                                        context = context,
                                        onNotInstalled = { showBlinkitNotInstalledDialog = true },
                                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFD600)
                                ),
                                border = BorderStroke(1.dp, Color(0xFF9ACD32)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 52.dp)
                                    .testTag("blinkit_ambulance_button")
                                    .semantics {
                                        contentDescription = "Book ambulance with Blinkit"
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFF0F7A3A),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.LocalHospital,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = tr("Blinkit Ambulance"),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E1E1E)
                                        )
                                        Text(
                                            text = tr("Emergency Ambulance • Tap to open app"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFF424242)
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.OpenInNew,
                                        contentDescription = null,
                                        tint = Color(0xFF424242),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))


                            // Rapido Fallback Button
                            Button(
                                onClick = {
                                    val (lat, lng) = currentLocation
                                    EmergencyTransportLauncher.launchRapido(
                                        context = context,
                                        latitude = lat,
                                        longitude = lng,
                                        onNotInstalled = { showRapidoNotInstalledDialog = true },
                                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFDE00)
                                ),
                                border = BorderStroke(1.dp, Color(0xFFE5C000)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 52.dp)
                                    .testTag("rapido_fallback_button")
                                    .semantics {
                                        contentDescription = "Book alternative transport with Rapido"
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFF1E1E1E),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.TwoWheeler,
                                                contentDescription = null,
                                                tint = Color(0xFFFFDE00),
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = tr("Book with Rapido"),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E1E1E)
                                        )
                                        Text(
                                            text = tr("Bike & Auto fallback • Tap to open app"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFF424242)
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.OpenInNew,
                                        contentDescription = null,
                                        tint = Color(0xFF424242),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Uber Fallback Button
                            Button(
                                onClick = {
                                    val (lat, lng) = currentLocation
                                    EmergencyTransportLauncher.launchUber(
                                        context = context,
                                        latitude = lat,
                                        longitude = lng,
                                        onNotInstalled = { showUberNotInstalledDialog = true },
                                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF000000)
                                ),
                                border = BorderStroke(1.dp, Color(0xFF334155)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 52.dp)
                                    .testTag("uber_fallback_button")
                                    .semantics {
                                        contentDescription = "Book alternative transport with Uber"
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFF27272A),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.DirectionsCar,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = tr("Ride with Uber"),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = tr("Cab & Auto fallback • Tap to open app"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFCBD5E1)
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.OpenInNew,
                                        contentDescription = null,
                                        tint = Color(0xFFCBD5E1),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Emergency Contacts Quick Section
            if (emergencyContacts.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = tr("Emergency Contacts"),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        emergencyContacts.forEach { contact ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Column {
                                        Text(contact.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                                        Text("${contact.relationship} • ${contact.phone}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    IconButton(
                                        onClick = { viewModel.dialPhoneNumber(context, contact.phone) },
                                        colors = IconButtonDefaults.iconButtonColors(containerColor = EmergencyRed.copy(alpha = 0.1f))
                                    ) {
                                        Icon(Icons.Default.Call, contentDescription = tr("Call"), tint = EmergencyRed)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Nearest Emergency Ready Hospitals with ICU Availability
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = tr("Nearest Emergency & ICU Hospitals"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tr("Ranked dynamically by road distance with live ICU bed counters"),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(emergencyHospitals.take(6)) { hospWithDist ->
                HospitalCard(
                    hospitalWithDist = hospWithDist,
                    isSaved = false,
                    onCardClick = { onNavigateTo(Screen.HospitalDetail.createRoute(hospWithDist.hospital.id)) },
                    onCallClick = { viewModel.dialPhoneNumber(context, hospWithDist.hospital.emergencyPhone) },
                    onNavigateClick = { viewModel.navigateToHospital(context, hospWithDist.hospital) },
                    onAmbulanceClick = {
                        viewModel.requestAmbulance(
                            pickupAddress = currentLocationName,
                            destinationHospital = hospWithDist.hospital,
                            onDispatched = { onNavigateTo(Screen.Ambulance.route) }
                        )
                    },
                    onBookClick = { onNavigateTo(Screen.HospitalDetail.createRoute(hospWithDist.hospital.id)) },
                    onToggleSave = {},
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }

    if (showAmbulanceConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showAmbulanceConfirmDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = EmergencyRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(tr("Confirm Ambulance Dispatch"), fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(tr("Dispatch WB-04-1081 (ALS - Advanced Cardiac Life Support) to your current location?"))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${tr("Pickup")}: $currentLocationName", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(tr("Destination: Nearest Emergency Hospital"), fontWeight = FontWeight.SemiBold)
                    Text(tr("ETA: Approx. 9 - 12 minutes"), color = EmergencyRed, fontWeight = FontWeight.Bold)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAmbulanceConfirmDialog = false
                        viewModel.requestAmbulance(
                            pickupAddress = currentLocationName,
                            destinationHospital = emergencyHospitals.firstOrNull()?.hospital,
                            onDispatched = { onNavigateTo(Screen.Ambulance.route) }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed)
                ) {
                    Text(tr("Dispatch Now"))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAmbulanceConfirmDialog = false }) {
                    Text(tr("Cancel"))
                }
            }
        )
    }



    // Blinkit Not Installed Dialog
    if (showBlinkitNotInstalledDialog) {
        AlertDialog(
            onDismissRequest = { showBlinkitNotInstalledDialog = false },
            icon = {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = null,
                            tint = Color(0xFF0F7A3A),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = tr("Blinkit is not installed"),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = tr("You can install Blinkit to access the ambulance service."),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showBlinkitNotInstalledDialog = false
                        EmergencyTransportLauncher.openPlayStore(
                            context,
                            EmergencyTransportLauncher.BLINKIT_PACKAGE
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("install_blinkit_button")
                ) {
                    Text(
                        text = tr("Install Blinkit"),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showBlinkitNotInstalledDialog = false },
                    modifier = Modifier.testTag("cancel_blinkit_install_button")
                ) {
                    Text(tr("Cancel"))
                }
            }
        )
    }

    // Rapido Not Installed Dialog
    if (showRapidoNotInstalledDialog) {
        AlertDialog(
            onDismissRequest = { showRapidoNotInstalledDialog = false },
            icon = {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFEF3C7),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.TwoWheeler,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = tr("Rapido is not installed"),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = tr("You can install Rapido to book an alternative ride."),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRapidoNotInstalledDialog = false
                        EmergencyTransportLauncher.openPlayStore(context, EmergencyTransportLauncher.RAPIDO_PACKAGE)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDE00)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("install_rapido_button")
                ) {
                    Text(
                        text = tr("Install Rapido"),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRapidoNotInstalledDialog = false },
                    modifier = Modifier.testTag("cancel_rapido_install_button")
                ) {
                    Text(tr("Cancel"))
                }
            }
        )
    }

    // Uber Not Installed Dialog
    if (showUberNotInstalledDialog) {
        AlertDialog(
            onDismissRequest = { showUberNotInstalledDialog = false },
            icon = {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE2E8F0),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = tr("Uber is not installed"),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = tr("You can install Uber to book an alternative ride."),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showUberNotInstalledDialog = false
                        EmergencyTransportLauncher.openPlayStore(context, EmergencyTransportLauncher.UBER_PACKAGE)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("install_uber_button")
                ) {
                    Text(
                        text = tr("Install Uber"),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUberNotInstalledDialog = false },
                    modifier = Modifier.testTag("cancel_uber_install_button")
                ) {
                    Text(tr("Cancel"))
                }
            }
        )
    }
}
