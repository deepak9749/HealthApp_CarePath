package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.local.*
import com.example.data.model.*
import com.example.data.remote.CarePathBackendClient
import com.example.domain.AIActionEngine
import com.example.domain.AIResponse
import com.example.domain.DistanceCalculator
import com.example.domain.TriageEngine
import com.example.domain.TriageInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

data class HospitalWithDistance(
    val hospital: HospitalEntity,
    val distanceKm: Double,
    val distanceFormatted: String,
    val bedAvailabilities: List<BedAvailabilityEntity> = emptyList(),
    val totalIcuAvailable: Int = 0,
    val totalEmergencyAvailable: Int = 0,
    val totalGeneralAvailable: Int = 0
)

data class HospitalFilterState(
    val query: String = "",
    val type: HospitalType? = null,
    val specialization: String? = null,
    val requireIcu: Boolean = false,
    val requireEmergencyBed: Boolean = false,
    val maxDistanceKm: Double = 50.0
)

class SmartHealthRepository(
    private val dao: SmartHealthDao,
    context: Context? = null
) {
    private val prefs =
        context?.getSharedPreferences("smart_health_prefs", Context.MODE_PRIVATE)

    // Current user location
    private val _currentLocation =
        MutableStateFlow(Pair(22.44335, 88.41543))
    val currentLocation: StateFlow<Pair<Double, Double>> =
        _currentLocation.asStateFlow()

    private val _currentLocationName =
        MutableStateFlow("Sonarpur / Narendrapur, Kolkata")
    val currentLocationName: StateFlow<String> =
        _currentLocationName.asStateFlow()

    private val _activeRole =
        MutableStateFlow(UserRole.GUEST)
    val activeRole: StateFlow<UserRole> =
        _activeRole.asStateFlow()

    private val _currentLanguage =
        MutableStateFlow(
            prefs?.getString("selected_language", "EN") ?: "EN"
        )
    val currentLanguage: StateFlow<String> =
        _currentLanguage.asStateFlow()

    private val _filterState =
        MutableStateFlow(HospitalFilterState())
    val filterState: StateFlow<HospitalFilterState> =
        _filterState.asStateFlow()

    val currentUser: Flow<UserEntity?> =
        dao.getCurrentUser()
    val latestGuestSession: Flow<GuestSessionEntity?> =
        dao.getLatestGuestSession()
    val healthProfile: Flow<HealthProfileEntity?> =
        dao.getHealthProfile()
    val emergencyContacts: Flow<List<EmergencyContactEntity>> =
        dao.getAllEmergencyContacts()
    val savedHospitals: Flow<List<SavedHospitalEntity>> =
        dao.getSavedHospitals()
    val symptomCategories: Flow<List<SymptomCategoryEntity>> =
        dao.getAllSymptomCategories()
    val allSymptoms: Flow<List<SymptomEntity>> =
        dao.getAllSymptoms()
    val healthAssessments: Flow<List<HealthAssessmentEntity>> =
        dao.getAllAssessments()
    val allHospitals: Flow<List<HospitalEntity>> =
        dao.getAllHospitals()
    val allBedAvailabilities: Flow<List<BedAvailabilityEntity>> =
        dao.getAllBedAvailabilities()
    val ambulances: Flow<List<AmbulanceEntity>> =
        dao.getAllAmbulances()
    val activeAmbulanceRequest: Flow<AmbulanceRequestEntity?> =
        dao.getActiveAmbulanceRequest()
    val allAmbulanceRequests: Flow<List<AmbulanceRequestEntity>> =
        dao.getAllAmbulanceRequests()
    val chatMessages: Flow<List<ChatMessageEntity>> =
        dao.getAllChatMessages()
    val medicalReports: Flow<List<MedicalReportEntity>> =
        dao.getAllMedicalReports()
    val prescriptions: Flow<List<PrescriptionEntity>> =
        dao.getAllPrescriptions()
    val medicines: Flow<List<MedicineEntity>> =
        dao.getAllMedicines()
    val medicineAvailabilities: Flow<List<MedicineAvailabilityEntity>> =
        dao.getAllMedicineAvailabilities()
    val diagnosticTests: Flow<List<DiagnosticTestEntity>> =
        dao.getAllDiagnosticTests()
    val facilityDiagnostics: Flow<List<FacilityDiagnosticEntity>> =
        dao.getAllFacilityDiagnostics()
    val diagnosticBookings: Flow<List<DiagnosticBookingEntity>> =
        dao.getAllDiagnosticBookings()

    fun getLatestBookingForTest(testId: String): Flow<DiagnosticBookingEntity?> =
        dao.getLatestBookingForTest(testId)
    val doctors: Flow<List<DoctorEntity>> =
        dao.getAllDoctors()
    val appointments: Flow<List<AppointmentEntity>> =
        dao.getAllAppointments()
    val liveQueue: Flow<QueueEntity?> =
        dao.getLiveQueue()
    val referrals: Flow<List<ReferralEntity>> =
        dao.getAllReferrals()
    val followUps: Flow<List<FollowUpEntity>> =
        dao.getAllFollowUps()
    val maternalHealth: Flow<MaternalHealthEntity?> =
        dao.getMaternalHealth()
    val childHealth: Flow<ChildHealthEntity?> =
        dao.getChildHealth()
    val chronicCare: Flow<List<ChronicCareEntity>> =
        dao.getAllChronicCare()
    val apiSyncLogs: Flow<List<ApiSyncLogEntity>> =
        dao.getAllApiSyncLogs()

    val filteredHospitalsWithDistance: Flow<List<HospitalWithDistance>> =
        combine(
            dao.getAllHospitals(),
            dao.getAllBedAvailabilities(),
            _currentLocation,
            _filterState
        ) { hospitals, beds, location, filter ->
            val userLat = location.first
            val userLng = location.second

            hospitals
                .map { hosp ->
                    val dist =
                        DistanceCalculator.calculateDistanceKm(
                            userLat,
                            userLng,
                            hosp.latitude,
                            hosp.longitude
                        )
                    val hospBeds =
                        beds.filter {
                            it.hospitalId == hosp.id
                        }
                    val icuAvail =
                        hospBeds
                            .find {
                                it.bedTypeName.contains(
                                    "ICU",
                                    ignoreCase = true
                                )
                            }
                            ?.available ?: 0
                    val emAvail =
                        hospBeds
                            .find {
                                it.bedTypeName.contains(
                                    "Emergency",
                                    ignoreCase = true
                                )
                            }
                            ?.available ?: 0
                    val genAvail =
                        hospBeds
                            .find {
                                it.bedTypeName.contains(
                                    "General",
                                    ignoreCase = true
                                )
                            }
                            ?.available ?: 0

                    HospitalWithDistance(
                        hospital = hosp,
                        distanceKm = dist,
                        distanceFormatted =
                            DistanceCalculator.formatDistance(dist),
                        bedAvailabilities = hospBeds,
                        totalIcuAvailable = icuAvail,
                        totalEmergencyAvailable = emAvail,
                        totalGeneralAvailable = genAvail
                    )
                }
                .filter { item ->
                    val matchesQuery =
                        filter.query.isBlank() ||
                                item.hospital.name.contains(
                                    filter.query,
                                    ignoreCase = true
                                ) ||
                                item.hospital.area.contains(
                                    filter.query,
                                    ignoreCase = true
                                ) ||
                                item.hospital.address.contains(
                                    filter.query,
                                    ignoreCase = true
                                )
                    val matchesType =
                        filter.type == null ||
                                item.hospital.type == filter.type
                    val matchesIcu =
                        !filter.requireIcu ||
                                item.totalIcuAvailable > 0
                    val matchesEm =
                        !filter.requireEmergencyBed ||
                                item.totalEmergencyAvailable > 0
                    val matchesDist =
                        item.distanceKm <= filter.maxDistanceKm

                    matchesQuery &&
                            matchesType &&
                            matchesIcu &&
                            matchesEm &&
                            matchesDist
                }
                .sortedBy { it.distanceKm }
                .take(30)
        }

    fun updateLocation(lat: Double, lng: Double, locationName: String) {
        _currentLocation.value = Pair(lat, lng)
        _currentLocationName.value = locationName
    }

    fun updateFilter(filter: HospitalFilterState) {
        _filterState.value = filter
    }

    fun resetFilter() {
        _filterState.value = HospitalFilterState()
    }

    fun setRole(role: UserRole) {
        _activeRole.value = role
    }

    fun setLanguage(lang: String) {
        prefs?.edit()
            ?.putString("selected_language", lang)
            ?.apply()
        _currentLanguage.value = lang
    }

    suspend fun performTriage(input: TriageInput): HealthAssessmentEntity =
        withContext(Dispatchers.IO) {
            val assessment = TriageEngine.evaluateTriage(input, "guest_active")
            dao.insertHealthAssessment(assessment)
            assessment
        }

    suspend fun saveHospital(hospitalId: String) = withContext(Dispatchers.IO) {
        dao.insertSavedHospital(
            SavedHospitalEntity(
                id = "saved_${System.currentTimeMillis()}",
                userId = "user_default",
                hospitalId = hospitalId
            )
        )
    }

    suspend fun removeSavedHospital(hospitalId: String) = withContext(Dispatchers.IO) {
        dao.deleteSavedHospital(hospitalId)
    }

    suspend fun requestAmbulance(
        pickupAddress: String,
        destinationHospitalId: String?,
        destinationName: String,
        vehicleType: String = "ALS - ICU Mobile Unit"
    ): AmbulanceRequestEntity = withContext(Dispatchers.IO) {
        val request = AmbulanceRequestEntity(
            id = "amb_req_${System.currentTimeMillis()}",
            ambulanceId = "amb_1",
            providerName = "WB EMRI 108 Emergency Service",
            vehicleNumber = "WB-04-1081",
            pickupAddress = pickupAddress,
            pickupLat = _currentLocation.value.first,
            pickupLng = _currentLocation.value.second,
            destinationHospitalId = destinationHospitalId,
            destinationName = destinationName,
            status = AmbulanceStatus.DISPATCHED,
            etaMinutes = 9
        )
        dao.insertAmbulanceRequest(request)
        dao.insertApiSyncLog(
            ApiSyncLogEntity(
                id = "sync_amb_${System.currentTimeMillis()}",
                apiId = "api_ambulance_108",
                hospitalName = destinationName,
                status = SyncStatus.SUCCESS,
                recordsUpdated = 1
            )
        )
        request
    }

    suspend fun updateAmbulanceStatus(request: AmbulanceRequestEntity, newStatus: AmbulanceStatus) = withContext(Dispatchers.IO) {
        dao.updateAmbulanceRequest(request.copy(status = newStatus))
    }

    // =========================================================
    // CAREPATH AI CHAT
    // =========================================================
    suspend fun sendChatMessage(userMessage: String): AIResponse = withContext(Dispatchers.IO) {
        val chatSessionId = "session_main"
        Log.i("CarePathAI", "==================================================")
        Log.i("CarePathAI", "[CarePathAI] sendChatMessage() START")
        Log.i("CarePathAI", "[CarePathAI] User message: \"$userMessage\"")

        // 1. Save user message to Room database
        try {
            val userMsgEntity = ChatMessageEntity(
                id = "msg_user_${System.currentTimeMillis()}",
                chatSessionId = chatSessionId,
                sender = ChatSender.USER,
                message = userMessage
            )
            dao.insertChatMessage(userMsgEntity)
            Log.i("CarePathAI", "[CarePathAI] User message saved to Room")
        } catch (e: Exception) {
            Log.e("CarePathAI", "[CarePathAI] Room user-message save error: ${e.message}", e)
        }

        // 2. Language
        val currentLang = _currentLanguage.value
        Log.i("CarePathAI", "[CarePathAI] Active language: $currentLang")

        // 3. Direct CarePath Feature Action check
        val directResponse = try {
            if (AIActionEngine.isCarePathFeatureRequest(userMessage)) {
                Log.i("CarePathAI", "[CarePathAI] Explicit CarePath feature request recognized")
                AIActionEngine.processDirectFeatureQuery(userMessage, currentLang)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("CarePathAI", "[CarePathAI] AIActionEngine error: ${e.message}", e)
            null
        }

        val aiResponse: AIResponse
        if (directResponse != null && directResponse.actionType != null) {
            Log.i("CarePathAI", "[CarePathAI] Direct UI action dispatched: ${directResponse.actionType}")
            aiResponse = directResponse
        } else {
            // 4. Send request to Render Groq backend
            Log.i("CarePathAI", "[CarePathAI] Sending request to Render Groq backend...")
            val historyList = listOf("user" to userMessage)
            val remoteResponse = try {
                CarePathBackendClient.queryMedicalAI(
                    conversationHistory = historyList,
                    language = currentLang
                )
            } catch (e: Exception) {
                Log.e("CarePathAI", "[CarePathAI] queryMedicalAI exception: ${e.message}", e)
                null
            }

            if (remoteResponse != null && remoteResponse.replyText.isNotBlank()) {
                Log.i("CarePathAI", "[CarePathAI] AI response successfully received from Render backend!")
                Log.i("CarePathAI", "[CarePathAI] Reply Preview: ${remoteResponse.replyText.take(150)}")
                aiResponse = remoteResponse
            } else {
                val errorDetail = CarePathBackendClient.lastConnectionError
                Log.w("CarePathAI", "[CarePathAI] Render request returned no response. Last error: $errorDetail")

                val hasEmergency = try {
                    AIActionEngine.hasEmergencySymptoms(userMessage)
                } catch (e: Exception) {
                    false
                }

                if (hasEmergency) {
                    aiResponse = AIActionEngine.getFallbackMedicalResponse(currentLang, true)
                } else {
                    val errMsg = when (currentLang.uppercase()) {
                        "HI" -> "क्षमा करें, इस समय CarePath AI सेवा से संपर्क नहीं हो पा रहा है। कृपया कुछ समय बाद पुनः प्रयास करें।"
                        "BN" -> "দুঃখিত, এই মুহূর্তে CarePath AI পরিষেবার সাথে সংযোগ স্থাপন করা সম্ভব হচ্ছে না। অনুগ্রহ করে কিছুক্ষণ পরে আবার চেষ্টা করুন।"
                        else -> "Sorry, I couldn't connect to the CarePath AI service right now. Please try again."
                    }
                    aiResponse = AIResponse(
                        replyText = errMsg,
                        actionType = null,
                        actionLabel = null,
                        actionPayload = null
                    )
                }
            }
        }

        // 5. Save AI response to Room database
        try {
            val aiMsgEntity = ChatMessageEntity(
                id = "msg_ai_${System.currentTimeMillis()}",
                chatSessionId = chatSessionId,
                sender = ChatSender.AI,
                message = aiResponse.replyText,
                actionType = aiResponse.actionType,
                actionPayload = aiResponse.actionPayload
            )
            dao.insertChatMessage(aiMsgEntity)
            Log.i("CarePathAI", "[CarePathAI] AI message saved to Room")
        } catch (e: Exception) {
            Log.e("CarePathAI", "[CarePathAI] Room AI-message save error: ${e.message}", e)
        }

        Log.i("CarePathAI", "[CarePathAI] sendChatMessage() END")
        Log.i("CarePathAI", "==================================================")
        return@withContext aiResponse
    }

    suspend fun bookAppointment(
        patientName: String,
        doctor: DoctorEntity,
        date: String,
        timeSlot: String,
        type: ConsultationType,
        notes: String
    ): AppointmentEntity = withContext(Dispatchers.IO) {
        val appt = AppointmentEntity(
            id = "appt_${System.currentTimeMillis()}",
            patientName = patientName,
            doctorId = doctor.id,
            doctorName = doctor.name,
            hospitalId = doctor.hospitalId,
            hospitalName = doctor.hospitalName,
            specialization = doctor.specialization,
            appointmentDate = date,
            timeSlot = timeSlot,
            type = type,
            status = AppointmentStatus.CONFIRMED,
            tokenNumber = "A-${(20..45).random()}",
            notes = notes
        )
        dao.insertAppointment(appt)
        appt
    }

    suspend fun addMedicalReport(
        hospitalName: String,
        reportType: String,
        fileName: String,
        aiAnalysis: String,
        fileUri: String = ""
    ) = withContext(Dispatchers.IO) {
        val report = MedicalReportEntity(
            id = "rep_${System.currentTimeMillis()}",
            hospitalName = hospitalName,
            reportType = reportType,
            fileName = fileName,
            fileUri = fileUri,
            reportDate = "Today",
            aiAnalysis = aiAnalysis
        )
        dao.insertMedicalReport(report)
    }

    suspend fun addPrescription(doctorName: String, hospitalName: String, diagnosis: String, instructions: String) = withContext(Dispatchers.IO) {
        val rx = PrescriptionEntity(
            id = "rx_${System.currentTimeMillis()}",
            doctorName = doctorName,
            hospitalName = hospitalName,
            date = "Today",
            diagnosis = diagnosis,
            instructions = instructions
        )
        dao.insertPrescription(rx)
    }

    suspend fun createReferral(
        patientName: String,
        patientAge: Int,
        patientGender: String,
        fromFacility: String,
        toHospital: HospitalEntity,
        specialization: String,
        clinicalSummary: String,
        urgency: UrgencyLevel,
        healthWorker: String
    ): ReferralEntity = withContext(Dispatchers.IO) {
        val ref = ReferralEntity(
            id = "ref_${System.currentTimeMillis()}",
            patientName = patientName,
            patientAge = patientAge,
            patientGender = patientGender,
            fromFacility = fromFacility,
            toHospitalId = toHospital.id,
            toHospitalName = toHospital.name,
            specializationRequired = specialization,
            clinicalSummary = clinicalSummary,
            urgency = urgency,
            status = ReferralStatus.SENT,
            createdByWorker = healthWorker
        )
        dao.insertReferral(ref)
        ref
    }

    suspend fun updateReferralStatus(referral: ReferralEntity, newStatus: ReferralStatus) = withContext(Dispatchers.IO) {
        dao.updateReferral(referral.copy(status = newStatus, updatedAt = System.currentTimeMillis()))
    }

    suspend fun updateBedAvailability(bed: BedAvailabilityEntity, available: Int) = withContext(Dispatchers.IO) {
        dao.updateBedAvailability(bed.copy(available = available, lastUpdated = System.currentTimeMillis()))
    }

    suspend fun registerUser(name: String, email: String, phone: String, mergeGuest: Boolean): UserEntity = withContext(Dispatchers.IO) {
        val user = UserEntity(
            id = "user_${System.currentTimeMillis()}",
            name = name,
            email = email,
            phone = phone,
            role = UserRole.PATIENT
        )
        dao.insertUser(user)
        _activeRole.value = UserRole.PATIENT
        user
    }

    suspend fun updateHealthProfile(profile: HealthProfileEntity) = withContext(Dispatchers.IO) {
        dao.insertHealthProfile(profile)
    }

    suspend fun addEmergencyContact(name: String, relation: String, phone: String) = withContext(Dispatchers.IO) {
        val contact = EmergencyContactEntity(
            contactId = "ec_${System.currentTimeMillis()}",
            name = name,
            relationship = relation,
            phone = phone,
            priority = 1
        )
        dao.insertEmergencyContact(contact)
    }

    suspend fun deleteEmergencyContact(contact: EmergencyContactEntity) = withContext(Dispatchers.IO) {
        dao.deleteEmergencyContact(contact)
    }

    suspend fun addFollowUp(patientName: String, riskLevel: RiskLevel, purpose: String, hospitalName: String, doctorName: String, dueDate: String) = withContext(Dispatchers.IO) {
        val followUp = FollowUpEntity(
            id = "fu_${System.currentTimeMillis()}",
            patientName = patientName,
            riskLevel = riskLevel,
            purpose = purpose,
            hospitalName = hospitalName,
            doctorName = doctorName,
            dueDate = dueDate
        )
        dao.insertFollowUp(followUp)
    }

    suspend fun createDiagnosticBooking(booking: DiagnosticBookingEntity) = withContext(Dispatchers.IO) {
        dao.insertDiagnosticBooking(booking)
    }

    suspend fun updateDiagnosticBookingStatus(bookingId: String, newStatus: DiagnosticBookingStatus) = withContext(Dispatchers.IO) {
        val all = dao.getAllDiagnosticBookings().first()
        val found = all.find { it.bookingId == bookingId }
        if (found != null) {
            dao.updateDiagnosticBooking(found.copy(status = newStatus))
        }
    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        dao.clearUsers()
        _activeRole.value = UserRole.PATIENT
    }
}

