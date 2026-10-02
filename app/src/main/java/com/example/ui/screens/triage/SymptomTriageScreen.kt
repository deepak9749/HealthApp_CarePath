package com.example.ui.screens.triage

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.datasource.ComprehensiveSymptomCatalog
import com.example.data.local.SymptomCategoryEntity
import com.example.data.local.SymptomEntity
import com.example.ui.SmartHealthViewModel
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.EmergencyRedContainer
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SymptomTriageScreen(
    viewModel: SmartHealthViewModel,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dbCategories by viewModel.symptomCategories.collectAsStateWithLifecycle()
    val dbSymptoms by viewModel.allSymptoms.collectAsStateWithLifecycle()
    val selectedSymptoms by viewModel.selectedSymptoms.collectAsStateWithLifecycle()
    val currentSeverity by viewModel.currentSeverity.collectAsStateWithLifecycle()
    val duration by viewModel.symptomDuration.collectAsStateWithLifecycle()

    // Ensure all 28 categories are present even during initial sync
    val categories = remember(dbCategories) {
        if (dbCategories.isNotEmpty()) dbCategories else ComprehensiveSymptomCatalog.categories
    }
    val allSymptoms = remember(dbSymptoms) {
        if (dbSymptoms.isNotEmpty()) dbSymptoms else ComprehensiveSymptomCatalog.symptoms
    }

    var selectedCategoryId by remember { mutableStateOf<String?>("cat_neuro") }
    var categorySearchQuery by remember { mutableStateOf("") }
    var symptomSearchQuery by remember { mutableStateOf("") }
    var isCategoriesExpanded by remember { mutableStateOf(true) }

    // Filter categories based on category search query
    val filteredCategories = remember(categories, categorySearchQuery) {
        if (categorySearchQuery.isBlank()) categories
        else categories.filter {
            it.categoryName.contains(categorySearchQuery, ignoreCase = true) ||
            it.bodySystem.contains(categorySearchQuery, ignoreCase = true) ||
            it.description.contains(categorySearchQuery, ignoreCase = true)
        }
    }

    // Filter symptoms dynamically based on selected category & symptom search query
    val filteredSymptoms = remember(allSymptoms, selectedCategoryId, symptomSearchQuery) {
        allSymptoms.filter { sym ->
            val matchesCategory = selectedCategoryId == null || sym.categoryId == selectedCategoryId
            val matchesQuery = symptomSearchQuery.isBlank() ||
                sym.name.contains(symptomSearchQuery, ignoreCase = true) ||
                sym.subcategory.contains(symptomSearchQuery, ignoreCase = true) ||
                sym.description.contains(symptomSearchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    val selectedCategoryObj = remember(categories, selectedCategoryId) {
        categories.find { it.id == selectedCategoryId }
    }

    val isEmergencyCategorySelected = selectedCategoryId == "cat_emergency_critical"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Hierarchical Symptom Triage",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Clinical Multi-Step Assessment (${categories.size} Body Systems)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (selectedSymptoms.isNotEmpty()) {
                        TextButton(onClick = { viewModel.clearTriageSelections() }) {
                            Text("Clear (${selectedSymptoms.size})", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.runTriageAssessment {
                                onNavigateTo(Screen.TriageResult.route)
                            }
                        },
                        enabled = selectedSymptoms.isNotEmpty(),
                        shape = RoundedCornerShape(12.dp),
                        colors = if (selectedSymptoms.any { it.isEmergencyRedFlag }) {
                            ButtonDefaults.buttonColors(containerColor = EmergencyRed, contentColor = Color.White)
                        } else {
                            ButtonDefaults.buttonColors()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("run_assessment_button")
                    ) {
                        Icon(
                            imageVector = if (selectedSymptoms.any { it.isEmergencyRedFlag }) Icons.Default.Warning else Icons.Default.HealthAndSafety,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedSymptoms.isNotEmpty())
                                "Evaluate Digital Triage (${selectedSymptoms.size} Selected)"
                            else "Select Symptom(s) in Step 2 to Evaluate",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("symptom_triage_screen")
        ) {
            val screenWidth = maxWidth
            // Responsive columns: 1 column on narrow screens (<340dp), 2 columns on normal mobile (340dp-600dp), 3 columns on tablet/expanded (>=600dp)
            val categoryColumns = when {
                screenWidth < 340.dp -> 1
                screenWidth < 600.dp -> 2
                else -> 3
            }
            // 1 column for symptoms on phones ensures full text visibility and easy tapping; 2 on large screens/tablets
            val symptomGridColumns = if (screenWidth >= 600.dp) 2 else 1

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {

                // ==========================================
                // STEP 1: SELECT BODY SYSTEM
                // ==========================================
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("1", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Select Body System",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Tap a body system to filter specific symptoms in Step 2",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // "All Systems" quick filter chip
                            FilterChip(
                                selected = selectedCategoryId == null,
                                onClick = { selectedCategoryId = null },
                                label = {
                                    Text(
                                        text = "All (${categories.size})",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (selectedCategoryId == null) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.FilterList,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category Search Bar for Step 1
                        OutlinedTextField(
                            value = categorySearchQuery,
                            onValueChange = { categorySearchQuery = it },
                            placeholder = { Text("Search 28 body systems (e.g. heart, neuro, trauma)...", style = MaterialTheme.typography.bodySmall) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) },
                            trailingIcon = {
                                if (categorySearchQuery.isNotEmpty()) {
                                    IconButton(onClick = { categorySearchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Emergency / Critical Global Banner Button (Prominent shortcut)
                        val emergencyCategory = categories.find { it.id == "cat_emergency_critical" }
                        if (emergencyCategory != null && (categorySearchQuery.isBlank() || emergencyCategory.categoryName.contains(categorySearchQuery, ignoreCase = true) || emergencyCategory.description.contains(categorySearchQuery, ignoreCase = true))) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (selectedCategoryId == emergencyCategory.id) EmergencyRedContainer else EmergencyRed.copy(alpha = 0.08f),
                                border = BorderStroke(
                                    width = if (selectedCategoryId == emergencyCategory.id) 2.dp else 1.dp,
                                    color = if (selectedCategoryId == emergencyCategory.id) EmergencyRed else EmergencyRed.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategoryId = if (selectedCategoryId == emergencyCategory.id) null else emergencyCategory.id
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = EmergencyRed,
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Emergency,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Emergency / Critical Symptoms",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = EmergencyRed,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = EmergencyRed
                                            ) {
                                                Text(
                                                    text = "PRIORITY",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Black,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = emergencyCategory.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            lineHeight = 16.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    RadioButton(
                                        selected = selectedCategoryId == emergencyCategory.id,
                                        onClick = {
                                            selectedCategoryId = if (selectedCategoryId == emergencyCategory.id) null else emergencyCategory.id
                                        },
                                        colors = RadioButtonDefaults.colors(selectedColor = EmergencyRed)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Standard 27 Body System Grid with content-driven responsive heights
                        val standardCategories = filteredCategories.filter { it.id != "cat_emergency_critical" }
                        val displayedCategories = if (isCategoriesExpanded || categorySearchQuery.isNotBlank()) {
                            standardCategories
                        } else {
                            standardCategories.take(categoryColumns * 2)
                        }
                        val categoryRows = displayedCategories.chunked(categoryColumns)

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            categoryRows.forEach { rowCategories ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    rowCategories.forEach { cat ->
                                        val isSelected = selectedCategoryId == cat.id
                                        BodySystemCategoryCard(
                                            category = cat,
                                            isSelected = isSelected,
                                            onClick = {
                                                selectedCategoryId = if (isSelected) null else cat.id
                                            },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    if (rowCategories.size < categoryColumns) {
                                        repeat(categoryColumns - rowCategories.size) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }

                        if (categorySearchQuery.isBlank() && standardCategories.size > categoryColumns * 2) {
                            TextButton(
                                onClick = { isCategoriesExpanded = !isCategoriesExpanded },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 6.dp)
                            ) {
                                Text(
                                    text = if (isCategoriesExpanded) "Show Less" else "View All ${categories.size} Body Systems",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Icon(
                                    imageVector = if (isCategoriesExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                // ==========================================
                // EMERGENCY ACTION SHORTCUTS (When Emergency Selected)
                // ==========================================
                if (isEmergencyCategorySelected) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = EmergencyRedContainer),
                            border = BorderStroke(1.5.dp, EmergencyRed),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Emergency, contentDescription = null, tint = EmergencyRed, modifier = Modifier.size(22.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Immediate Emergency Care Available",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = EmergencyRed
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "If experiencing life-threatening distress, access direct emergency services immediately:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = { onNavigateTo(Screen.EmergencyCenter.route) },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.DirectionsCar, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Ambulance", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    OutlinedButton(
                                        onClick = { onNavigateTo(Screen.Hospitals.route) },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = EmergencyRed),
                                        border = BorderStroke(1.dp, EmergencyRed),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.LocalHospital, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("ER Hospitals", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:108"))
                                            context.startActivity(intent)
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = EmergencyRed),
                                        border = BorderStroke(1.dp, EmergencyRed),
                                        modifier = Modifier.weight(0.9f)
                                    ) {
                                        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Call 108", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // SELECTED SYMPTOMS SUMMARY CHIPS (Full Text Wrapping)
                // ==========================================
                if (selectedSymptoms.isNotEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Selected for Assessment (${selectedSymptoms.size})",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Text(
                                        text = "Tap to remove",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    selectedSymptoms.forEach { sym ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (sym.isEmergencyRedFlag) EmergencyRedContainer else MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(
                                                1.dp,
                                                if (sym.isEmergencyRedFlag) EmergencyRed.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outlineVariant
                                            ),
                                            modifier = Modifier.clickable { viewModel.toggleSymptomSelection(sym) }
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            ) {
                                                if (sym.isEmergencyRedFlag) {
                                                    Icon(
                                                        Icons.Default.Warning,
                                                        contentDescription = null,
                                                        tint = EmergencyRed,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                }
                                                Text(
                                                    text = sym.name,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = if (sym.isEmergencyRedFlag) EmergencyRed else MaterialTheme.colorScheme.onSurface,
                                                    lineHeight = 16.sp
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "Remove",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = if (sym.isEmergencyRedFlag) EmergencyRed else MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // STEP 2: SPECIFIC SYMPTOMS & RED FLAGS
                // ==========================================
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("2", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Specific Symptoms & Red Flags",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = if (selectedCategoryObj != null)
                                            "Showing for: ${selectedCategoryObj.categoryName}"
                                        else "Showing all categories (${filteredSymptoms.size} symptoms)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "${filteredSymptoms.size} symptoms",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Symptom Search Bar for Step 2
                        OutlinedTextField(
                            value = symptomSearchQuery,
                            onValueChange = { symptomSearchQuery = it },
                            placeholder = {
                                Text(
                                    text = if (selectedCategoryObj != null)
                                        "Search in ${selectedCategoryObj.categoryName}..."
                                    else "Search all symptoms...",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (symptomSearchQuery.isNotEmpty()) {
                                    IconButton(onClick = { symptomSearchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }
                }

                if (filteredSymptoms.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                                Text(
                                    text = "No symptoms match your filter in this category.\nTry clearing the search query or select another body system.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                } else if (symptomGridColumns == 1) {
                    items(filteredSymptoms, key = { it.id }) { sym ->
                        val isSelected = selectedSymptoms.any { it.id == sym.id }
                        CompactSymptomCard(
                            symptom = sym,
                            isSelected = isSelected,
                            onToggle = { viewModel.toggleSymptomSelection(sym) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    val symptomChunks = filteredSymptoms.chunked(symptomGridColumns)
                    items(symptomChunks) { rowSymptoms ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            rowSymptoms.forEach { sym ->
                                val isSelected = selectedSymptoms.any { it.id == sym.id }
                                CompactSymptomCard(
                                    symptom = sym,
                                    isSelected = isSelected,
                                    onToggle = { viewModel.toggleSymptomSelection(sym) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowSymptoms.size < symptomGridColumns) {
                                repeat(symptomGridColumns - rowSymptoms.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // STEP 3: SEVERITY LEVEL (1 - 10)
                // ==========================================
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("3", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "Severity (1 - 10)",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Rate the highest pain or discomfort intensity",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = when {
                                        currentSeverity >= 9 -> EmergencyRed
                                        currentSeverity >= 7 -> WarningAmber
                                        currentSeverity >= 4 -> Color(0xFF006874)
                                        else -> Color(0xFF16A34A)
                                    }
                                ) {
                                    Text(
                                        text = "$currentSeverity / 10 • ${
                                            when {
                                                currentSeverity >= 9 -> "Critical"
                                                currentSeverity >= 7 -> "Severe"
                                                currentSeverity >= 4 -> "Moderate"
                                                else -> "Mild"
                                            }
                                        }",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Slider(
                                value = currentSeverity.toFloat(),
                                onValueChange = { viewModel.setSeverity(it.toInt()) },
                                valueRange = 1f..10f,
                                steps = 8,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("1 (Mild)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("5 (Moderate)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("10 (Unbearable)", style = MaterialTheme.typography.labelSmall, color = EmergencyRed, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // ==========================================
                // STEP 4: DURATION OF SYMPTOMS
                // ==========================================
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("4", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Duration of Symptoms",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "How long have you experienced these symptoms?",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            val durations = listOf("< 2 hrs (Sudden)", "2 - 6 hrs", "6 - 24 hrs", "1 - 3 days", "3 - 7 days", "> 1 week")
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                durations.forEach { dur ->
                                    val isSelected = duration == dur
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                                        ),
                                        modifier = Modifier
                                            .clickable { viewModel.setDuration(dur) }
                                    ) {
                                        Text(
                                            text = dur,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Body System category card: Content-driven height, full natural text wrapping, balanced icon alignment
 */
@Composable
private fun BodySystemCategoryCard(
    category: SymptomCategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = getCategoryIcon(category.id)
    val isEmergency = category.id == "cat_emergency_critical"

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when {
            isSelected && isEmergency -> EmergencyRedContainer
            isSelected -> MaterialTheme.colorScheme.primaryContainer
            isEmergency -> Color(0xFFFFF5F5)
            else -> MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = when {
                isSelected && isEmergency -> EmergencyRed
                isSelected -> MaterialTheme.colorScheme.primary
                isEmergency -> EmergencyRed.copy(alpha = 0.4f)
                else -> MaterialTheme.colorScheme.outlineVariant
            }
        ),
        shadowElevation = if (isSelected) 2.dp else 0.5.dp,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = when {
                    isSelected && isEmergency -> EmergencyRed
                    isSelected -> MaterialTheme.colorScheme.primary
                    isEmergency -> EmergencyRed.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                },
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 2.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = when {
                            isSelected -> Color.White
                            isEmergency -> EmergencyRed
                            else -> MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.categoryName,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = when {
                        isSelected && isEmergency -> EmergencyRed
                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                        isEmergency -> EmergencyRed
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    lineHeight = 17.sp
                )

                if (category.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

/**
 * Fully readable, content-driven Symptom card with clear Red Flag badges and full text wrapping
 */
@Composable
private fun CompactSymptomCard(
    symptom: SymptomEntity,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                symptom.isEmergencyRedFlag -> Color(0xFFFFF7F7)
                else -> MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = when {
                isSelected -> MaterialTheme.colorScheme.primary
                symptom.isEmergencyRedFlag -> EmergencyRed.copy(alpha = 0.4f)
                else -> MaterialTheme.colorScheme.outlineVariant
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp, end = 4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (symptom.isEmergencyRedFlag) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = EmergencyRedContainer,
                        border = BorderStroke(0.5.dp, EmergencyRed.copy(alpha = 0.6f)),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = EmergencyRed,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "RED FLAG EMERGENCY",
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Black,
                                color = EmergencyRed
                            )
                        }
                    }
                }

                Text(
                    text = symptom.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (symptom.isEmergencyRedFlag) EmergencyRed else MaterialTheme.colorScheme.onSurface,
                    lineHeight = 19.sp
                )

                if (symptom.subcategory.isNotBlank() || symptom.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = if (symptom.subcategory.isNotBlank()) "${symptom.subcategory} • ${symptom.description}" else symptom.description,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

/**
 * Returns a clinical icon mapped to each of the 28 body system categories
 */
private fun getCategoryIcon(categoryId: String): ImageVector {
    return when (categoryId) {
        "cat_neuro" -> Icons.Default.Psychology
        "cat_cardio" -> Icons.Default.Favorite
        "cat_respiratory" -> Icons.Default.Air
        "cat_mouth_jaw" -> Icons.Default.Face
        "cat_ear" -> Icons.Default.Hearing
        "cat_eyes" -> Icons.Default.Visibility
        "cat_nose_sinuses" -> Icons.Default.SentimentSatisfied
        "cat_throat_voice" -> Icons.Default.RecordVoiceOver
        "cat_digestive" -> Icons.Default.Restaurant
        "cat_liver_gallbladder" -> Icons.Default.Science
        "cat_kidney_urinary" -> Icons.Default.WaterDrop
        "cat_bowel_rectal" -> Icons.Default.Wc
        "cat_blood_immune" -> Icons.Default.Shield
        "cat_bones_joints", "cat_ortho" -> Icons.Default.AccessibilityNew
        "cat_muscles_soft_tissue" -> Icons.Default.FitnessCenter
        "cat_skin_hair_nails" -> Icons.Default.Spa
        "cat_endocrine_hormonal" -> Icons.Default.BubbleChart
        "cat_female_reproductive" -> Icons.Default.Female
        "cat_maternal_pregnancy", "cat_maternal" -> Icons.Default.PregnantWoman
        "cat_male_reproductive" -> Icons.Default.Male
        "cat_child_infant", "cat_pediatric" -> Icons.Default.ChildCare
        "cat_infectious_diseases", "cat_fever_infectious" -> Icons.Default.Coronavirus
        "cat_mental_health" -> Icons.Default.SelfImprovement
        "cat_sleep_health" -> Icons.Default.Bedtime
        "cat_general_body" -> Icons.Default.Person
        "cat_injury_trauma", "cat_trauma" -> Icons.Default.Healing
        "cat_poisoning_toxic" -> Icons.Default.Warning
        "cat_emergency_critical" -> Icons.Default.Emergency
        else -> Icons.Default.MedicalServices
    }
}
