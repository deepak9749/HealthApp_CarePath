package com.example.domain

import com.example.data.local.HealthAssessmentEntity
import com.example.data.local.SymptomEntity
import com.example.data.model.RiskLevel

data class TriageInput(
    val selectedSymptoms: List<SymptomEntity>,
    val severity: Int, // 1 - 10
    val duration: String,
    val patientNotes: String = "",
    val age: Int = 30,
    val hasComorbidities: Boolean = false
)

object TriageEngine {

    fun evaluateTriage(input: TriageInput, guestSessionId: String?): HealthAssessmentEntity {
        val hasRedFlag = input.selectedSymptoms.any { it.isEmergencyRedFlag }
        val maxSeverity = maxOf(input.severity, input.selectedSymptoms.maxOfOrNull { it.defaultSeverity } ?: 1)

        val riskLevel: RiskLevel
        val summary: String
        val recommendation: String
        val emergencyWarning: String?
        val recommendedSpecialization: String

        when {
            hasRedFlag || maxSeverity >= 9 -> {
                riskLevel = RiskLevel.EMERGENCY
                summary = "Critical Alert: High-risk emergency symptom(s) detected: ${input.selectedSymptoms.joinToString { it.name }}."
                recommendation = "IMMEDIATE EMERGENCY ATTENTION REQUIRED. Do not drive yourself. Call 108 Emergency Service or request an immediate ICU-equipped ambulance to the nearest emergency medical centre."
                emergencyWarning = "CRITICAL WARNING: This assessment indicates potential immediate threat to health or life. Please access the emergency action buttons below now."
                recommendedSpecialization = "Emergency Medicine / Critical Care / Cardiology"
            }
            maxSeverity in 7..8 || (maxSeverity >= 6 && input.hasComorbidities) -> {
                riskLevel = RiskLevel.HIGH
                summary = "Significant symptom intensity detected: ${input.selectedSymptoms.joinToString { it.name }} with severity $maxSeverity/10."
                recommendation = "Urgent medical evaluation recommended within 2 to 4 hours at the nearest hospital OPD or emergency triage ward. Keep emergency contacts notified."
                emergencyWarning = "Seek instant emergency care if breathing becomes difficult, chest tightness intensifies, or sudden weakness occurs."
                recommendedSpecialization = determineSpecialization(input.selectedSymptoms)
            }
            maxSeverity in 4..6 -> {
                riskLevel = RiskLevel.MODERATE
                summary = "Moderate symptoms reported: ${input.selectedSymptoms.joinToString { it.name }} for ${input.duration}."
                recommendation = "Consult a licensed physician or specialist within 24 hours. Rest, stay hydrated, monitor vital signs, and avoid strenuous physical activity."
                emergencyWarning = "If symptoms worsen or pain score reaches above 7, visit the nearest hospital emergency room."
                recommendedSpecialization = determineSpecialization(input.selectedSymptoms)
            }
            else -> {
                riskLevel = RiskLevel.LOW
                summary = "Mild symptoms reported: ${input.selectedSymptoms.joinToString { it.name }}."
                recommendation = "Self-care with adequate rest and oral hydration. If symptoms persist for more than 48-72 hours or new symptoms develop, schedule an OPD doctor consultation."
                emergencyWarning = null
                recommendedSpecialization = "General Medicine / Primary Care"
            }
        }

        return HealthAssessmentEntity(
            id = "assess_${System.currentTimeMillis()}",
            guestSessionId = guestSessionId,
            riskLevel = riskLevel,
            summary = summary,
            recommendation = recommendation,
            emergencyWarning = emergencyWarning,
            recommendedSpecialization = recommendedSpecialization,
            timestamp = System.currentTimeMillis()
        )
    }

    private fun determineSpecialization(symptoms: List<SymptomEntity>): String {
        val categoryIds = symptoms.map { it.categoryId }
        return when {
            categoryIds.contains("cat_emergency_critical") -> "Emergency Medicine & Critical Care"
            categoryIds.contains("cat_cardio") -> "Cardiology"
            categoryIds.contains("cat_respiratory") -> "Pulmonology / Chest Medicine"
            categoryIds.contains("cat_neuro") -> "Neurology"
            categoryIds.contains("cat_mouth_jaw") -> "Dentistry & Maxillofacial"
            categoryIds.contains("cat_ear") || categoryIds.contains("cat_nose_sinuses") || categoryIds.contains("cat_throat_voice") -> "ENT (Otolaryngology)"
            categoryIds.contains("cat_eyes") -> "Ophthalmology"
            categoryIds.contains("cat_digestive") || categoryIds.contains("cat_liver_gallbladder") -> "Gastroenterology & Hepatology"
            categoryIds.contains("cat_kidney_urinary") -> "Nephrology & Urology"
            categoryIds.contains("cat_bowel_rectal") -> "Colorectal Surgery & Proctology"
            categoryIds.contains("cat_blood_immune") -> "Hematology & Immunology"
            categoryIds.contains("cat_bones_joints") || categoryIds.contains("cat_ortho") -> "Orthopedics"
            categoryIds.contains("cat_muscles_soft_tissue") -> "Physical Medicine & Orthopedics"
            categoryIds.contains("cat_skin_hair_nails") -> "Dermatology"
            categoryIds.contains("cat_endocrine_hormonal") -> "Endocrinology"
            categoryIds.contains("cat_female_reproductive") -> "Gynecology"
            categoryIds.contains("cat_maternal_pregnancy") || categoryIds.contains("cat_maternal") -> "Obstetrics & Maternal-Fetal Medicine"
            categoryIds.contains("cat_male_reproductive") -> "Urology & Andrology"
            categoryIds.contains("cat_child_infant") || categoryIds.contains("cat_pediatric") -> "Pediatrics"
            categoryIds.contains("cat_infectious_diseases") || categoryIds.contains("cat_fever_infectious") -> "Infectious Disease & Internal Medicine"
            categoryIds.contains("cat_mental_health") -> "Psychiatry & Behavioral Health"
            categoryIds.contains("cat_sleep_health") -> "Sleep Medicine & Pulmonology"
            categoryIds.contains("cat_injury_trauma") || categoryIds.contains("cat_trauma") -> "Emergency Medicine & Trauma Surgery"
            categoryIds.contains("cat_poisoning_toxic") -> "Emergency Toxicology"
            else -> "General Medicine / Internal Medicine"
        }
    }
}
