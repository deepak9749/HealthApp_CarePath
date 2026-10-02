package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.MedicalReportEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class MedicalReportTest {

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testUploadAndPersistMedicalReportWithFileUriAndCategory() = runBlocking {
        val report = MedicalReportEntity(
            id = "rep_test_101",
            hospitalName = "Peerless Hospital / Sonarpur Diag",
            reportType = "Blood Test Report",
            fileName = "CBC_Report_2026.pdf",
            fileUri = "content://com.android.providers.media.documents/document/101",
            reportDate = "Today",
            aiAnalysis = "Hemoglobin: 13.8 g/dL. Primary hematology markers within standard clinical reference ranges."
        )

        db.smartHealthDao().insertMedicalReport(report)

        val allReports = db.smartHealthDao().getAllMedicalReports().first()
        val saved = allReports.find { it.id == "rep_test_101" }

        assertNotNull(saved)
        assertEquals("Blood Test Report", saved?.reportType)
        assertEquals("CBC_Report_2026.pdf", saved?.fileName)
        assertEquals("content://com.android.providers.media.documents/document/101", saved?.fileUri)
        assertEquals("Peerless Hospital / Sonarpur Diag", saved?.hospitalName)
        assertTrue(saved?.aiAnalysis?.contains("Hemoglobin") == true)
    }

    @Test
    fun testCameraCapturedReportUpload() = runBlocking {
        val photoReport = MedicalReportEntity(
            id = "rep_camera_202",
            hospitalName = "Sonarpur Rural Hospital",
            reportType = "Prescription",
            fileName = "Medical_Report_2026_10_01_143022.jpg",
            fileUri = "content://com.example.fileprovider/camera_photos/Medical_Report_2026_10_01_143022.jpg",
            reportDate = "Today",
            aiAnalysis = "Prescription medication schedule, dosage instructions, and clinical precautions recorded."
        )

        db.smartHealthDao().insertMedicalReport(photoReport)

        val allReports = db.smartHealthDao().getAllMedicalReports().first()
        val saved = allReports.find { it.id == "rep_camera_202" }

        assertNotNull(saved)
        assertEquals("Prescription", saved?.reportType)
        assertEquals("Medical_Report_2026_10_01_143022.jpg", saved?.fileName)
        assertTrue(saved?.fileUri?.contains("camera_photos") == true)
    }
}
