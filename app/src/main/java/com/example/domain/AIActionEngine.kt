package com.example.domain

data class AIResponse(
    val replyText: String,
    val actionType: String? = null,
    val actionLabel: String? = null,
    val actionPayload: String? = null
)

object AIActionEngine {

    fun isCarePathFeatureRequest(userQuery: String): Boolean {
        val lower = userQuery.lowercase().trim()

        val chips = listOf(
            "request emergency ambulance", "find icu beds near me", "show government hospitals",
            "check my symptoms (triage)", "explain blood test report", "book a doctor appointment",
            "search medicines in pharmacy", "check my symptoms", "check symptoms"
        )
        if (chips.any { lower.contains(it) }) return true

        if (lower.contains("ambulance") || lower.contains("ambulence") || lower.contains("অ্যাম্বুলেন্স") || lower.contains("एम्बुलेंस") ||
            lower.contains("emergency") || lower.contains("জরুরি") || lower.contains("आपातकाल") || lower.contains("sos") ||
            lower.contains("108") || lower.contains("save me")) {
            if (lower.contains("call") || lower.contains("need") || lower.contains("request") || lower.contains("dispatch") ||
                lower.contains("send") || lower.contains("emergency") || lower.contains("sos") || lower.contains("ডাকুন") ||
                lower.contains("চাই") || lower.contains("चाहिए")) {
                return true
            }
        }

        val hasHospital = lower.contains("hospital") || lower.contains("হাসপাতাল") || lower.contains("अस्पताल")
        val hasSearchOrNeed = lower.contains("need") || lower.contains("find") || lower.contains("nearby") ||
                              lower.contains("near me") || lower.contains("show") || lower.contains("search") ||
                              lower.contains("view") || lower.contains("locate") || lower.contains("খুঁজুন") ||
                              lower.contains("দেখান") || lower.contains("দরকার") || lower.contains("কাছে") ||
                              lower.contains("पास") || lower.contains("ढूंढें") || lower.contains("चाहिए")
        if (hasHospital && hasSearchOrNeed) return true

        if (lower.contains("icu") || lower.contains("ventilator") || lower.contains("আইসিইউ") || lower.contains("आईसीयू")) return true
        if (lower.contains("govt hospital") || lower.contains("government hospital") || lower.contains("nearest hospital")) return true

        val hasDoctor = lower.contains("doctor") || lower.contains("ডাক্তার") || lower.contains("डॉक्टर")
        val hasDocAction = lower.contains("appointment") || lower.contains("consult") || lower.contains("book") ||
                           lower.contains("need") || lower.contains("find") || lower.contains("see a doctor") ||
                           lower.contains("বুকিং") || lower.contains("পরামর্শ") || lower.contains("দেখান") ||
                           lower.contains("अपॉइंटमेंट") || lower.contains("दिखाना")
        if (hasDoctor && hasDocAction) return true

        if (lower.contains("check symptoms") || lower.contains("check my symptoms") || lower.contains("start triage") ||
            lower.contains("symptom triage") || lower.contains("triage assessment") || lower.contains("digital triage") ||
            lower.contains("লক্ষণ পরীক্ষা") || lower.contains("উপসর্গ") || lower.contains("लक्षण जांच")) {
            return true
        }

        if (lower.contains("view report") || lower.contains("upload report") || lower.contains("medical report") ||
            lower.contains("my prescription") || lower.contains("view prescription") || lower.contains("track referral") ||
            lower.contains("live queue") || lower.contains("opd token") || lower.contains("pharmacy stock") ||
            lower.contains("search medicine") || lower.contains("ওষুধ") || lower.contains("दवा")) {
            return true
        }

        return false
    }

    fun processDirectFeatureQuery(userQuery: String, userLang: String = "EN"): AIResponse {
        val lower = userQuery.lowercase().trim()
        val lang = userLang.uppercase()

        return when {
            lower.contains("ambulance") || lower.contains("ambulence") || lower.contains("অ্যাম্বুলেন্স") || lower.contains("এম্বুলেন্স") || lower.contains("एम्बुलेंस") -> {
                val reply = when (lang) {
                    "HI" -> "मैं तुरंत आपके वर्तमान स्थान पर आपातकालीन एम्बुलेंस भेजने में मदद कर सकता हूँ।"
                    "BN" -> "আমি অবিলম্বে আপনার বর্তমান অবস্থানে একটি জরুরি অ্যাম্বুলেন্স পাঠানোর ব্যবস্থা করতে পারি।"
                    else -> "I can immediately help you coordinate and request an emergency ambulance dispatched to your current location."
                }
                val label = when (lang) {
                    "HI" -> "अभी एम्बुलेंस का अनुरोध करें"
                    "BN" -> "এখনই অ্যাম্বুলেন্স ডাকুন"
                    else -> "Request Ambulance Now"
                }
                AIResponse(replyText = reply, actionType = "NAV_AMBULANCE", actionLabel = label, actionPayload = "open_ambulance")
            }
            lower.contains("emergency") || lower.contains("জরুরি") || lower.contains("आपातकाल") || lower.contains("sos") -> {
                val reply = when (lang) {
                    "HI" -> "🚨 आपातकालीन सहायता। त्वरित प्रतिक्रिया और 108 सेवा के लिए कृपया केयरपाथ आपातकालीन केंद्र खोलें।"
                    "BN" -> "🚨 জরুরি সহায়তা। তাৎক্ষণিক সেবা এবং ১০৮ সহায়তার জন্য অনুগ্রহ করে কেয়ারপাথ জরুরি কেন্দ্রটি খুলুন।"
                    else -> "🚨 Accessing Emergency Assistance. Please open the CarePath Emergency Center immediately for instant response and 108 dispatch."
                }
                val label = when (lang) {
                    "HI" -> "आपातकालीन केंद्र खोलें (SOS)"
                    "BN" -> "জরুরি কেন্দ্র খুলুন (SOS)"
                    else -> "Open Emergency Center (SOS)"
                }
                AIResponse(replyText = reply, actionType = "NAV_EMERGENCY", actionLabel = label, actionPayload = "open_emergency")
            }
            lower.contains("icu") || lower.contains("ventilator") || lower.contains("critical care") || lower.contains("আইসিইউ") || lower.contains("आईसीयू") -> {
                val reply = when (lang) {
                    "HI" -> "सोनारपुर, नरेंद्रपुर और कोलकाता में लाइव उपलब्ध आईसीयू और क्रिटिकल केयर बेड वाले नजदीकी अस्पताल।"
                    "BN" -> "সোনারপুর, নরেন্দ্রপুর এবং কলকাতায় রিয়েল-টাইম আইসিইউ এবং ক্রিটিক্যাল কেয়ার বেড উপলব্ধ থাকা নিকটবর্তী হাসপাতালসমূহ।"
                    else -> "Here are the nearby hospitals with live available ICU and Critical Care beds in Sonarpur, Narendrapur, and Kolkata."
                }
                val label = when (lang) {
                    "HI" -> "आईसीयू बेड वाले अस्पताल दिखाएं"
                    "BN" -> "আইসিইউ বেডযুক্ত হাসপাতাল দেখুন"
                    else -> "Show Hospitals with ICU Beds"
                }
                AIResponse(replyText = reply, actionType = "NAV_HOSPITALS_ICU", actionLabel = label, actionPayload = "filter_icu")
            }
            lower.contains("government") || lower.contains("govt") || lower.contains("সরকারি") || lower.contains("सरकारी") -> {
                val reply = when (lang) {
                    "HI" -> "मुफ्त सार्वजनिक स्वास्थ्य सेवाएं और 24x7 आपातकालीन देखभाल प्रदान करने वाले सत्यापित सरकारी अस्पताल।"
                    "BN" -> "বিনামূল্যে স্বাস্থ্যসেবা এবং ২৪x৭ জরুরি সেবা প্রদানকারী যাচাইকৃত সরকারি হাসপাতালসমূহ প্রদর্শিত হচ্ছে।"
                    else -> "Displaying verified Government hospitals and rural health centres offering free public health services and 24x7 emergency care."
                }
                val label = when (lang) {
                    "HI" -> "सरकारी अस्पताल दिखाएं"
                    "BN" -> "সরকারি হাসপাতাল দেখুন"
                    else -> "Show Government Hospitals"
                }
                AIResponse(replyText = reply, actionType = "NAV_HOSPITALS_GOVT", actionLabel = label, actionPayload = "filter_govt")
            }
            lower.contains("hospital") || lower.contains("near me") || lower.contains("directions") ||
            lower.contains("হাসপাতাল") || lower.contains("अस्पताल") -> {
                val reply = when (lang) {
                    "HI" -> "मैंने वास्तविक समय की जीपीएस दूरी के अनुसार निकटतम सत्यापित चिकित्सा केंद्रों की पहचान की है।"
                    "BN" -> "আমি রিয়েল-টাইম জিপিএস দূরত্ব অনুযায়ী নিকটতম যাচাইকৃত চিকিৎসা কেন্দ্রগুলো চিহ্নিত করেছি।"
                    else -> "I have identified the nearest verified medical facilities ranked dynamically by real-time GPS distance."
                }
                val label = when (lang) {
                    "HI" -> "निकटतम अस्पताल खोजें"
                    "BN" -> "নিকটবর্তী হাসপাতাল খুঁজুন"
                    else -> "Find Nearest Hospitals"
                }
                AIResponse(replyText = reply, actionType = "NAV_HOSPITALS_NEAREST", actionLabel = label, actionPayload = "filter_distance")
            }
            lower.contains("doctor") || lower.contains("appointment") || lower.contains("consult") || lower.contains("teleconsult") || lower.contains("ডাক্তার") || lower.contains("डॉक्टर") -> {
                val reply = when (lang) {
                    "HI" -> "आप विभिन्न विशेषताओं (कार्डियोलॉजी, न्यूरोलॉजी, मेडिसिन) में डॉक्टरों को खोज सकते हैं और अपॉइंटमेंट बुक कर सकते हैं।"
                    "BN" -> "আপনি বিভিন্ন বিশেষজ্ঞ ডাক্তারদের অনুসন্ধান করতে পারেন এবং ওপিডি বা টেলি-পরামর্শ বুক করতে পারেন।"
                    else -> "You can search verified doctors across specializations (Cardiology, Neurology, Medicine, Orthopedics) and book OPD or teleconsultation slots."
                }
                val label = when (lang) {
                    "HI" -> "डॉक्टर अपॉइंटमेंट बुक करें"
                    "BN" -> "ডাক্তারের অ্যাপয়েন্টমেন্ট নিন"
                    else -> "Book Doctor Appointment"
                }
                AIResponse(replyText = reply, actionType = "NAV_DOCTORS", actionLabel = label, actionPayload = "open_doctors")
            }
            lower.contains("report") || lower.contains("blood test") || lower.contains("cbc") || lower.contains("x-ray") || lower.contains("রিপোর্ট") || lower.contains("रिपोर्ट") -> {
                val reply = when (lang) {
                    "HI" -> "शब्दावली, सामान्य संदर्भ श्रेणियों और मापदंडों की व्याख्या प्राप्त करने के लिए अपनी मेडिकल रिपोर्ट (पीडीएफ या छवि) अपलोड करें।"
                    "BN" -> "পরিভাষা এবং স্বাভাবিক রেফারেন্স রেঞ্জ সহজে বোঝার জন্য আপনার মেডিকেল রিপোর্ট (PDF বা ছবি) আপলোড করুন।"
                    else -> "Upload your medical report (PDF or Image) to receive an educational explanation of terminology, normal reference ranges, and parameters. (Note: AI explanations do not replace a physician's diagnosis)."
                }
                val label = when (lang) {
                    "HI" -> "मेडिकल रिपोर्ट देखें / अपलोड करें"
                    "BN" -> "মেডিকেল রিপোর্ট দেখুন / আপলোড করুন"
                    else -> "View / Upload Medical Reports"
                }
                AIResponse(replyText = reply, actionType = "NAV_REPORTS", actionLabel = label, actionPayload = "open_reports")
            }
            lower.contains("prescription") || lower.contains("rx") || lower.contains("প্রেসক্রিপশন") || lower.contains("पर्चा") -> {
                val reply = when (lang) {
                    "HI" -> "दवा कार्यक्रम और सामान्य उपयोग निर्देशों के लिए अपने नुस्खे अपलोड करें या देखें।"
                    "BN" -> "ঔষধের সময়সূচী এবং সাধারণ নির্দেশাবলীর জন্য আপনার প্রেসক্রিপশন দেখুন বা আপলোড করুন।"
                    else -> "Upload or view your prescriptions for structured medication schedules, dosage guidelines, and general usage instructions."
                }
                val label = when (lang) {
                    "HI" -> "पर्चे देखें"
                    "BN" -> "প্রেসক্রিপশন খুলুন"
                    else -> "Open Prescriptions"
                }
                AIResponse(replyText = reply, actionType = "NAV_PRESCRIPTIONS", actionLabel = label, actionPayload = "open_prescriptions")
            }
            lower.contains("pharmacy") || lower.contains("medicine") || lower.contains("tablet") || lower.contains("ওষুধ") || lower.contains("ফার্মেসি") || lower.contains("दवा") -> {
                val reply = when (lang) {
                    "HI" -> "अस्पताल फार्मेसी में जेनेरिक दवाओं के नाम, खुराक दिशानिर्देश और सत्यापित स्टॉक उपलब्धता खोजें।"
                    "BN" -> "হাসপাতাল ফার্মেসিতে জেনেরিক ওষুধের নাম, ব্যবহারের নির্দেশিকা এবং যাচাইকৃত স্টক প্রাপ্যতা অনুসন্ধান করুন।"
                    else -> "Search generic medicine names, usage guidelines, standard dosages, and verified stock availability across hospital pharmacies."
                }
                val label = when (lang) {
                    "HI" -> "दवा उपलब्धता खोजें"
                    "BN" -> "ওষুধের স্টক খুঁজুন"
                    else -> "Search Medicine Availability"
                }
                AIResponse(replyText = reply, actionType = "NAV_MEDICINES", actionLabel = label, actionPayload = "open_medicines")
            }
            lower.contains("referral") || lower.contains("queue") || lower.contains("token") || lower.contains("wait") || lower.contains("সিরিয়াল") || lower.contains("कतार") -> {
                val reply = when (lang) {
                    "HI" -> "रीयल-टाइम में अपने रेफरल स्थिति और लाइव ओपीडी कतार प्रतीक्षा समय को ट्रैक करें।"
                    "BN" -> "রিয়েল-টাইমে আপনার রেফারেল স্থিতি এবং লাইভ ওপিডি টোকেন অপেক্ষার সময় ট্র্যাক করুন।"
                    else -> "Track your inter-facility clinical referral status and live OPD queue token wait time in real-time."
                }
                val label = when (lang) {
                    "HI" -> "रेफरल और कतार ट्रैक करें"
                    "BN" -> "রেফারেল ও লাইভ কিউ ট্র্যাক করুন"
                    else -> "Track Referrals & Live Queue"
                }
                AIResponse(replyText = reply, actionType = "NAV_REFERRALS", actionLabel = label, actionPayload = "open_referrals")
            }
            lower.contains("symptom") || lower.contains("triage") || lower.contains("লক্ষণ") || lower.contains("लक्षण") -> {
                val reply = when (lang) {
                    "HI" -> "आपके लक्षणों, गंभीरता (1-10) का मूल्यांकन करने के लिए डिजिटल ट्राइएज मूल्यांकन शुरू करें।"
                    "BN" -> "আপনার লক্ষণ এবং তীব্রতা (১-১০) মূল্যায়ন করে প্রয়োজনীয় পরামর্শ পেতে ট্রায়াজ মূল্যায়ন শুরু করুন।"
                    else -> "Let's perform a digital triage assessment to evaluate your symptoms, severity (1-10), and determine the recommended level of care."
                }
                val label = when (lang) {
                    "HI" -> "लक्षण मूल्यांकन शुरू करें"
                    "BN" -> "লক্ষণ পরীক্ষা শুরু করুন"
                    else -> "Start Symptom Assessment"
                }
                AIResponse(replyText = reply, actionType = "NAV_SYMPTOMS", actionLabel = label, actionPayload = "start_triage")
            }
            else -> {
                AIResponse(replyText = "", actionType = null, actionLabel = null, actionPayload = null)
            }
        }
    }

    fun hasEmergencySymptoms(text: String): Boolean {
        val lower = text.lowercase()
        return lower.contains("chest pain") || lower.contains("heart attack") ||
               lower.contains("stroke") || lower.contains("difficulty breathing") ||
               lower.contains("unconscious") || lower.contains("severe bleeding") ||
               (lower.contains("বুকে") && lower.contains("ব্যথা")) || lower.contains("হার্ট অ্যাটাক") ||
               ((lower.contains("सीने") || lower.contains("छाती")) && lower.contains("दर्द")) || lower.contains("बेहोश")
    }

    fun getFallbackMedicalResponse(userLang: String = "EN", hasEmergency: Boolean = false): AIResponse {
        val lang = userLang.uppercase()
        if (hasEmergency) {
            val reply = when (lang) {
                "HI" -> "🚨 गंभीर चिकित्सा सूचना: वर्णित लक्षण किसी आपात स्थिति का संकेत हो सकते हैं। कृपया तुरंत आपातकालीन चिकित्सा सहायता लें।"
                "BN" -> "🚨 জরুরি চিকিৎসা সতর্কতা: উল্লেখিত লক্ষণগুলো জরুরি পরিস্থিতির ইঙ্গিত হতে পারে। অনুগ্রহ করে অবিলম্বে জরুরি সেবা নিন।"
                else -> "🚨 CRITICAL MEDICAL NOTICE: The symptoms described may indicate an acute emergency. Please seek immediate medical care or use CarePath Emergency SOS right away."
            }
            val label = when (lang) {
                "HI" -> "आपातकालीन केंद्र खोलें (SOS)"
                "BN" -> "জরুরি কেন্দ্র খুলুন (SOS)"
                else -> "Open Emergency Center (SOS)"
            }
            return AIResponse(replyText = reply, actionType = "NAV_EMERGENCY", actionLabel = label, actionPayload = "open_emergency")
        }

        val reply = when (lang) {
            "HI" -> "CarePath AI सहायता के लिए उपलब्ध है। कृपया अपने लक्षण बताएं या स्वास्थ्य से जुड़े प्रश्न पूछें।"
            "BN" -> "CarePath এআই সহায়তার জন্য প্রস্তুত। আপনার লক্ষণগুলো জানান অথবা স্বাস্থ্য সম্পর্কিত যেকোনো প্রশ্ন জিজ্ঞাসা করুন।"
            else -> "CarePath Medical AI is here to help. Feel free to describe your symptoms or ask healthcare-related questions. For serious concerns, please consult a healthcare professional."
        }
        return AIResponse(replyText = reply, actionType = null, actionLabel = null, actionPayload = null)
    }
}
