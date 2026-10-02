package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.HealthProfileEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HealthProfileTest {

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
    fun testHealthProfileInitialAndUpdatedValues() = runBlocking {
        val initialProfile = HealthProfileEntity(
            profileId = "prof_default",
            gender = "Male",
            age = 21,
            bloodGroup = "B+ (Positive)",
            heightCm = 172.0,
            weightKg = 69.5,
            isAllergic = "No",
            allergies = "Penicillin (Mild Rash)",
            chronicDiseases = "Hypertension (Managed)"
        )

        db.smartHealthDao().insertHealthProfile(initialProfile)

        val retrieved1 = db.smartHealthDao().getHealthProfile().first()
        assertNotNull(retrieved1)
        assertEquals("Male", retrieved1?.gender)
        assertEquals(21, retrieved1?.age)
        assertEquals("B+ (Positive)", retrieved1?.bloodGroup)
        assertEquals(172.0, retrieved1?.heightCm ?: 0.0, 0.01)
        assertEquals(69.5, retrieved1?.weightKg ?: 0.0, 0.01)
        assertEquals("No", retrieved1?.isAllergic)
        assertEquals("Penicillin (Mild Rash)", retrieved1?.allergies)
        assertEquals("Hypertension (Managed)", retrieved1?.chronicDiseases)

        // Simulate User Editing All 6 fields:
        // Blood Group -> O+
        // Height -> 175 cm
        // Weight -> 72 kg
        // Gender -> Male
        // Age -> 22
        // Allergic -> Yes
        val updatedProfile = retrieved1!!.copy(
            gender = "Male",
            age = 22,
            bloodGroup = "O+",
            heightCm = 175.0,
            weightKg = 72.0,
            isAllergic = "Yes"
        )

        db.smartHealthDao().insertHealthProfile(updatedProfile)

        val retrieved2 = db.smartHealthDao().getHealthProfile().first()
        assertNotNull(retrieved2)
        assertEquals("Male", retrieved2?.gender)
        assertEquals(22, retrieved2?.age)
        assertEquals("O+", retrieved2?.bloodGroup)
        assertEquals(175.0, retrieved2?.heightCm ?: 0.0, 0.01)
        assertEquals(72.0, retrieved2?.weightKg ?: 0.0, 0.01)
        assertEquals("Yes", retrieved2?.isAllergic)
        // Existing data must NOT be lost:
        assertEquals("Penicillin (Mild Rash)", retrieved2?.allergies)
        assertEquals("Hypertension (Managed)", retrieved2?.chronicDiseases)
    }
}
