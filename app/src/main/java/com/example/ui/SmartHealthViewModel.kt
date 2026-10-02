package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.SmartHealthApp
import com.example.data.local.*
import com.example.data.model.*
import com.example.data.repository.HospitalFilterState
import com.example.data.repository.HospitalWithDistance
import com.example.data.repository.SmartHealthRepository
import com.example.domain.AIResponse
import com.example.domain.TriageInput
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SmartHealthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SmartHealthRepository =
        (application as SmartHealthApp).repository

    // State flows from repository
    val currentLocation = repository.currentLocation
    val currentLocationName = repository.currentLocationName
    val activeRole = repository.activeRole
    val currentLanguage = repository.currentLanguage
    val filterState = repository.filterState

    val currentUser = repository.currentUser.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val latestGuestSession = repository.latestGuestSession.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val healthProfile = repository.healthProfile.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val emergencyContacts = repository.emergencyContacts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val savedHospitals = repository.savedHospitals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val symptomCategories = repository.symptomCategories.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allSymptoms = repository.allSymptoms.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val healthAssessments = repository.healthAssessments.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredHospitalsWithDistance = repository.filteredHospitalsWithDistance.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allHospitals = repository.allHospitals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allBedAvailabilities = repository.allBedAvailabilities.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ambulances = repository.ambulances.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val activeAmbulanceRequest = repository.activeAmbulanceRequest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val allAmbulanceRequests = repository.allAmbulanceRequests.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chatMessages = repository.chatMessages.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val medicalReports = repository.medicalReports.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val prescriptions = repository.prescriptions.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val medicines = repository.medicines.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val medicineAvailabilities = repository.medicineAvailabilities.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val diagnosticTests = repository.diagnosticTests.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val facilityDiagnostics = repository.facilityDiagnostics.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val diagnosticBookings = repository.diagnosticBookings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getLatestBookingForTest(testId: String): Flow<DiagnosticBookingEntity?> =
        repository.getLatestBookingForTest(testId)

    val doctors = repository.doctors.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val appointments = repository.appointments.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val liveQueue = repository.liveQueue.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val referrals = repository.referrals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val followUps = repository.followUps.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val maternalHealth = repository.maternalHealth.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val childHealth = repository.childHealth.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val chronicCare = repository.chronicCare.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val apiSyncLogs = repository.apiSyncLogs.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Triage State
    private val _selectedSymptoms = MutableStateFlow<List<SymptomEntity>>(emptyList())
    val selectedSymptoms: StateFlow<List<SymptomEntity>> = _selectedSymptoms.asStateFlow()

    private val _currentSeverity = MutableStateFlow(5)
    val currentSeverity: StateFlow<Int> = _currentSeverity.asStateFlow()

    private val _symptomDuration = MutableStateFlow("2 days")
    val symptomDuration: StateFlow<String> = _symptomDuration.asStateFlow()

    private val _latestAssessmentResult = MutableStateFlow<HealthAssessmentEntity?>(null)
    val latestAssessmentResult: StateFlow<HealthAssessmentEntity?> = _latestAssessmentResult.asStateFlow()

    private val _isAIGenerating = MutableStateFlow(false)
    val isAIGenerating: StateFlow<Boolean> = _isAIGenerating.asStateFlow()

    // UI Dialog & BottomSheet Visibility
    private val _isAIChatOpen = MutableStateFlow(false)
    val isAIChatOpen: StateFlow<Boolean> = _isAIChatOpen.asStateFlow()

    private val _isLoginDialogOpen = MutableStateFlow(false)
    val isLoginDialogOpen: StateFlow<Boolean> = _isLoginDialogOpen.asStateFlow()

    private val _isLocationDialogOpen = MutableStateFlow(false)
    val isLocationDialogOpen: StateFlow<Boolean> = _isLocationDialogOpen.asStateFlow()

    private val _isRoleSelectorOpen = MutableStateFlow(false)
    val isRoleSelectorOpen: StateFlow<Boolean> = _isRoleSelectorOpen.asStateFlow()

    private val _isLanguageDialogOpen = MutableStateFlow(false)
    val isLanguageDialogOpen: StateFlow<Boolean> = _isLanguageDialogOpen.asStateFlow()

    // Toast/Snackbar message
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    fun showToast(msg: String) {
        _userMessage.value = msg
    }

    fun clearToast() {
        _userMessage.value = null
    }

    fun setAIChatOpen(isOpen: Boolean) {
        _isAIChatOpen.value = isOpen
    }

    fun setLoginDialogOpen(isOpen: Boolean) {
        _isLoginDialogOpen.value = isOpen
    }

    fun setLocationDialogOpen(isOpen: Boolean) {
        _isLocationDialogOpen.value = isOpen
    }

    fun setRoleSelectorOpen(isOpen: Boolean) {
        _isRoleSelectorOpen.value = isOpen
    }

    fun setLanguageDialogOpen(isOpen: Boolean) {
        _isLanguageDialogOpen.value = isOpen
    }

    fun setRole(role: UserRole) {
        repository.setRole(role)
        showToast("Switched role to: ${role.name.replace("_", " ")}")
    }

    fun setLanguage(lang: String) {
        repository.setLanguage(lang)
        val msg = when (lang) {
            "HI" -> "भाषा बदलकर हिंदी कर दी गई"
            "BN" -> "ভাষা পরিবর্তন করে বাংলা করা হয়েছে"
            else -> "Language changed to English"
        }
        showToast(msg)
    }

    fun updateLocation(lat: Double, lng: Double, name: String) {
        repository.updateLocation(lat, lng, name)
        showToast("Location updated: $name")
    }

    fun updateSearchQuery(query: String) {
        repository.updateFilter(filterState.value.copy(query = query))
    }

    fun updateFilter(filter: HospitalFilterState) {
        repository.updateFilter(filter)
    }

    fun resetFilters() {
        repository.resetFilter()
    }

    fun toggleSymptomSelection(symptom: SymptomEntity) {
        val current = _selectedSymptoms.value.toMutableList()
        if (current.any { it.id == symptom.id }) {
            current.removeAll { it.id == symptom.id }
        } else {
            current.add(symptom)
        }
        _selectedSymptoms.value = current
    }

    fun setSeverity(sev: Int) {
        _currentSeverity.value = sev
    }

    fun setDuration(dur: String) {
        _symptomDuration.value = dur
    }

    fun clearTriageSelections() {
        _selectedSymptoms.value = emptyList()
        _currentSeverity.value = 5
        _symptomDuration.value = "2 days"
    }

    fun runTriageAssessment(onComplete: (HealthAssessmentEntity) -> Unit) {
        if (_selectedSymptoms.value.isEmpty()) {
            showToast("Please select at least one symptom to assess.")
            return
        }

        viewModelScope.launch {
            val input = TriageInput(
                selectedSymptoms = _selectedSymptoms.value,
                severity = _currentSeverity.value,
                duration = _symptomDuration.value
            )
            val result = repository.performTriage(input)
            _latestAssessmentResult.value = result
            onComplete(result)
        }
    }

    fun sendChatMessage(msg: String) {
        if (msg.isBlank() || _isAIGenerating.value) return
        viewModelScope.launch {
            _isAIGenerating.value = true
            try {
                repository.sendChatMessage(msg)
            } finally {
                _isAIGenerating.value = false
            }
        }
    }

    fun requestAmbulance(
        pickupAddress: String,
        destinationHospital: HospitalEntity?,
        onDispatched: (AmbulanceRequestEntity) -> Unit
    ) {
        viewModelScope.launch {
            val req = repository.requestAmbulance(
                pickupAddress = pickupAddress,
                destinationHospitalId = destinationHospital?.id,
                destinationName = destinationHospital?.name ?: "Nearest Emergency Centre (Sonarpur / Peerless)"
            )
            showToast("Ambulance dispatched! ETA: ${req.etaMinutes} minutes.")
            onDispatched(req)
        }
    }

    fun updateAmbulanceStatus(req: AmbulanceRequestEntity, status: AmbulanceStatus) {
        viewModelScope.launch {
            repository.updateAmbulanceStatus(req, status)
            showToast("Ambulance status: ${status.name}")
        }
    }

    fun bookAppointment(
        patientName: String,
        doctor: DoctorEntity,
        date: String,
        timeSlot: String,
        type: ConsultationType,
        notes: String,
        onSuccess: (AppointmentEntity) -> Unit
    ) {
        viewModelScope.launch {
            val appt = repository.bookAppointment(
                patientName = patientName,
                doctor = doctor,
                date = date,
                timeSlot = timeSlot,
                type = type,
                notes = notes
            )
            showToast("Appointment confirmed with ${doctor.name}! Token: ${appt.tokenNumber}")
            onSuccess(appt)
        }
    }

    fun toggleSaveHospital(hospitalId: String, isCurrentlySaved: Boolean) {
        viewModelScope.launch {
            if (isCurrentlySaved) {
                repository.removeSavedHospital(hospitalId)
                showToast("Removed from saved hospitals.")
            } else {
                repository.saveHospital(hospitalId)
                showToast("Hospital saved for quick access.")
            }
        }
    }

    fun uploadMedicalReport(
        hospitalName: String,
        reportType: String,
        fileName: String,
        aiAnalysis: String,
        fileUri: String = ""
    ) {
        viewModelScope.launch {
            repository.addMedicalReport(hospitalName, reportType, fileName, aiAnalysis, fileUri)
            showToast("Medical report analyzed and saved to your health timeline.")
        }
    }

    fun addPrescription(
        doctorName: String,
        hospitalName: String,
        diagnosis: String,
        instructions: String
    ) {
        viewModelScope.launch {
            repository.addPrescription(doctorName, hospitalName, diagnosis, instructions)
            showToast("Prescription recorded successfully.")
        }
    }

    fun createReferral(
        patientName: String,
        patientAge: Int,
        patientGender: String,
        fromFacility: String,
        toHospital: HospitalEntity,
        specialization: String,
        clinicalSummary: String,
        urgency: UrgencyLevel,
        healthWorker: String
    ) {
        viewModelScope.launch {
            repository.createReferral(
                patientName, patientAge, patientGender, fromFacility,
                toHospital, specialization, clinicalSummary, urgency, healthWorker
            )
            showToast("Referral generated and transmitted to ${toHospital.name}")
        }
    }

    fun updateReferralStatus(referral: ReferralEntity, status: ReferralStatus) {
        viewModelScope.launch {
            repository.updateReferralStatus(referral, status)
            showToast("Referral status updated to ${status.name}")
        }
    }

    fun registerUser(name: String, email: String, phone: String, mergeGuest: Boolean) {
        viewModelScope.launch {
            repository.registerUser(name, email, phone, mergeGuest)
            showToast("Welcome $name! Your account is active.")
            setLoginDialogOpen(false)
        }
    }

    fun updateHealthProfile(profile: HealthProfileEntity) {
        viewModelScope.launch {
            repository.updateHealthProfile(profile)
            showToast("Health profile updated.")
        }
    }

    fun addEmergencyContact(name: String, relation: String, phone: String) {
        viewModelScope.launch {
            repository.addEmergencyContact(name, relation, phone)
            showToast("Emergency contact saved.")
        }
    }

    fun deleteEmergencyContact(contact: EmergencyContactEntity) {
        viewModelScope.launch {
            repository.deleteEmergencyContact(contact)
            showToast("Emergency contact deleted.")
        }
    }

    fun addFollowUp(
        patientName: String,
        riskLevel: RiskLevel,
        purpose: String,
        hospitalName: String,
        doctorName: String,
        dueDate: String
    ) {
        viewModelScope.launch {
            repository.addFollowUp(patientName, riskLevel, purpose, hospitalName, doctorName, dueDate)
            showToast("High-risk follow-up reminder scheduled.")
        }
    }

    fun updateBedAvailability(bed: BedAvailabilityEntity, newCount: Int) {
        viewModelScope.launch {
            repository.updateBedAvailability(bed, newCount)
            showToast("Updated ${bed.bedTypeName} availability to $newCount.")
        }
    }

    // Helper functions for calling and Google Maps navigation
    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phoneNumber.replace(" ", "")}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            showToast("Unable to open phone dialer: ${e.message}")
        }
    }

    fun navigateToHospital(context: Context, hospital: HospitalEntity) {
        try {
            val uri = Uri.parse("geo:${hospital.latitude},${hospital.longitude}?q=${Uri.encode(hospital.name)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            // Fallback to web maps URL
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${hospital.latitude},${hospital.longitude}")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(webIntent)
        }
    }

    fun createDiagnosticBooking(booking: DiagnosticBookingEntity) {
        viewModelScope.launch {
            repository.createDiagnosticBooking(booking)
        }
    }

    fun updateDiagnosticBookingStatus(bookingId: String, status: DiagnosticBookingStatus) {
        viewModelScope.launch {
            repository.updateDiagnosticBookingStatus(bookingId, status)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}

