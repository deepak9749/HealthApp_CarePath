package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.*

// 1. USER
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole = UserRole.PATIENT,
    val hospitalId: String? = null,
    val healthWorkerId: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

// 2. GUEST_SESSION
@Entity(tableName = "guest_sessions")
data class GuestSessionEntity(
    @PrimaryKey val guestSessionId: String,
    val deviceIdentifier: String,
    val latitude: Double = 22.44335,
    val longitude: Double = 88.41543,
    val locationPermissionGranted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val expiryTime: Long = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L),
    val convertedToUserId: String? = null
)

// 3. SESSION
@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val userId: String?,
    val guestSessionId: String?,
    val token: String,
    val lastActive: Long = System.currentTimeMillis()
)

// 4. HEALTH_PROFILE
@Entity(tableName = "health_profiles")
data class HealthProfileEntity(
    @PrimaryKey val profileId: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val gender: String = "Male",
    val age: Int = 21,
    val bloodGroup: String = "B+ (Positive)",
    val heightCm: Double = 172.0,
    val weightKg: Double = 69.5,
    val isAllergic: String = "No",
    val allergies: String = "Penicillin (Mild Rash)",
    val existingConditions: String = "Mild Hypertension",
    val chronicDiseases: String = "Hypertension (Managed)",
    val emergencyNotes: String = ""
)

// 5. EMERGENCY_CONTACT
@Entity(tableName = "emergency_contacts")
data class EmergencyContactEntity(
    @PrimaryKey val contactId: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val name: String,
    val relationship: String,
    val phone: String,
    val priority: Int = 1
)

// 6. SAVED_HOSPITAL
@Entity(tableName = "saved_hospitals")
data class SavedHospitalEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val hospitalId: String,
    val savedAt: Long = System.currentTimeMillis()
)

// 7. SYMPTOM_CATEGORY
@Entity(tableName = "symptom_categories")
data class SymptomCategoryEntity(
    @PrimaryKey val id: String,
    val bodySystem: String,
    val categoryName: String,
    val iconName: String,
    val description: String
)

// 8. SYMPTOM
@Entity(tableName = "symptoms")
data class SymptomEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val name: String,
    val subcategory: String,
    val description: String,
    val isEmergencyRedFlag: Boolean = false,
    val defaultSeverity: Int = 3
)

// 9. HEALTH_ASSESSMENT
@Entity(tableName = "health_assessments")
data class HealthAssessmentEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val riskLevel: RiskLevel,
    val summary: String,
    val recommendation: String,
    val emergencyWarning: String? = null,
    val recommendedSpecialization: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// 10. ASSESSMENT_SYMPTOM
@Entity(tableName = "assessment_symptoms")
data class AssessmentSymptomEntity(
    @PrimaryKey val id: String,
    val assessmentId: String,
    val symptomId: String,
    val symptomName: String,
    val severity: Int,
    val duration: String,
    val notes: String = ""
)

// 11. HOSPITAL
@Entity(tableName = "hospitals")
data class HospitalEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: HospitalType,
    val address: String,
    val area: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val emergencyPhone: String,
    val emergencyAvailable: Boolean = true,
    val operatingStatus: String = "24x7 Open",
    val isVerified: Boolean = true,
    val consultationFeeEstimate: String = "Free / ₹100 - ₹800",
    val hasAmbulanceOnSite: Boolean = true,
    val dataSourceType: String = "Verified Facility Registry",
    val lastUpdated: Long = System.currentTimeMillis()
)

// 12. HOSPITAL_DEPARTMENT
@Entity(tableName = "hospital_departments")
data class HospitalDepartmentEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val name: String,
    val headDoctor: String,
    val phone: String,
    val description: String
)

// 13. SPECIALIZATION
@Entity(tableName = "specializations")
data class SpecializationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val iconName: String = "medical"
)

// 14. HOSPITAL_SPECIALIZATION
@Entity(tableName = "hospital_specializations")
data class HospitalSpecializationEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val specializationName: String,
    val doctorCount: Int,
    val opdTimings: String = "9:00 AM - 4:00 PM"
)

// 15. HOSPITAL_API
@Entity(tableName = "hospital_apis")
data class HospitalApiEntity(
    @PrimaryKey val apiId: String,
    val hospitalId: String,
    val endpoint: String,
    val version: String = "v2.1",
    val authType: String = "OAuth2 / API Key",
    val status: String = "ACTIVE",
    val lastSync: Long = System.currentTimeMillis()
)

// 16. BED
@Entity(tableName = "beds")
data class BedEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val bedTypeName: String,
    val wardNumber: String,
    val status: String = "AVAILABLE"
)

// 17. BED_TYPE
@Entity(tableName = "bed_types")
data class BedTypeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String
)

// 18. BED_AVAILABILITY
@Entity(tableName = "bed_availabilities")
data class BedAvailabilityEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val bedTypeName: String, // ICU, Emergency, General, HDU, NICU
    val total: Int,
    val available: Int,
    val occupied: Int,
    val reserved: Int,
    val isApiSynced: Boolean = true,
    val freshnessLabel: String = "Updated 5 mins ago",
    val lastUpdated: Long = System.currentTimeMillis()
)

// 19. API_SYNC_LOG
@Entity(tableName = "api_sync_logs")
data class ApiSyncLogEntity(
    @PrimaryKey val id: String,
    val apiId: String,
    val hospitalName: String,
    val syncTime: Long = System.currentTimeMillis(),
    val status: SyncStatus = SyncStatus.SUCCESS,
    val recordsUpdated: Int = 12,
    val errorMessage: String? = null
)

// 20. HOSPITAL_SEARCH
@Entity(tableName = "hospital_searches")
data class HospitalSearchEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val query: String,
    val latitude: Double,
    val longitude: Double,
    val filterType: String = "ALL",
    val timestamp: Long = System.currentTimeMillis()
)

// 21. HOSPITAL_SEARCH_RESULT
@Entity(tableName = "hospital_search_results")
data class HospitalSearchResultEntity(
    @PrimaryKey val id: String,
    val searchId: String,
    val hospitalId: String,
    val distanceKm: Double
)

// 22. HOSPITAL_SELECTION
@Entity(tableName = "hospital_selections")
data class HospitalSelectionEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val hospitalId: String,
    val actionType: String, // VIEW, CALL, NAVIGATE, BOOK
    val timestamp: Long = System.currentTimeMillis()
)

// 23. NAVIGATION_REQUEST
@Entity(tableName = "navigation_requests")
data class NavigationRequestEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val hospitalId: String,
    val originLat: Double,
    val originLng: Double,
    val destLat: Double,
    val destLng: Double,
    val timestamp: Long = System.currentTimeMillis()
)

// 24. EMERGENCY_REQUEST
@Entity(tableName = "emergency_requests")
data class EmergencyRequestEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val latitude: Double,
    val longitude: Double,
    val emergencyType: String,
    val status: String = "DISPATCHED",
    val timestamp: Long = System.currentTimeMillis()
)

// 25. AMBULANCE_PROVIDER
@Entity(tableName = "ambulance_providers")
data class AmbulanceProviderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val emergencyHelpline: String = "108",
    val coverageArea: String = "South 24 Parganas & Kolkata",
    val isVerified: Boolean = true
)

// 26. AMBULANCE
@Entity(tableName = "ambulances")
data class AmbulanceEntity(
    @PrimaryKey val id: String,
    val providerId: String,
    val providerName: String,
    val vehicleNumber: String,
    val type: String = "Advanced Life Support (ALS)",
    val driverName: String,
    val driverPhone: String,
    val currentLat: Double,
    val currentLng: Double,
    val status: String = "STANDBY"
)

// 27. AMBULANCE_REQUEST
@Entity(tableName = "ambulance_requests")
data class AmbulanceRequestEntity(
    @PrimaryKey val id: String,
    val emergencyRequestId: String? = null,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val ambulanceId: String,
    val providerName: String,
    val vehicleNumber: String,
    val pickupAddress: String,
    val pickupLat: Double,
    val pickupLng: Double,
    val destinationHospitalId: String?,
    val destinationName: String,
    val status: AmbulanceStatus = AmbulanceStatus.REQUESTED,
    val etaMinutes: Int = 12,
    val requestTime: Long = System.currentTimeMillis()
)

// 28. CHAT_SESSION
@Entity(tableName = "chat_sessions")
data class ChatSessionEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val title: String = "Healthcare Inquiry",
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis()
)

// 29. CHAT_MESSAGE
@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val chatSessionId: String,
    val sender: ChatSender,
    val message: String,
    val actionType: String? = null, // e.g., "REQUEST_AMBULANCE", "SHOW_ICU", "BOOK_APPOINTMENT", "NAVIGATE_HOSPITAL"
    val actionPayload: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// 30. MEDICAL_REPORT
@Entity(tableName = "medical_reports")
data class MedicalReportEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val hospitalId: String? = null,
    val hospitalName: String = "Diagnostic Center",
    val reportType: String = "Complete Blood Count (CBC)",
    val fileUri: String = "",
    val fileName: String = "CBC_Report_Aug2026.pdf",
    val reportDate: String = "2026-08-22",
    val aiAnalysis: String? = null,
    val verificationStatus: String = "Verified by Lab",
    val isPrivate: Boolean = true,
    val uploadedAt: Long = System.currentTimeMillis()
)

// 31. PRESCRIPTION
@Entity(tableName = "prescriptions")
data class PrescriptionEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val doctorName: String,
    val hospitalName: String,
    val date: String,
    val diagnosis: String,
    val instructions: String,
    val fileUri: String = "",
    val uploadedAt: Long = System.currentTimeMillis()
)

// 32. MEDICINE
@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey val id: String,
    val name: String,
    val genericName: String,
    val purpose: String,
    val usageInfo: String,
    val precautions: String = "Take with water after meals. Do not exceed prescribed dosage.",
    val category: String = "General",
    val standardDosage: String = "500 mg"
)

// 33. PRESCRIPTION_MEDICINE
@Entity(tableName = "prescription_medicines")
data class PrescriptionMedicineEntity(
    @PrimaryKey val id: String,
    val prescriptionId: String,
    val medicineName: String,
    val dosage: String,
    val frequency: String,
    val durationDays: Int,
    val instructions: String
)

// 34. AI_ANALYSIS
@Entity(tableName = "ai_analyses")
data class AiAnalysisEntity(
    @PrimaryKey val id: String,
    val entityType: String, // REPORT, PRESCRIPTION, SYMPTOMS
    val entityId: String,
    val summary: String,
    val keyFindings: String,
    val terminologyExplanation: String,
    val abnormalFlags: String,
    val disclaimer: String = "AI-generated explanation — not a medical diagnosis. Please consult a qualified physician.",
    val timestamp: Long = System.currentTimeMillis()
)

// EXTENSIONS FOR COMPLETE HEALTHCARE ECOSYSTEM

@Entity(tableName = "doctors")
data class DoctorEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val hospitalName: String,
    val name: String,
    val qualification: String,
    val specialization: String,
    val experienceYears: Int,
    val consultationFee: String,
    val opdDays: String,
    val opdTimings: String,
    val availableForTeleconsult: Boolean = true,
    val rating: Double = 4.8
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val patientName: String,
    val doctorId: String,
    val doctorName: String,
    val hospitalId: String,
    val hospitalName: String,
    val specialization: String,
    val appointmentDate: String,
    val timeSlot: String,
    val type: ConsultationType = ConsultationType.IN_PERSON,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED,
    val tokenNumber: String = "A-27",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "queues")
data class QueueEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val doctorId: String,
    val currentToken: String = "A-21",
    val myToken: String = "A-27",
    val patientsAhead: Int = 6,
    val estimatedWaitMinutes: Int = 35,
    val status: String = "ONGOING",
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "referrals")
data class ReferralEntity(
    @PrimaryKey val id: String,
    val patientName: String,
    val patientAge: Int,
    val patientGender: String,
    val fromFacility: String,
    val toHospitalId: String,
    val toHospitalName: String,
    val specializationRequired: String,
    val clinicalSummary: String,
    val urgency: UrgencyLevel = UrgencyLevel.URGENT,
    val status: ReferralStatus = ReferralStatus.IN_TRANSIT,
    val createdByWorker: String = "Sunita Das (ASHA Community Lead)",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "diagnostic_tests")
data class DiagnosticTestEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String, // Pathology, Radiology, Cardiology
    val description: String,
    val prepInstructions: String,
    val approxPrice: String,
    val turnAroundTime: String
)

@Entity(tableName = "facility_diagnostics")
data class FacilityDiagnosticEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val hospitalName: String,
    val testName: String,
    val category: String,
    val isAvailable: Boolean = true,
    val price: String = "₹250",
    val isHomeCollection: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "medicine_availabilities")
data class MedicineAvailabilityEntity(
    @PrimaryKey val id: String,
    val hospitalId: String,
    val hospitalName: String,
    val medicineName: String,
    val genericName: String,
    val pharmacyName: String,
    val stockStatus: StockStatus = StockStatus.IN_STOCK,
    val quantity: Int = 120,
    val price: String = "₹45.00",
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "follow_ups")
data class FollowUpEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val patientName: String,
    val riskLevel: RiskLevel,
    val purpose: String,
    val hospitalName: String,
    val doctorName: String,
    val dueDate: String,
    val status: FollowUpStatus = FollowUpStatus.PENDING,
    val notes: String = "",
    val reminderEnabled: Boolean = true
)

@Entity(tableName = "maternal_health")
data class MaternalHealthEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val motherName: String,
    val gestationalWeeks: Int,
    val expectedDueDate: String,
    val highRiskFactors: String = "None",
    val lastCheckupDate: String,
    val nextCheckupDate: String,
    val bloodPressure: String = "118/76",
    val hemoglobin: String = "11.8 g/dL",
    val notes: String = "Regular IFA supplementation advised."
)

@Entity(tableName = "child_health")
data class ChildHealthEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val childName: String,
    val dob: String,
    val gender: String,
    val birthWeightKg: Double = 3.1,
    val currentWeightKg: Double = 7.4,
    val vaccinationsDue: String = "Pentavalent-3, IPV-2",
    val lastVaccinationDate: String = "2026-06-15",
    val notes: String = "Growth milestones on schedule."
)

@Entity(tableName = "chronic_cares")
data class ChronicCareEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val guestSessionId: String? = null,
    val conditionName: String, // Type 2 Diabetes, Hypertension, Asthma
    val diagnosisDate: String,
    val currentMedications: String,
    val targetMetrics: String,
    val lastReading: String,
    val lastCheckupDate: String
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val title: String,
    val message: String,
    val type: String = "APPOINTMENT",
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "diagnostic_bookings")
data class DiagnosticBookingEntity(
    @PrimaryKey val bookingId: String,
    val userId: String? = null,
    val patientName: String,
    val testId: String,
    val testName: String,
    val facilityId: String,
    val facilityName: String,
    val facilityType: String, // Government, Private
    val phoneNumber: String,
    val bookingDate: String,
    val prescriptionUri: String? = null,
    val prescriptionFileName: String? = null,
    val requestCreatedAt: Long = System.currentTimeMillis(),
    val status: DiagnosticBookingStatus = DiagnosticBookingStatus.WAITING_FOR_CONFIRMATION
)

