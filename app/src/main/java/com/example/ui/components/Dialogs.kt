package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.i18n.tr

@Composable
fun LocationDialog(
    currentLocationName: String,
    onDismiss: () -> Unit,
    onSelectLocation: (Double, Double, String) -> Unit
) {
    val hubs = listOf(
        Triple(22.44335, 88.41543, "Sonarpur / Narendrapur (FIEM Demo Hub)"),
        Triple(22.46400, 88.38400, "Garia / Panchasayar"),
        Triple(22.49300, 88.40100, "Mukundapur / E.M. Bypass (Medica / Peerless)"),
        Triple(22.51500, 88.40200, "Anandapur / Kasba (Fortis / Ruby / Desun)"),
        Triple(22.50150, 88.34780, "Tollygunge (M.R. Bangur Hospital)"),
        Triple(22.53850, 88.34420, "Bhowanipore / Central Kolkata (SSKM / IPGMER)"),
        Triple(22.36210, 88.43580, "Baruipur Sub-Division"),
        Triple(22.58500, 88.48900, "New Town / Salt Lake")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(tr("Select Location"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = tr("GPS coordinates dynamically calculate nearby hospital distances, ICU beds, and ambulance response times."),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                hubs.forEach { hub ->
                    val isSelected = currentLocationName.contains(hub.third.substringBefore(" "))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                onSelectLocation(hub.first, hub.second, hub.third)
                                onDismiss()
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    onSelectLocation(hub.first, hub.second, hub.third)
                                    onDismiss()
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = hub.third,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = "Lat: ${hub.first}, Lng: ${hub.second}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("Close"))
            }
        }
    )
}

@Composable
fun RoleSelectorDialog(
    currentRole: UserRole,
    onDismiss: () -> Unit,
    onSelectRole: (UserRole) -> Unit
) {
    val roles = listOf(
        Pair(UserRole.GUEST, "Guest Mode (Zero Mandatory Login - Instant Emergency)"),
        Pair(UserRole.PATIENT, "Registered Patient (Timeline, Records, Prescriptions)"),
        Pair(UserRole.FRONTLINE_HEALTH_WORKER, "Frontline Health Worker (ASHA / ANM Field Triage & Referrals)"),
        Pair(UserRole.HOSPITAL_STAFF, "Hospital Portal (Bed Management, Queues, OPD Slots)"),
        Pair(UserRole.GOVERNMENT_ADMIN, "Government Admin (Quality Monitoring, Response Times, API Logs)")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(tr("Switch Role / Portal"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = tr("Smart Health System provides specialized role-based views for patients, frontline workers, hospitals, and health administrators."),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                roles.forEach { (role, label) ->
                    val isSelected = currentRole == role
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                onSelectRole(role)
                                onDismiss()
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    onSelectRole(role)
                                    onDismiss()
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tr(label),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("Done"))
            }
        }
    )
}

@Composable
fun LanguageDialog(
    currentLanguage: String,
    onDismiss: () -> Unit,
    onSelectLanguage: (String) -> Unit
) {
    val languages = listOf(
        Pair("EN", "English (Global)"),
        Pair("HI", "हिंदी (Hindi)"),
        Pair("BN", "বাংলা (Bengali)")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Select Language / ভাষা / भाषा", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                languages.forEach { (code, label) ->
                    val isSelected = currentLanguage == code
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                onSelectLanguage(code)
                                onDismiss()
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    onSelectLanguage(code)
                                    onDismiss()
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("Close"))
            }
        }
    )
}

@Composable
fun LoginSignUpDialog(
    onDismiss: () -> Unit,
    onRegister: (name: String, email: String, phone: String, mergeGuest: Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var mergeGuestActivity by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Create Health Account", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = "Registration is optional. Creates persistent health records, timeline, and appointment sync.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (+91)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Guest activity merge consent checkbox as mandated by prompt
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Checkbox(
                            checked = mergeGuestActivity,
                            onCheckedChange = { mergeGuestActivity = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Save previous guest activity?",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Transfer your recent symptom assessments, searches, and chat to your new profile.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onRegister(name, email, phone, mergeGuestActivity)
                    }
                },
                modifier = Modifier.testTag("submit_register_button")
            ) {
                Text("Create Account")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Continue as Guest")
            }
        }
    )
}
