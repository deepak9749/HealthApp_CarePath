package com.example.ui.screens.diagnostics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.local.DiagnosticBookingEntity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DiagnosticReportPdfGenerator {

    fun generateAndOpenReport(context: Context, booking: DiagnosticBookingEntity) {
        val safeFileName = "CarePath_Diagnostic_Booking_${booking.bookingId}.pdf"
        val file = File(context.cacheDir, safeFileName)
        try {
            var pdfSuccess = false
            try {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standard A4 page
                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas

                val paint = Paint().apply {
                    isAntiAlias = true
                }

                // 1. Header Banner Background (CarePath Teal: 0xFF006D77)
                paint.color = 0xFF006D77.toInt()
                canvas.drawRect(0f, 0f, 595f, 110f, paint)

                // Header Titles
                paint.color = Color.WHITE
                paint.textSize = 22f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText("CAREPATH HEALTH SYSTEMS", 40f, 48f, paint)

                paint.textSize = 13f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                canvas.drawText("Diagnostic Test Booking & Confirmation Receipt", 40f, 72f, paint)

                paint.textSize = 9.5f
                val issueDateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
                canvas.drawText("Generated on: $issueDateStr", 40f, 92f, paint)

                // 2. Receipt Reference Box
                paint.color = 0xFFF1F5F9.toInt()
                canvas.drawRoundRect(40f, 130f, 555f, 210f, 8f, 8f, paint)

                paint.color = 0xFF0F172A.toInt()
                paint.textSize = 11.5f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText("Booking Reference:", 60f, 155f, paint)
                paint.color = 0xFF006D77.toInt()
                canvas.drawText(booking.bookingId, 200f, 155f, paint)

                paint.color = 0xFF0F172A.toInt()
                canvas.drawText("Booking Status:", 60f, 175f, paint)
                paint.color = 0xFF16A34A.toInt() // Success Green
                canvas.drawText("CONFIRMED", 200f, 175f, paint)

                paint.color = 0xFF0F172A.toInt()
                canvas.drawText("Booking/Request Date:", 60f, 195f, paint)
                val reqDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(booking.requestCreatedAt))
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                canvas.drawText(reqDate, 200f, 195f, paint)

                // 3. Section: Appointment Details
                paint.color = 0xFF006D77.toInt()
                paint.textSize = 13.5f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText("PATIENT & APPOINTMENT DETAILS", 40f, 245f, paint)

                paint.color = 0xFFCBD5E1.toInt()
                paint.strokeWidth = 1f
                canvas.drawLine(40f, 252f, 555f, 252f, paint)

                var yPos = 280f
                val lineSpacing = 30f

                fun drawDetailRow(label: String, value: String) {
                    paint.color = 0xFF475569.toInt()
                    paint.textSize = 11.5f
                    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    canvas.drawText(label, 40f, yPos, paint)

                    paint.color = 0xFF0F172A.toInt()
                    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                    canvas.drawText(value, 200f, yPos, paint)

                    paint.color = 0xFFF1F5F9.toInt()
                    canvas.drawLine(40f, yPos + 8f, 555f, yPos + 8f, paint)
                    yPos += lineSpacing
                }

                drawDetailRow("Patient Name:", booking.patientName)
                drawDetailRow("Test Prescribed:", booking.testName)
                drawDetailRow("Lab / Clinic:", booking.facilityName)
                drawDetailRow("Facility Type:", booking.facilityType)
                drawDetailRow("Contact Phone:", booking.phoneNumber)
                drawDetailRow("Booking Date:", booking.bookingDate)
                drawDetailRow("Prescription Status:", if (booking.prescriptionFileName != null) "Attached (${booking.prescriptionFileName})" else "None attached")

                // 4. Instructions for Patient
                yPos += 15f
                paint.color = 0xFFFFFBEB.toInt()
                canvas.drawRoundRect(40f, yPos, 555f, yPos + 100f, 8f, 8f, paint)

                paint.color = 0xFFD97706.toInt()
                paint.textSize = 11.5f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText("Instructions for Test Day:", 55f, yPos + 25f, paint)

                paint.color = 0xFF92400E.toInt()
                paint.textSize = 10f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                canvas.drawText("• Please arrive 15 minutes before your scheduled appointment.", 55f, yPos + 46f, paint)
                canvas.drawText("• Carry this booking confirmation slip and any physical prescription with you.", 55f, yPos + 64f, paint)
                canvas.drawText("• Strictly follow test preparation guidelines (fasting, hydration, or medicine timings).", 55f, yPos + 82f, paint)

                // 5. Footer
                paint.color = 0xFF94A3B8.toInt()
                paint.textSize = 9.5f
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                canvas.drawText("Report generated by: CarePath • Integrated Clinical Healthcare System", 40f, 790f, paint)
                canvas.drawText("This is an official computer-generated receipt issued for diagnostic services.", 40f, 805f, paint)

                pdfDocument.finishPage(page)

                FileOutputStream(file).use { out ->
                    pdfDocument.writeTo(out)
                }
                pdfDocument.close()
                pdfSuccess = true
            } catch (_: Throwable) {
                pdfSuccess = false
            }

            if (!pdfSuccess || !file.exists() || file.length() == 0L) {
                file.writeText(
                    "%PDF-1.4\n%CarePath Diagnostic Test Booking Report\n" +
                            "Booking ID: ${booking.bookingId}\n" +
                            "Patient Name: ${booking.patientName}\n" +
                            "Test Name: ${booking.testName}\n" +
                            "Facility: ${booking.facilityName} (${booking.facilityType})\n" +
                            "Scheduled Date: ${booking.bookingDate}\n" +
                            "Status: CONFIRMED\n" +
                            "Generated By: CarePath Health Systems\n%%EOF"
                )
            }

            // Open via Intent
            try {
                val authority = "${context.applicationContext.packageName}.fileprovider"
                val uri = FileProvider.getUriForFile(context, authority, file)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                val chooser = Intent.createChooser(intent, "Download / Open Booking Report").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(chooser)
            } catch (_: Exception) {}
            Toast.makeText(context, "Report downloaded: $safeFileName", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Could not generate PDF: ${e.localizedMessage ?: "Unknown error"}", Toast.LENGTH_LONG).show()
        }
    }
}
