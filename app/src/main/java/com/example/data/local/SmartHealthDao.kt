package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartHealthDao {

    // User & Session
    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("SELECT * FROM guest_sessions ORDER BY createdAt DESC LIMIT 1")
    fun getLatestGuestSession(): Flow<GuestSessionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuestSession(session: GuestSessionEntity)

    // Health Profile
    @Query("SELECT * FROM health_profiles LIMIT 1")
    fun getHealthProfile(): Flow<HealthProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthProfile(profile: HealthProfileEntity)

    // Emergency Contacts
    @Query("SELECT * FROM emergency_contacts ORDER BY priority ASC")
    fun getAllEmergencyContacts(): Flow<List<EmergencyContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContact(contact: EmergencyContactEntity)

    @Delete
    suspend fun deleteEmergencyContact(contact: EmergencyContactEntity)

    // Symptoms & Triage
    @Query("SELECT * FROM symptom_categories ORDER BY bodySystem ASC")
    fun getAllSymptomCategories(): Flow<List<SymptomCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymptomCategories(categories: List<SymptomCategoryEntity>)

    @Query("SELECT * FROM symptoms WHERE categoryId = :categoryId")
    fun getSymptomsByCategory(categoryId: String): Flow<List<SymptomEntity>>

    @Query("SELECT * FROM symptoms")
    fun getAllSymptoms(): Flow<List<SymptomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymptoms(symptoms: List<SymptomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthAssessment(assessment: HealthAssessmentEntity)

    @Query("SELECT * FROM health_assessments ORDER BY timestamp DESC")
    fun getAllAssessments(): Flow<List<HealthAssessmentEntity>>

    // Hospitals
    @Query("SELECT * FROM hospitals")
    fun getAllHospitals(): Flow<List<HospitalEntity>>

    @Query("SELECT * FROM hospitals WHERE id = :hospitalId")
    fun getHospitalById(hospitalId: String): Flow<HospitalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitals(hospitals: List<HospitalEntity>)

    @Query("SELECT * FROM bed_availabilities WHERE hospitalId = :hospitalId")
    fun getBedAvailabilitiesForHospital(hospitalId: String): Flow<List<BedAvailabilityEntity>>

    @Query("SELECT * FROM bed_availabilities")
    fun getAllBedAvailabilities(): Flow<List<BedAvailabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBedAvailabilities(beds: List<BedAvailabilityEntity>)

    @Update
    suspend fun updateBedAvailability(bed: BedAvailabilityEntity)

    // Saved Hospitals
    @Query("SELECT * FROM saved_hospitals")
    fun getSavedHospitals(): Flow<List<SavedHospitalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedHospital(savedHospital: SavedHospitalEntity)

    @Query("DELETE FROM saved_hospitals WHERE hospitalId = :hospitalId")
    suspend fun deleteSavedHospital(hospitalId: String)

    // Ambulances & Requests
    @Query("SELECT * FROM ambulances")
    fun getAllAmbulances(): Flow<List<AmbulanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmbulances(ambulances: List<AmbulanceEntity>)

    @Query("SELECT * FROM ambulance_requests ORDER BY requestTime DESC")
    fun getAllAmbulanceRequests(): Flow<List<AmbulanceRequestEntity>>

    @Query("SELECT * FROM ambulance_requests ORDER BY requestTime DESC LIMIT 1")
    fun getActiveAmbulanceRequest(): Flow<AmbulanceRequestEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmbulanceRequest(request: AmbulanceRequestEntity)

    @Update
    suspend fun updateAmbulanceRequest(request: AmbulanceRequestEntity)

    // Chat
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllChatMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun clearChatMessages()

    // Medical Reports & Prescriptions
    @Query("SELECT * FROM medical_reports ORDER BY uploadedAt DESC")
    fun getAllMedicalReports(): Flow<List<MedicalReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicalReport(report: MedicalReportEntity)

    @Query("SELECT * FROM prescriptions ORDER BY uploadedAt DESC")
    fun getAllPrescriptions(): Flow<List<PrescriptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescription(prescription: PrescriptionEntity)

    // Medicines
    @Query("SELECT * FROM medicines")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM medicine_availabilities")
    fun getAllMedicineAvailabilities(): Flow<List<MedicineAvailabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<MedicineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicineAvailabilities(items: List<MedicineAvailabilityEntity>)

    // Diagnostics
    @Query("SELECT * FROM diagnostic_tests")
    fun getAllDiagnosticTests(): Flow<List<DiagnosticTestEntity>>

    @Query("SELECT * FROM facility_diagnostics")
    fun getAllFacilityDiagnostics(): Flow<List<FacilityDiagnosticEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosticTests(tests: List<DiagnosticTestEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacilityDiagnostics(facilities: List<FacilityDiagnosticEntity>)

    // Doctors & Appointments
    @Query("SELECT * FROM doctors")
    fun getAllDoctors(): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors WHERE specialization = :specialization")
    fun getDoctorsBySpecialization(specialization: String): Flow<List<DoctorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctors(doctors: List<DoctorEntity>)

    @Query("SELECT * FROM appointments ORDER BY createdAt DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity)

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity)

    // Queues
    @Query("SELECT * FROM queues LIMIT 1")
    fun getLiveQueue(): Flow<QueueEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueue(queue: QueueEntity)

    // Referrals
    @Query("SELECT * FROM referrals ORDER BY createdAt DESC")
    fun getAllReferrals(): Flow<List<ReferralEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: ReferralEntity)

    @Update
    suspend fun updateReferral(referral: ReferralEntity)

    // Follow-ups
    @Query("SELECT * FROM follow_ups ORDER BY dueDate ASC")
    fun getAllFollowUps(): Flow<List<FollowUpEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowUp(followUp: FollowUpEntity)

    @Update
    suspend fun updateFollowUp(followUp: FollowUpEntity)

    // Maternal, Child, Chronic
    @Query("SELECT * FROM maternal_health LIMIT 1")
    fun getMaternalHealth(): Flow<MaternalHealthEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaternalHealth(maternal: MaternalHealthEntity)

    @Query("SELECT * FROM child_health LIMIT 1")
    fun getChildHealth(): Flow<ChildHealthEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildHealth(child: ChildHealthEntity)

    @Query("SELECT * FROM chronic_cares")
    fun getAllChronicCare(): Flow<List<ChronicCareEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChronicCare(item: ChronicCareEntity)

    // API Logs
    @Query("SELECT * FROM api_sync_logs ORDER BY syncTime DESC")
    fun getAllApiSyncLogs(): Flow<List<ApiSyncLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApiSyncLog(log: ApiSyncLogEntity)

    // Diagnostic Bookings
    @Query("SELECT * FROM diagnostic_bookings WHERE testId = :testId ORDER BY requestCreatedAt DESC LIMIT 1")
    fun getLatestBookingForTest(testId: String): Flow<DiagnosticBookingEntity?>

    @Query("SELECT * FROM diagnostic_bookings ORDER BY requestCreatedAt DESC")
    fun getAllDiagnosticBookings(): Flow<List<DiagnosticBookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosticBooking(booking: DiagnosticBookingEntity)

    @Update
    suspend fun updateDiagnosticBooking(booking: DiagnosticBookingEntity)
}

