package com.example.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ui.SmartHealthViewModel
import com.example.ui.components.*
import com.example.ui.i18n.tr
import com.example.ui.screens.ai.AIChatBottomSheet
import com.example.ui.screens.ambulance.AmbulanceScreen
import com.example.ui.screens.appointments.DoctorSearchScreen
import com.example.ui.screens.appointments.QueueStatusScreen
import com.example.ui.screens.appointments.TeleconsultScreen
import com.example.ui.screens.diagnostics.DiagnosticSearchScreen
import com.example.ui.screens.diagnostics.DiagnosticTestBookingScreen
import com.example.ui.screens.documents.MedicalReportScreen
import com.example.ui.screens.documents.PrescriptionScreen
import com.example.ui.screens.emergency.EmergencyCenterScreen
import com.example.ui.screens.health.*
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.hospitals.HospitalDetailScreen
import com.example.ui.screens.hospitals.HospitalSearchScreen
import com.example.ui.screens.medicines.MedicineSearchScreen
import com.example.ui.screens.portals.GovernmentAdminScreen
import com.example.ui.screens.portals.HealthWorkerPortalScreen
import com.example.ui.screens.portals.HospitalStaffPortalScreen
import com.example.ui.screens.referrals.ReferralTrackingScreen
import com.example.ui.screens.triage.SymptomTriageScreen
import com.example.ui.screens.triage.TriageResultScreen

@Composable
fun SmartHealthNavHost(
    viewModel: SmartHealthViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentLocationName by viewModel.currentLocationName.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    val isAIChatOpen by viewModel.isAIChatOpen.collectAsStateWithLifecycle()
    val isLoginDialogOpen by viewModel.isLoginDialogOpen.collectAsStateWithLifecycle()
    val isLocationDialogOpen by viewModel.isLocationDialogOpen.collectAsStateWithLifecycle()
    val isRoleSelectorOpen by viewModel.isRoleSelectorOpen.collectAsStateWithLifecycle()
    val isLanguageDialogOpen by viewModel.isLanguageDialogOpen.collectAsStateWithLifecycle()

    LaunchedEffect(userMessage) {
        userMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Hospitals.route,
        Screen.Doctors.route,
        Screen.HealthTimeline.route,
        Screen.HealthProfile.route
    )

    val showTopEmergencyBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Hospitals.route,
        Screen.Doctors.route,
        Screen.HealthTimeline.route,
        Screen.HealthProfile.route
    )

    Scaffold(
        topBar = {
            if (showTopEmergencyBar) {
                EmergencyTopBar(
                    locationName = currentLocationName,
                    currentLanguage = currentLanguage,
                    activeRole = activeRole,
                    onEmergencyClick = { navController.navigate(Screen.EmergencyCenter.route) },
                    onLocationClick = { viewModel.setLocationDialogOpen(true) },
                    onLanguageClick = { viewModel.setLanguageDialogOpen(true) },
                    onRoleClick = { viewModel.setRoleSelectorOpen(true) }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                    ) {
                        NavigationBarItem(
                            selected = currentRoute == Screen.Home.route,
                            onClick = { navController.navigate(Screen.Home.route) },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text(tr("Home"), fontWeight = if (currentRoute == Screen.Home.route) FontWeight.Bold else FontWeight.Normal) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Hospitals.route,
                            onClick = { navController.navigate(Screen.Hospitals.route) },
                            icon = { Icon(Icons.Default.LocalHospital, contentDescription = "Hospitals") },
                            label = { Text(tr("Hospitals"), fontWeight = if (currentRoute == Screen.Hospitals.route) FontWeight.Bold else FontWeight.Normal) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Doctors.route,
                            onClick = { navController.navigate(Screen.Doctors.route) },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Doctors") },
                            label = { Text(tr("Doctors"), fontWeight = if (currentRoute == Screen.Doctors.route) FontWeight.Bold else FontWeight.Normal) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.HealthTimeline.route,
                            onClick = { navController.navigate(Screen.HealthTimeline.route) },
                            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Timeline") },
                            label = { Text(tr("Timeline"), fontWeight = if (currentRoute == Screen.HealthTimeline.route) FontWeight.Bold else FontWeight.Normal) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.HealthProfile.route,
                            onClick = { navController.navigate(Screen.HealthProfile.route) },
                            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                            label = { Text(tr("Profile"), fontWeight = if (currentRoute == Screen.HealthProfile.route) FontWeight.Bold else FontWeight.Normal) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(viewModel = viewModel, onNavigateTo = { navController.navigate(it) })
                }
                composable(Screen.EmergencyCenter.route) {
                    EmergencyCenterScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.SymptomsTriage.route) {
                    SymptomTriageScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.TriageResult.route) {
                    TriageResultScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Hospitals.route) {
                    HospitalSearchScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(
                    route = Screen.HospitalDetail.route,
                    arguments = listOf(navArgument("hospitalId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val hospId = backStackEntry.arguments?.getString("hospitalId") ?: ""
                    HospitalDetailScreen(
                        hospitalId = hospId,
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Ambulance.route) {
                    AmbulanceScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Doctors.route) {
                    DoctorSearchScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.QueueStatus.route) {
                    QueueStatusScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Teleconsult.route) {
                    TeleconsultScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Reports.route) {
                    MedicalReportScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Prescriptions.route) {
                    PrescriptionScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Medicines.route) {
                    MedicineSearchScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Diagnostics.route) {
                    DiagnosticSearchScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onSelectTest = { testId ->
                            navController.navigate(Screen.DiagnosticBooking.createRoute(testId))
                        }
                    )
                }
                composable(
                    route = Screen.DiagnosticBooking.route,
                    arguments = listOf(navArgument("testId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val testId = backStackEntry.arguments?.getString("testId") ?: ""
                    DiagnosticTestBookingScreen(
                        viewModel = viewModel,
                        testId = testId,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.Referrals.route) {
                    ReferralTrackingScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.HealthTimeline.route) {
                    HealthTimelineScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.HealthProfile.route) {
                    HealthProfileScreen(
                        viewModel = viewModel,
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.EmergencyContacts.route) {
                    EmergencyContactsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.MaternalHealth.route) {
                    MaternalHealthScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.ChildHealth.route) {
                    ChildHealthScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.ChronicCare.route) {
                    ChronicCareScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.HealthWorkerPortal.route) {
                    HealthWorkerPortalScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.HospitalStaffPortal.route) {
                    HospitalStaffPortalScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.GovernmentAdmin.route) {
                    GovernmentAdminScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            // Persistent Floating AI Button (On top of every screen)
            FloatingAIBubble(
                onClick = { viewModel.setAIChatOpen(true) },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }

    // Modal AI Chat BottomSheet
    if (isAIChatOpen) {
        AIChatBottomSheet(
            viewModel = viewModel,
            onNavigateTo = { navController.navigate(it) },
            onDismiss = { viewModel.setAIChatOpen(false) }
        )
    }

    // Modal Dialogs
    if (isLocationDialogOpen) {
        LocationDialog(
            currentLocationName = currentLocationName,
            onDismiss = { viewModel.setLocationDialogOpen(false) },
            onSelectLocation = { lat, lng, name ->
                viewModel.updateLocation(lat, lng, name)
            }
        )
    }

    if (isRoleSelectorOpen) {
        RoleSelectorDialog(
            currentRole = activeRole,
            onDismiss = { viewModel.setRoleSelectorOpen(false) },
            onSelectRole = { viewModel.setRole(it) }
        )
    }

    if (isLanguageDialogOpen) {
        LanguageDialog(
            currentLanguage = currentLanguage,
            onDismiss = { viewModel.setLanguageDialogOpen(false) },
            onSelectLanguage = { viewModel.setLanguage(it) }
        )
    }

    if (isLoginDialogOpen) {
        LoginSignUpDialog(
            onDismiss = { viewModel.setLoginDialogOpen(false) },
            onRegister = { name, email, phone, mergeGuest ->
                viewModel.registerUser(name, email, phone, mergeGuest)
            }
        )
    }
}
