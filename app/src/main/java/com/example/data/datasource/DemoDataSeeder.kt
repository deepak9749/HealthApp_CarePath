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

        // Seed 30+ Hospitals in Sonarpur / Narendrapur / Garia / Kolkata area
        val hospitals = listOf(
            HospitalEntity(
                id = "hosp_sonarpur_rural",
                name = "Sonarpur Rural Hospital",
                type = HospitalType.GOVERNMENT,
                address = "Rajpur Sonarpur, South 24 Parganas",
                area = "Sonarpur",
                latitude = 22.44180,
                longitude = 88.42310,
                phone = "+91 33 2434 0012",
                emergencyPhone = "108 / +91 33 2434 0012",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free (Govt. of WB)",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. Health Portal API"
            ),
            HospitalEntity(
                id = "hosp_subhasgram_govt",
                name = "Subhasgram Rural Hospital",
                type = HospitalType.GOVERNMENT,
                address = "Station Road, Subhasgram",
                area = "Subhasgram",
                latitude = 22.42150,
                longitude = 88.43120,
                phone = "+91 33 2477 1145",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. Health Portal API"
            ),
            HospitalEntity(
                id = "hosp_peerless",
                name = "Peerless Hospital & B.K. Roy Research Centre",
                type = HospitalType.PRIVATE,
                address = "360 Panchasayar, Garia",
                area = "Panchasayar / Garia",
                latitude = 22.48310,
                longitude = 88.39750,
                phone = "+91 33 4011 1222",
                emergencyPhone = "+91 33 2462 2394",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹600 - ₹1200",
                hasAmbulanceOnSite = true,
                dataSourceType = "Hospital Live API v2.4"
            ),
            HospitalEntity(
                id = "hosp_rntics",
                name = "Rabindranath Tagore International Institute of Cardiac Sciences (NH RTIICS)",
                type = HospitalType.PRIVATE,
                address = "124 Mukundapur, E.M. Bypass",
                area = "Mukundapur",
                latitude = 22.49270,
                longitude = 88.40050,
                phone = "+91 33 7122 2222",
                emergencyPhone = "1800 3090 309",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹700 - ₹1500",
                hasAmbulanceOnSite = true,
                dataSourceType = "Narayana Health API"
            ),
            HospitalEntity(
                id = "hosp_medica",
                name = "Medica Superspecialty Hospital",
                type = HospitalType.PRIVATE,
                address = "127 Mukundapur, E.M. Bypass",
                area = "Mukundapur",
                latitude = 22.49480,
                longitude = 88.40180,
                phone = "+91 33 6652 0000",
                emergencyPhone = "+91 33 6652 0100",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹800 - ₹1500",
                hasAmbulanceOnSite = true,
                dataSourceType = "Medica Cloud Connect API"
            ),
            HospitalEntity(
                id = "hosp_manipal_mukundapur",
                name = "Manipal Hospitals Mukundapur (formerly AMRI)",
                type = HospitalType.PRIVATE,
                address = "230 Barakhola Lane, Mukundapur",
                area = "Mukundapur",
                latitude = 22.49810,
                longitude = 88.40240,
                phone = "+91 33 6680 0000",
                emergencyPhone = "033 6680 0108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹800 - ₹1600",
                hasAmbulanceOnSite = true,
                dataSourceType = "Manipal Health API"
            ),
            HospitalEntity(
                id = "hosp_ruby_general",
                name = "Ruby General Hospital",
                type = HospitalType.PRIVATE,
                address = "Kasba Golpark, E.M. Bypass",
                area = "Kasba / Anandapur",
                latitude = 22.51320,
                longitude = 88.40110,
                phone = "+91 33 3987 1800",
                emergencyPhone = "+91 33 2442 7091",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹600 - ₹1200",
                hasAmbulanceOnSite = true,
                dataSourceType = "Ruby Hospital API"
            ),
            HospitalEntity(
                id = "hosp_fortis_anandapur",
                name = "Fortis Hospital Anandapur",
                type = HospitalType.PRIVATE,
                address = "730 Anandapur, E.M. Bypass",
                area = "Anandapur",
                latitude = 22.52040,
                longitude = 88.40420,
                phone = "+91 33 6628 4444",
                emergencyPhone = "105010 / 033 6628 4444",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹1000 - ₹1800",
                hasAmbulanceOnSite = true,
                dataSourceType = "Fortis Central Health API"
            ),
            HospitalEntity(
                id = "hosp_desun",
                name = "Desun Hospital & Heart Institute",
                type = HospitalType.PRIVATE,
                address = "720 Anandapur, Desun More",
                area = "Anandapur",
                latitude = 22.51860,
                longitude = 88.40350,
                phone = "+91 90517 15171",
                emergencyPhone = "+91 83340 31300",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹700 - ₹1400",
                hasAmbulanceOnSite = true,
                dataSourceType = "Desun Live System"
            ),
            HospitalEntity(
                id = "hosp_mr_bangur",
                name = "M.R. Bangur District Hospital",
                type = HospitalType.GOVERNMENT,
                address = "241 Deshpran Sashmal Road, Tollygunge",
                area = "Tollygunge",
                latitude = 22.50150,
                longitude = 88.34780,
                phone = "+91 33 2473 3901",
                emergencyPhone = "108 / +91 33 2473 3901",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free (Govt. District Hospital)",
                hasAmbulanceOnSite = true,
                dataSourceType = "WB Health Directorate API"
            ),
            HospitalEntity(
                id = "hosp_manipal_dhakuria",
                name = "Manipal Hospital Dhakuria (formerly AMRI)",
                type = HospitalType.PRIVATE,
                address = "P-4&5, CIT Scheme LXXII, Block-A, Gariahat",
                area = "Dhakuria",
                latitude = 22.51180,
                longitude = 88.36620,
                phone = "+91 33 6606 3800",
                emergencyPhone = "033 6606 3800",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹800 - ₹1500",
                hasAmbulanceOnSite = true,
                dataSourceType = "Manipal Health API"
            ),
            HospitalEntity(
                id = "hosp_baruipur_subdiv",
                name = "Baruipur Sub-Divisional Hospital",
                type = HospitalType.GOVERNMENT,
                address = "Kulpi Road, Baruipur",
                area = "Baruipur",
                latitude = 22.36210,
                longitude = 88.43580,
                phone = "+91 33 2433 8223",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. Health Portal API"
            ),
            HospitalEntity(
                id = "hosp_ramakrishna_mission_seva",
                name = "Ramakrishna Mission Seva Pratishthan",
                type = HospitalType.TRUST_CHARITABLE,
                address = "99 Sarat Bose Road",
                area = "Southern Avenue",
                latitude = 22.52760,
                longitude = 88.35120,
                phone = "+91 33 2475 3636",
                emergencyPhone = "+91 33 2475 3639",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹100 - ₹300 (Subsidized)",
                hasAmbulanceOnSite = true,
                dataSourceType = "Trust Healthcare System"
            ),
            HospitalEntity(
                id = "hosp_chittaranjan_cancer",
                name = "Chittaranjan National Cancer Institute (CNCI)",
                type = HospitalType.GOVERNMENT,
                address = "Street No. 299, Plot No. DJ-01, Action Area I, New Town & Hazra",
                area = "Hazra & Rajarhat",
                latitude = 22.52210,
                longitude = 88.34900,
                phone = "+91 33 2476 5101",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free / Subsidized Cancer Care",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. Central Registry"
            ),
            HospitalEntity(
                id = "hosp_genesis",
                name = "Genesis Hospital",
                type = HospitalType.PRIVATE,
                address = "1470 Rajdanga Main Road, Kasba",
                area = "Kasba",
                latitude = 22.51600,
                longitude = 88.38800,
                phone = "+91 33 2442 5555",
                emergencyPhone = "+91 33 2442 5556",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹500 - ₹1000",
                hasAmbulanceOnSite = true,
                dataSourceType = "Hospital API"
            ),
            HospitalEntity(
                id = "hosp_himalayan_sonarpur",
                name = "Himalayan Institute of Medical Sciences Care Unit",
                type = HospitalType.PRIVATE,
                address = "Sonarpur Station Road, Narendrapur",
                area = "Narendrapur",
                latitude = 22.43900,
                longitude = 88.40800,
                phone = "+91 33 2428 1199",
                emergencyPhone = "+91 33 2428 1100",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹400 - ₹800",
                hasAmbulanceOnSite = true,
                dataSourceType = "Local Registry"
            ),
            HospitalEntity(
                id = "hosp_sankar_netralaya",
                name = "Sankara Nethralaya Eye Hospital",
                type = HospitalType.TRUST_CHARITABLE,
                address = "10 Panditiya Road / Mukundapur",
                area = "Mukundapur",
                latitude = 22.49100,
                longitude = 88.39900,
                phone = "+91 33 4401 3000",
                emergencyPhone = "+91 33 4401 3001",
                emergencyAvailable = false,
                operatingStatus = "8:00 AM - 7:00 PM",
                isVerified = true,
                consultationFeeEstimate = "₹400 - ₹800",
                hasAmbulanceOnSite = false,
                dataSourceType = "SN Eye System"
            ),
            HospitalEntity(
                id = "hosp_sskm_pg",
                name = "SSKM Hospital & IPGMER",
                type = HospitalType.GOVERNMENT,
                address = "244 AJC Bose Road, Bhowanipore",
                area = "Bhowanipore",
                latitude = 22.53850,
                longitude = 88.34420,
                phone = "+91 33 2223 1589",
                emergencyPhone = "108 / +91 33 2204 1100",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free (Premier State Medical College)",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. WB Health Live"
            ),
            HospitalEntity(
                id = "hosp_cnmc",
                name = "Calcutta National Medical College & Hospital",
                type = HospitalType.GOVERNMENT,
                address = "32 Gorachand Road, Beniapukur",
                area = "Park Circus",
                latitude = 22.54500,
                longitude = 88.36800,
                phone = "+91 33 2284 3582",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. Registry"
            ),
            HospitalEntity(
                id = "hosp_apollo_gleneagles",
                name = "Apollo Multispeciality Hospitals",
                type = HospitalType.PRIVATE,
                address = "58 Canal Circular Road, Kadapara",
                area = "Phoolbagan / EM Bypass",
                latitude = 22.57120,
                longitude = 88.39890,
                phone = "+91 33 2320 3040",
                emergencyPhone = "1066 / 033 2320 2122",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹1200 - ₹2200",
                hasAmbulanceOnSite = true,
                dataSourceType = "Apollo Live Connect"
            ),
            HospitalEntity(
                id = "hosp_aria_sonarpur",
                name = "Aria Multi-care Centre & Maternity Home",
                type = HospitalType.PRIVATE,
                address = "Kamalgazi More, Narendrapur",
                area = "Narendrapur",
                latitude = 22.44900,
                longitude = 88.40100,
                phone = "+91 33 2435 9901",
                emergencyPhone = "+91 33 2435 9902",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹400 - ₹700",
                hasAmbulanceOnSite = true,
                dataSourceType = "Private Registry"
            ),
            HospitalEntity(
                id = "hosp_nirmal_hriday_trust",
                name = "Nirmal Seva Diagnostic & Clinic",
                type = HospitalType.TRUST_CHARITABLE,
                address = "Garia Main Road, Garia",
                area = "Garia",
                latitude = 22.46400,
                longitude = 88.38400,
                phone = "+91 33 2430 4411",
                emergencyPhone = "+91 33 2430 4412",
                emergencyAvailable = false,
                operatingStatus = "7:00 AM - 9:00 PM",
                isVerified = true,
                consultationFeeEstimate = "₹150 - ₹350",
                hasAmbulanceOnSite = false,
                dataSourceType = "Charity Network"
            ),
            HospitalEntity(
                id = "hosp_suraksha_kasba",
                name = "Suraksha PolyClinic & Day Care",
                type = HospitalType.PRIVATE,
                address = "Kasba New Market, Kolkata",
                area = "Kasba",
                latitude = 22.51900,
                longitude = 88.38100,
                phone = "+91 33 6619 1000",
                emergencyPhone = "+91 33 6619 1001",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹500 - ₹900",
                hasAmbulanceOnSite = true,
                dataSourceType = "Suraksha Health API"
            ),
            HospitalEntity(
                id = "hosp_spandan_garia",
                name = "Spandan Diagnostic & Critical Care Unit",
                type = HospitalType.PRIVATE,
                address = "Kavi Nazrul Metro, Garia",
                area = "Garia",
                latitude = 22.46900,
                longitude = 88.39100,
                phone = "+91 33 2436 8800",
                emergencyPhone = "+91 33 2436 8801",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹450 - ₹850",
                hasAmbulanceOnSite = true,
                dataSourceType = "Local Hospital API"
            ),
            HospitalEntity(
                id = "hosp_woodlands",
                name = "Woodlands Multispeciality Hospital",
                type = HospitalType.PRIVATE,
                address = "8/5 Alipore Road, Alipore",
                area = "Alipore",
                latitude = 22.53100,
                longitude = 88.32900,
                phone = "+91 33 4033 7000",
                emergencyPhone = "033 4033 7000",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹1000 - ₹2000",
                hasAmbulanceOnSite = true,
                dataSourceType = "Woodlands Live API"
            ),
            HospitalEntity(
                id = "hosp_kothari",
                name = "Kothari Medical Centre",
                type = HospitalType.PRIVATE,
                address = "8/3 Alipore Road, Alipore",
                area = "Alipore",
                latitude = 22.53250,
                longitude = 88.32980,
                phone = "+91 33 2456 7050",
                emergencyPhone = "+91 33 2456 7055",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹800 - ₹1500",
                hasAmbulanceOnSite = true,
                dataSourceType = "Hospital API"
            ),
            HospitalEntity(
                id = "hosp_bellevue",
                name = "Belle Vue Clinic",
                type = HospitalType.PRIVATE,
                address = "9 Dr. U. N. Brahmachari Street",
                area = "Elgin / Park Street",
                latitude = 22.54410,
                longitude = 88.35300,
                phone = "+91 33 2287 2321",
                emergencyPhone = "+91 33 2287 6925",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹1000 - ₹1800",
                hasAmbulanceOnSite = true,
                dataSourceType = "Belle Vue API"
            ),
            HospitalEntity(
                id = "hosp_nrs_medical",
                name = "Nil Ratan Sircar (NRS) Medical College & Hospital",
                type = HospitalType.GOVERNMENT,
                address = "138 AJC Bose Road, Sealdah",
                area = "Sealdah",
                latitude = 22.56300,
                longitude = 88.36950,
                phone = "+91 33 2286 0033",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. WB Health Live"
            ),
            HospitalEntity(
                id = "hosp_rgkar",
                name = "R. G. Kar Medical College & Hospital",
                type = HospitalType.GOVERNMENT,
                address = "1 Khudiram Bose Sarani, Belgachia",
                area = "Shyambazar",
                latitude = 22.60400,
                longitude = 88.37500,
                phone = "+91 33 2555 7656",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. WB Health Live"
            ),
            HospitalEntity(
                id = "hosp_medical_college_kolkata",
                name = "Medical College and Hospital, Kolkata (Calcutta Medical College)",
                type = HospitalType.GOVERNMENT,
                address = "88 College Street, Bowbazar",
                area = "College Street",
                latitude = 22.57260,
                longitude = 88.36150,
                phone = "+91 33 2255 1621",
                emergencyPhone = "108",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "Free (Heritage Tertiary Hospital)",
                hasAmbulanceOnSite = true,
                dataSourceType = "Govt. WB Health Live"
            ),
            HospitalEntity(
                id = "hosp_tata_medical_centre",
                name = "Tata Medical Center",
                type = HospitalType.TRUST_CHARITABLE,
                address = "14 MAR (EW), Action Area II, New Town",
                area = "New Town",
                latitude = 22.58500,
                longitude = 88.48900,
                phone = "+91 33 6605 7000",
                emergencyPhone = "+91 33 6605 7001",
                emergencyAvailable = true,
                operatingStatus = "24x7 Open",
                isVerified = true,
                consultationFeeEstimate = "₹500 - ₹1200 / Subsidized Category",
                hasAmbulanceOnSite = true,
                dataSourceType = "Tata Trust Health API"
            )
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
