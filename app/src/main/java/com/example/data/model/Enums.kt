package com.example.data.model

enum class UserRole {
    PATIENT,
    GUEST,
    FRONTLINE_HEALTH_WORKER,
    HOSPITAL_STAFF,
    ADMIN,
    GOVERNMENT_ADMIN
}

enum class RiskLevel {
    LOW,
    MODERATE,
    HIGH,
    EMERGENCY
}

enum class HospitalType {
    GOVERNMENT,
    PRIVATE,
    TRUST_CHARITABLE,
    OTHER
}

enum class AmbulanceStatus {
    REQUESTED,
    ACCEPTED,
    DISPATCHED,
    ARRIVING,
    PICKED_UP,
    ARRIVED,
    CANCELLED,
    COMPLETED
}

enum class AppointmentStatus {
    REQUESTED,
    CONFIRMED,
    RESCHEDULED,
    CANCELLED,
    COMPLETED,
    NO_SHOW
}

enum class ConsultationType {
    IN_PERSON,
    TELECONSULT_VIDEO,
    TELECONSULT_AUDIO,
    TELECONSULT_CHAT
}

enum class ReferralStatus {
    CREATED,
    SENT,
    ACCEPTED,
    REJECTED,
    IN_TRANSIT,
    ARRIVED,
    COMPLETED,
    CANCELLED
}

enum class UrgencyLevel {
    ROUTINE,
    URGENT,
    EMERGENCY
}

enum class StockStatus {
    IN_STOCK,
    LOW_STOCK,
    OUT_OF_STOCK
}

enum class FollowUpStatus {
    PENDING,
    COMPLETED,
    OVERDUE
}

enum class ChatSender {
    USER,
    AI,
    SYSTEM
}

enum class SyncStatus {
    SUCCESS,
    FAILED,
    PARTIAL,
    TIMEOUT
}

enum class DiagnosticBookingStatus {
    REQUEST_SUBMITTED,
    WAITING_FOR_CONFIRMATION,
    CONFIRMED
}

