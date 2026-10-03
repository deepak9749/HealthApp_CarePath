package com.example.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserRole
import com.example.ui.SmartHealthViewModel
import com.example.ui.components.HospitalCard
import com.example.ui.i18n.tr
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTealPrimary

@Composable
fun HomeScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hospitalsWithDist by viewModel.filteredHospitalsWithDistance.collectAsStateWithLifecycle()
    val savedHospitals by viewModel.savedHospitals.collectAsStateWithLifecycle()
    val activeAmbulance by viewModel.activeAmbulanceRequest.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()

    val nearestEmergencyHosp = hospitalsWithDist.firstOrNull { it.hospital.emergencyAvailable }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // 1. Hero & Branding Section (Uploaded Healthcare Illustration as Background)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                // Full responsive Background Illustration (Shield, ECG, Leaves, Pathway, Meditating Figure, Skyline)
                HealthcareHeroBackground(
                    modifier = Modifier.matchParentSize()
                )

                // Foreground content on top of background
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.68f)
                        .padding(start = 16.dp, top = 20.dp, bottom = 18.dp, end = 6.dp)
                ) {
                    Text(
                        text = "CARE PATH",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF005B66),
                        letterSpacing = 0.8.sp,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(2.dp)
                                .background(Color(0xFF00897B))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SUSHRUTA",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00897B),
                            letterSpacing = 2.5.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(2.dp)
                                .background(Color(0xFF00897B))
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = tr("From Sushruta’s wisdom to Jeevan Shakti’s innovation — Care Path, a path towards better health."),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = Color(0xFF334155),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Guest Mode / Active Role Pill
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.95f),
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                        shadowElevation = 1.dp,
                        modifier = Modifier.clickable { viewModel.setRoleSelectorOpen(true) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonOutline,
                                contentDescription = "Role Selector",
                                tint = Color(0xFF006D77),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = when (activeRole) {
                                    UserRole.GUEST -> tr("Guest Mode")
                                    UserRole.PATIENT -> tr("Patient")
                                    UserRole.FRONTLINE_HEALTH_WORKER -> tr("ASHA Worker")
                                    UserRole.HOSPITAL_STAFF -> tr("Hospital Staff")
                                    UserRole.GOVERNMENT_ADMIN, UserRole.ADMIN -> tr("Govt Admin")
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF006D77)
                            )
                        }
                    }
                }
            }
        }

        // Active Ambulance Tracker Banner (if active)
        if (activeAmbulance != null) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EmergencyRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable { onNavigateTo(Screen.Ambulance.route) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AirportShuttle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${tr("Ambulance Dispatched")}: ${activeAmbulance?.status?.name}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${tr("Vehicle")} ${activeAmbulance?.vehicleNumber} • ${tr("ETA")}: ${activeAmbulance?.etaMinutes} ${tr("mins")}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // 2. Nearest Emergency Facility Card (Matches Reference)
        if (nearestEmergencyHosp != null) {
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFFEE2E2)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Header: Nearest 24x7 Emergency Facility + Distance
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Emergency,
                                    contentDescription = null,
                                    tint = EmergencyRed,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = tr("Nearest 24x7 Emergency Facility"),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmergencyRed
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFE0F2F1),
                                border = BorderStroke(1.dp, Color(0xFF80CBC4))
                            ) {
                                Text(
                                    text = nearestEmergencyHosp.distanceFormatted,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006874),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Hospital Name
                        Text(
                            text = nearestEmergencyHosp.hospital.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Bed Availability
                        Text(
                            text = "${tr("ICU Beds")}: ${nearestEmergencyHosp.totalIcuAvailable} ${tr("Available")}  •  ${tr("Emergency Beds")}: ${nearestEmergencyHosp.totalEmergencyAvailable} ${tr("Available")}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF475569)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Two Action Buttons: Emergency SOS & Navigate
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { onNavigateTo(Screen.EmergencyCenter.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = EmergencyRed,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_emergency_sos_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = tr("EMERGENCY SOS"),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.4.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { viewModel.navigateToHospital(context, nearestEmergencyHosp.hospital) },
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.5.dp, Color(0xFF006874)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF006874)),
                                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_navigate_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Navigation,
                                    contentDescription = null,
                                    tint = Color(0xFF006874),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = tr("NAVIGATE"),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.4.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Highlighted Section Header: "How can we help you?"
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                HighlightedSectionHeader(
                    icon = Icons.Default.HealthAndSafety,
                    title = "How can we help you?"
                )
            }
        }

        // 4. 2x2 Quick Action Cards Grid (Matches Reference)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReferenceActionCard(
                        title = "Check Symptoms",
                        subtitle = "Digital Triage (1-10)",
                        icon = Icons.Default.HealthAndSafety,
                        iconBg = Color(0xFFE0F2F1),
                        iconTint = Color(0xFF00796B),
                        onClick = { onNavigateTo(Screen.SymptomsTriage.route) },
                        modifier = Modifier.weight(1f)
                    )
                    ReferenceActionCard(
                        title = "Nearby Hospitals",
                        subtitle = "30+ Facilities & ICU",
                        icon = Icons.Default.LocalHospital,
                        iconBg = Color(0xFFE0F2FE),
                        iconTint = Color(0xFF0288D1),
                        onClick = { onNavigateTo(Screen.Hospitals.route) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReferenceActionCard(
                        title = "Emergency Help",
                        subtitle = "SOS & Ambulance",
                        icon = Icons.Default.AirportShuttle,
                        iconBg = Color(0xFFFFEBEE),
                        iconTint = EmergencyRed,
                        onClick = { onNavigateTo(Screen.EmergencyCenter.route) },
                        modifier = Modifier.weight(1f)
                    )
                    ReferenceActionCard(
                        title = "Talk to AI",
                        subtitle = "Healthcare Assistant",
                        icon = Icons.Default.AutoAwesome,
                        iconBg = Color(0xFFF3E5F5),
                        iconTint = Color(0xFF7B1FA2),
                        onClick = { viewModel.setAIChatOpen(true) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        

        // 6. Highlighted Section Header: "Clinical Services" (No View All)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                HighlightedSectionHeader(
                    icon = Icons.Default.MedicalServices,
                    title = "Clinical Services"
                )
            }
        }

        // 7. Clinical Service Cards Row (Matches Reference)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // First row: Find Medicine, Health Records, Lab Tests
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ReferenceServiceCard(
                        icon = Icons.Default.Medication,
                        iconBg = Color(0xFFE0F2F1),
                        iconTint = Color(0xFF00897B),
                        title = "Find Medicine",
                        onClick = { onNavigateTo(Screen.Medicines.route) },
                        modifier = Modifier.weight(1f)
                    )
                    ReferenceServiceCard(
                        icon = Icons.Default.FolderShared,
                        iconBg = Color(0xFFE0F2FE),
                        iconTint = Color(0xFF0288D1),
                        title = "Health Records",
                        onClick = { onNavigateTo(Screen.Reports.route) },
                        modifier = Modifier.weight(1f)
                    )
                    ReferenceServiceCard(
                        icon = Icons.Default.Biotech,
                        iconBg = Color(0xFFF3E5F5),
                        iconTint = Color(0xFF8E24AA),
                        title = "Lab Tests",
                        onClick = { onNavigateTo(Screen.Diagnostics.route) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Second row: Consult Doctors, Maternal Programs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ReferenceServiceCard(
                        icon = Icons.Default.Person,
                        iconBg = Color(0xFFE0F2FE),
                        iconTint = Color(0xFF0369A1),
                        title = "Consult Doctors",
                        onClick = { onNavigateTo(Screen.Doctors.route) },
                        modifier = Modifier.weight(1f)
                    )
                    ReferenceServiceCard(
                        icon = Icons.Default.VolunteerActivism,
                        iconBg = Color(0xFFFFEBEE),
                        iconTint = Color(0xFFE11D48),
                        title = "Maternal Programs",
                        onClick = { onNavigateTo(Screen.MaternalHealth.route) },
                        modifier = Modifier.weight(1f)
                    )
                    // Empty third slot keeps the second row aligned with the first row.
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        // 8. Nearby Verified Hospitals Section Header (Matching Teal Banner with functional View All)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                HighlightedSectionHeaderWithAction(
                    icon = Icons.Default.LocalHospital,
                    title = "Nearby Hospitals & Live Beds",
                    actionText = "View All",
                    onActionClick = { onNavigateTo(Screen.Hospitals.route) }
                )
            }
        }

        items(hospitalsWithDist.take(4)) { hospWithDist ->
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

/**
 * 2x2 Quick Action Card matching the reference design:
 * Icon in colored circular container + Title + Subtitle + Circular arrow button
 */
@Composable
fun ReferenceActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        modifier = modifier
            .height(115.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row: Icon Container & Trailing Arrow Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(1.dp, iconTint.copy(alpha = 0.5f)),
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Bottom: Title & Subtitle
            Column {
                Text(
                    text = tr(title),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tr(subtitle),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Trust & Benefit Info Item
 */
@Composable
fun TrustItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tr(title),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = tr(subtitle),
            fontSize = 9.sp,
            lineHeight = 11.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

/**
 * Teal Healthcare Section Banner: "How can we help you?"
 * Compact rounded banner with white healthcare icon, white title, and decorative diagonal lines on the right
 */
@Composable
fun HighlightedSectionHeader(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF006D77),
                        Color(0xFF00796B),
                        Color(0xFF00897B)
                    )
                )
            )
            .drawBehind {
                val w = size.width
                val h = size.height
                val strokeWidth = 2.2.dp.toPx()
                val lineColor = Color.White

                // 3 subtle decorative parallel diagonal lines on the right
                drawLine(
                    color = lineColor.copy(alpha = 0.15f),
                    start = Offset(w - 48.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w - 28.dp.toPx(), -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor.copy(alpha = 0.24f),
                    start = Offset(w - 34.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w - 14.dp.toPx(), -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor.copy(alpha = 0.18f),
                    start = Offset(w - 20.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w, -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
            .padding(horizontal = 14.dp, vertical = 9.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.20f),
                modifier = Modifier.size(26.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = tr(title),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.2.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Teal Healthcare Section Banner: "Clinical Services" with "View All ->"
 * Companion header matching exact height, styling, and diagonal accents
 */
@Composable
fun HighlightedSectionHeaderWithAction(
    icon: ImageVector,
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF006D77),
                        Color(0xFF00796B),
                        Color(0xFF00897B)
                    )
                )
            )
            .drawBehind {
                val w = size.width
                val h = size.height
                val strokeWidth = 2.2.dp.toPx()
                val lineColor = Color.White

                // 3 subtle decorative parallel diagonal lines towards the right side
                drawLine(
                    color = lineColor.copy(alpha = 0.15f),
                    start = Offset(w - 118.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w - 98.dp.toPx(), -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor.copy(alpha = 0.24f),
                    start = Offset(w - 104.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w - 84.dp.toPx(), -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor.copy(alpha = 0.18f),
                    start = Offset(w - 90.dp.toPx(), h + 6.dp.toPx()),
                    end = Offset(w - 70.dp.toPx(), -6.dp.toPx()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
            .padding(horizontal = 14.dp, vertical = 9.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Left: White Icon in soft circle + Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.20f),
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = tr(title),
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.1.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Right: "View All →" in a distinct compact rounded button container
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onActionClick() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = tr(actionText),
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006D77),
                        maxLines = 1,
                        softWrap = false
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "${tr("View All")} $title",
                        tint = Color(0xFF006D77),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

/**
 * Clinical Service Compact Card matching reference
 */
@Composable
fun ReferenceServiceCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        shadowElevation = 1.5.dp,
        modifier = modifier
            .width(96.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tr(title),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 13.sp
            )
        }
    }
}

/**
 * Responsive Healthcare Hero Background:
 * Faithfully matches the uploaded visual artwork:
 * - Clean white-to-teal expansive canvas
 * - Soft hospital city skyline in the background
 * - Botanical mint/teal leaves blooming behind the shield
 * - Glowing hexagonal badge with 3D split-tone medical shield, white cross, and ECG pulse
 * - Meditating figure in lotus posture beside the health pathway
 * - Serene winding teal Care Path flowing across the landscape
 * - Subtle left-hand readability gradient for the text UI
 */
@Composable
fun HealthcareHeroBackground(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // 1. Base Canvas Gradient: Crisp white on the left, soft aqua/mint on the right
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.White,
                    Color.White.copy(alpha = 0.95f),
                    Color(0xFFF0FDF4).copy(alpha = 0.85f),
                    Color(0xFFE0F2F1).copy(alpha = 0.90f)
                ),
                startX = 0f,
                endX = width
            ),
            size = size
        )

        // 2. Far Right Soft Skyline (Hospitals, towers, trees)
        val skylineColor = Color(0xFF80CBC4).copy(alpha = 0.28f)
        val skylineDeeper = Color(0xFF4DB6AC).copy(alpha = 0.35f)

        // Building blocks
        drawRect(
            color = skylineColor,
            topLeft = Offset(width * 0.84f, height * 0.25f),
            size = Size(width * 0.05f, height * 0.35f)
        )
        drawRect(
            color = skylineDeeper,
            topLeft = Offset(width * 0.89f, height * 0.18f),
            size = Size(width * 0.045f, height * 0.42f)
        )
        // Spire on tower
        val spirePath = Path().apply {
            moveTo(width * 0.9125f, height * 0.12f)
            lineTo(width * 0.935f, height * 0.18f)
            lineTo(width * 0.89f, height * 0.18f)
            close()
        }
        drawPath(spirePath, skylineDeeper)

        drawRect(
            color = skylineColor,
            topLeft = Offset(width * 0.94f, height * 0.28f),
            size = Size(width * 0.055f, height * 0.32f)
        )

        // Soft foliage/clouds behind skyline
        drawCircle(
            color = Color(0xFFB2DFDB).copy(alpha = 0.25f),
            radius = width * 0.06f,
            center = Offset(width * 0.92f, height * 0.52f)
        )
        drawCircle(
            color = Color(0xFFB2DFDB).copy(alpha = 0.25f),
            radius = width * 0.05f,
            center = Offset(width * 0.86f, height * 0.54f)
        )

        // 3. Botanical Leaves radiating behind the Shield
        val leafCenter = Offset(width * 0.72f, height * 0.38f)
        val leafColors = listOf(
            Color(0xFF80CBC4).copy(alpha = 0.60f),
            Color(0xFFB2DFDB).copy(alpha = 0.50f),
            Color(0xFF4DB6AC).copy(alpha = 0.45f)
        )

        // Left upper leaf
        val leaf1 = Path().apply {
            moveTo(leafCenter.x - width * 0.04f, leafCenter.y + height * 0.05f)
            cubicTo(
                leafCenter.x - width * 0.20f, leafCenter.y - height * 0.05f,
                leafCenter.x - width * 0.18f, leafCenter.y - height * 0.22f,
                leafCenter.x - width * 0.08f, leafCenter.y - height * 0.26f
            )
            cubicTo(
                leafCenter.x - width * 0.04f, leafCenter.y - height * 0.18f,
                leafCenter.x - width * 0.02f, leafCenter.y - height * 0.05f,
                leafCenter.x - width * 0.04f, leafCenter.y + height * 0.05f
            )
            close()
        }
        drawPath(leaf1, leafColors[0])

        // Left mid leaf
        val leaf2 = Path().apply {
            moveTo(leafCenter.x - width * 0.04f, leafCenter.y + height * 0.10f)
            cubicTo(
                leafCenter.x - width * 0.24f, leafCenter.y + height * 0.08f,
                leafCenter.x - width * 0.26f, leafCenter.y - height * 0.08f,
                leafCenter.x - width * 0.14f, leafCenter.y - height * 0.12f
            )
            cubicTo(
                leafCenter.x - width * 0.08f, leafCenter.y - height * 0.06f,
                leafCenter.x - width * 0.03f, leafCenter.y + height * 0.02f,
                leafCenter.x - width * 0.04f, leafCenter.y + height * 0.10f
            )
            close()
        }
        drawPath(leaf2, leafColors[1])

        // Right upper leaf
        val leaf3 = Path().apply {
            moveTo(leafCenter.x + width * 0.04f, leafCenter.y + height * 0.05f)
            cubicTo(
                leafCenter.x + width * 0.18f, leafCenter.y - height * 0.05f,
                leafCenter.x + width * 0.16f, leafCenter.y - height * 0.22f,
                leafCenter.x + width * 0.07f, leafCenter.y - height * 0.25f
            )
            cubicTo(
                leafCenter.x + width * 0.03f, leafCenter.y - height * 0.16f,
                leafCenter.x + width * 0.02f, leafCenter.y - height * 0.05f,
                leafCenter.x + width * 0.04f, leafCenter.y + height * 0.05f
            )
            close()
        }
        drawPath(leaf3, leafColors[0])

        // Right mid leaf
        val leaf4 = Path().apply {
            moveTo(leafCenter.x + width * 0.04f, leafCenter.y + height * 0.10f)
            cubicTo(
                leafCenter.x + width * 0.22f, leafCenter.y + height * 0.06f,
                leafCenter.x + width * 0.24f, leafCenter.y - height * 0.07f,
                leafCenter.x + width * 0.13f, leafCenter.y - height * 0.10f
            )
            cubicTo(
                leafCenter.x + width * 0.07f, leafCenter.y - height * 0.04f,
                leafCenter.x + width * 0.03f, leafCenter.y + height * 0.03f,
                leafCenter.x + width * 0.04f, leafCenter.y + height * 0.10f
            )
            close()
        }
        drawPath(leaf4, leafColors[2])

        // 4. Glowing Hexagonal Background Frame around Shield
        val hexSize = width * 0.18f
        val hexCenter = Offset(width * 0.72f, height * 0.38f)
        val hexPath = Path().apply {
            moveTo(hexCenter.x, hexCenter.y - hexSize * 1.15f)
            lineTo(hexCenter.x + hexSize * 0.95f, hexCenter.y - hexSize * 0.58f)
            lineTo(hexCenter.x + hexSize * 0.95f, hexCenter.y + hexSize * 0.58f)
            lineTo(hexCenter.x, hexCenter.y + hexSize * 1.15f)
            lineTo(hexCenter.x - hexSize * 0.95f, hexCenter.y + hexSize * 0.58f)
            lineTo(hexCenter.x - hexSize * 0.95f, hexCenter.y - hexSize * 0.58f)
            close()
        }
        drawPath(
            path = hexPath,
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.85f),
                    Color(0xFFE0F2F1).copy(alpha = 0.65f),
                    Color(0xFFB2DFDB).copy(alpha = 0.35f)
                ),
                center = hexCenter,
                radius = hexSize * 1.2f
            )
        )
        drawPath(
            path = hexPath,
            color = Color.White.copy(alpha = 0.9f),
            style = Stroke(width = 2.dp.toPx())
        )

        // 5. Main 3D Medical Shield (Split Tone: Light Teal Left / Deep Teal Right)
        val sWidth = width * 0.22f
        val sHeight = height * 0.46f
        val sLeft = hexCenter.x - sWidth * 0.5f
        val sTop = hexCenter.y - sHeight * 0.48f

        // Left half of shield
        val leftShield = Path().apply {
            moveTo(sLeft + sWidth * 0.5f, sTop)
            lineTo(sLeft, sTop + sHeight * 0.15f)
            cubicTo(
                sLeft, sTop + sHeight * 0.65f,
                sLeft + sWidth * 0.48f, sTop + sHeight * 0.92f,
                sLeft + sWidth * 0.5f, sTop + sHeight
            )
            lineTo(sLeft + sWidth * 0.5f, sTop)
            close()
        }
        drawPath(
            path = leftShield,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF00897B),
                    Color(0xFF006D77)
                ),
                startY = sTop,
                endY = sTop + sHeight
            )
        )

        // Right half of shield
        val rightShield = Path().apply {
            moveTo(sLeft + sWidth * 0.5f, sTop)
            lineTo(sLeft + sWidth, sTop + sHeight * 0.15f)
            cubicTo(
                sLeft + sWidth, sTop + sHeight * 0.65f,
                sLeft + sWidth * 0.52f, sTop + sHeight * 0.92f,
                sLeft + sWidth * 0.5f, sTop + sHeight
            )
            lineTo(sLeft + sWidth * 0.5f, sTop)
            close()
        }
        drawPath(
            path = rightShield,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF006D77),
                    Color(0xFF004D40)
                ),
                startY = sTop,
                endY = sTop + sHeight
            )
        )

        // Full Shield Outline
        val fullShield = Path().apply {
            moveTo(sLeft + sWidth * 0.5f, sTop)
            lineTo(sLeft + sWidth, sTop + sHeight * 0.15f)
            cubicTo(
                sLeft + sWidth, sTop + sHeight * 0.65f,
                sLeft + sWidth * 0.5f, sTop + sHeight * 0.95f,
                sLeft + sWidth * 0.5f, sTop + sHeight
            )
            cubicTo(
                sLeft + sWidth * 0.5f, sTop + sHeight * 0.95f,
                sLeft, sTop + sHeight * 0.65f,
                sLeft, sTop + sHeight * 0.15f
            )
            close()
        }
        drawPath(
            path = fullShield,
            color = Color.White.copy(alpha = 0.9f),
            style = Stroke(width = 1.8.dp.toPx())
        )

        // 6. Crisp White Medical Cross inside Shield
        val crossX = hexCenter.x
        val crossY = hexCenter.y - sHeight * 0.04f
        val crossW = sWidth * 0.52f
        val armW = crossW * 0.35f
        val crossH = crossW * 0.5f

        val crossPath = Path().apply {
            moveTo(crossX - armW * 0.5f, crossY - crossH)
            lineTo(crossX + armW * 0.5f, crossY - crossH)
            lineTo(crossX + armW * 0.5f, crossY - armW * 0.5f)
            lineTo(crossX + crossH, crossY - armW * 0.5f)
            lineTo(crossX + crossH, crossY + armW * 0.5f)
            lineTo(crossX + armW * 0.5f, crossY + armW * 0.5f)
            lineTo(crossX + armW * 0.5f, crossY + crossH)
            lineTo(crossX - armW * 0.5f, crossY + crossH)
            lineTo(crossX - armW * 0.5f, crossY + armW * 0.5f)
            lineTo(crossX - crossH, crossY + armW * 0.5f)
            lineTo(crossX - crossH, crossY - armW * 0.5f)
            lineTo(crossX - armW * 0.5f, crossY - armW * 0.5f)
            close()
        }
        drawPath(crossPath, Color.White)

        // 7. Dynamic ECG Heartbeat Pulse cutting across the Cross
        val ecgPath = Path().apply {
            moveTo(crossX - crossH * 1.15f, crossY)
            lineTo(crossX - crossH * 0.45f, crossY)
            lineTo(crossX - crossH * 0.25f, crossY - crossH * 0.55f)
            lineTo(crossX + crossH * 0.05f, crossY + crossH * 0.65f)
            lineTo(crossX + crossH * 0.30f, crossY - crossH * 0.42f)
            lineTo(crossX + crossH * 0.50f, crossY)
            lineTo(crossX + crossH * 1.15f, crossY)
        }
        drawPath(
            path = ecgPath,
            color = Color(0xFF006D77),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // 8. Soft Hill on bottom right
        val hillPath = Path().apply {
            moveTo(width * 0.58f, height)
            cubicTo(
                width * 0.65f, height * 0.78f,
                width * 0.82f, height * 0.70f,
                width, height * 0.72f
            )
            lineTo(width, height)
            close()
        }
        drawPath(
            path = hillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE0F2F1),
                    Color(0xFFB2DFDB).copy(alpha = 0.8f)
                ),
                startY = height * 0.70f,
                endY = height
            )
        )

        // 9. Meditating Figure Silhouette on the Hill
        val figX = width * 0.83f
        val figY = height * 0.66f
        val figColor = Color(0xFF004D40)

        // Head
        drawCircle(
            color = figColor,
            radius = width * 0.024f,
            center = Offset(figX, figY - height * 0.058f)
        )
        // Torso and meditating arms in dhyana mudra
        val bodyPath = Path().apply {
            moveTo(figX, figY - height * 0.038f)
            lineTo(figX + width * 0.038f, figY)
            lineTo(figX - width * 0.038f, figY)
            close()
        }
        drawPath(bodyPath, figColor)
        // Lotus pose crossed legs base
        drawOval(
            color = figColor,
            topLeft = Offset(figX - width * 0.048f, figY - height * 0.012f),
            size = Size(width * 0.096f, height * 0.028f)
        )

        // 10. Winding Healthcare Pathway (Care Path)
        val pathRoad = Path().apply {
            moveTo(width * 0.32f, height)
            cubicTo(
                width * 0.50f, height * 0.98f,
                width * 0.58f, height * 0.85f,
                width * 0.68f, height * 0.78f
            )
            cubicTo(
                width * 0.76f, height * 0.72f,
                width * 0.70f, height * 0.63f,
                width * 0.72f, height * 0.62f
            )
            lineTo(width * 0.76f, height * 0.62f)
            cubicTo(
                width * 0.75f, height * 0.66f,
                width * 0.84f, height * 0.74f,
                width * 0.74f, height * 0.82f
            )
            cubicTo(
                width * 0.64f, height * 0.88f,
                width * 0.58f, height,
                width * 0.52f, height
            )
            close()
        }
        drawPath(
            path = pathRoad,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF26A69A),
                    Color(0xFF00897B),
                    Color(0xFF004D40)
                ),
                start = Offset(width * 0.32f, height),
                end = Offset(width * 0.74f, height * 0.62f)
            )
        )

        // Care Path Center White Guideline
        val centerLine = Path().apply {
            moveTo(width * 0.42f, height)
            cubicTo(
                width * 0.55f, height * 0.94f,
                width * 0.62f, height * 0.84f,
                width * 0.71f, height * 0.77f
            )
            cubicTo(
                width * 0.77f, height * 0.70f,
                width * 0.72f, height * 0.63f,
                width * 0.74f, height * 0.62f
            )
        }
        drawPath(
            path = centerLine,
            color = Color.White.copy(alpha = 0.85f),
            style = Stroke(
                width = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        // 11. Soft White Readability Gradient on Left Edge
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.92f),
                    Color.White.copy(alpha = 0.75f),
                    Color.Transparent
                ),
                startX = 0f,
                endX = width * 0.65f
            ),
            size = size
        )
    }
}
