package com.example.data.datasource

import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DemoDataSeeder {

    suspend fun seedDatabaseIfEmpty(dao: SmartHealthDao) = withContext(Dispatchers.IO) {
        // Seed default guest session if none exists
        val defaultGuestSession = GuestSessionEntity(
            guestSessionId = "guest_${System.currentTimeMillis()}",
            deviceIdentifier = "dev_android_guest",
            latitude = 22.44335,
            longitude = 88.41543,
            locationPermissionGranted = true
        )
        dao.insertGuestSession(defaultGuestSession)

        // Seed 30 nearest hospitals / healthcare facilities around FIEM, Sonarpur
        val hospitals = listOf(
            HospitalEntity(
                id = "hosp_sonarpur_rural",
                name = "Sonarpur Rural Hospital",
                type = HospitalType.GOVERNMENT,
                address = "Subhasgram, Rajpur Sonarpur, South 24 Parganas",
                area = "Sonarpur / Subhasgram",
                latitude = 22.4418,
                longitude = 88.4231,
                phone = "Not publicly listed",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free / Govt.",
                hasAmbulanceOnSite = true,
                dataSourceType = "West Bengal Government / Google Maps"
            ),
            HospitalEntity(
                id = "hosp_south_star",
                name = "South Star Nursing Home",
                type = HospitalType.PRIVATE,
                address = "Sonarpur Bhangor Road, Sonarpur Bazar, Ghasiara",
                area = "Sonarpur",
                latitude = 22.4432,
                longitude = 88.4192,
                phone = "+91 97485 09261",
                emergencyPhone = "+91 97485 09261",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_atlas_health",
                name = "Atlas Health Point",
                type = HospitalType.PRIVATE,
                address = "282 Baidyapara Road, Khiristola More, Sonarpur",
                area = "Sonarpur",
                latitude = 22.449,
                longitude = 88.414,
                phone = "+91 33 3535 5555",
                emergencyPhone = "+91 33 3535 5555",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_apollo_clinic_sonarpur",
                name = "Apollo Clinic - Sonarpur",
                type = HospitalType.PRIVATE,
                address = "2269 Sonarpur Station Road, Tegharia, Sonarpur",
                area = "Sonarpur",
                latitude = 22.4455,
                longitude = 88.4105,
                phone = "+91 70444 46141",
                emergencyPhone = "+91 70444 46141",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_ramakrishna_mission_narendrapur",
                name = "Ramakrishna Mission Hospital and Charitable Dispensary",
                type = HospitalType.TRUST_CHARITABLE,
                address = "Narendrapur, Rajpur Sonarpur, West Bengal 700103",
                area = "Narendrapur",
                latitude = 22.442,
                longitude = 88.401,
                phone = "Not publicly listed",
                emergencyPhone = "Contact facility",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Charitable / Subsidized",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_apollo_narendrapur",
                name = "Apollo Hospitals Narendrapur",
                type = HospitalType.PRIVATE,
                address = "366 Paikpara Road, Ramchandrapur, Narendrapur, Kolkata 700103",
                area = "Narendrapur",
                latitude = 22.4505,
                longitude = 88.3965,
                phone = "+91 33 4420 2122",
                emergencyPhone = "+91 33 4420 2122",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Apollo Hospitals / Google Maps"
            ),
            HospitalEntity(
                id = "hosp_iilds",
                name = "Indian Institute of Liver and Digestive Sciences",
                type = HospitalType.PRIVATE,
                address = "Shitala, East Sonarpur, Kolkata 700150",
                area = "East Sonarpur",
                latitude = 22.4475,
                longitude = 88.4075,
                phone = "+91 33 2434 2300",
                emergencyPhone = "+91 33 2434 2300",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_kalpana_nursing",
                name = "Kalpana Nursing Home",
                type = HospitalType.PRIVATE,
                address = "S-4/16, A.P. Nagar, Rajpur Sonarpur",
                area = "Sonarpur",
                latitude = 22.4505,
                longitude = 88.418,
                phone = "+91 70443 58122",
                emergencyPhone = "+91 70443 58122",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_isp_at_coop",
                name = "Ispat Cooperative Hospital",
                type = HospitalType.PRIVATE,
                address = "Village & P.O. Kalikapur, P.S. Sonarpur",
                area = "Kalikapur / Sonarpur",
                latitude = 22.43,
                longitude = 88.448,
                phone = "+91 96744 83465",
                emergencyPhone = "+91 96744 83465",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_manikpur_phc",
                name = "Manikpur Primary Health Centre",
                type = HospitalType.GOVERNMENT,
                address = "Manikpur, Harinavi, Rajpur Sonarpur",
                area = "Harinavi",
                latitude = 22.445,
                longitude = 88.439,
                phone = "Not publicly listed",
                emergencyPhone = "108",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Free / Govt.",
                hasAmbulanceOnSite = false,
                dataSourceType = "Government facility / Google Maps"
            ),
            HospitalEntity(
                id = "hosp_hhp",
                name = "Hindustan Health Point",
                type = HospitalType.PRIVATE,
                address = "2406 Garia Main Road, Hindustan More, Garia",
                area = "Garia / Mahamayatala",
                latitude = 22.4595,
                longitude = 88.3835,
                phone = "+91 33 2435 9997",
                emergencyPhone = "+91 33 2435 9997",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_welkin",
                name = "Welkin Medicare Private Limited",
                type = HospitalType.PRIVATE,
                address = "1699 Garia Station Road, Barhans, Garia",
                area = "Garia",
                latitude = 22.466,
                longitude = 88.3855,
                phone = "+91 33 2462 6508",
                emergencyPhone = "+91 33 2462 6508",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_remedy",
                name = "Remedy Hospital",
                type = HospitalType.PRIVATE,
                address = "Kalitala, Garia Station Road, near Shahid Khudiram Metro",
                area = "Garia",
                latitude = 22.4652784,
                longitude = 88.3898123,
                phone = "+91 33 2462 8677",
                emergencyPhone = "+91 33 2462 8678",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_brws",
                name = "BRWS Hospital",
                type = HospitalType.PRIVATE,
                address = "279 Kendua Main Road, P.O. Garia, Kolkata 700084",
                area = "Garia",
                latitude = 22.4712333,
                longitude = 88.3776719,
                phone = "+91 90736 14869",
                emergencyPhone = "+91 90736 14869",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_sevangan",
                name = "Sevangan Nursing Home",
                type = HospitalType.PRIVATE,
                address = "398/1 Netaji Subhas Chandra Bose Road, Garia",
                area = "Garia",
                latitude = 22.472,
                longitude = 88.39,
                phone = "+91 33 2430 8302",
                emergencyPhone = "+91 33 2430 8302",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_peerless",
                name = "Peerless Hospital & B.K. Roy Research Centre",
                type = HospitalType.PRIVATE,
                address = "360 Panchasayar, Kanti Roy Sarani, Kolkata 700094",
                area = "Panchasayar / Garia",
                latitude = 22.4809641,
                longitude = 88.3938205,
                phone = "Not publicly listed",
                emergencyPhone = "Contact facility",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_rntics",
                name = "Rabindranath Tagore International Institute of Cardiac Sciences",
                type = HospitalType.PRIVATE,
                address = "124 Mukundapur, E.M. Bypass, Kolkata",
                area = "Mukundapur",
                latitude = 22.4919,
                longitude = 88.4005,
                phone = "+91 33 7122 2222",
                emergencyPhone = "1800 3090 309",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Narayana Health / Local Business"
            ),
            HospitalEntity(
                id = "hosp_medica",
                name = "Medica Superspecialty Hospital",
                type = HospitalType.PRIVATE,
                address = "127 Mukundapur, E.M. Bypass, Kolkata 700099",
                area = "Mukundapur",
                latitude = 22.4942793,
                longitude = 88.400994,
                phone = "+91 33 6652 0000",
                emergencyPhone = "+91 33 6652 0100",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_vision_care",
                name = "Vision Care Hospital",
                type = HospitalType.PRIVATE,
                address = "Mukundapur, Kolkata",
                area = "Mukundapur",
                latitude = 22.49405,
                longitude = 88.4022,
                phone = "Not publicly listed",
                emergencyPhone = "Contact facility",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "OpenStreetMap / Local Business"
            ),
            HospitalEntity(
                id = "hosp_manipal_mukundapur",
                name = "Manipal Hospitals Mukundapur",
                type = HospitalType.PRIVATE,
                address = "223 & 230, Pano Road, Mukundapur, Kolkata 700099",
                area = "Mukundapur",
                latitude = 22.4981,
                longitude = 88.4024,
                phone = "+91 33 6907 0000",
                emergencyPhone = "+91 33 6907 0000",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_ruby_general",
                name = "Ruby General Hospital",
                type = HospitalType.PRIVATE,
                address = "576 Anandapur Main Road, Golpark, Kasba, Kolkata 700107",
                area = "Kasba / Anandapur",
                latitude = 22.5135311,
                longitude = 88.4030473,
                phone = "+91 33 6601 1800",
                emergencyPhone = "+91 33 2442 7091",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_desun",
                name = "Desun Hospital & Heart Institute",
                type = HospitalType.PRIVATE,
                address = "720 Eastern Metropolitan Bypass, Desun More, Kasba",
                area = "Kasba / Anandapur",
                latitude = 22.5145798,
                longitude = 88.403259,
                phone = "+91 90517 15171",
                emergencyPhone = "+91 90517 15171",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_genesis",
                name = "Genesis Hospital",
                type = HospitalType.PRIVATE,
                address = "1470 Rajdanga Main Road, Kasba",
                area = "Kasba",
                latitude = 22.5141274,
                longitude = 88.3991255,
                phone = "Not publicly listed",
                emergencyPhone = "Contact facility",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_fortis_anandapur",
                name = "Fortis Hospital Anandapur",
                type = HospitalType.PRIVATE,
                address = "730 Eastern Metropolitan Bypass Road, Anandapur",
                area = "Anandapur",
                latitude = 22.5203111,
                longitude = 88.4009514,
                phone = "+91 33 6628 4444",
                emergencyPhone = "105010 / +91 33 6628 4444",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = true,
                dataSourceType = "Open Government Data / Local Business"
            ),
            HospitalEntity(
                id = "hosp_iris",
                name = "IRIS Multispeciality Hospital",
                type = HospitalType.PRIVATE,
                address = "82/1 Raja Subodh Chandra Mallick Road, Ganguly Bagan",
                area = "Ganguly Bagan / Garia",
                latitude = 22.476,
                longitude = 88.372,
                phone = "+91 33 6609 6000",
                emergencyPhone = "+91 33 6609 6000",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_apex_medical",
                name = "Apex Institute of Medical Sciences",
                type = HospitalType.PRIVATE,
                address = "1219 Sammilani Park Road, Hiland Park, Survey Park",
                area = "Hiland Park / Survey Park",
                latitude = 22.487,
                longitude = 88.39,
                phone = "+91 33 7125 6666",
                emergencyPhone = "+91 33 7125 6666",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_cosmos",
                name = "Cosmos Hospital",
                type = HospitalType.PRIVATE,
                address = "155 Eastern Metropolitan Bypass, Survey Park, Santoshpur",
                area = "Survey Park / Santoshpur",
                latitude = 22.493,
                longitude = 88.385,
                phone = "+91 94331 32304",
                emergencyPhone = "+91 94331 32304",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_rg_stone",
                name = "RG Stone Urology & Laparoscopy Hospital",
                type = HospitalType.PRIVATE,
                address = "Jodhpur Park, Kolkata",
                area = "Jodhpur Park",
                latitude = 22.502,
                longitude = 88.365,
                phone = "Not publicly listed",
                emergencyPhone = "Contact facility",
                emergencyAvailable = false,
                operatingStatus = "Facility hours vary",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Banchbo healthcare network"
            ),
            HospitalEntity(
                id = "hosp_rsv",
                name = "RSV Hospital Pvt. Ltd.",
                type = HospitalType.PRIVATE,
                address = "40 Deshpran Sasmal Road, Tollygunge Phari",
                area = "Tollygunge",
                latitude = 22.5005,
                longitude = 88.347,
                phone = "+91 33 4081 8000",
                emergencyPhone = "+91 33 4081 8000",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Contact facility",
                hasAmbulanceOnSite = false,
                dataSourceType = "Google Maps / Local Business"
            ),
            HospitalEntity(
                id = "hosp_nrs_medical",
                name = "Nil Ratan Sircar Medical College & Hospital",
                type = HospitalType.GOVERNMENT,
                address = "138 A.J.C. Bose Road, Sealdah, Kolkata",
                area = "Sealdah",
                latitude = 22.563,
                longitude = 88.3695,
                phone = "Not publicly listed",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free / Govt.",
                hasAmbulanceOnSite = true,
                dataSourceType = "West Bengal Government Health"
            ),
)
        dao.insertHospitals(hospitals)

        // Seed Bed Availabilities for each hospital
        val bedAvailabilities = mutableListOf<BedAvailabilityEntity>()
        hospitals.forEach { hosp ->
            val isGovt = hosp.type == HospitalType.GOVERNMENT
            val icuTotal = if (isGovt) 30 else 50
            val icuAvail = if (isGovt) 4 else 14
            val emTotal = if (isGovt) 25 else 35
            val emAvail = if (isGovt) 6 else 11
            val genTotal = if (isGovt) 200 else 180
            val genAvail = if (isGovt) 28 else 45

            bedAvailabilities.add(
                BedAvailabilityEntity(
                    id = "bed_icu_${hosp.id}",
                    hospitalId = hosp.id,
                    bedTypeName = "ICU Beds",
                    total = icuTotal,
                    available = icuAvail,
                    occupied = icuTotal - icuAvail - 2,
                    reserved = 2,
                    isApiSynced = true,
                    freshnessLabel = "Live API sync: 3 mins ago"
                )
            )
            bedAvailabilities.add(
                BedAvailabilityEntity(
                    id = "bed_em_${hosp.id}",
                    hospitalId = hosp.id,
                    bedTypeName = "Emergency Beds",
                    total = emTotal,
                    available = emAvail,
                    occupied = emTotal - emAvail - 1,
                    reserved = 1,
                    isApiSynced = true,
                    freshnessLabel = "Live API sync: 3 mins ago"
                )
            )
            bedAvailabilities.add(
                BedAvailabilityEntity(
                    id = "bed_gen_${hosp.id}",
                    hospitalId = hosp.id,
                    bedTypeName = "General Beds",
                    total = genTotal,
                    available = genAvail,
                    occupied = genTotal - genAvail - 5,
                    reserved = 5,
                    isApiSynced = true,
                    freshnessLabel = "Live API sync: 5 mins ago"
                )
            )
        }
        dao.insertBedAvailabilities(bedAvailabilities)

        // Seed Symptom Categories (Hierarchical: Body System -> Category) - 28 Comprehensive Categories
        dao.insertSymptomCategories(ComprehensiveSymptomCatalog.categories)

        // Seed Symptoms with Subcategories and Emergency Red Flags - Comprehensive Catalog
        dao.insertSymptoms(ComprehensiveSymptomCatalog.symptoms)

        // Seed Doctors
        val doctors = listOf(
            DoctorEntity("doc_1", "hosp_peerless", "Peerless Hospital", "Dr. Subhasish Mukherjee", "MBBS, MD, DM (Cardiology)", "Cardiology", 18, "₹800", "Mon, Wed, Fri", "10:00 AM - 1:00 PM", true, 4.9),
            DoctorEntity("doc_2", "hosp_rntics", "NH RTIICS", "Dr. Arindam Banerjee", "MBBS, MS, MCh (Cardiac Surgery)", "Cardiology", 22, "₹1000", "Tue, Thu, Sat", "2:00 PM - 5:00 PM", true, 4.9),
            DoctorEntity("doc_3", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Dr. Tanmoy Ghosh", "MBBS, MD (General Medicine)", "General Medicine", 12, "Free", "Daily", "9:00 AM - 2:00 PM", true, 4.7),
            DoctorEntity("doc_4", "hosp_medica", "Medica Superspecialty Hospital", "Dr. Ritu Sen", "MBBS, MD, DM (Neurology)", "Neurology", 15, "₹900", "Mon, Thu", "11:00 AM - 3:00 PM", true, 4.8),
            DoctorEntity("doc_5", "hosp_ruby_general", "Ruby General Hospital", "Dr. Debashis Roy", "MBBS, MS (Orthopedics)", "Orthopedics", 16, "₹700", "Mon, Wed, Sat", "4:00 PM - 7:00 PM", true, 4.8),
            DoctorEntity("doc_6", "hosp_fortis_anandapur", "Fortis Hospital Anandapur", "Dr. Ananya Roychowdhury", "MBBS, MD, DGO (Gynecology)", "Gynecology", 14, "₹1100", "Tue, Fri", "10:00 AM - 2:00 PM", true, 4.9)
        )
        dao.insertDoctors(doctors)

        // Seed Ambulances
        val ambulances = listOf(
            AmbulanceEntity("amb_1", "prov_108", "WB National Health Mission (EMRI 108)", "WB-04-1081", "Advanced Life Support (ALS) - ICU Equipped", "Bikash Mondal", "+91 98300 10801", 22.44500, 88.41800, "STANDBY"),
            AmbulanceEntity("amb_2", "prov_peerless", "Peerless Emergency Mobile ICU", "WB-02-PEER-1", "Advanced Cardiac Life Support", "Ramesh Sardar", "+91 98311 22334", 22.48100, 88.39600, "STANDBY"),
            AmbulanceEntity("amb_3", "prov_sonarpur", "Sonarpur Quick Response Ambulance", "WB-20-SON-9", "Basic Life Support (BLS) with Oxygen", "Alok Halder", "+91 98322 44556", 22.44100, 88.42100, "STANDBY")
        )
        dao.insertAmbulances(ambulances)

        // Seed Medicines from Comprehensive Non-Critical Catalogue
        dao.insertMedicines(ComprehensiveMedicineCatalog.defaultMedicineEntities)

        // Seed Medicine Availabilities
        dao.insertMedicineAvailabilities(ComprehensiveMedicineCatalog.defaultAvailabilities)

        // Seed Diagnostic Tests
        val tests = listOf(
            DiagnosticTestEntity("diag_1", "Complete Blood Count (CBC) with ESR", "Pathology", "Measures red cells, white cells, hemoglobin, and platelets.", "Fasting not strictly required. Normal water intake allowed.", "₹250 - ₹400", "4 - 6 Hours"),
            DiagnosticTestEntity("diag_2", "12-Lead Electrocardiogram (ECG)", "Cardiology", "Records electrical signals of the heart to detect arrhythmias or ischemia.", "No special preparation. Wear loose comfortable clothing.", "₹200 - ₹500", "Instant (15 mins)"),
            DiagnosticTestEntity("diag_3", "Digital Chest X-Ray (PA View)", "Radiology", "Images lungs, heart, ribs and pleural spaces.", "Remove all metal jewelry and piercings from chest area.", "₹350 - ₹600", "1 - 2 Hours"),
            DiagnosticTestEntity("diag_4", "Fasting Blood Sugar (FBS) & HbA1c", "Pathology / Diabetes", "Measures blood glucose after 8-10 hours fasting and 3-month average.", "Strict 8 to 10 hours overnight fasting required.", "₹450 - ₹750", "6 Hours"),
            DiagnosticTestEntity("diag_5", "2D Echocardiography with Color Doppler", "Cardiology", "Ultrasound imaging of heart chambers, valves, and pumping efficiency.", "No specific fasting required.", "₹1500 - ₹2500", "Same Day"),
            DiagnosticTestEntity("diag_6", "CT Scan - Brain (Plain)", "Advanced Radiology", "Rapid cross-sectional imaging for trauma, stroke, or hemorrhage.", "Inform technician if pregnant or wearing implants.", "₹2000 - ₹3500", "2 - 4 Hours")
        )
        dao.insertDiagnosticTests(tests)

        val facilityDiagnostics = listOf(
            FacilityDiagnosticEntity("fd_1", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Complete Blood Count (CBC)", "Pathology", true, "Free (Govt. Lab)", true),
            FacilityDiagnosticEntity("fd_2", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "12-Lead ECG", "Cardiology", true, "Free", false),
            FacilityDiagnosticEntity("fd_3", "hosp_peerless", "Peerless Hospital", "2D Echocardiography", "Cardiology", true, "₹1800", false),
            FacilityDiagnosticEntity("fd_4", "hosp_medica", "Medica Superspecialty Hospital", "CT Scan - Brain", "Advanced Radiology", true, "₹2800", false),
            FacilityDiagnosticEntity("fd_5", "hosp_ruby_general", "Ruby General Hospital", "Digital Chest X-Ray", "Radiology", true, "₹450", false)
        )
        dao.insertFacilityDiagnostics(facilityDiagnostics)

        // Seed Sample Health Profile
        val sampleProfile = HealthProfileEntity(
            profileId = "prof_default",
            gender = "Male",
            age = 21,
            bloodGroup = "B+ (Positive)",
            heightCm = 172.0,
            weightKg = 69.5,
            isAllergic = "No",
            allergies = "Penicillin (Mild Rash)",
            existingConditions = "Mild Hypertension",
            chronicDiseases = "Hypertension (Managed)",
            emergencyNotes = "Carries emergency SOS card. Primary contact: Deepak Mandal (Friend)."
        )
        dao.insertHealthProfile(sampleProfile)

        // Seed Emergency Contacts
        val contact1 = EmergencyContactEntity("ec_1", name = "Deepak Mandal", relationship = "Friend", phone = "9749864859", priority = 1)
        val contact2 = EmergencyContactEntity("ec_2", name = "Tapashri Sur", relationship = "Ma'am", phone = "8240432095", priority = 2)
        dao.insertEmergencyContact(contact1)
        dao.insertEmergencyContact(contact2)

        // Seed Sample Health Assessment (Triage)
        val sampleAssessment = HealthAssessmentEntity(
            id = "assess_1",
            riskLevel = RiskLevel.MODERATE,
            summary = "Acute Epigastric Burning Pain with mild nausea",
            recommendation = "Consult General Physician or Gastroenterologist within 24 hours. Maintain hydration and avoid spicy meals.",
            emergencyWarning = "If pain radiates to chest/back, or sweating occurs, seek emergency care immediately.",
            recommendedSpecialization = "Gastroenterology / General Medicine",
            timestamp = System.currentTimeMillis() - 86400000L
        )
        dao.insertHealthAssessment(sampleAssessment)

        // Seed Sample Appointment & Queue
        val sampleAppt = AppointmentEntity(
            id = "appt_1",
            patientName = "Deepak Mandal",
            doctorId = "doc_3",
            doctorName = "Dr. Tanmoy Ghosh",
            hospitalId = "hosp_sonarpur_rural",
            hospitalName = "Sonarpur Rural Hospital",
            specialization = "General Medicine",
            appointmentDate = "Today, 11:30 AM",
            timeSlot = "11:30 AM - 12:00 PM",
            type = ConsultationType.IN_PERSON,
            status = AppointmentStatus.CONFIRMED,
            tokenNumber = "A-27",
            notes = "Routine seasonal checkup and BP review."
        )
        dao.insertAppointment(sampleAppt)

        val sampleQueue = QueueEntity(
            id = "queue_1",
            hospitalId = "hosp_sonarpur_rural",
            doctorId = "doc_3",
            currentToken = "A-21",
            myToken = "A-27",
            patientsAhead = 6,
            estimatedWaitMinutes = 35,
            status = "ACTIVE - Dr. Tanmoy Ghosh OPD"
        )
        dao.insertQueue(sampleQueue)

        // Seed Sample Referral
        val sampleReferral = ReferralEntity(
            id = "ref_1028",
            patientName = "Sita Devi",
            patientAge = 46,
            patientGender = "Female",
            fromFacility = "Sonarpur Rural Hospital (PHC)",
            toHospitalId = "hosp_peerless",
            toHospitalName = "Peerless Hospital & Research Centre",
            specializationRequired = "Cardiology (Coronary Angiogram)",
            clinicalSummary = "Patient presented with exertional chest heaviness, ECG shows ST depression in leads V4-V6. Stable for transfer.",
            urgency = UrgencyLevel.URGENT,
            status = ReferralStatus.IN_TRANSIT,
            createdByWorker = "Sunita Das (ASHA Community Lead)",
            createdAt = System.currentTimeMillis() - 7200000L,
            updatedAt = System.currentTimeMillis() - 1800000L
        )
        dao.insertReferral(sampleReferral)

        // Seed Sample Medical Report
        val sampleReport = MedicalReportEntity(
            id = "rep_1",
            hospitalName = "Peerless Diagnostic Labs",
            reportType = "Complete Blood Count (CBC)",
            fileName = "CBC_Report_Aug2026.pdf",
            reportDate = "2026-08-22",
            aiAnalysis = "Hemoglobin is 13.8 g/dL (Normal: 13.0 - 17.0). Total Leucocyte Count is 7,400 /mcL (Normal: 4,000 - 11,000). Platelet Count is 245,000 /mcL (Normal: 150,000 - 450,000). All parameters are within normal physiological reference ranges. No acute infection flags detected.",
            verificationStatus = "Verified by Chief Pathologist",
            isPrivate = true
        )
        dao.insertMedicalReport(sampleReport)

        // Seed Sample Prescription
        val samplePrescription = PrescriptionEntity(
            id = "rx_1",
            doctorName = "Dr. Tanmoy Ghosh",
            hospitalName = "Sonarpur Rural Hospital",
            date = "2026-08-20",
            diagnosis = "Acute Acid Peptic Disorder & Mild Pharyngitis",
            instructions = "1. Tab Pantoprazole 40mg (1-0-0) before food for 7 days.\n2. Tab Paracetamol 650mg SOS if temp > 100°F.\n3. Warm saline gargle thrice daily."
        )
        dao.insertPrescription(samplePrescription)

        // Seed Maternal, Child, Chronic
        val maternal = MaternalHealthEntity(
            id = "mat_1",
            motherName = "Priyanka Mondal",
            gestationalWeeks = 28,
            expectedDueDate = "2026-11-14",
            highRiskFactors = "None - Normal Pregnancy",
            lastCheckupDate = "2026-08-10",
            nextCheckupDate = "2026-09-08",
            bloodPressure = "116/74 mmHg",
            hemoglobin = "12.1 g/dL",
            notes = "Third trimester ultrasound scheduled. Fetal movements healthy."
        )
        dao.insertMaternalHealth(maternal)

        val child = ChildHealthEntity(
            id = "child_1",
            childName = "Aarav Mondal",
            dob = "2025-11-04",
            gender = "Male",
            birthWeightKg = 3.2,
            currentWeightKg = 8.6,
            vaccinationsDue = "MR 1st Dose, JE 1st Dose",
            lastVaccinationDate = "2026-05-10",
            notes = "Child active, immunization up-to-date under Universal Immunization Programme."
        )
        dao.insertChildHealth(child)

        val chronic1 = ChronicCareEntity(
            id = "chr_1",
            conditionName = "Essential Hypertension",
            diagnosisDate = "2024-03-12",
            currentMedications = "Tab Telmisartan 40mg once daily after breakfast",
            targetMetrics = "Target BP < 130/80 mmHg",
            lastReading = "124/82 mmHg (Recorded Yesterday)",
            lastCheckupDate = "2026-08-01"
        )
        dao.insertChronicCare(chronic1)

        // Seed API Sync Logs
        val syncLog1 = ApiSyncLogEntity(
            id = "sync_1",
            apiId = "api_peerless",
            hospitalName = "Peerless Hospital & B.K. Roy Research Centre",
            status = SyncStatus.SUCCESS,
            recordsUpdated = 18,
            syncTime = System.currentTimeMillis() - 180000L
        )
        val syncLog2 = ApiSyncLogEntity(
            id = "sync_2",
            apiId = "api_wb_health",
            hospitalName = "Govt. WB Health Directorate Bed Portal",
            status = SyncStatus.SUCCESS,
            recordsUpdated = 34,
            syncTime = System.currentTimeMillis() - 360000L
        )
        dao.insertApiSyncLog(syncLog1)
        dao.insertApiSyncLog(syncLog2)
    }
}
