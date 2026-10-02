package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("ALTER TABLE health_profiles ADD COLUMN gender TEXT NOT NULL DEFAULT 'Male'")
        } catch (_: Exception) {}
        try {
            db.execSQL("ALTER TABLE health_profiles ADD COLUMN age INTEGER NOT NULL DEFAULT 21")
        } catch (_: Exception) {}
        try {
            db.execSQL("ALTER TABLE health_profiles ADD COLUMN isAllergic TEXT NOT NULL DEFAULT 'No'")
        } catch (_: Exception) {}
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `diagnostic_bookings` (
                `bookingId` TEXT NOT NULL PRIMARY KEY,
                `userId` TEXT,
                `patientName` TEXT NOT NULL,
                `testId` TEXT NOT NULL,
                `testName` TEXT NOT NULL,
                `facilityId` TEXT NOT NULL,
                `facilityName` TEXT NOT NULL,
                `facilityType` TEXT NOT NULL,
                `phoneNumber` TEXT NOT NULL,
                `bookingDate` TEXT NOT NULL,
                `prescriptionUri` TEXT,
                `prescriptionFileName` TEXT,
                `requestCreatedAt` INTEGER NOT NULL,
                `status` TEXT NOT NULL
            )
        """.trimIndent())
    }
}

@Database(
    entities = [
        UserEntity::class,
        GuestSessionEntity::class,
        SessionEntity::class,
        HealthProfileEntity::class,
        EmergencyContactEntity::class,
        SavedHospitalEntity::class,
        SymptomCategoryEntity::class,
        SymptomEntity::class,
        HealthAssessmentEntity::class,
        AssessmentSymptomEntity::class,
        HospitalEntity::class,
        HospitalDepartmentEntity::class,
        SpecializationEntity::class,
        HospitalSpecializationEntity::class,
        HospitalApiEntity::class,
        BedEntity::class,
        BedTypeEntity::class,
        BedAvailabilityEntity::class,
        ApiSyncLogEntity::class,
        HospitalSearchEntity::class,
        HospitalSearchResultEntity::class,
        HospitalSelectionEntity::class,
        NavigationRequestEntity::class,
        EmergencyRequestEntity::class,
        AmbulanceProviderEntity::class,
        AmbulanceEntity::class,
        AmbulanceRequestEntity::class,
        ChatSessionEntity::class,
        ChatMessageEntity::class,
        MedicalReportEntity::class,
        PrescriptionEntity::class,
        MedicineEntity::class,
        PrescriptionMedicineEntity::class,
        AiAnalysisEntity::class,
        DoctorEntity::class,
        AppointmentEntity::class,
        QueueEntity::class,
        ReferralEntity::class,
        DiagnosticTestEntity::class,
        FacilityDiagnosticEntity::class,
        MedicineAvailabilityEntity::class,
        FollowUpEntity::class,
        MaternalHealthEntity::class,
        ChildHealthEntity::class,
        ChronicCareEntity::class,
        NotificationEntity::class,
        DiagnosticBookingEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun smartHealthDao(): SmartHealthDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_health_db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
