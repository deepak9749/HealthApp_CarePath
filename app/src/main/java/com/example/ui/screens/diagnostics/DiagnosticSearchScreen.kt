package com.example.ui.screens.diagnostics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.DiagnosticTestEntity
import com.example.data.local.FacilityDiagnosticEntity
import com.example.ui.SmartHealthViewModel
import com.example.ui.theme.HealthPrimaryLight
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosticSearchScreen(
    viewModel: SmartHealthViewModel,
    onBack: () -> Unit,
    onSelectTest: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val tests by viewModel.diagnosticTests.collectAsStateWithLifecycle()
    val facilityTests by viewModel.facilityDiagnostics.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = remember(tests) {
        listOf("All") + tests.map { it.category.split("/").first().trim() }.distinct()
    }

    val filteredTests = tests.filter { test ->
        val matchesSearch = searchQuery.isBlank() ||
                test.name.contains(searchQuery, ignoreCase = true) ||
                test.category.contains(searchQuery, ignoreCase = true) ||
                test.description.contains(searchQuery, ignoreCase = true)
        val matchesCat = selectedCategory == "All" || test.category.contains(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCat
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Diagnostic Tests & Imaging",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .background(Color(0xFFF8FAFC))
                .testTag("diagnostic_search_screen"),
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Search Input
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Search tests (e.g. CBC, ECG, MRI, Blood Sugar)...",
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFF006D77),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF006D77),
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Quick Category Filter Chips
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) Color(0xFF006D77) else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) Color(0xFF006D77) else Color(0xFFE2E8F0)),
                            modifier = Modifier.clickable { selectedCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF475569),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            // Count summary
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Available Tests (${filteredTests.size})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Compact Diagnostic Test Cards
            items(filteredTests, key = { it.id }) { test ->
                val availableFacilities = facilityTests.filter { it.testName.contains(test.name, ignoreCase = true) }
                CompactDiagnosticTestCard(
                    test = test,
                    availableFacilities = availableFacilities,
                    onClick = { onSelectTest(test.id) }
                )
            }
        }
    }
}

/**
 * Compact, modern, and easily scannable Diagnostic Test Card
 */
@Composable
private fun CompactDiagnosticTestCard(
    test: DiagnosticTestEntity,
    availableFacilities: List<FacilityDiagnosticEntity>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val icon: ImageVector = when {
        test.category.contains("Pathology", ignoreCase = true) || test.name.contains("Blood", ignoreCase = true) -> Icons.Default.Biotech
        test.category.contains("Cardiology", ignoreCase = true) || test.name.contains("ECG", ignoreCase = true) || test.name.contains("Echo", ignoreCase = true) -> Icons.Default.MonitorHeart
        test.category.contains("Radiology", ignoreCase = true) || test.name.contains("X-Ray", ignoreCase = true) || test.name.contains("Scan", ignoreCase = true) -> Icons.Default.CenterFocusStrong
        else -> Icons.Default.Science
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("test_card_${test.id}")
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            // Header Row: Icon + Title/Category + Price/Expand Action
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Leading Icon in soft circle
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE0F2F1),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color(0xFF006D77),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Title and category subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = test.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Text(
                                text = test.category,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF006D77),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                            )
                        }
                        Text(
                            text = "• ${test.turnAroundTime}",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Price and Arrow indicator
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = test.approxPrice,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF00796B)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Expand details",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Test Short Description
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = test.description,
                fontSize = 11.5.sp,
                lineHeight = 15.sp,
                color = Color(0xFF475569)
            )

            // Available Labs / Preparation details (Expandable or quick indicator)
            if (test.prepInstructions.isNotBlank() && isExpanded) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFFFFBEB),
                    border = BorderStroke(0.5.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Prep: ${test.prepInstructions}",
                            fontSize = 11.sp,
                            color = Color(0xFF92400E),
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            // Facilities Section
            if (availableFacilities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))

                if (!isExpanded) {
                    // Compact Lab count indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${availableFacilities.size} hospital labs available",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF006D77)
                        )
                        Text(
                            text = "Tap to view units",
                            fontSize = 10.5.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                } else {
                    // Expanded clean listing
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 6.dp),
                        color = Color(0xFFF1F5F9)
                    )
                    Text(
                        text = "Available Hospital Labs & Diagnostic Units:",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    availableFacilities.forEach { fac ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = fac.hospitalName,
                                fontSize = 11.5.sp,
                                color = Color(0xFF334155),
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFE0F2F1)
                            ) {
                                Text(
                                    text = "${fac.price} • Available Today",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HealthPrimaryLight,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

