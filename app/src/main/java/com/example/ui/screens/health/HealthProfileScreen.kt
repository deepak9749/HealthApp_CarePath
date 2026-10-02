package com.example.ui.screens.health

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.HealthProfileEntity
import com.example.data.model.UserRole
import com.example.ui.SmartHealthViewModel
import com.example.ui.navigation.Screen
import com.example.ui.theme.HealthPrimaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthProfileScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val healthProfile by viewModel.healthProfile.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()

    var showEditProfileDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Profile & Identity", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showEditProfileDialog = true },
                        modifier = Modifier.testTag("edit_profile_button")
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
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
                .testTag("health_profile_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User Header Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentUser?.name ?: "Guest Patient",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = currentUser?.phone ?: "No phone registered (Guest Mode)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (currentUser == null) {
                            Button(
                                onClick = { viewModel.setLoginDialogOpen(true) },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Create Account / Sign In")
                            }
                        } else {
                            OutlinedButton(
                                onClick = { viewModel.logout() },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                                modifier = Modifier.testTag("profile_logout_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Log Out")
                            }
                        }
                    }
                }
            }

            // Health Parameters (Core Clinical Details)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Core Clinical Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Row 1: Gender, Age, Blood Group
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            VitalsChip("Gender", healthProfile?.gender ?: "Male", Modifier.weight(1f))
                            VitalsChip("Age", "${healthProfile?.age ?: 21}", Modifier.weight(1f))
                            VitalsChip("Blood Group", healthProfile?.bloodGroup ?: "B+ (Positive)", Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Row 2: Height, Weight, Allergic
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val heightVal = healthProfile?.heightCm ?: 172.0
                            val heightDisplay = if (heightVal % 1.0 == 0.0) "${heightVal.toInt()}.0 cm" else "$heightVal cm"
                            VitalsChip("Height", heightDisplay, Modifier.weight(1f))

                            val weightVal = healthProfile?.weightKg ?: 69.5
                            val weightDisplay = if (weightVal % 1.0 == 0.0) "${weightVal.toInt()}.0 kg" else "$weightVal kg"
                            VitalsChip("Weight", weightDisplay, Modifier.weight(1f))

                            VitalsChip("Allergic", healthProfile?.isAllergic ?: "No", Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(14.dp))

                        Text("Known Allergies & Drug Sensitivity", fontWeight = FontWeight.SemiBold)
                        Text(healthProfile?.allergies ?: "Penicillin (Mild Rash)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Chronic Medical Conditions:", fontWeight = FontWeight.SemiBold)
                        Text(healthProfile?.chronicDiseases ?: "Hypertension (Managed)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Quick Links
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        ProfileMenuRow("Emergency Contacts", Icons.Default.Emergency, Color(0xFFC62828)) {
                            onNavigateTo(Screen.EmergencyContacts.route)
                        }
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                        ProfileMenuRow("Frontline Health Worker Portal (ASHA)", Icons.Default.VolunteerActivism, HealthPrimaryLight) {
                            onNavigateTo(Screen.HealthWorkerPortal.route)
                        }
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                        ProfileMenuRow("Hospital Staff Portal (Bed Management)", Icons.Default.LocalHospital, Color(0xFF006874)) {
                            onNavigateTo(Screen.HospitalStaffPortal.route)
                        }
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                        ProfileMenuRow("Government Health Quality Portal", Icons.Default.AdminPanelSettings, Color(0xFF5C6BC0)) {
                            onNavigateTo(Screen.GovernmentAdmin.route)
                        }
                    }
                }
            }
        }
    }

    if (showEditProfileDialog) {
        var gender by remember(healthProfile) { mutableStateOf(healthProfile?.gender ?: "Male") }
        var ageText by remember(healthProfile) { mutableStateOf((healthProfile?.age ?: 21).toString()) }
        var bloodGroup by remember(healthProfile) {
            val bg = healthProfile?.bloodGroup ?: "B+ (Positive)"
            val matchedOption = when {
                bg.startsWith("A+") -> "A+"
                bg.startsWith("A-") -> "A-"
                bg.startsWith("B+") -> "B+"
                bg.startsWith("B-") -> "B-"
                bg.startsWith("AB+") -> "AB+"
                bg.startsWith("AB-") -> "AB-"
                bg.startsWith("O+") -> "O+"
                bg.startsWith("O-") -> "O-"
                else -> bg
            }
            mutableStateOf(matchedOption)
        }
        var heightText by remember(healthProfile) { mutableStateOf((healthProfile?.heightCm ?: 172.0).toString()) }
        var weightText by remember(healthProfile) { mutableStateOf((healthProfile?.weightKg ?: 69.5).toString()) }
        var isAllergic by remember(healthProfile) { mutableStateOf(healthProfile?.isAllergic ?: "No") }

        val genderOptions = listOf("Male", "Female", "Other", "Prefer not to say")
        val bloodGroupOptions = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        val allergicOptions = listOf("No", "Yes")

        var genderDropdownExpanded by remember { mutableStateOf(false) }
        var bloodGroupDropdownExpanded by remember { mutableStateOf(false) }
        var allergicDropdownExpanded by remember { mutableStateOf(false) }

        val scrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Health Profile", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 1. Gender Dropdown
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gender") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (genderDropdownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Gender"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { genderDropdownExpanded = true }
                        )
                        DropdownMenu(
                            expanded = genderDropdownExpanded,
                            onDismissRequest = { genderDropdownExpanded = false }
                        ) {
                            genderOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        gender = option
                                        genderDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // 2. Age (Numeric only)
                    OutlinedTextField(
                        value = ageText,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() } && input.length <= 3) {
                                ageText = input
                            }
                        },
                        label = { Text("Age") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 3. Blood Group Dropdown
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = bloodGroup,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Blood Group") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (bloodGroupDropdownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Blood Group"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { bloodGroupDropdownExpanded = true }
                        )
                        DropdownMenu(
                            expanded = bloodGroupDropdownExpanded,
                            onDismissRequest = { bloodGroupDropdownExpanded = false }
                        ) {
                            bloodGroupOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        bloodGroup = option
                                        bloodGroupDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // 4. Height (cm)
                    OutlinedTextField(
                        value = heightText,
                        onValueChange = { input ->
                            if (input.isEmpty() || (input.matches(Regex("""^\d*\.?\d*$""")) && input.length <= 6)) {
                                heightText = input
                            }
                        },
                        label = { Text("Height") },
                        suffix = { Text("cm") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 5. Weight (kg)
                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { input ->
                            if (input.isEmpty() || (input.matches(Regex("""^\d*\.?\d*$""")) && input.length <= 6)) {
                                weightText = input
                            }
                        },
                        label = { Text("Weight") },
                        suffix = { Text("kg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 6. Allergic Dropdown (Yes / No)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = isAllergic,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Allergic") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (allergicDropdownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Allergic Status"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { allergicDropdownExpanded = true }
                        )
                        DropdownMenu(
                            expanded = allergicDropdownExpanded,
                            onDismissRequest = { allergicDropdownExpanded = false }
                        ) {
                            allergicOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        isAllergic = option
                                        allergicDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedAge = ageText.toIntOrNull()?.coerceIn(1, 130) ?: (healthProfile?.age ?: 21)
                        val parsedHeight = heightText.toDoubleOrNull()?.coerceIn(30.0, 260.0) ?: (healthProfile?.heightCm ?: 172.0)
                        val parsedWeight = weightText.toDoubleOrNull()?.coerceIn(2.0, 350.0) ?: (healthProfile?.weightKg ?: 69.5)

                        viewModel.updateHealthProfile(
                            HealthProfileEntity(
                                profileId = healthProfile?.profileId ?: "prof_default",
                                userId = healthProfile?.userId ?: currentUser?.id ?: "user_default",
                                guestSessionId = healthProfile?.guestSessionId,
                                gender = gender,
                                age = parsedAge,
                                bloodGroup = bloodGroup,
                                heightCm = parsedHeight,
                                weightKg = parsedWeight,
                                isAllergic = isAllergic,
                                allergies = healthProfile?.allergies ?: "Penicillin (Mild Rash)",
                                chronicDiseases = healthProfile?.chronicDiseases ?: "Hypertension (Managed)",
                                existingConditions = healthProfile?.existingConditions ?: "Mild Hypertension",
                                emergencyNotes = healthProfile?.emergencyNotes ?: ""
                            )
                        )
                        showEditProfileDialog = false
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun VitalsChip(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ProfileMenuRow(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
