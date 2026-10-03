package com.example.ui.i18n

object AppTranslations {

    private val hindiMap: Map<String, String> = mapOf(
        // Navigation & Top Bar
        "Home" to "होम",
        "Hospitals" to "अस्पताल",
        "Doctors" to "डॉक्टर्स",
        "Timeline" to "टाइमलाइन",
        "Profile" to "प्रोफ़ाइल",
        "EMERGENCY" to "आपातकाल",
        "Back" to "पीछे",
        "Close" to "बंद करें",
        "Done" to "पूर्ण",
        "Cancel" to "रद्द करें",
        "Save" to "सहेजें",
        "Search" to "खोजें",
        "Clear" to "साफ़ करें",
        "Select Location" to "स्थान चुनें",
        "Switch Role / Portal" to "रोल / पोर्टल बदलें",
        "Select Language / ভাষা / भाषा" to "भाषा चुनें / भाषा / ভাষা",
        "Select Language" to "भाषा चुनें",
        "Guest Mode" to "गेस्ट मोड",
        "Patient" to "मरीज",
        "ASHA Worker" to "आशा कार्यकर्ता",
        "Hospital Staff" to "अस्पताल स्टाफ",
        "Govt Admin" to "सरकारी प्रशासक",
        "Admin" to "प्रशासक",
        "Frontline Health Worker" to "फ्रंटलाइन स्वास्थ्य कार्यकर्ता",
        "Government Admin" to "सरकारी स्वास्थ्य प्रशासक",

        // Hero & Branding
        "CARE PATH" to "केयर पाथ",
        "SUSHRUTA" to "सुश्रुत",
        "From Sushruta’s wisdom to Jeevan Shakti’s innovation — Care Path, a path towards better health." to
            "सुश्रुत के ज्ञान से जीवन शक्ति के नवाचार तक — केयर पाथ, बेहतर स्वास्थ्य का मार्ग।",
        "GPS coordinates dynamically calculate nearby hospital distances, ICU beds, and ambulance response times." to
            "जीपीएस निर्देशांक निकटवर्ती अस्पतालों की दूरी, आईसीयू बेड और एम्बुलेंस प्रतिक्रिया समय की गणना करते हैं।",
        "Smart Health System provides specialized role-based views for patients, frontline workers, hospitals, and health administrators." to
            "स्मार्ट हेल्थ सिस्टम मरीजों, स्वास्थ्य कार्यकर्ताओं, अस्पतालों और प्रशासकों के लिए विशेष भूमिका-आधारित सुविधाएं प्रदान करता है।",
        "Guest Mode (Zero Mandatory Login - Instant Emergency)" to
            "गेस्ट मोड (शून्य अनिवार्य लॉगिन - त्वरित आपातकालीन सहायता)",
        "Registered Patient (Timeline, Records, Prescriptions)" to
            "पंजीकृत मरीज (टाइमलाइन, रिकॉर्ड, नुस्खे)",
        "Frontline Health Worker (ASHA / ANM Field Triage & Referrals)" to
            "फ्रंटलाइन स्वास्थ्य कार्यकर्ता (आशा / एएनएम फील्ड ट्राइएज और रेफरल)",
        "Hospital Staff (Bed Management, Queues, OPD Slots)" to
            "अस्पताल स्टाफ (बेड प्रबंधन, कतारें, ओपीडी स्लॉट)",
        "Government Admin (Quality Monitoring, Response Times, API Logs)" to
            "सरकारी प्रशासक (गुणवत्ता निगरानी, प्रतिक्रिया समय, सिस्टम लॉग)",

        // Home Section Headers & Cards
        "Nearest 24x7 Emergency Facility" to "निकटतम 24x7 आपातकालीन सुविधा",
        "How can we help you?" to "हम आपकी क्या सहायता कर सकते हैं?",
        "Check Symptoms" to "लक्षण जांचें",
        "Digital Triage (1-10)" to "डिजिटल ट्राइएज (1-10)",
        "Nearby Hospitals" to "निकटवर्ती अस्पताल",
        "30+ Facilities & ICU" to "30+ सुविधाएं और आईसीयू",
        "Emergency Help" to "आपातकालीन सहायता",
        "SOS & Ambulance" to "एसओएस और एम्बुलेंस",
        "Talk to AI" to "एआई से बात करें",
        "Healthcare Assistant" to "स्वास्थ्य सहायक",
        "Secure & Private" to "सुरक्षित और निजी",
        "Your health,\nour priority" to "आपका स्वास्थ्य,\nहमारी प्राथमिकता",
        "Fast & Reliable" to "त्वरित और विश्वसनीय",
        "Quick help when\nyou need it" to "जरूरत पड़ने पर\nत्वरित मदद",
        "Trusted Network" to "विश्वसनीय नेटवर्क",
        "Verified hospitals\n& professionals" to "सत्यापित अस्पताल\nऔर विशेषज्ञ",
        "Always With You" to "सदैव आपके साथ",
        "Compassionate\ncare, always" to "सहानुभूतिपूर्ण\nदेखभाल, सदैव",
        "Clinical Services" to "क्लिनिकल सेवाएं",
        "Find Medicine" to "दवा खोजें",
        "Health Records" to "स्वास्थ्य रिकॉर्ड",
        "Lab Tests" to "लैब टेस्ट",
        "Consult Doctors" to "डॉक्टर परामर्श",
        "Maternal Programs" to "मातृ स्वास्थ्य कार्यक्रम",
        "Nearby Hospitals & Live Beds" to "निकटवर्ती अस्पताल और लाइव बेड्स",
        "View All" to "सभी देखें",
        "View All →" to "सभी देखें →",

        // Hospital Cards
        "ICU Beds" to "आईसीयू बेड",
        "Emergency" to "आपातकाल",
        "General" to "सामान्य",
        "Call" to "कॉल करें",
        "Directions" to "दिशा-निर्देश",
        "Details" to "विवरण",
        "Government Facility" to "सरकारी स्वास्थ्य केंद्र",
        "Private Hospital" to "निजी अस्पताल",
        "Trust / Charitable" to "ट्रस्ट / धर्मार्थ अस्पताल",
        "Healthcare Centre" to "स्वास्थ्य केंद्र",
        "24x7 Emergency" to "24x7 आपातकालीन सेवा",
        "Save Hospital" to "अस्पताल सहेजें",
        "Saved" to "सहेजा गया",

        // Symptom Triage Screen
        "Hierarchical Symptom Triage" to "क्रमबद्ध लक्षण ट्राइएज",
        "Clinical Multi-Step Assessment" to "क्लिनिकल बहु-चरणीय मूल्यांकन",
        "Step 1: Select Body System" to "चरण 1: शरीर का अंग / प्रणाली चुनें",
        "Step 2: Select Specific Symptoms" to "चरण 2: विशिष्ट लक्षण चुनें",
        "Step 3: Severity & Duration" to "चरण 3: गंभीरता और अवधि",
        "Severity Rating (1 to 10)" to "गंभीरता रेटिंग (1 से 10)",
        "Symptom Duration" to "लक्षणों की अवधि",
        "Today (< 24 hours)" to "आज (< 24 घंटे)",
        "1 - 3 Days" to "1 - 3 दिन",
        "4 - 7 Days" to "4 - 7 दिन",
        "More than a week" to "एक सप्ताह से अधिक",
        "Evaluate Digital Triage" to "डिजिटल ट्राइएज मूल्यांकन करें",
        "Select Symptom(s) in Step 2 to Evaluate" to "मूल्यांकन के लिए चरण 2 में लक्षण चुनें",
        "Search body systems or symptoms..." to "अंग प्रणाली या लक्षण खोजें...",
        "Emergency Red Flags" to "आपातकालीन खतरे के लक्षण",
        "Clear All" to "सभी साफ़ करें",

        // Body System Categories
        "Brain & Nervous System" to "मस्तिष्क एवं तंत्रिका तंत्र",
        "Heart & Circulation" to "हृदय एवं रक्त परिसंचरण",
        "Respiratory System" to "श्वसन तंत्र (फेफड़े)",
        "Mouth, Teeth & Jaw" to "मुख, दांत एवं जबड़ा",
        "Ear" to "कान",
        "Eyes & Vision" to "आंखें एवं दृष्टि",
        "Nose & Sinuses" to "नाक एवं साइनस",
        "Throat & Voice" to "गला एवं आवाज",
        "Digestive System" to "पाचन तंत्र (पेट)",
        "Liver & Gallbladder" to "यकृत एवं पित्ताशय",
        "Kidney & Urinary System" to "गुर्दे एवं मूत्र प्रणाली",
        "Bowel & Rectal Health" to "आंत एवं मलाशय स्वास्थ्य",
        "Blood & Immune System" to "रक्त एवं प्रतिरक्षा प्रणाली",
        "Bones & Joints" to "हड्डियां एवं जोड़",
        "Muscles & Soft Tissue" to "मांसपेशियां एवं कोमल ऊतक",
        "Skin, Hair & Nails" to "त्वचा, बाल एवं नाखून",
        "Hormonal & Endocrine System" to "हार्मोनल एवं अंतःस्रावी प्रणाली",
        "Female Reproductive System" to "महिला प्रजनन प्रणाली",
        "Pregnancy & Maternal Health" to "गर्भावस्था एवं मातृ स्वास्थ्य",
        "Male Reproductive System" to "पुरुष प्रजनन प्रणाली",
        "Child & Infant Health" to "बाल एवं शिशु स्वास्थ्य",
        "Infectious Diseases" to "संक्रामक रोग",
        "Mental & Behavioral Health" to "मानसिक एवं व्यवहारिक स्वास्थ्य",
        "Sleep Health" to "नींद स्वास्थ्य",
        "General / Whole Body" to "सामान्य / संपूर्ण शरीर",
        "Injury & Trauma" to "चोट एवं आघात",
        "Poisoning & Toxic Exposure" to "विषाक्तता एवं रासायनिक प्रभाव",
        "Emergency / Critical Symptoms" to "आपातकालीन / गंभीर लक्षण",

        // Triage Result
        "Triage Assessment Result" to "ट्राइएज मूल्यांकन परिणाम",
        "Immediate Emergency Care Required" to "तत्काल आपातकालीन चिकित्सा आवश्यक",
        "Urgent Medical Evaluation Recommended" to "अतिशीघ्र चिकित्सीय मूल्यांकन की सलाह",
        "Doctor Consultation Advised within 24 Hours" to "24 घंटे के भीतर डॉक्टर से परामर्श की सलाह",
        "Mild Symptoms — General Home Care & Monitoring" to "हल्के लक्षण — सामान्य घरेलू देखभाल एवं निगरानी",
        "Clinical Summary" to "क्लिनिकल सारांश",
        "Actionable Recommendation" to "अनुशंसित कार्रवाई",
        "Specialization Required" to "आवश्यक विशेषज्ञता",
        "Emergency Warning" to "आपातकालीन चेतावनी",
        "Nearest Recommended Facilities" to "निकटतम अनुशंसित स्वास्थ्य केंद्र",
        "Request Ambulance Now" to "अब एम्बुलेंस का अनुरोध करें",
        "Call Nearest Hospital" to "निकटतम अस्पताल को कॉल करें",

        // Emergency Center & Ambulance
        "Emergency Center (SOS)" to "आपातकालीन केंद्र (एसओएस)",
        "Immediate SOS Helplines" to "तत्काल एसओएस हेल्पलाइन",
        "Call 108" to "108 पर कॉल करें",
        "National Emergency" to "राष्ट्रीय आपातकाल",
        "Call 102" to "102 पर कॉल करें",
        "Govt Ambulance" to "सरकारी एम्बुलेंस",
        "Call 112" to "112 पर कॉल करें",
        "All Helpline" to "सर्व आपातकालीन हेल्पलाइन",
        "Dispatch ICU Ambulance to My Location" to "मेरे स्थान पर आईसीयू एम्बुलेंस भेजें",
        "Need Alternative Transport?" to "वैकल्पिक परिवहन चाहिए?",
        "If an ambulance is unavailable, you may arrange alternative transport. For life-threatening emergencies, use emergency medical services whenever possible." to
            "यदि एम्बुलेंस उपलब्ध नहीं है, तो आप वैकल्पिक परिवहन की व्यवस्था कर सकते हैं। जानलेवा आपात स्थिति के लिए हमेशा आपातकालीन चिकित्सा सेवाओं का उपयोग करें।",
        "OR ALTERNATIVE TRANSPORT" to "या वैकल्पिक परिवहन",
        "Book with Rapido" to "रैपिडो से बुक करें",
        "Ride with Uber" to "उबर से राइड करें",
        "Bike & Auto fallback • Tap to open app" to "बाइक और ऑटो विकल्प • ऐप खोलने के लिए टैप करें",
        "Cab & Auto fallback • Tap to open app" to "कैब और ऑटो विकल्प • ऐप खोलने के लिए टैप करें",
        "Rapido is not installed" to "रैपिडो इंस्टॉल नहीं है",
        "You can install Rapido to book an alternative ride." to "वैकल्पिक राइड बुक करने के लिए आप रैपिडो इंस्टॉल कर सकते हैं।",
        "Install Rapido" to "रैपिडो इंस्टॉल करें",
        "Uber is not installed" to "उबर इंस्टॉल नहीं है",
        "You can install Uber to book an alternative ride." to "वैकल्पिक राइड बुक करने के लिए आप उबर इंस्टॉल कर सकते हैं।",
        "Install Uber" to "उबर इंस्टॉल करें",
        "Emergency Contacts" to "आपातकालीन संपर्क",
        "Nearest Emergency & ICU Hospitals" to "निकटतम आपातकालीन एवं आईसीयू अस्पताल",
        "Ranked dynamically by road distance with live ICU bed counters" to "सड़क दूरी और लाइव आईसीयू बेड के अनुसार व्यवस्थित",
        "Confirm Ambulance Dispatch" to "एम्बुलेंस प्रेषण की पुष्टि करें",
        "Dispatch WB-04-1081 (ALS - Advanced Cardiac Life Support) to your current location?" to
            "क्या आप अपने वर्तमान स्थान पर WB-04-1081 (एएलएस) एम्बुलेंस भेजना चाहते हैं?",
        "Emergency Location" to "आपातकालीन स्थान",
        "Dispatch Now" to "अब भेजें",
        "Destination: Nearest Emergency Hospital" to "गंतव्य: निकटतम आपातकालीन अस्पताल",
        "ETA: Approx. 9 - 12 minutes" to "पहुंचने का समय: लगभग 9 - 12 मिनट",
        "Pickup" to "पिकअप",
        "Call 108 Ambulance" to "108 एम्बुलेंस को कॉल करें",
        "Call 102 Maternal/Child" to "102 मातृ/शिशु सेवा को कॉल करें",
        "Call 112 Police/Emergency" to "112 पुलिस/आपातकाल को कॉल करें",
        "Call 1070 Disaster Helpline" to "1070 आपदा हेल्पलाइन पर कॉल करें",
        "Dispatch Emergency Ambulance" to "आपातकालीन एम्बुलेंस भेजें",
        "Live GPS Ambulance Dispatch" to "लाइव जीपीएस एम्बुलेंस प्रेषण",
        "Ambulance Dispatch Tracker" to "एम्बुलेंस ट्रैकर",
        "No Active Ambulance Request" to "कोई सक्रिय एम्बुलेंस अनुरोध नहीं",
        "Request an ambulance from the Emergency Center or Hospital Detail screen." to
            "आपातकालीन केंद्र या अस्पताल विवरण स्क्रीन से एम्बुलेंस का अनुरोध करें।",
        "Vehicle Number" to "वाहन संख्या",
        "Driver Name" to "चालक का नाम",
        "Driver Phone" to "चालक का फोन",
        "Pickup Location" to "पिकअप स्थान",
        "Destination Hospital" to "गंतव्य अस्पताल",
        "Cancel Ambulance Request" to "एम्बुलेंस अनुरोध रद्द करें",
        "Ambulance Dispatched" to "एम्बुलेंस रवाना की गई",

        // AI Chat
        "Smart Health AI Assistant" to "स्मार्ट हेल्थ एआई सहायक",
        "Ask about symptoms, hospitals, beds, medicines..." to "लक्षणों, अस्पतालों, बेड, दवाओं आदि के बारे में पूछें...",
        "Ask AI health assistant..." to "एआई स्वास्थ्य सहायक से पूछें...",
        "Triage, beds, hospitals, diagnostics" to "ट्राइएज, बेड, अस्पताल, डायग्नोस्टिक्स",
        "Send" to "भेजें",
        "Request Emergency Ambulance" to "आपातकालीन एम्बुलेंस का अनुरोध करें",
        "Find ICU beds near me" to "मेरे पास आईसीयू बेड खोजें",
        "Show Government hospitals" to "सरकारी अस्पताल दिखाएं",
        "Check my symptoms (Triage)" to "मेरे लक्षण जांचें (ट्राइएज)",
        "Explain blood test report" to "ब्लड टेस्ट रिपोर्ट समझाइए",
        "Book a doctor appointment" to "डॉक्टर अपॉइंटमेंट बुक करें",
        "Search medicines in pharmacy" to "फार्मेसी में दवाएं खोजें",

        // Doctor & OPD
        "Find Doctors & Teleconsult" to "डॉक्टर खोजें एवं टेली-परामर्श",
        "Search by doctor name or hospital..." to "डॉक्टर के नाम या अस्पताल से खोजें...",
        "Experience" to "अनुभव",
        "Consultation Fee" to "परामर्श शुल्क",
        "Next Available Slot" to "अगला उपलब्ध समय",
        "Book OPD Slot" to "ओपीडी स्लॉट बुक करें",
        "Start Teleconsult" to "टेली-परामर्श शुरू करें",
        "Instant Teleconsultation" to "तत्काल टेली-परामर्श",
        "Live OPD Queue & Token" to "लाइव ओपीडी कतार एवं टोकन",

        // Documents & Clinical
        "Medical Reports & AI OCR" to "मेडिकल रिपोर्ट एवं एआई ओसीआर",
        "Upload Report" to "रिपोर्ट अपलोड करें",
        "Digital Prescriptions" to "डिजिटल नुस्खे",
        "Active Medications" to "सक्रिय दवाएं",
        "Diagnostic Tests & Imaging" to "डायग्नोस्टिक टेस्ट एवं इमेजिंग",
        "Medicine Directory & Pharmacy Stock" to "दवा निर्देशिका एवं फार्मेसी स्टॉक",
        "In Stock" to "उपलब्ध है",
        "Out of Stock" to "उपलब्ध नहीं है",

        // Maternal & Specialized
        "Maternal & Prenatal Health" to "मातृ एवं प्रसवपूर्व स्वास्थ्य",
        "Active Pregnancy Care" to "सक्रिय गर्भावस्था देखभाल",
        "Gestational Age" to "गर्भकालीन आयु",
        "Estimated Due Date (EDD)" to "प्रसव की संभावित तिथि (ईडीडी)",
        "High-Risk Factors" to "उच्च जोखिम वाले कारक",
        "Prenatal Checkups (ANC Schedule)" to "प्रसवपूर्व जांच (एएनसी समय-सारणी)",

        // Profile & Timeline
        "Health Profile & Identity" to "स्वास्थ्य प्रोफ़ाइल एवं पहचान",
        "Emergency Contacts" to "आपातकालीन संपर्क",
        "Blood Group" to "रक्त समूह",
        "Known Allergies" to "ज्ञात एलर्जी",
        "Chronic Conditions" to "पुरानी बीमारियां",
        "Health Timeline" to "स्वास्थ्य समय-रेखा",
        "Frontline Worker (ASHA / ANM)" to "फ्रंटलाइन कार्यकर्ता (आशा / एएनएम)",
        "Generate Field Referral" to "फील्ड रेफरल बनाएं",
        "Hospital Portal" to "अस्पताल पोर्टल",
        "Government Health Admin Dashboard" to "सरकारी स्वास्थ्य प्रशासन डैशबोर्ड"
    )

    private val bengaliMap: Map<String, String> = mapOf(
        // Navigation & Top Bar
        "Home" to "হোম",
        "Hospitals" to "হাসপাতাল",
        "Doctors" to "ডাক্তার",
        "Timeline" to "টাইমলাইন",
        "Profile" to "প্রোফাইল",
        "EMERGENCY" to "জরুরি অবস্থা",
        "Back" to "ফিরে যান",
        "Close" to "বন্ধ করুন",
        "Done" to "সম্পন্ন",
        "Cancel" to "বাতিল",
        "Save" to "সংরক্ষণ",
        "Search" to "অনুসন্ধান",
        "Clear" to "মুছে ফেলুন",
        "Select Location" to "স্থান নির্বাচন করুন",
        "Switch Role / Portal" to "ভূমিকা / পোর্টাল পরিবর্তন করুন",
        "Select Language / ভাষা / भाषा" to "ভাষা নির্বাচন করুন / ভাষা / भाषा",
        "Select Language" to "ভাষা নির্বাচন করুন",
        "Guest Mode" to "গেস্ট মোড",
        "Patient" to "রোগী",
        "ASHA Worker" to "আশা কর্মী",
        "Hospital Staff" to "হাসপাতাল কর্মী",
        "Govt Admin" to "সরকারি প্রশাসক",
        "Admin" to "প্রশাসক",
        "Frontline Health Worker" to "স্বাস্থ্যকর্মী (আশা / এএনএম)",
        "Government Admin" to "সরকারি স্বাস্থ্য প্রশাসক",

        // Hero & Branding
        "CARE PATH" to "কেয়ার পাথ",
        "SUSHRUTA" to "সুশ্রুত",
        "From Sushruta’s wisdom to Jeevan Shakti’s innovation — Care Path, a path towards better health." to
            "সুশ্রুতের প্রজ্ঞা থেকে জীবন শক্তির উদ্ভাবন — কেয়ার পাথ, সুস্থ জীবনের পথ।",
        "GPS coordinates dynamically calculate nearby hospital distances, ICU beds, and ambulance response times." to
            "জিপিএস স্থানাঙ্ক কাছের হাসপাতালের দূরত্ব, আইসিইউ বেড এবং অ্যাম্বুলেন্স পৌঁছানোর সময় গণনা করে।",
        "Smart Health System provides specialized role-based views for patients, frontline workers, hospitals, and health administrators." to
            "স্মার্ট হেলথ সিস্টেম রোগী, স্বাস্থ্যকর্মী, হাসপাতাল এবং প্রশাসকদের জন্য বিশেষ সুবিধা প্রদান করে।",
        "Guest Mode (Zero Mandatory Login - Instant Emergency)" to
            "গেস্ট মোড (লগইন ছাড়া তাৎক্ষণিক জরুরি সেবা)",
        "Registered Patient (Timeline, Records, Prescriptions)" to
            "নিবন্ধিত রোগী (টাইমলাইন, রেকর্ড, প্রেসক্রিপশন)",
        "Frontline Health Worker (ASHA / ANM Field Triage & Referrals)" to
            "স্বাস্থ্যকর্মী (আশা / এএনএম ফিল্ড ট্রায়াজ ও রেফারেল)",
        "Hospital Staff (Bed Management, Queues, OPD Slots)" to
            "হাসপাতাল কর্মী (বেড পরিচালনা, ওপিডি লাইন ও স্লট)",
        "Government Admin (Quality Monitoring, Response Times, API Logs)" to
            "সরকারি প্রশাসক (মান পর্যবেক্ষণ, সাড়া দেওয়ার সময়, সিস্টেম লগ)",

        // Home Section Headers & Cards
        "Nearest 24x7 Emergency Facility" to "নিকটতম ২৪x৭ জরুরি চিকিৎসা কেন্দ্র",
        "How can we help you?" to "আমরা আপনাকে কীভাবে সাহায্য করতে পারি?",
        "Check Symptoms" to "লক্ষণ পরীক্ষা করুন",
        "Digital Triage (1-10)" to "ডিজিটাল ট্রায়াজ (১-১০)",
        "Nearby Hospitals" to "কাছের হাসপাতাল",
        "30+ Facilities & ICU" to "৩০+ স্বাস্থ্যকেন্দ্র ও আইসিইউ",
        "Emergency Help" to "জরুরি সহায়তা",
        "SOS & Ambulance" to "এসওএস ও অ্যাম্বুলেন্স",
        "Talk to AI" to "এআই-এর সাথে কথা বলুন",
        "Healthcare Assistant" to "স্বাস্থ্য সহকারী",
        "Secure & Private" to "নিরাপদ ও গোপনীয়",
        "Your health,\nour priority" to "আপনার স্বাস্থ্য,\nআমাদের অগ্রাধিকার",
        "Fast & Reliable" to "দ্রুত ও নির্ভরযোগ্য",
        "Quick help when\nyou need it" to "প্রয়োজনের সময়\nদ্রুত সাহায্য",
        "Trusted Network" to "বিশ্বস্ত নেটওয়ার্ক",
        "Verified hospitals\n& professionals" to "যাচাইকৃত হাসপাতাল\nও বিশেষজ্ঞ",
        "Always With You" to "সর্বদা আপনার পাশে",
        "Compassionate\ncare, always" to "সহমর্মিতাপূর্ণ\nসেবা, সর্বদা",
        "Clinical Services" to "ক্লিনিকাল পরিষেবাসমূহ",
        "Find Medicine" to "ওষুধ খুঁজুন",
        "Health Records" to "স্বাস্থ্য রেকর্ড",
        "Lab Tests" to "ল্যাব পরীক্ষা",
        "Consult Doctors" to "ডাক্তার পরামর্শ",
        "Maternal Programs" to "মাতৃ স্বাস্থ্য কর্মসূচি",
        "Nearby Hospitals & Live Beds" to "নিকটবর্তী হাসপাতাল ও লাইভ বেড",
        "View All" to "সব দেখুন",
        "View All →" to "সব দেখুন →",

        // Hospital Cards
        "ICU Beds" to "আইসিইউ বেড",
        "Emergency" to "জরুরি",
        "General" to "সাধারণ",
        "Call" to "কল করুন",
        "Directions" to "দিকনির্দেশ",
        "Details" to "বিস্তারিত",
        "Government Facility" to "সরকারি স্বাস্থ্যকেন্দ্র",
        "Private Hospital" to "বেসরকারি হাসপাতাল",
        "Trust / Charitable" to "ট্রাস্ট / চ্যারিটেবল",
        "Healthcare Centre" to "স্বাস্থ্যকেন্দ্র",
        "24x7 Emergency" to "২৪x৭ জরুরি পরিষেবা",
        "Save Hospital" to "হাসপাতাল সংরক্ষণ করুন",
        "Saved" to "সংরক্ষিত",

        // Symptom Triage Screen
        "Hierarchical Symptom Triage" to "ধাপভিত্তিক লক্ষণ ট্রায়াজ",
        "Clinical Multi-Step Assessment" to "ক্লিনিকাল বহু-ধাপ মূল্যায়ন",
        "Step 1: Select Body System" to "ধাপ ১: শারীরিক অঙ্গ / ব্যবস্থা নির্বাচন",
        "Step 2: Select Specific Symptoms" to "ধাপ ২: সুনির্দিষ্ট লক্ষণ নির্বাচন",
        "Step 3: Severity & Duration" to "ধাপ ৩: তীব্রতা ও সময়কাল",
        "Severity Rating (1 to 10)" to "তীব্রতার মাত্রা (১ থেকে ১০)",
        "Symptom Duration" to "লক্ষণের সময়কাল",
        "Today (< 24 hours)" to "আজ (< ২৪ ঘণ্টা)",
        "1 - 3 Days" to "১ - ৩ দিন",
        "4 - 7 Days" to "৪ - ৭ দিন",
        "More than a week" to "এক সপ্তাহের বেশি",
        "Evaluate Digital Triage" to "ডিজিটাল ট্রায়াজ মূল্যায়ন করুন",
        "Select Symptom(s) in Step 2 to Evaluate" to "মূল্যায়নের জন্য ধাপ ২-এ লক্ষণ নির্বাচন করুন",
        "Search body systems or symptoms..." to "শারীরিক অংশ বা লক্ষণ খুঁজুন...",
        "Emergency Red Flags" to "জরুরি বিপদের লক্ষণ",
        "Clear All" to "সব মুছুন",

        // Body System Categories
        "Brain & Nervous System" to "মস্তিষ্ক ও স্নায়ুতন্ত্র",
        "Heart & Circulation" to "হৃদযন্ত্র ও রক্ত সঞ্চালন",
        "Respiratory System" to "শ্বসনতন্ত্র (ফুসফুস)",
        "Mouth, Teeth & Jaw" to "মুখ, দাঁত ও চোয়াল",
        "Ear" to "কান",
        "Eyes & Vision" to "চোখ ও দৃষ্টিশক্তি",
        "Nose & Sinuses" to "নাক ও সাইনাস",
        "Throat & Voice" to "গলা ও কণ্ঠস্বর",
        "Digestive System" to "পাচনতন্ত্র (পেট)",
        "Liver & Gallbladder" to "যকৃত ও পিত্তথলি",
        "Kidney & Urinary System" to "কিডনি ও মূত্রনালী",
        "Bowel & Rectal Health" to "অন্ত্র ও মলদ্বার স্বাস্থ্য",
        "Blood & Immune System" to "রক্ত ও রোগ প্রতিরোধ ব্যবস্থা",
        "Bones & Joints" to "হাড় ও অস্থিসন্ধি",
        "Muscles & Soft Tissue" to "পেশি ও কলা",
        "Skin, Hair & Nails" to "ত্বক, চুল ও নখ",
        "Hormonal & Endocrine System" to "হরমোন ও এন্ডোক্রাইন ব্যবস্থা",
        "Female Reproductive System" to "মহিলা প্রজনন ব্যবস্থা",
        "Pregnancy & Maternal Health" to "গর্ভাবস্থা ও মাতৃস্বাস্থ্য",
        "Male Reproductive System" to "পুরুষ প্রজনন ব্যবস্থা",
        "Child & Infant Health" to "শিশু ও নবজাতক স্বাস্থ্য",
        "Infectious Diseases" to "সংক্রামক রোগ",
        "Mental & Behavioral Health" to "মানসিক ও আচরণগত স্বাস্থ্য",
        "Sleep Health" to "ঘুম ও নিদ্রা স্বাস্থ্য",
        "General / Whole Body" to "সাধারণ / সমগ্র শরীর",
        "Injury & Trauma" to "আঘাত ও ট্রমা",
        "Poisoning & Toxic Exposure" to "বিষক্রিয়া ও বিষাক্ত প্রভাব",
        "Emergency / Critical Symptoms" to "জরুরি / সংকটজনক লক্ষণ",

        // Triage Result
        "Triage Assessment Result" to "ট্রায়াজ মূল্যায়ন ফলাফল",
        "Immediate Emergency Care Required" to "অবিলম্বে জরুরি চিকিৎসার প্রয়োজন",
        "Urgent Medical Evaluation Recommended" to "দ্রুত চিকিৎসকের পরামর্শ নেওয়ার সুপারিশ",
        "Doctor Consultation Advised within 24 Hours" to "২৪ ঘণ্টার মধ্যে চিকিৎসকের পরামর্শ নেওয়ার উপদেশ",
        "Mild Symptoms — General Home Care & Monitoring" to "মৃদু লক্ষণ — সাধারণ ঘরোয়া যত্ন ও পর্যবেক্ষণ",
        "Clinical Summary" to "ক্লিনিকাল সারাংশ",
        "Actionable Recommendation" to "প্রয়োজনীয় পদক্ষেপ",
        "Specialization Required" to "প্রয়োজনীয় বিশেষজ্ঞ",
        "Emergency Warning" to "জরুরি সতর্কতা",
        "Nearest Recommended Facilities" to "কাছের প্রস্তাবিত হাসপাতাল",
        "Request Ambulance Now" to "এখনই অ্যাম্বুলেন্স ডাকুন",
        "Call Nearest Hospital" to "নিকটতম হাসপাতালে কল করুন",

        // Emergency Center & Ambulance
        "Emergency Center (SOS)" to "জরুরি কেন্দ্র (এসওএস)",
        "Immediate SOS Helplines" to "জরুরি এসওএস হেল্পলাইন",
        "Call 108" to "১০৮ নম্বরে কল করুন",
        "National Emergency" to "জাতীয় জরুরি অবস্থা",
        "Call 102" to "১০২ নম্বরে কল করুন",
        "Govt Ambulance" to "সরকারি অ্যাম্বুলেন্স",
        "Call 112" to "১১২ নম্বরে কল করুন",
        "All Helpline" to "সর্বজনীন জরুরি হেল্পলাইন",
        "Dispatch ICU Ambulance to My Location" to "আমার অবস্থানে আইসিইউ অ্যাম্বুলেন্স পাঠান",
        "Need Alternative Transport?" to "বিকল্প পরিবহন প্রয়োজন?",
        "If an ambulance is unavailable, you may arrange alternative transport. For life-threatening emergencies, use emergency medical services whenever possible." to
            "যদি অ্যাম্বুলেন্স উপলব্ধ না থাকে, তবে আপনি বিকল্প পরিবহনের ব্যবস্থা করতে পারেন। জীবন সংশয়ী জরুরি অবস্থায় সর্বদা জরুরি চিকিৎসা পরিষেবা ব্যবহার করুন।",
        "OR ALTERNATIVE TRANSPORT" to "বা বিকল্প পরিবহন",
        "Book with Rapido" to "র‍্যাপিডো দিয়ে বুক করুন",
        "Ride with Uber" to "উবার দিয়ে রাইড করুন",
        "Bike & Auto fallback • Tap to open app" to "বাইক ও অটো বিকল্প • অ্যাপ খুলতে ট্যাপ করুন",
        "Cab & Auto fallback • Tap to open app" to "ক্যাব ও অটো বিকল্প • অ্যাপ খুলতে ট্যাপ করুন",
        "Rapido is not installed" to "র‍্যাপিডো ইনস্টল করা নেই",
        "You can install Rapido to book an alternative ride." to "বিকল্প রাইড বুক করতে আপনি র‍্যাপিডো ইনস্টল করতে পারেন।",
        "Install Rapido" to "র‍্যাপিডো ইনস্টল করুন",
        "Uber is not installed" to "উবার ইনস্টল করা নেই",
        "You can install Uber to book an alternative ride." to "বিকল্প রাইড বুক করতে আপনি উবার ইনস্টল করতে পারেন।",
        "Install Uber" to "উবার ইনস্টল করুন",
        "Emergency Contacts" to "জরুরি যোগাযোগ",
        "Nearest Emergency & ICU Hospitals" to "নিকটবর্তী জরুরি ও আইসিইউ হাসপাতাল",
        "Ranked dynamically by road distance with live ICU bed counters" to "রাস্তার দূরত্ব এবং লাইভ আইসিইউ বেডের সংখ্যা অনুযায়ী সাজানো",
        "Confirm Ambulance Dispatch" to "অ্যাম্বুলেন্স পাঠানোর নিশ্চিতকরণ",
        "Dispatch WB-04-1081 (ALS - Advanced Cardiac Life Support) to your current location?" to
            "আপনার বর্তমান অবস্থানে WB-04-1081 (ALS) অ্যাম্বুলেন্স পাঠাবেন?",
        "Emergency Location" to "জরুরি অবস্থান",
        "Dispatch Now" to "এখনই পাঠান",
        "Destination: Nearest Emergency Hospital" to "গন্তব্য: নিকটতম জরুরি হাসপাতাল",
        "ETA: Approx. 9 - 12 minutes" to "পৌঁছানোর সময়: প্রায় ৯ - ১২ মিনিট",
        "Pickup" to "পিকআপ",
        "Call 108 Ambulance" to "১০৮ অ্যাম্বুলেন্সে কল করুন",
        "Call 102 Maternal/Child" to "১০২ মাতৃ ও শিশু সেবায় কল করুন",
        "Call 112 Police/Emergency" to "১১২ পুলিশ/জরুরি নম্বরে কল করুন",
        "Call 1070 Disaster Helpline" to "১০৭০ দুর্যোগ হেল্পলাইনে কল করুন",
        "Dispatch Emergency Ambulance" to "জরুরি অ্যাম্বুলেন্স পাঠান",
        "Live GPS Ambulance Dispatch" to "লাইভ জিপিএস অ্যাম্বুলেন্স পাঠানো",
        "Ambulance Dispatch Tracker" to "অ্যাম্বুলেন্স ট্র্যাকার",
        "No Active Ambulance Request" to "কোনো সক্রিয় অ্যাম্বুলেন্স অনুরোধ নেই",
        "Request an ambulance from the Emergency Center or Hospital Detail screen." to
            "জরুরি কেন্দ্র বা হাসপাতাল বিবরণ পাতা থেকে অ্যাম্বুলেন্সের অনুরোধ করুন।",
        "Vehicle Number" to "গাড়ির নম্বর",
        "Driver Name" to "চালকের নাম",
        "Driver Phone" to "চালকের ফোন নম্বর",
        "Pickup Location" to "পিকআপের স্থান",
        "Destination Hospital" to "গন্তব্য হাসপাতাল",
        "Cancel Ambulance Request" to "অ্যাম্বুলেন্স অনুরোধ বাতিল করুন",
        "Ambulance Dispatched" to "অ্যাম্বুলেন্স পাঠানো হয়েছে",

        // AI Chat
        "Smart Health AI Assistant" to "স্মার্ট হেলথ এআই সহকারী",
        "Ask about symptoms, hospitals, beds, medicines..." to "লক্ষণ, হাসপাতাল, বেড, ওষুধ ইত্যাদি সম্পর্কে জিজ্ঞাসা করুন...",
        "Ask AI health assistant..." to "এআই স্বাস্থ্য সহকারীকে জিজ্ঞাসা করুন...",
        "Triage, beds, hospitals, diagnostics" to "ট্রায়াজ, বেড, হাসপাতাল, ডায়াগনস্টিক",
        "Send" to "পাঠান",
        "Request Emergency Ambulance" to "জরুরি অ্যাম্বুলেন্সের অনুরোধ করুন",
        "Find ICU beds near me" to "কাছের আইসিইউ বেড খুঁজুন",
        "Show Government hospitals" to "সরকারি হাসপাতাল দেখান",
        "Check my symptoms (Triage)" to "আমার লক্ষণ পরীক্ষা করুন (ট্রায়াজ)",
        "Explain blood test report" to "রক্ত পরীক্ষার রিপোর্ট ব্যাখ্যা করুন",
        "Book a doctor appointment" to "ডাক্তার অ্যাপয়েন্টমেন্ট বুক করুন",
        "Search medicines in pharmacy" to "ফার্মেসিতে ওষুধ অনুসন্ধান করুন",

        // Doctor & OPD
        "Find Doctors & Teleconsult" to "ডাক্তার খুঁজুন ও টেলি-পরামর্শ",
        "Search by doctor name or hospital..." to "ডাক্তার বা হাসপাতালের নাম দিয়ে খুঁজুন...",
        "Experience" to "অভিজ্ঞতা",
        "Consultation Fee" to "পরামর্শ ফি",
        "Next Available Slot" to "পরবর্তী উপলব্ধ সময়",
        "Book OPD Slot" to "ওপিডি স্লট বুক করুন",
        "Start Teleconsult" to "টেলি-পরামর্শ শুরু করুন",
        "Instant Teleconsultation" to "তাৎক্ষণিক টেলি-পরামর্শ",
        "Live OPD Queue & Token" to "লাইভ ওপিডি লাইন ও টোকেন",

        // Documents & Clinical
        "Medical Reports & AI OCR" to "মেডিকেল রিপোর্ট ও এআই ওসিআর",
        "Upload Report" to "রিপোর্ট আপলোড করুন",
        "Digital Prescriptions" to "ডিজিটাল প্রেসক্রিপশন",
        "Active Medications" to "বর্তমান ওষুধসমূহ",
        "Diagnostic Tests & Imaging" to "ডায়াগনস্টিক টেস্ট ও ইমেজিং",
        "Medicine Directory & Pharmacy Stock" to "ওষুধ নির্দেশিকা ও ফার্মেসি স্টক",
        "In Stock" to "স্টকে আছে",
        "Out of Stock" to "স্টকে নেই",

        // Maternal & Specialized
        "Maternal & Prenatal Health" to "মাতৃ ও প্রসবপূর্ব স্বাস্থ্য",
        "Active Pregnancy Care" to "সক্রিয় গর্ভাবস্থা যত্ন",
        "Gestational Age" to "গর্ভকালীন সময়",
        "Estimated Due Date (EDD)" to "প্রসবের সম্ভাব্য তারিখ (ইডিডি)",
        "High-Risk Factors" to "উচ্চ ঝুঁকির কারণসমূহ",
        "Prenatal Checkups (ANC Schedule)" to "প্রসবপূর্ব চেকআপ (এএনসি সূচি)",

        // Profile & Timeline
        "Health Profile & Identity" to "স্বাস্থ্য প্রোফাইল ও পরিচয়",
        "Emergency Contacts" to "জরুরি যোগাযোগ",
        "Blood Group" to "রক্তের গ্রুপ",
        "Known Allergies" to "অ্যালার্জি",
        "Chronic Conditions" to "দীর্ঘস্থায়ী রোগ",
        "Health Timeline" to "স্বাস্থ্য টাইমলাইন",
        "Frontline Worker (ASHA / ANM)" to "স্বাস্থ্যকর্মী (আশা / এএনএম)",
        "Generate Field Referral" to "ফিল্ড রেফারেল তৈরি করুন",
        "Hospital Portal" to "হাসপাতাল পোর্টাল",
        "Government Health Admin Dashboard" to "সরকারি স্বাস্থ্য প্রশাসন ড্যাশবোর্ড"
    )

    fun translate(key: String, lang: String): String {
        if (lang.equals("EN", ignoreCase = true)) return key
        val cleanKey = key.trim()

        val translation = when (lang.uppercase()) {
            "HI" -> hindiMap[cleanKey] ?: findFuzzy(hindiMap, cleanKey)
            "BN" -> bengaliMap[cleanKey] ?: findFuzzy(bengaliMap, cleanKey)
            else -> null
        }

        return translation ?: key
    }

    fun translateFormat(format: String, lang: String, vararg args: Any): String {
        val translatedTemplate = translate(format, lang)
        return try {
            String.format(translatedTemplate, *args)
        } catch (_: Exception) {
            try {
                String.format(format, *args)
            } catch (_: Exception) {
                format
            }
        }
    }

    private fun findFuzzy(map: Map<String, String>, query: String): String? {
        val lower = query.lowercase()
        return map.entries.firstOrNull { it.key.lowercase() == lower }?.value
    }
}
