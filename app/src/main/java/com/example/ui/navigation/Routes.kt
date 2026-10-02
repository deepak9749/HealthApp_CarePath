package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object EmergencyCenter : Screen("emergency_center", "Emergency Center")
    object SymptomsTriage : Screen("symptoms_triage", "Symptom Assessment")
    object TriageResult : Screen("triage_result", "Triage Result")
    object Hospitals : Screen("hospitals", "Hospitals & Beds")
    object HospitalDetail : Screen("hospital_detail/{hospitalId}", "Hospital Details") {
        fun createRoute(hospitalId: String) = "hospital_detail/$hospitalId"
    }
    object Ambulance : Screen("ambulance", "Ambulance")
    object Doctors : Screen("doctors", "Find Doctors")
    object Appointments : Screen("appointments", "Appointments")
    object QueueStatus : Screen("queue_status", "Live Queue")
    object Teleconsult : Screen("teleconsult", "Teleconsultation")
    object Reports : Screen("reports", "Medical Reports")
    object Prescriptions : Screen("prescriptions", "Prescriptions")
    object Medicines : Screen("medicines", "Medicines")
    object Diagnostics : Screen("diagnostics", "Diagnostic Tests")
    object DiagnosticBooking : Screen("diagnostic_booking/{testId}", "Book Diagnostic Test") {
        fun createRoute(testId: String) = "diagnostic_booking/$testId"
    }
    object Referrals : Screen("referrals", "Referral Tracking")
    object HealthTimeline : Screen("health_timeline", "Health Timeline")
    object HealthProfile : Screen("health_profile", "Health Profile")
    object EmergencyContacts : Screen("emergency_contacts", "Emergency Contacts")
    object MaternalHealth : Screen("maternal_health", "Maternal Care")
    object ChildHealth : Screen("child_health", "Child Health")
    object ChronicCare : Screen("chronic_care", "Chronic Care")
    object HealthWorkerPortal : Screen("worker_portal", "Health Worker Portal")
    object HospitalStaffPortal : Screen("staff_portal", "Hospital Staff Portal")
    object GovernmentAdmin : Screen("government_admin", "Govt Quality Portal")
}

enum class BottomNavItem(val route: String, val label: String) {
    HOME(Screen.Home.route, "Home"),
    HEALTH(Screen.HealthTimeline.route, "Health"),
    HOSPITALS(Screen.Hospitals.route, "Hospitals"),
    APPOINTMENTS(Screen.Appointments.route, "Care"),
    PROFILE(Screen.HealthProfile.route, "Profile")
}
