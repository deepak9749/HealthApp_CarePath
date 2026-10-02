package com.example.data.datasource

import com.example.data.local.SymptomCategoryEntity
import com.example.data.local.SymptomEntity

object ComprehensiveSymptomCatalog {

    val categories: List<SymptomCategoryEntity> = listOf(
        SymptomCategoryEntity("cat_neuro", "Brain & Nervous System", "Brain & Nervous System", "brain", "Headache, dizziness, numbness, seizure, weakness, confusion"),
        SymptomCategoryEntity("cat_cardio", "Heart & Circulation", "Heart & Circulation", "heart", "Chest pain, palpitations, blood pressure, swelling, circulation"),
        SymptomCategoryEntity("cat_respiratory", "Respiratory System", "Respiratory System", "lungs", "Breathing difficulty, cough, wheezing, congestion, asthma"),
        SymptomCategoryEntity("cat_mouth_jaw", "Mouth, Teeth & Jaw", "Mouth, Teeth & Jaw", "mouth", "Tooth pain, gum problems, mouth ulcer, jaw pain, oral swelling"),
        SymptomCategoryEntity("cat_ear", "Ear", "Ear", "ear", "Ear pain, hearing difficulty, discharge, ringing, dizziness"),
        SymptomCategoryEntity("cat_eyes", "Eyes & Vision", "Eyes & Vision", "eyes", "Eye pain, red eye, blurred vision, discharge, swelling"),
        SymptomCategoryEntity("cat_nose_sinuses", "Nose & Sinuses", "Nose & Sinuses", "nose", "Nasal congestion, runny nose, sinus pain, bleeding, smell loss"),
        SymptomCategoryEntity("cat_throat_voice", "Throat & Voice", "Throat & Voice", "throat", "Sore throat, difficulty swallowing, hoarseness, tonsil swelling"),
        SymptomCategoryEntity("cat_digestive", "Digestive System", "Digestive System", "digestive", "Stomach pain, acidity, vomiting, nausea, diarrhea, constipation"),
        SymptomCategoryEntity("cat_liver_gallbladder", "Liver & Gallbladder", "Liver & Gallbladder", "liver", "Jaundice, upper abdominal pain, dark urine, pale stool"),
        SymptomCategoryEntity("cat_kidney_urinary", "Kidney & Urinary System", "Kidney & Urinary System", "kidney", "Kidney pain, urinary pain, frequent urination, blood in urine"),
        SymptomCategoryEntity("cat_bowel_rectal", "Bowel & Rectal Health", "Bowel & Rectal Health", "bowel", "Rectal pain, piles, anal bleeding, fissure, stool changes"),
        SymptomCategoryEntity("cat_blood_immune", "Blood & Immune System", "Blood & Immune System", "blood", "Anemia, bruising, excessive bleeding, frequent infections"),
        SymptomCategoryEntity("cat_bones_joints", "Bones & Joints", "Bones & Joints", "bones", "Bone pain, joint pain, arthritis, fracture, back/neck pain"),
        SymptomCategoryEntity("cat_muscles_soft_tissue", "Muscles & Soft Tissue", "Muscles & Soft Tissue", "muscles", "Muscle pain, weakness, cramps, sprain, strain, stiffness"),
        SymptomCategoryEntity("cat_skin_hair_nails", "Skin, Hair & Nails", "Skin, Hair & Nails", "skin", "Rash, itching, acne, skin infection, wound, burn, hair loss"),
        SymptomCategoryEntity("cat_endocrine_hormonal", "Hormonal & Endocrine System", "Hormonal & Endocrine System", "endocrine", "Diabetes, thyroid, hormonal imbalance, abnormal sweating, thirst"),
        SymptomCategoryEntity("cat_female_reproductive", "Female Reproductive System", "Female Reproductive System", "female", "Period problems, pelvic pain, discharge, breast pain/lump"),
        SymptomCategoryEntity("cat_maternal_pregnancy", "Pregnancy & Maternal Health", "Pregnancy & Maternal Health", "maternal", "Pregnancy pain, bleeding, fetal movement, complications"),
        SymptomCategoryEntity("cat_male_reproductive", "Male Reproductive System", "Male Reproductive System", "male", "Testicular pain, genital swelling, prostate symptoms, erectile issues"),
        SymptomCategoryEntity("cat_child_infant", "Child & Infant Health", "Child & Infant Health", "pediatric", "Fever in child, feeding issues, crying, growth, development"),
        SymptomCategoryEntity("cat_infectious_diseases", "Infectious Diseases", "Infectious Diseases", "infectious", "Fever, flu, viral/bacterial infection, dengue, malaria, TB"),
        SymptomCategoryEntity("cat_mental_health", "Mental & Behavioral Health", "Mental & Behavioral Health", "mental", "Anxiety, stress, depression, panic attacks, mood changes"),
        SymptomCategoryEntity("cat_sleep_health", "Sleep Health", "Sleep Health", "sleep", "Insomnia, excessive sleepiness, snoring, sleep apnea"),
        SymptomCategoryEntity("cat_general_body", "General / Whole Body", "General / Whole Body", "general", "Fever, fatigue, weakness, weight changes, appetite, chills"),
        SymptomCategoryEntity("cat_injury_trauma", "Injury & Trauma", "Injury & Trauma", "trauma", "Accident, fall, head injury, fracture, bleeding, burn, cuts"),
        SymptomCategoryEntity("cat_poisoning_toxic", "Poisoning & Toxic Exposure", "Poisoning & Toxic Exposure", "poisoning", "Food poisoning, chemical exposure, drug overdose, gas inhalation"),
        SymptomCategoryEntity("cat_emergency_critical", "Emergency / Critical Symptoms", "Emergency / Critical Symptoms", "emergency", "Global emergency: severe chest pain, breathing difficulty, coma, bleeding")
    )

    val symptoms: List<SymptomEntity> = buildList {
        // 1. BRAIN & NERVOUS SYSTEM
        add(SymptomEntity("sym_neuro_1", "cat_neuro", "Headache", "Brain & Nerves", "Generalized or localized head discomfort or throbbing sensation", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_neuro_2", "cat_neuro", "Migraine", "Brain & Nerves", "Unilateral throbbing head pain, often with light/sound sensitivity or aura", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_neuro_3", "cat_neuro", "Dizziness", "Vestibular / Balance", "Lightheadedness, unsteadiness, or feeling faint", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_neuro_4", "cat_neuro", "Seizure", "Neurological", "Involuntary muscle contractions, shaking or brief blank episodes", isEmergencyRedFlag = false, defaultSeverity = 7))
        add(SymptomEntity("sym_neuro_5", "cat_neuro", "Memory / Confusion", "Cognitive", "Difficulty remembering, disorientation, or slowed thinking", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_neuro_6", "cat_neuro", "Numbness", "Sensory Nerves", "Reduced sensation or deadened feeling in limbs or face", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_neuro_7", "cat_neuro", "Weakness", "Motor System", "Reduced muscular strength or heaviness in arms or legs", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_neuro_8", "cat_neuro", "Balance Problems", "Coordination", "Unsteady gait, swaying, or difficulty walking in a straight line", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_neuro_9", "cat_neuro", "Tremor", "Movement Nerves", "Involuntary rhythmic shaking of hands, fingers, or chin", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_neuro_10", "cat_neuro", "Tingling", "Sensory Nerves", "Pins and needles sensation in hands, feet, or extremities", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_neuro_11", "cat_neuro", "Loss of Coordination", "Cerebellar", "Clumsiness in motor tasks, difficulty grasping objects", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_neuro_12", "cat_neuro", "Speech Difficulty", "Speech & Language", "Slow or slightly slurred speech articulation", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_neuro_13", "cat_neuro", "Sudden Difficulty Understanding", "Cognitive / Stroke", "Trouble comprehending spoken or written sentences", isEmergencyRedFlag = false, defaultSeverity = 7))
        add(SymptomEntity("sym_neuro_14", "cat_neuro", "Sudden Facial Weakness", "Cranial Nerves", "Asymmetrical facial movement or smile", isEmergencyRedFlag = false, defaultSeverity = 7))
        add(SymptomEntity("sym_neuro_15", "cat_neuro", "Sudden Limb Weakness", "Motor Nerves", "Sudden weakness in one arm, leg, or side of body", isEmergencyRedFlag = false, defaultSeverity = 7))
        add(SymptomEntity("sym_neuro_16", "cat_neuro", "Sudden Vision Change", "Visual Pathway", "Sudden blurring, dimming, or darkness in one or both visual fields", isEmergencyRedFlag = false, defaultSeverity = 6))
        // Red Flags
        add(SymptomEntity("sym_neuro_rf_1", "cat_neuro", "Sudden severe headache", "Emergency Red Flag", "Explosive thunderclap headache reaching maximum severity instantly", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_neuro_rf_2", "cat_neuro", "Sudden facial drooping", "Emergency Red Flag", "Unilateral face drooping or sagging smile (FAST stroke warning)", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_neuro_rf_3", "cat_neuro", "Sudden arm or leg weakness", "Emergency Red Flag", "Acute one-sided limb paralysis or inability to lift arm", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_neuro_rf_4", "cat_neuro", "Sudden speech difficulty", "Emergency Red Flag", "Severe slurring, word-finding failure or inability to speak", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_neuro_rf_5", "cat_neuro", "Sudden confusion", "Emergency Red Flag", "Acute disorientation to time, person, or surroundings", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_neuro_rf_6", "cat_neuro", "New seizure", "Emergency Red Flag", "First-time convulsive episode or repeated epileptic status", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_neuro_rf_7", "cat_neuro", "Loss of consciousness", "Emergency Red Flag", "Syncope, unresponsiveness, or blackout episode", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_neuro_rf_8", "cat_neuro", "Sudden severe balance disturbance", "Emergency Red Flag", "Acute ataxia or complete inability to stand without falling", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_neuro_rf_9", "cat_neuro", "Sudden vision loss", "Emergency Red Flag", "Monocular blindness or sudden total visual blackout", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 2. HEART & CIRCULATION
        add(SymptomEntity("sym_cardio_1", "cat_cardio", "Chest Pain", "Cardiovascular", "Dull ache, tightness, or pressure over central or left chest", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_cardio_2", "cat_cardio", "Palpitations", "Heart Rhythm", "Fluttering, pounding, or skipped beats in the chest", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_cardio_3", "cat_cardio", "Fast Heartbeat", "Tachycardia", "Resting pulse persistently above 100 bpm", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_cardio_4", "cat_cardio", "Slow Heartbeat", "Bradycardia", "Resting pulse below 50 bpm causing fatigue or sluggishness", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_cardio_5", "cat_cardio", "High Blood Pressure", "Hypertension", "Systolic BP reading > 140 or diastolic > 90 mmHg", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_cardio_6", "cat_cardio", "Low Blood Pressure", "Hypotension", "Systolic BP reading < 90 mmHg with dizziness on standing", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_cardio_7", "cat_cardio", "Fainting", "Syncope", "Temporary loss of consciousness with spontaneous recovery", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_cardio_8", "cat_cardio", "Dizziness", "Hemodynamic", "Postural lightheadedness or feeling unsteady when moving", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_cardio_9", "cat_cardio", "Leg Swelling", "Fluid Retention", "Pitting edema in ankles, feet, or lower legs", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_cardio_10", "cat_cardio", "General Swelling", "Edema", "Puffiness in hands, face, or abdomen", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_cardio_11", "cat_cardio", "Cold Hands / Feet", "Peripheral Vascular", "Cold extremities with sluggish capillary refill", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_cardio_12", "cat_cardio", "Poor Circulation", "Vascular", "Cramping in legs when walking, pale skin on legs", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_cardio_13", "cat_cardio", "Irregular Heartbeat", "Arrhythmia", "Uneven pulse cadence or intermittent erratic rhythm", isEmergencyRedFlag = false, defaultSeverity = 5))
        // Red Flags
        add(SymptomEntity("sym_cardio_rf_1", "cat_cardio", "Severe or persistent chest pain", "Emergency Red Flag", "Crushing, heavy chest pressure lasting > 10 minutes", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_2", "cat_cardio", "Chest pressure with breathing difficulty", "Emergency Red Flag", "Squeezing central chest pain accompanied by severe shortness of breath", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_3", "cat_cardio", "Chest pain with sweating", "Emergency Red Flag", "Retrosternal chest pain with cold clammy profuse diaphoresis", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_4", "cat_cardio", "Chest pain with fainting", "Emergency Red Flag", "Chest discomfort with syncope or collapse", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_5", "cat_cardio", "Sudden severe palpitations with dizziness", "Emergency Red Flag", "Rapid racing heart accompanied by pre-syncope or blackouts", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_cardio_rf_6", "cat_cardio", "Sudden collapse", "Emergency Red Flag", "Unexplained loss of posture and unresponsiveness", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_7", "cat_cardio", "Blue lips or skin", "Emergency Red Flag", "Cyanosis indicating critically low blood oxygenation", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_cardio_rf_8", "cat_cardio", "Severe breathing difficulty with chest symptoms", "Emergency Red Flag", "Acute cardiogenic dyspnea or orthopnea", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 3. RESPIRATORY SYSTEM
        add(SymptomEntity("sym_resp_1", "cat_respiratory", "Breathing Difficulty", "Airway", "Feeling of increased effort to breathe or breathlessness on exertion", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_resp_2", "cat_respiratory", "Shortness of Breath", "Pulmonary", "Inability to take a deep, satisfying breath", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_resp_3", "cat_respiratory", "Cough", "Airway", "Persistent clearing of throat and bronchial passages", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_resp_4", "cat_respiratory", "Dry Cough", "Bronchial", "Hacking non-productive cough with tickling throat sensation", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_resp_5", "cat_respiratory", "Wet Cough", "Mucus", "Cough producing clear, white, yellow, or greenish sputum", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_resp_6", "cat_respiratory", "Wheezing", "Airway Obstruction", "High-pitched whistling sound during exhalation", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_resp_7", "cat_respiratory", "Chest Congestion", "Bronchial", "Heavy rattling or tightness deep in chest", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_resp_8", "cat_respiratory", "Phlegm", "Airway Secretions", "Excessive thick mucus in respiratory tract", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_resp_9", "cat_respiratory", "Sore Throat", "Pharynx", "Scratchy, irritated, or raw feeling in back of throat", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_resp_10", "cat_respiratory", "Asthma Symptoms", "Chronic Airway", "Episode of chest tightness, cough, and wheeze in known asthmatic", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_resp_11", "cat_respiratory", "Noisy Breathing", "Stridor / Airway", "Audible coarse sounds during inhalation or exhalation", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_resp_12", "cat_respiratory", "Difficulty Breathing While Lying Down", "Orthopnea", "Shortness of breath relieved only by propping up with pillows", isEmergencyRedFlag = false, defaultSeverity = 6))
        // Red Flags
        add(SymptomEntity("sym_resp_rf_1", "cat_respiratory", "Severe breathing difficulty", "Emergency Red Flag", "Gasping for air, unable to finish full sentences", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_2", "cat_respiratory", "Sudden inability to breathe normally", "Emergency Red Flag", "Acute asphyxia sensation or severe respiratory distress", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_3", "cat_respiratory", "Blue lips", "Emergency Red Flag", "Central cyanosis due to hypoxemia", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_4", "cat_respiratory", "Severe wheezing", "Emergency Red Flag", "Audible severe wheezing with silent chest and exhaustion", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_resp_rf_5", "cat_respiratory", "Coughing large amounts of blood", "Emergency Red Flag", "Hemoptysis with bright red blood or clots", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_6", "cat_respiratory", "Breathing difficulty with chest pain", "Emergency Red Flag", "Shortness of breath accompanied by sharp or crushing chest pain", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_7", "cat_respiratory", "Severe breathing difficulty in child", "Emergency Red Flag", "Chest wall indrawing, nasal flaring, grunting in infant/child", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_resp_rf_8", "cat_respiratory", "Severe breathing difficulty in pregnancy", "Emergency Red Flag", "Acute shortness of breath in pregnancy with tachycardia", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 4. MOUTH, TEETH & JAW
        add(SymptomEntity("sym_mouth_1", "cat_mouth_jaw", "Tooth Pain", "Dental", "Aching, throbbing, or sharp pain in tooth or gums", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mouth_2", "cat_mouth_jaw", "Gum Problems", "Periodontal", "Red, swollen, tender gums or gingival inflammation", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_mouth_3", "cat_mouth_jaw", "Mouth Ulcer", "Oral Mucosa", "Painful canker sores or aphthous ulcers on tongue or inner cheek", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_mouth_4", "cat_mouth_jaw", "Bad Breath", "Halitosis", "Persistent unpleasant oral odor not cleared by brushing", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_mouth_5", "cat_mouth_jaw", "Jaw Pain", "TMJ / Maxillofacial", "Ache or clicking in jaw joint when chewing or talking", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mouth_6", "cat_mouth_jaw", "Oral Swelling", "Soft Tissue", "Swelling of lips, gums, tongue, or cheek lining", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mouth_7", "cat_mouth_jaw", "Difficulty Chewing", "Dental Function", "Pain or mechanical obstruction when biting food", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mouth_8", "cat_mouth_jaw", "Difficulty Opening Mouth", "Trismus", "Reduced mouth opening span or muscle spasm in jaw", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_mouth_9", "cat_mouth_jaw", "Bleeding Gums", "Periodontal", "Spontaneous or brushing-induced bleeding from gingiva", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_mouth_10", "cat_mouth_jaw", "Tooth Sensitivity", "Dental Enamel", "Sharp transient pain on hot, cold, sweet, or acidic foods", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_mouth_rf_1", "cat_mouth_jaw", "Severe facial swelling", "Emergency Red Flag", "Rapidly spreading swelling over cheek, eye, or jawline (Ludwig's angina risk)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_mouth_rf_2", "cat_mouth_jaw", "Rapidly increasing mouth or jaw swelling", "Emergency Red Flag", "Rapidly expanding floor-of-mouth or submandibular abscess", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_mouth_rf_3", "cat_mouth_jaw", "Difficulty breathing due to swelling", "Emergency Red Flag", "Oral/pharyngeal swelling compromising upper airway patency", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mouth_rf_4", "cat_mouth_jaw", "Difficulty swallowing due to swelling", "Emergency Red Flag", "Inability to swallow fluids or saliva due to oral-pharyngeal mass", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_mouth_rf_5", "cat_mouth_jaw", "Severe uncontrolled oral bleeding", "Emergency Red Flag", "Heavy arterial or persistent post-extraction hemorrhage", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 5. EAR
        add(SymptomEntity("sym_ear_1", "cat_ear", "Ear Pain", "Otology", "Sharp, dull, or throbbing pain inside or around the ear", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ear_2", "cat_ear", "Hearing Difficulty", "Auditory", "Muffled hearing, reduced volume, or sensation of blocked ear", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ear_3", "cat_ear", "Ear Discharge", "Otorrhea", "Fluid, watery, or purulent drainage leaking from ear canal", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ear_4", "cat_ear", "Ringing in Ear", "Tinnitus", "Continuous or intermittent buzzing, ringing, or clicking in ear", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ear_5", "cat_ear", "Ear Infection Symptoms", "Otitis", "Fever, ear warmth, tenderness when touching earlobe or tragus", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ear_6", "cat_ear", "Ear Fullness", "Eustachian", "Pressure sensation like being on an airplane", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ear_7", "cat_ear", "Ear Itching", "Otitis Externa", "Intense itching inside ear canal", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_ear_8", "cat_ear", "Dizziness Related to Ear", "Vestibular", "Vertigo or spinning feeling triggered by head movement", isEmergencyRedFlag = false, defaultSeverity = 5))
        // Red Flags
        add(SymptomEntity("sym_ear_rf_1", "cat_ear", "Sudden hearing loss", "Emergency Red Flag", "Sudden sensorineural hearing loss developing within hours", isEmergencyRedFlag = true, defaultSeverity = 8))
        add(SymptomEntity("sym_ear_rf_2", "cat_ear", "Severe ear pain with neurological symptoms", "Emergency Red Flag", "Ear infection with severe headache, facial palsy, or neck stiffness", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ear_rf_3", "cat_ear", "Ear injury with bleeding", "Emergency Red Flag", "Traumatic ear canal bleeding or clear CSF otorrhea after head injury", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ear_rf_4", "cat_ear", "Severe dizziness with other neurological symptoms", "Emergency Red Flag", "Severe acute vertigo with double vision, ataxia, or weakness", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 6. EYES & VISION
        add(SymptomEntity("sym_eye_1", "cat_eyes", "Eye Pain", "Ophthalmology", "Aching, burning, or gritty feeling on or behind eyeball", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_eye_2", "cat_eyes", "Red Eye", "Conjunctiva", "Bloodshot eyes, pink eye, or conjunctival injection", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_eye_3", "cat_eyes", "Blurred Vision", "Refraction / Cornea", "Loss of sharpness or difficulty focusing on objects", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_eye_4", "cat_eyes", "Vision Loss", "Optic / Retina", "Diminished visual acuity or dark spots in field of view", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_eye_5", "cat_eyes", "Eye Discharge", "Infection", "Yellow, green, or crusty discharge from eyelids", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_eye_6", "cat_eyes", "Dry Eyes", "Tear Film", "Stinging, scratchiness, or fatigue in eyes", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_eye_7", "cat_eyes", "Eye Swelling", "Periorbital", "Puffiness or swelling around eyelids", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_eye_8", "cat_eyes", "Eye Injury", "Trauma", "Scratch, foreign body, or mild impact to eye", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_eye_9", "cat_eyes", "Itching Eyes", "Allergy", "Allergic itchiness with watery eyes and sneezing", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_eye_10", "cat_eyes", "Light Sensitivity", "Photophobia", "Eye discomfort or squinting in ordinary room light", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_eye_11", "cat_eyes", "Double Vision", "Diplopia", "Seeing two overlapping images instead of one", isEmergencyRedFlag = false, defaultSeverity = 5))
        // Red Flags
        add(SymptomEntity("sym_eye_rf_1", "cat_eyes", "Sudden vision loss", "Emergency Red Flag", "Acute painless or painful loss of sight in one or both eyes", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_eye_rf_2", "cat_eyes", "Sudden severe eye pain", "Emergency Red Flag", "Excruciating eye ache with nausea and corneal haziness (Acute Glaucoma)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_eye_rf_3", "cat_eyes", "Eye injury with vision loss", "Emergency Red Flag", "Penetrating eye trauma or blunt globe rupture with vision drop", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_eye_rf_4", "cat_eyes", "Chemical exposure to eye", "Emergency Red Flag", "Acid, alkali, or hazardous liquid splashed into eye", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_eye_rf_5", "cat_eyes", "Sudden double vision", "Emergency Red Flag", "Acute binocular diplopia indicating cranial nerve palsy or stroke", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_eye_rf_6", "cat_eyes", "Severe eye swelling with vision changes", "Emergency Red Flag", "Orbital cellulitis or proptosis with fever and restricted eye movement", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 7. NOSE & SINUSES
        add(SymptomEntity("sym_nose_1", "cat_nose_sinuses", "Nasal Congestion", "Rhinitis", "Stuffy or blocked nasal passages causing mouth breathing", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_nose_2", "cat_nose_sinuses", "Runny Nose", "Rhinorrhea", "Watery or mucoid drainage from nostrils", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_nose_3", "cat_nose_sinuses", "Sinus Pain", "Sinusitis", "Pressure and ache over forehead, cheeks, or between eyes", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_nose_4", "cat_nose_sinuses", "Nose Bleeding", "Epistaxis", "Mild dripping of blood from one nostril", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_nose_5", "cat_nose_sinuses", "Loss of Smell", "Anosmia", "Reduced or absent ability to smell fragrances and food", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_nose_6", "cat_nose_sinuses", "Nasal Allergy", "Allergic Rhinitis", "Seasonal itching, watery nose, and throat tickle", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_nose_7", "cat_nose_sinuses", "Sneezing", "Nasal Reflex", "Frequent bouts of sneezing due to dust, pollen, or cold", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_nose_8", "cat_nose_sinuses", "Facial Pressure", "Sinus Cavities", "Heaviness under eyes aggravated by bending forward", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_nose_9", "cat_nose_sinuses", "Nasal Discharge", "Mucus", "Thick yellow or green nasal secretions", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_nose_rf_1", "cat_nose_sinuses", "Heavy uncontrolled nose bleeding", "Emergency Red Flag", "Continuous brisk posterior epistaxis not stopping with pressure", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_nose_rf_2", "cat_nose_sinuses", "Severe facial swelling", "Emergency Red Flag", "Rapid midface, periorbital, or bridge-of-nose cellulitis/erysipelas", isEmergencyRedFlag = true, defaultSeverity = 8))
        add(SymptomEntity("sym_nose_rf_3", "cat_nose_sinuses", "Breathing obstruction", "Emergency Red Flag", "Severe bilateral nasal airway occlusion with stridor or retractions", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_nose_rf_4", "cat_nose_sinuses", "Nose injury with severe bleeding", "Emergency Red Flag", "Nasal bone fracture with profuse hemorrhage or CSF leak", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 8. THROAT & VOICE
        add(SymptomEntity("sym_throat_1", "cat_throat_voice", "Sore Throat", "Pharyngitis", "Pain, scratchiness, or irritation in throat aggravated by swallowing", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_throat_2", "cat_throat_voice", "Difficulty Swallowing", "Dysphagia", "Discomfort or feeling of food sticking during swallowing", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_throat_3", "cat_throat_voice", "Hoarseness", "Vocal Cords", "Raspy, strained, breathy, or low-pitch vocal changes", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_throat_4", "cat_throat_voice", "Tonsil Problems", "Tonsillitis", "Enlarged red tonsils with white spots or exudate", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_throat_5", "cat_throat_voice", "Throat Swelling", "Pharynx", "Sensation of throat fullness or tightness", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_throat_6", "cat_throat_voice", "Voice Problems", "Laryngology", "Voice fatigue, loss of vocal range, or aphonia", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_throat_7", "cat_throat_voice", "Throat Irritation", "Airway", "Tickle causing frequent dry throat-clearing", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_throat_8", "cat_throat_voice", "Foreign Body Sensation", "Globus", "Feeling of a lump or pill caught in throat", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_throat_rf_1", "cat_throat_voice", "Severe difficulty breathing", "Emergency Red Flag", "Acute laryngeal edema or epiglottitis with inspiratory stridor", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_throat_rf_2", "cat_throat_voice", "Severe throat swelling", "Emergency Red Flag", "Rapidly expanding peritonsillar or retropharyngeal abscess", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_throat_rf_3", "cat_throat_voice", "Inability to swallow saliva", "Emergency Red Flag", "Drooling and total inability to swallow secretions", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_throat_rf_4", "cat_throat_voice", "Rapidly worsening throat swelling", "Emergency Red Flag", "Anaphylactic throat constriction progressing over minutes", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_throat_rf_5", "cat_throat_voice", "Sudden airway obstruction", "Emergency Red Flag", "Choking, acute upper airway blockage requiring urgent intervention", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 9. DIGESTIVE SYSTEM
        add(SymptomEntity("sym_dig_1", "cat_digestive", "Stomach Pain", "Gastric", "Cramping or aching in upper central abdomen", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_dig_2", "cat_digestive", "Abdominal Pain", "GI Tract", "Generalized or localized abdominal discomfort", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_dig_3", "cat_digestive", "Acidity", "Acid Peptic", "Sour taste, acid reflux, or burning in esophagus", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_4", "cat_digestive", "Indigestion", "Dyspepsia", "Fullness early during meals, heavy stomach sensation", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_5", "cat_digestive", "Vomiting", "Emesis", "Forceful expulsion of stomach contents", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_dig_6", "cat_digestive", "Nausea", "Upper GI", "Queasy feeling with urge to vomit", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_7", "cat_digestive", "Diarrhea", "Bowel", "Frequent loose or watery bowel movements", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_dig_8", "cat_digestive", "Constipation", "Bowel", "Infrequent, hard, or difficult bowel evacuations", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_9", "cat_digestive", "Bloating", "Intestinal Gas", "Distension or feeling swollen in belly", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_10", "cat_digestive", "Gas", "Flatulence", "Excessive flatulence or belching", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_dig_11", "cat_digestive", "Heartburn", "Reflux", "Burning retrosternal chest pain rising after food", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_12", "cat_digestive", "Loss of Appetite", "Nutrition", "Reduced desire to eat meals", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_dig_13", "cat_digestive", "Abdominal Swelling", "Ascites / Distension", "Visible expansion or tautness of the abdomen", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_dig_14", "cat_digestive", "Blood in Stool", "GI Bleed", "Small streaks of red blood on toilet paper or stool surface", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_dig_15", "cat_digestive", "Black Stool", "Melena", "Dark sticky stools with foul odor", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_dig_16", "cat_digestive", "Persistent Vomiting", "Emesis", "Inability to keep liquids down for several hours", isEmergencyRedFlag = false, defaultSeverity = 6))
        // Red Flags
        add(SymptomEntity("sym_dig_rf_1", "cat_digestive", "Severe abdominal pain", "Emergency Red Flag", "Intense rigid abdominal guarding or rebound tenderness (Acute Abdomen)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_dig_rf_2", "cat_digestive", "Sudden severe abdominal pain", "Emergency Red Flag", "Acute sharp knife-like onset pain (possible perforation / appendicitis)", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_dig_rf_3", "cat_digestive", "Vomiting blood", "Emergency Red Flag", "Hematemesis with frank red blood or coffee-ground material", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_dig_rf_4", "cat_digestive", "Black or tar-like stool", "Emergency Red Flag", "Significant upper GI hemorrhage causing heavy melena", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_dig_rf_5", "cat_digestive", "Large amount of blood in stool", "Emergency Red Flag", "Major lower GI hemorrhage / hematochezia", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_dig_rf_6", "cat_digestive", "Severe dehydration", "Emergency Red Flag", "Sunken eyes, dry mucous membranes, postural collapse from fluid loss", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_dig_rf_7", "cat_digestive", "Persistent uncontrollable vomiting", "Emergency Red Flag", "Protracted emesis causing severe electrolyte disarray and shock", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_dig_rf_8", "cat_digestive", "Abdominal pain with fainting", "Emergency Red Flag", "Severe peritoneal pain combined with syncope or hypotensive shock", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_dig_rf_9", "cat_digestive", "Abdominal pain during pregnancy", "Emergency Red Flag", "Acute abdominal distress in pregnancy (ectopic / abruption risk)", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 10. LIVER & GALLBLADDER
        add(SymptomEntity("sym_liv_1", "cat_liver_gallbladder", "Jaundice", "Hepatology", "Yellowing of the whites of the eyes (sclera) and skin", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_liv_2", "cat_liver_gallbladder", "Upper Abdominal Pain", "Biliary", "Ache under right ribcage radiating towards shoulder blade", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_liv_3", "cat_liver_gallbladder", "Right Upper Abdominal Pain", "Gallbladder", "Pain under right costal margin triggered by fatty meals", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_liv_4", "cat_liver_gallbladder", "Gallbladder Pain", "Biliary Colic", "Episodic intense cramping in right hypochondrium", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_liv_5", "cat_liver_gallbladder", "Dark Urine", "Bilirubin", "Tea-colored or deep brownish-yellow urine", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_liv_6", "cat_liver_gallbladder", "Pale Stool", "Cholestasis", "Clay-colored, grayish or whitish feces", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_liv_7", "cat_liver_gallbladder", "Abdominal Swelling", "Ascites", "Fluid accumulation in peritoneal cavity with tense belly", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_liv_8", "cat_liver_gallbladder", "Nausea", "Hepatic", "Persistent feeling of nausea and food aversion", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_liv_9", "cat_liver_gallbladder", "Loss of Appetite", "Metabolic", "Marked anorexia and taste alteration", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_liv_rf_1", "cat_liver_gallbladder", "Severe upper abdominal pain", "Emergency Red Flag", "Acute cholecystitis, cholangitis, or hepatic capsule stretch", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_liv_rf_2", "cat_liver_gallbladder", "Jaundice with confusion", "Emergency Red Flag", "Hepatic encephalopathy with asterixis, lethargy, or delirium", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_liv_rf_3", "cat_liver_gallbladder", "Jaundice with severe weakness", "Emergency Red Flag", "Fulminant acute liver failure with profound debility", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_liv_rf_4", "cat_liver_gallbladder", "Severe abdominal swelling", "Emergency Red Flag", "Tense refractory ascites or suspected spontaneous bacterial peritonitis", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_liv_rf_5", "cat_liver_gallbladder", "Vomiting blood", "Emergency Red Flag", "Bleeding esophageal varices secondary to portal hypertension", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_liv_rf_6", "cat_liver_gallbladder", "Sudden severe liver-related symptoms", "Emergency Red Flag", "Acute yellow atrophy or toxic hepatitis with encephalopathy", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 11. KIDNEY & URINARY SYSTEM
        add(SymptomEntity("sym_kid_1", "cat_kidney_urinary", "Kidney Pain", "Renal", "Flank or lower back ache below ribcage on one or both sides", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_kid_2", "cat_kidney_urinary", "Urinary Pain", "Dysuria", "Burning, stinging, or scalding pain during urination", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_kid_3", "cat_kidney_urinary", "Frequent Urination", "Urinary Frequency", "Need to pass urine much more often than usual, day and night", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_kid_4", "cat_kidney_urinary", "Blood in Urine", "Hematuria", "Pink, reddish, or smoky-brown discoloration of urine", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_kid_5", "cat_kidney_urinary", "Difficulty Urinating", "Hesitancy", "Straining or weak urinary stream", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_kid_6", "cat_kidney_urinary", "Kidney Stones", "Nephrolithiasis", "History or current colicky flank-to-groin pain", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_kid_7", "cat_kidney_urinary", "Urinary Infection Symptoms", "UTI", "Foul-smelling cloudy urine, low fever, suprapubic pressure", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_kid_8", "cat_kidney_urinary", "Urinary Retention", "Bladder Outlet", "Feeling bladder is full but unable to initiate urination", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_kid_9", "cat_kidney_urinary", "Urgency to Urinate", "Bladder Spasm", "Sudden compelling urge to urinate that is difficult to defer", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_kid_10", "cat_kidney_urinary", "Reduced Urine Output", "Oliguria", "Noticeably small volume of urine passed in 24 hours", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_kid_11", "cat_kidney_urinary", "Increased Urine Output", "Polyuria", "Passing abnormally large volumes of dilute urine", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_kid_rf_1", "cat_kidney_urinary", "Inability to urinate", "Emergency Red Flag", "Complete acute urinary retention with painful distended bladder", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_kid_rf_2", "cat_kidney_urinary", "Severe kidney pain", "Emergency Red Flag", "Intolerable renal colic with vomiting and agitation", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_kid_rf_3", "cat_kidney_urinary", "Blood in urine with severe pain", "Emergency Red Flag", "Gross frank hematuria with clots causing bladder outlet obstruction", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_kid_rf_4", "cat_kidney_urinary", "Very low urine output", "Emergency Red Flag", "Anuria or severe oliguria indicating acute kidney injury (AKI)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_kid_rf_5", "cat_kidney_urinary", "Severe urinary symptoms with fever", "Emergency Red Flag", "High fever with chills, rigors, and flank tenderness (Pyelonephritis / Urosepsis)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_kid_rf_6", "cat_kidney_urinary", "Severe flank pain with vomiting", "Emergency Red Flag", "Obstructing infected ureteral stone requiring urgent decompression", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 12. BOWEL & RECTAL HEALTH
        add(SymptomEntity("sym_bowel_1", "cat_bowel_rectal", "Rectal Pain", "Proctology", "Pain, aching, or burning in the anal canal or perineum", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_2", "cat_bowel_rectal", "Piles / Hemorrhoids", "Vascular", "Swollen veins around anus causing itching, bleeding, or protrusion", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_3", "cat_bowel_rectal", "Anal Bleeding", "Proctology", "Bright red blood on wiping or dripping into toilet bowl", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_4", "cat_bowel_rectal", "Anal Fissure", "Mucosal Tear", "Sharp tearing pain during and after bowel movement with streak of blood", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_5", "cat_bowel_rectal", "Blood in Stool", "Colorectal", "Blood mixed in stool or clots passed during defecation", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_bowel_6", "cat_bowel_rectal", "Constipation", "Colonic Transit", "Infrequent, hard stool requiring prolonged straining", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_bowel_7", "cat_bowel_rectal", "Bowel Control Problems", "Fecal Incontinence", "Inability to control gas or stool passage", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_8", "cat_bowel_rectal", "Pain During Bowel Movement", "Defecation", "Severe burning or cramping when passing stool", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bowel_9", "cat_bowel_rectal", "Rectal Swelling", "Perianal Mass", "Tender lump, abscess, or swelling near the anal opening", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_bowel_rf_1", "cat_bowel_rectal", "Heavy rectal bleeding", "Emergency Red Flag", "Large volume of fresh red or dark blood passed per rectum", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bowel_rf_2", "cat_bowel_rectal", "Black stool", "Emergency Red Flag", "Tar-like black stool indicating major gastrointestinal bleed", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bowel_rf_3", "cat_bowel_rectal", "Severe rectal pain with fever", "Emergency Red Flag", "Perianal or ischiorectal abscess with systemic toxicity", isEmergencyRedFlag = true, defaultSeverity = 8))
        add(SymptomEntity("sym_bowel_rf_4", "cat_bowel_rectal", "Continuous uncontrolled bleeding", "Emergency Red Flag", "Unremitting rectal hemorrhage causing dizziness or pallor", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 13. BLOOD & IMMUNE SYSTEM
        add(SymptomEntity("sym_bld_1", "cat_blood_immune", "Anemia-like Symptoms", "Hematology", "Tiredness, pallor, dizziness on standing, breathlessness on minor exertion", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bld_2", "cat_blood_immune", "Easy Bruising", "Coagulation", "Frequent unexplained blue or purple bruises with minor bumps", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_bld_3", "cat_blood_immune", "Excessive Bleeding", "Hemostasis", "Prolonged bleeding from small cuts or dental work", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bld_4", "cat_blood_immune", "Frequent Infections", "Immune Deficit", "Recurring colds, chest infections, boils, or sinus trouble", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bld_5", "cat_blood_immune", "Persistent Weakness", "Constitutional", "Chronic profound exhaustion not relieved by sleep", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bld_6", "cat_blood_immune", "Pale Skin", "Anemia Sign", "Noticeable paleness of conjunctiva, tongue, and nail beds", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_bld_7", "cat_blood_immune", "Unusual Fatigue", "Metabolic / Blood", "Inability to carry out normal daily tasks due to lack of energy", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_bld_8", "cat_blood_immune", "Enlarged Lymph Nodes", "Lymphatics", "Swollen painless or tender glands in neck, armpits, or groin", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_bld_rf_1", "cat_blood_immune", "Severe uncontrolled bleeding", "Emergency Red Flag", "Heavy spontaneous hemorrhages, petechiae, or purpura throughout body", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bld_rf_2", "cat_blood_immune", "Fainting with suspected bleeding", "Emergency Red Flag", "Syncope or hypotensive shock with internal bleeding or severe anemia", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_bld_rf_3", "cat_blood_immune", "Severe weakness with bleeding", "Emergency Red Flag", "Profound collapse with active epistaxis, GI bleed, or purpura", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bld_rf_4", "cat_blood_immune", "Rapidly increasing bruising", "Emergency Red Flag", "Widespread ecchymoses / disseminated intravascular coagulation (DIC)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bld_rf_5", "cat_blood_immune", "Severe infection symptoms", "Emergency Red Flag", "Neutropenic fever, septic shock, hypothermia, or extreme prostration", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 14. BONES & JOINTS
        add(SymptomEntity("sym_bone_1", "cat_bones_joints", "Bone Pain", "Orthopedics", "Deep, aching, or localized pain in long bones or ribs", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_2", "cat_bones_joints", "Joint Pain", "Arthralgia", "Aching, tenderness, or discomfort in knees, hips, hands, or shoulders", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_3", "cat_bones_joints", "Arthritis Symptoms", "Rheumatology", "Morning joint stiffness lasting > 30 minutes, swelling, warmth", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_4", "cat_bones_joints", "Fracture", "Trauma", "Suspected break in bone with local tenderness and swelling", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_bone_5", "cat_bones_joints", "Back Pain", "Spine", "Lumbosacral or upper back ache, muscle spasm, or postural pain", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_6", "cat_bones_joints", "Neck Pain", "Cervical Spine", "Stiffness or ache in neck with restricted rotation", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_7", "cat_bones_joints", "Joint Swelling", "Synovitis", "Fluid accumulation or visible enlargement of a joint", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_8", "cat_bones_joints", "Joint Stiffness", "Mobility", "Tightness and difficulty bending or straightening joints", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_bone_9", "cat_bones_joints", "Reduced Movement", "Range of Motion", "Inability to achieve normal joint flexion or extension", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_bone_10", "cat_bones_joints", "Bone Injury", "Trauma", "Direct contusion, blunt trauma, or hairline fracture", isEmergencyRedFlag = false, defaultSeverity = 5))
        // Red Flags
        add(SymptomEntity("sym_bone_rf_1", "cat_bones_joints", "Suspected major fracture", "Emergency Red Flag", "Femur, pelvic, or open bone fracture with severe shock risk", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bone_rf_2", "cat_bones_joints", "Deformity after injury", "Emergency Red Flag", "Obvious unnatural angular deformity, limb shortening, or bone protrusion", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bone_rf_3", "cat_bones_joints", "Inability to move a limb after trauma", "Emergency Red Flag", "Complete motor loss or severe neurovascular compromise to limb", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_bone_rf_4", "cat_bones_joints", "Severe neck pain after trauma", "Emergency Red Flag", "High-velocity cervical spine injury requiring rigid immobilization", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_bone_rf_5", "cat_bones_joints", "Severe back pain with weakness/numbness", "Emergency Red Flag", "Cauda equina syndrome with bowel/bladder incontinence and saddle numbness", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_bone_rf_6", "cat_bones_joints", "Suspected spinal injury", "Emergency Red Flag", "Spinal cord injury following fall from height or road crash", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 15. MUSCLES & SOFT TISSUE
        add(SymptomEntity("sym_musc_1", "cat_muscles_soft_tissue", "Muscle Pain", "Myalgia", "Soreness, aching, or tenderness across muscular groups", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_musc_2", "cat_muscles_soft_tissue", "Muscle Weakness", "Motor", "Decreased power in arms, legs, or torso muscles", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_musc_3", "cat_muscles_soft_tissue", "Muscle Cramps", "Spasm", "Sudden painful involuntary contraction in calves or thighs", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_musc_4", "cat_muscles_soft_tissue", "Sprain", "Ligament", "Twisted ligament in ankle, wrist, or knee with swelling", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_musc_5", "cat_muscles_soft_tissue", "Strain", "Tendon / Muscle", "Pulled muscle fiber from overstretching or lifting", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_musc_6", "cat_muscles_soft_tissue", "Muscle Swelling", "Inflammation", "Localized muscular enlargement, tenderness, or hematoma", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_musc_7", "cat_muscles_soft_tissue", "Soft Tissue Injury", "Contusion", "Bruised subcutaneous tissue and muscle from impact", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_musc_8", "cat_muscles_soft_tissue", "Muscle Stiffness", "Flexibility", "Tightness and resistance to passive muscular stretch", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_musc_rf_1", "cat_muscles_soft_tissue", "Severe sudden muscle weakness", "Emergency Red Flag", "Acute ascending paralysis (Guillain-Barré) or acute myasthenia", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_musc_rf_2", "cat_muscles_soft_tissue", "Major muscle injury", "Emergency Red Flag", "Complete muscle belly rupture or massive soft tissue avulsion", isEmergencyRedFlag = true, defaultSeverity = 8))
        add(SymptomEntity("sym_musc_rf_3", "cat_muscles_soft_tissue", "Severe swelling after trauma", "Emergency Red Flag", "Compartment syndrome with tense hard extremity and loss of distal pulse", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_musc_rf_4", "cat_muscles_soft_tissue", "Muscle weakness with neurological symptoms", "Emergency Red Flag", "Weakness accompanied by difficulty breathing, swallowing, or diplopia", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 16. SKIN, HAIR & NAILS
        add(SymptomEntity("sym_skin_1", "cat_skin_hair_nails", "Rash", "Dermatology", "Red patches, bumps, hives, or irritation on skin surface", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_skin_2", "cat_skin_hair_nails", "Itching", "Pruritus", "Uncontrollable urge to scratch skin", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_skin_3", "cat_skin_hair_nails", "Acne", "Sebaceous", "Pimples, blackheads, or inflamed cysts on face or back", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_skin_4", "cat_skin_hair_nails", "Skin Infection", "Cutaneous", "Warm, red, tender skin area or boils/folliculitis", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_skin_5", "cat_skin_hair_nails", "Wound", "Abrasion / Cut", "Superficial cut, scratch, or abrasion", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_skin_6", "cat_skin_hair_nails", "Burn", "Thermal", "First-degree sunburn or minor thermal scald", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_skin_7", "cat_skin_hair_nails", "Hair Loss", "Trichology", "Excessive shedding, thinning hair, or patchy alopecia", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_skin_8", "cat_skin_hair_nails", "Nail Problems", "Onychology", "Brittle, discolored, ingrown, or thickened fungal nails", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_skin_9", "cat_skin_hair_nails", "Skin Discoloration", "Pigmentation", "Darkening, lightening, or uneven patches on skin", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_skin_10", "cat_skin_hair_nails", "Skin Swelling", "Urticaria", "Wheals or localized cutaneous edema", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_skin_11", "cat_skin_hair_nails", "Dry Skin", "Xerosis", "Flaking, rough, or scaling skin surface", isEmergencyRedFlag = false, defaultSeverity = 2))
        add(SymptomEntity("sym_skin_12", "cat_skin_hair_nails", "Skin Lesion", "Derm Examination", "New or changing mole, plaque, or ulcerated nodule", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_skin_rf_1", "cat_skin_hair_nails", "Severe burn", "Emergency Red Flag", "Second/third degree burns covering > 10% body surface or involving face/hands", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_skin_rf_2", "cat_skin_hair_nails", "Rapidly spreading rash with breathing difficulty", "Emergency Red Flag", "Anaphylaxis or Stevens-Johnson syndrome (SJS / TEN)", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_skin_rf_3", "cat_skin_hair_nails", "Facial swelling with allergy symptoms", "Emergency Red Flag", "Angioedema of lips, tongue, and eyelids with stridor", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_skin_rf_4", "cat_skin_hair_nails", "Severe skin infection symptoms", "Emergency Red Flag", "Necrotizing fasciitis with dusky skin, blistering, and extreme pain", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_skin_rf_5", "cat_skin_hair_nails", "Chemical burn", "Emergency Red Flag", "Deep tissue caustic injury requiring copious immediate irrigation", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_skin_rf_6", "cat_skin_hair_nails", "Major open wound", "Emergency Red Flag", "Deep laceration exposing tendon, bone, or pulsatile vessel", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 17. HORMONAL & ENDOCRINE SYSTEM
        add(SymptomEntity("sym_endo_1", "cat_endocrine_hormonal", "Diabetes-related Symptoms", "Glucose Metabolism", "High blood sugar, sweet breath, or tingling feet", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_endo_2", "cat_endocrine_hormonal", "Thyroid Problems", "Thyroid", "Enlarged thyroid (goiter), palpitations, fatigue, or mood swings", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_endo_3", "cat_endocrine_hormonal", "Hormonal Imbalance", "Endocrine", "Irregular cycles, acne, hirsutism, or hormonal weight shifts", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_endo_4", "cat_endocrine_hormonal", "Abnormal Sweating", "Autonomic", "Night sweats or excessive daytime perspiration unrelated to heat", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_endo_5", "cat_endocrine_hormonal", "Unexplained Weight Change", "Metabolism", "Rapid involuntary weight loss or unexplained sudden gain", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_endo_6", "cat_endocrine_hormonal", "Excessive Thirst", "Polydipsia", "Constant dry mouth and insatiable fluid intake", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_endo_7", "cat_endocrine_hormonal", "Frequent Urination", "Polyuria", "Waking multiple times at night to pass urine", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_endo_8", "cat_endocrine_hormonal", "Heat Intolerance", "Hyperthyroid", "Feeling uncomfortably hot and sweating in cool environments", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_endo_9", "cat_endocrine_hormonal", "Cold Intolerance", "Hypothyroid", "Persistent chilliness and wearing sweaters in warm rooms", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_endo_rf_1", "cat_endocrine_hormonal", "Severe confusion with suspected blood sugar problem", "Emergency Red Flag", "Diabetic Ketoacidosis (DKA) or severe hypoglycemia with altered sensorium", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_endo_rf_2", "cat_endocrine_hormonal", "Loss of consciousness", "Emergency Red Flag", "Diabetic coma or myxedema crisis", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_endo_rf_3", "cat_endocrine_hormonal", "Severe weakness with diabetic symptoms", "Emergency Red Flag", "Hyperosmolar Hyperglycemic State (HHS) with profound weakness", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_endo_rf_4", "cat_endocrine_hormonal", "Severe dehydration with excessive urination/thirst", "Emergency Red Flag", "Critical osmotic diuresis with electrolyte collapse and hypotension", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 18. FEMALE REPRODUCTIVE SYSTEM
        add(SymptomEntity("sym_fem_1", "cat_female_reproductive", "Period Problems", "Gynecology", "Painful cramps (dysmenorrhea) or heavy menstrual flow", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_fem_2", "cat_female_reproductive", "Abnormal Bleeding", "Metrorrhagia", "Spotting between periods or post-coital bleeding", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_fem_3", "cat_female_reproductive", "Pelvic Pain", "Pelvis", "Aching, sharp, or pressure discomfort in lower pelvis", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_fem_4", "cat_female_reproductive", "Vaginal Discharge", "Infection", "Thick, curdy, yellow/green, or foul-smelling discharge", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_fem_5", "cat_female_reproductive", "Vaginal Itching", "Vulvovaginitis", "Irritation, burning, or redness around genital area", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_fem_6", "cat_female_reproductive", "Menopause Symptoms", "Climacteric", "Hot flushes, sleep disruption, mood changes, or vaginal dryness", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_fem_7", "cat_female_reproductive", "Ovulation-related Problems", "Mittelschmerz", "Mid-cycle sharp unilateral pelvic twinges", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_fem_8", "cat_female_reproductive", "Breast Pain", "Mastalgia", "Cyclical or non-cyclical tenderness in breast tissue", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_fem_9", "cat_female_reproductive", "Breast Lump", "Mammary", "Palpable distinct lump or thickening in breast or axilla", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_fem_10", "cat_female_reproductive", "Irregular Periods", "Oligomenorrhea", "Unpredictable cycle lengths, skipped periods, or PCOS symptoms", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_fem_rf_1", "cat_female_reproductive", "Heavy uncontrolled vaginal bleeding", "Emergency Red Flag", "Soaking > 2 pads per hour for consecutive hours with weakness", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_fem_rf_2", "cat_female_reproductive", "Severe pelvic pain", "Emergency Red Flag", "Ruptured ovarian cyst, acute pelvic inflammatory disease (PID), or torsion", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_fem_rf_3", "cat_female_reproductive", "Severe pain with fainting", "Emergency Red Flag", "Acute gynecological emergency with peritoneal hemorrhage and syncope", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_fem_rf_4", "cat_female_reproductive", "Sudden severe reproductive symptoms", "Emergency Red Flag", "Acute adnexal torsion or severe toxic shock syndrome", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 19. PREGNANCY & MATERNAL HEALTH
        add(SymptomEntity("sym_mat_1", "cat_maternal_pregnancy", "Pregnancy-related Symptoms", "Obstetrics", "Early pregnancy fatigue, frequent urination, breast tenderness", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_mat_2", "cat_maternal_pregnancy", "Pregnancy Pain", "Maternal", "Round ligament discomfort or mild backache in pregnancy", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mat_3", "cat_maternal_pregnancy", "Pregnancy Bleeding", "Obstetrics", "Mild spotting in first or second trimester", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_mat_4", "cat_maternal_pregnancy", "Pregnancy Vomiting", "Hyperemesis", "Morning sickness or mild nausea during gestation", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mat_5", "cat_maternal_pregnancy", "Reduced Fetal Movement Concern", "Fetal Wellbeing", "Perceived slowing of baby's usual kicking pattern", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_mat_6", "cat_maternal_pregnancy", "Pregnancy Complications", "High Risk", "Gestational diabetes or elevated maternal blood pressure", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_mat_7", "cat_maternal_pregnancy", "Prenatal Concerns", "Antenatal", "Questions on fetal growth scans or routine prenatal tests", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_mat_8", "cat_maternal_pregnancy", "Postpartum Problems", "Puerperium", "Lochia changes, perineal pain, or breastfeeding difficulty", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mat_9", "cat_maternal_pregnancy", "Swelling During Pregnancy", "Edema", "Mild swelling of ankles and feet in third trimester", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_mat_10", "cat_maternal_pregnancy", "Headache During Pregnancy", "Maternal", "Mild tension headache in expectant mother", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_mat_rf_1", "cat_maternal_pregnancy", "Heavy bleeding during pregnancy", "Emergency Red Flag", "Placenta previa, abruption, or miscarriage with heavy bright bleeding", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mat_rf_2", "cat_maternal_pregnancy", "Severe abdominal pain during pregnancy", "Emergency Red Flag", "Ectopic rupture or uterine hypertonus with severe cramping", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mat_rf_3", "cat_maternal_pregnancy", "Severe headache with vision changes during pregnancy", "Emergency Red Flag", "Severe Preeclampsia / Eclampsia with scotoma, epigastric pain, and high BP", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mat_rf_4", "cat_maternal_pregnancy", "Reduced fetal movement concern", "Emergency Red Flag", "Absence of fetal kicks in third trimester requiring immediate CTG", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_mat_rf_5", "cat_maternal_pregnancy", "Severe breathing difficulty during pregnancy", "Emergency Red Flag", "Peripartum cardiomyopathy or pulmonary embolism risk in pregnancy", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mat_rf_6", "cat_maternal_pregnancy", "Loss of consciousness during pregnancy", "Emergency Red Flag", "Maternal collapse, syncope, or severe shock", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_mat_rf_7", "cat_maternal_pregnancy", "Seizure during pregnancy", "Emergency Red Flag", "Eclamptic convulsions requiring emergency magnesium sulfate & delivery", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 20. MALE REPRODUCTIVE SYSTEM
        add(SymptomEntity("sym_male_1", "cat_male_reproductive", "Testicular Pain", "Urology", "Ache or tenderness in testicles or scrotum", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_male_2", "cat_male_reproductive", "Genital Pain", "Andrology", "Discomfort in penis, scrotum, or perineal area", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_male_3", "cat_male_reproductive", "Genital Swelling", "Scrotal", "Hydrocele, varicocele, or scrotal fullness", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_male_4", "cat_male_reproductive", "Penile Problems", "Genital", "Discharge, sores, foreskin tightness (phimosis), or curvature", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_male_5", "cat_male_reproductive", "Prostate Symptoms", "BPH / Prostatitis", "Weak urine flow, nocturia, or feeling of incomplete emptying", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_male_6", "cat_male_reproductive", "Urination-related Problems", "Urology", "Hesitancy or burning sensation at tip of urethra", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_male_7", "cat_male_reproductive", "Erectile Problems", "Sexual Function", "Difficulty achieving or maintaining an erection", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_male_8", "cat_male_reproductive", "Male Infertility Concerns", "Reproduction", "Semen parameters or difficulty conceiving after 1 year", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_male_9", "cat_male_reproductive", "Genital Infection Symptoms", "STI", "Urethral discharge, genital blisters, or burning on urination", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_male_rf_1", "cat_male_reproductive", "Sudden severe testicular pain", "Emergency Red Flag", "Testicular torsion requiring surgical exploration within 6 hours", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_male_rf_2", "cat_male_reproductive", "Rapid testicular swelling", "Emergency Red Flag", "Fournier's gangrene or acute strangulated inguinoscrotal hernia", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_male_rf_3", "cat_male_reproductive", "Severe genital injury", "Emergency Red Flag", "Penile fracture or traumatic scrotal avulsion", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_male_rf_4", "cat_male_reproductive", "Severe bleeding", "Emergency Red Flag", "Heavy urethral hemorrhage or genital laceration bleed", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 21. CHILD & INFANT HEALTH
        add(SymptomEntity("sym_ped_1", "cat_child_infant", "Fever in Child", "Pediatrics", "Elevated temperature (100°F - 102°F) in infant or toddler", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_2", "cat_child_infant", "Feeding Problems", "Nutrition", "Reluctance to latch, suck, or eat solids", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ped_3", "cat_child_infant", "Excessive Crying", "Pediatric", "Colicky or persistent crying that is hard to soothe", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_4", "cat_child_infant", "Irritability", "Behavioral", "Fussiness, restlessness, or unusual moodiness", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ped_5", "cat_child_infant", "Breathing Problems", "Airway", "Mild nasal congestion, noisy snuffles, or mild cough", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_ped_6", "cat_child_infant", "Vomiting", "Pediatric GI", "Regurgitation or vomiting after feeds", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_7", "cat_child_infant", "Diarrhea", "Pediatric GI", "Frequent watery stools in infant or young child", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_8", "cat_child_infant", "Rash", "Pediatric Derm", "Diaper rash, heat rash, or mild viral exanthem", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ped_9", "cat_child_infant", "Growth Concerns", "Development", "Slow weight gain or falling along growth percentiles", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ped_10", "cat_child_infant", "Development Concerns", "Milestones", "Delayed motor milestones like sitting, crawling, or speech", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_ped_11", "cat_child_infant", "Newborn Concerns", "Neonatology", "Jaundice in first week, cord stump redness, or sleep cycles", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_12", "cat_child_infant", "Poor Feeding", "Nutrition", "Taking less than half of usual milk volume", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_ped_13", "cat_child_infant", "Unusual Sleepiness", "Neurology", "Sleeping longer than usual but arousable for feeds", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_ped_rf_1", "cat_child_infant", "Severe breathing difficulty", "Emergency Red Flag", "Subcostal retractions, grunting, tracheal tug, or stridor in child", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_ped_rf_2", "cat_child_infant", "Blue lips", "Emergency Red Flag", "Cyanosis around lips or tongue indicating severe hypoxemia", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_ped_rf_3", "cat_child_infant", "Unresponsiveness", "Emergency Red Flag", "Child floppy, non-responsive to voice, or impossible to wake", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_ped_rf_4", "cat_child_infant", "Seizure", "Emergency Red Flag", "Febrile convulsions, rhythmic limb jerking, or eye rolling", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_ped_rf_5", "cat_child_infant", "Severe dehydration", "Emergency Red Flag", "No wet diapers for > 8 hours, sunken fontanelle, absent tears when crying", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ped_rf_6", "cat_child_infant", "Persistent vomiting", "Emergency Red Flag", "Bile-stained green vomiting or projectile emesis with dehydration", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ped_rf_7", "cat_child_infant", "Severe lethargy", "Emergency Red Flag", "Profound stupor or lack of interaction in toddler", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ped_rf_8", "cat_child_infant", "Serious injury", "Emergency Red Flag", "Fall from height, head trauma, burn, or ingestion of toxic object", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_ped_rf_9", "cat_child_infant", "Newborn emergency symptoms", "Emergency Red Flag", "Fever in baby < 3 months, hypothermia, grunting, or severe jaundice", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 22. INFECTIOUS DISEASES
        add(SymptomEntity("sym_inf_1", "cat_infectious_diseases", "Fever", "Infectious", "Body temperature 100°F - 102.5°F with sweating and body aches", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_inf_2", "cat_infectious_diseases", "Flu-like Symptoms", "Viral", "Generalized myalgia, mild fever, chills, cough, and runny nose", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_inf_3", "cat_infectious_diseases", "Viral Infection Symptoms", "Viral", "Malaise, sore throat, low-grade temperature, and fatigue", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_inf_4", "cat_infectious_diseases", "Bacterial Infection Symptoms", "Bacterial", "Localized pus, purulent phlegm, or persistent fever > 3 days", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_inf_5", "cat_infectious_diseases", "Dengue-like Symptoms", "Tropical", "Retro-orbital eye pain, high fever, severe bone pain ('breakbone')", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_inf_6", "cat_infectious_diseases", "Malaria-like Symptoms", "Tropical", "Periodic cyclical high fever with intense shivering rigors", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_inf_7", "cat_infectious_diseases", "Tuberculosis-related Symptoms", "Mycobacterial", "Chronic cough > 2 weeks, night sweats, weight loss, low evening fever", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_inf_8", "cat_infectious_diseases", "Respiratory Infection", "Pulmonary", "Bronchitis, pneumonia symptoms with chest congestion and sputum", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_inf_9", "cat_infectious_diseases", "Recurrent Fever", "Infectious", "Fever resolving and returning repeatedly over weeks", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_inf_10", "cat_infectious_diseases", "Infection-related Weakness", "Post-viral", "Post-infectious debility and low stamina", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_inf_rf_1", "cat_infectious_diseases", "Severe breathing difficulty", "Emergency Red Flag", "Acute Respiratory Distress Syndrome (ARDS) or severe pneumonia", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_inf_rf_2", "cat_infectious_diseases", "Unconsciousness", "Emergency Red Flag", "Cerebral malaria, viral encephalitis, or severe septic encephalopathy", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_inf_rf_3", "cat_infectious_diseases", "Severe dehydration", "Emergency Red Flag", "Acute cholera-like watery purging with profound hypovolemic shock", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_inf_rf_4", "cat_infectious_diseases", "Seizure", "Emergency Red Flag", "Meningitis, encephalitis, or febrile status with neck stiffness", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_inf_rf_5", "cat_infectious_diseases", "Severe bleeding", "Emergency Red Flag", "Dengue hemorrhagic shock or thrombocytopenic spontaneous bleeding", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_inf_rf_6", "cat_infectious_diseases", "Rapid deterioration", "Emergency Red Flag", "Septic shock with cold mottled extremities, hypotension, and tachypnea", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 23. MENTAL & BEHAVIORAL HEALTH
        add(SymptomEntity("sym_men_1", "cat_mental_health", "Anxiety", "Psychology", "Persistent excessive worry, tension, or nervousness about everyday events", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_men_2", "cat_mental_health", "Stress", "Mental Wellbeing", "Feeling overwhelmed, irritable, or unable to cope with demands", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_men_3", "cat_mental_health", "Depression", "Mood Disorder", "Low mood, feelings of worthlessness, loss of interest in activities", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_men_4", "cat_mental_health", "Panic Attacks", "Anxiety", "Sudden intense fear, chest pounding, breathlessness, and shaking", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_men_5", "cat_mental_health", "Sleep Problems", "Mental Health", "Difficulty initiating or maintaining sleep linked to racing thoughts", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_men_6", "cat_mental_health", "Mood Changes", "Affective", "Rapid swings between irritability, sadness, or overexcitement", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_men_7", "cat_mental_health", "Memory Problems", "Cognitive", "Forgetfulness or brain fog associated with stress or depression", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_men_8", "cat_mental_health", "Behavioral Changes", "Psychology", "Social withdrawal, neglect of personal care, or agitation", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_men_9", "cat_mental_health", "Difficulty Concentrating", "Attention", "Inability to focus on work, reading, or conversations", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_men_10", "cat_mental_health", "Emotional Distress", "Mental Health", "Feeling emotionally drained, hopeless, or tearful", isEmergencyRedFlag = false, defaultSeverity = 4))
        // Red Flags
        add(SymptomEntity("sym_men_rf_1", "cat_mental_health", "Immediate danger to self", "Emergency Red Flag", "Active suicidal ideation, planning, self-harm crisis, or suicide attempt", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_men_rf_2", "cat_mental_health", "Immediate danger to others", "Emergency Red Flag", "Severe acute agitation, homicidal intent, or violent behavior", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_men_rf_3", "cat_mental_health", "Severe confusion", "Emergency Red Flag", "Acute delirium, hallucination-driven terror, or total disorientation", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_men_rf_4", "cat_mental_health", "Loss of contact with reality", "Emergency Red Flag", "Acute psychosis, paranoid delusions, or command auditory hallucinations", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_men_rf_5", "cat_mental_health", "Acute psychiatric crisis", "Emergency Red Flag", "Catatonia, severe neuroleptic reaction, or dangerous mania", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 24. SLEEP HEALTH
        add(SymptomEntity("sym_slp_1", "cat_sleep_health", "Insomnia", "Sleep Medicine", "Trouble falling asleep, staying asleep, or non-restorative sleep", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_slp_2", "cat_sleep_health", "Excessive Sleepiness", "Somnolence", "Struggling to stay awake during daytime meetings or quiet moments", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_slp_3", "cat_sleep_health", "Snoring", "Upper Airway", "Loud habitual snoring noticed by sleep partner", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_slp_4", "cat_sleep_health", "Sleep Apnea Symptoms", "Obstructive Apnea", "Waking with dry mouth, morning headache, and witnessed breath pauses", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_slp_5", "cat_sleep_health", "Sleep Disturbance", "Circadian", "Restless legs, vivid nightmares, or irregular sleep-wake cycles", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_slp_6", "cat_sleep_health", "Frequent Night Awakening", "Sleep Maintenance", "Waking 3 or more times per night and taking long to fall asleep", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_slp_7", "cat_sleep_health", "Difficulty Falling Asleep", "Sleep Onset", "Lying awake in bed for > 1 hour before sleep onset", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_slp_rf_1", "cat_sleep_health", "Severe daytime sleepiness causing safety risk", "Emergency Red Flag", "Sudden sleep attacks while driving or operating heavy machinery (Narcolepsy risk)", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_slp_rf_2", "cat_sleep_health", "Breathing pauses during sleep with severe symptoms", "Emergency Red Flag", "Severe obstructive sleep apnea causing cardiac arrhythmias or hypoxemia", isEmergencyRedFlag = true, defaultSeverity = 8))

        // 25. GENERAL / WHOLE BODY
        add(SymptomEntity("sym_gen_1", "cat_general_body", "Fever", "Systemic", "Elevated body temperature above 99.5°F", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_2", "cat_general_body", "Fatigue", "Constitutional", "Exhaustion, low stamina, or feeling drained throughout the day", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_3", "cat_general_body", "Weakness", "Systemic", "General reduction in physical energy or bodily strength", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_4", "cat_general_body", "Weight Loss", "Metabolism", "Unintentional loss of body weight without dieting", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_gen_5", "cat_general_body", "Weight Gain", "Metabolism", "Rapid unexplained fluid or weight accumulation", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_6", "cat_general_body", "Loss of Appetite", "Nutrition", "Diminished interest in food or feeling full quickly", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_7", "cat_general_body", "General Pain", "Somatic", "Widespread body aches or diffuse fibromyalgia-like discomfort", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_gen_8", "cat_general_body", "Dehydration", "Fluid Balance", "Dry lips, concentrated urine, and increased thirst", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_gen_9", "cat_general_body", "Chills", "Thermoregulation", "Cold sensations accompanied by shivering or goosebumps", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_10", "cat_general_body", "Sweating", "Autonomic", "Profuse sweating during day or night", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_gen_11", "cat_general_body", "Unexplained Symptoms", "Diagnostic Challenge", "Multiple non-specific bodily sensations requiring physician assessment", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_gen_12", "cat_general_body", "General Malaise", "Constitutional", "General feeling of being unwell, sick, or rundown", isEmergencyRedFlag = false, defaultSeverity = 3))
        // Red Flags
        add(SymptomEntity("sym_gen_rf_1", "cat_general_body", "Severe unexplained weakness", "Emergency Red Flag", "Sudden inability to stand, walk, or perform basic motor function", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_gen_rf_2", "cat_general_body", "Unconsciousness", "Emergency Red Flag", "Unresponsive state, stupor, or profound coma", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_gen_rf_3", "cat_general_body", "Severe dehydration", "Emergency Red Flag", "Severe volume depletion with hypotension, confusion, and dry tongue", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_gen_rf_4", "cat_general_body", "Rapid deterioration", "Emergency Red Flag", "Systemic collapse or vital signs crashing over a few hours", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_gen_rf_5", "cat_general_body", "Severe unexplained pain", "Emergency Red Flag", "Extreme 10/10 pain score without clear external cause", isEmergencyRedFlag = true, defaultSeverity = 9))

        // 26. INJURY & TRAUMA
        add(SymptomEntity("sym_trm_1", "cat_injury_trauma", "Accident", "Trauma", "Vehicular or domestic impact with soft tissue trauma", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_trm_2", "cat_injury_trauma", "Fall", "Mechanical", "Fall from standing height or steps with bruising or soreness", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_trm_3", "cat_injury_trauma", "Head Injury", "Trauma", "Direct blow to head without prolonged blackout", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_trm_4", "cat_injury_trauma", "Fracture", "Orthopedic", "Suspected bone crack or fracture in limb", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_trm_5", "cat_injury_trauma", "Bleeding", "Vascular", "Moderate bleeding from cut or wound", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_trm_6", "cat_injury_trauma", "Burn", "Thermal", "Superficial hot water or pan burn with blistering", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_trm_7", "cat_injury_trauma", "Cut / Laceration", "Wound", "Skin cut requiring cleaning or possible suturing", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_trm_8", "cat_injury_trauma", "Animal Bite", "Bite Wound", "Dog, cat, or monkey bite requiring rabies evaluation & cleaning", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_trm_9", "cat_injury_trauma", "Insect Bite", "Envenomation", "Bee, wasp, or spider sting with local swelling", isEmergencyRedFlag = false, defaultSeverity = 3))
        add(SymptomEntity("sym_trm_10", "cat_injury_trauma", "Sports Injury", "Athletic", "Twisted joint, hamstring pull, or collision injury", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_trm_11", "cat_injury_trauma", "Chemical Injury", "Hazard", "Contact with irritating solvent, detergent, or mild acid", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_trm_12", "cat_injury_trauma", "Crush Injury", "Mechanical", "Fingers or foot pinched by heavy door or object", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_trm_13", "cat_injury_trauma", "Sprain", "Ligament", "Ankle or wrist ligament tear with tenderness", isEmergencyRedFlag = false, defaultSeverity = 4))
        add(SymptomEntity("sym_trm_14", "cat_injury_trauma", "Road Traffic Injury", "Trauma", "Bicycle, motorcycle, or pedestrian road collision", isEmergencyRedFlag = false, defaultSeverity = 6))
        // Red Flags
        add(SymptomEntity("sym_trm_rf_1", "cat_injury_trauma", "Major trauma", "Emergency Red Flag", "High-speed vehicular crash, ejection, or severe crush impact", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_2", "cat_injury_trauma", "Uncontrolled bleeding", "Emergency Red Flag", "Pulsatile arterial bleeding or continuous heavy blood loss not stopped by pressure", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_3", "cat_injury_trauma", "Head injury with loss of consciousness", "Emergency Red Flag", "Traumatic brain injury, vomiting, unequal pupils, or blackout", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_4", "cat_injury_trauma", "Suspected spinal injury", "Emergency Red Flag", "Trauma with neck/back pain and limb numbness or tingling", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_5", "cat_injury_trauma", "Severe breathing difficulty after trauma", "Emergency Red Flag", "Tension pneumothorax, flail chest, or hemothorax", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_6", "cat_injury_trauma", "Major burns", "Emergency Red Flag", "Extensive deep burns, inhalation injury, or electrical high-voltage shock", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_trm_rf_7", "cat_injury_trauma", "Severe crush injury", "Emergency Red Flag", "Traumatic entrapment with risk of rhabdomyolysis and acute renal failure", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 27. POISONING & TOXIC EXPOSURE
        add(SymptomEntity("sym_tox_1", "cat_poisoning_toxic", "Food Poisoning", "Toxicology", "Acute vomiting, watery diarrhea, and abdominal cramps after contaminated food", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_tox_2", "cat_poisoning_toxic", "Chemical Exposure", "Occupational", "Skin contact or inhalation of cleaning chemicals or pesticides", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_tox_3", "cat_poisoning_toxic", "Drug Overdose", "Pharmacology", "Suspected accidental excessive intake of prescription or OTC medication", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_tox_4", "cat_poisoning_toxic", "Poison Ingestion", "Toxicology", "Swallowing of poisonous liquid, plant, or household toxin", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_tox_5", "cat_poisoning_toxic", "Gas Exposure", "Inhalation", "Breathing fumes from generator exhaust, burning coal, or smoke", isEmergencyRedFlag = false, defaultSeverity = 6))
        add(SymptomEntity("sym_tox_6", "cat_poisoning_toxic", "Toxic Inhalation", "Respiratory", "Coughing, burning eyes, and dizziness from inhaled aerosol", isEmergencyRedFlag = false, defaultSeverity = 5))
        add(SymptomEntity("sym_tox_7", "cat_poisoning_toxic", "Unknown Substance Exposure", "Toxicology", "Handling or ingesting unidentifiable substance causing sickness", isEmergencyRedFlag = false, defaultSeverity = 5))
        // Red Flags
        add(SymptomEntity("sym_tox_rf_1", "cat_poisoning_toxic", "Loss of consciousness", "Emergency Red Flag", "Toxic encephalopathy, coma, or profound CNS depression", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_tox_rf_2", "cat_poisoning_toxic", "Breathing difficulty", "Emergency Red Flag", "Toxic pulmonary edema, bronchospasm, or chemical pneumonitis", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_tox_rf_3", "cat_poisoning_toxic", "Seizure", "Emergency Red Flag", "Toxin-induced status epilepticus or organophosphate poisoning", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_tox_rf_4", "cat_poisoning_toxic", "Severe confusion", "Emergency Red Flag", "Severe delirium, visual hallucinations, or metabolic acidosis from toxin", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_tox_rf_5", "cat_poisoning_toxic", "Suspected overdose", "Emergency Red Flag", "Opioid, sedative, or cardiotoxic drug ingestion requiring urgent antidote", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_tox_rf_6", "cat_poisoning_toxic", "Major chemical exposure", "Emergency Red Flag", "Corrosive acid/alkali ingestion, organophosphate absorption, or cyanide risk", isEmergencyRedFlag = true, defaultSeverity = 10))

        // 28. EMERGENCY / CRITICAL SYMPTOMS (Global Emergency Category)
        add(SymptomEntity("sym_em_1", "cat_emergency_critical", "Severe Chest Pain", "Emergency Red Flag", "Crushing, radiating retrosternal chest pain with cold sweat (Suspected Heart Attack)", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_2", "cat_emergency_critical", "Severe Breathing Difficulty", "Emergency Red Flag", "Acute respiratory failure, gasping, central cyanosis, or stridor", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_3", "cat_emergency_critical", "Unconsciousness", "Emergency Red Flag", "Unresponsive to voice and painful stimuli, comatose state", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_4", "cat_emergency_critical", "Severe Bleeding", "Emergency Red Flag", "Spurting arterial hemorrhage or massive blood loss with shock", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_5", "cat_emergency_critical", "Stroke-like Symptoms", "Emergency Red Flag", "Acute face droop, arm weakness, and slurred speech (FAST Stroke)", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_6", "cat_emergency_critical", "Seizure", "Emergency Red Flag", "Active convulsive seizure or post-ictal unresponsiveness", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_7", "cat_emergency_critical", "Severe Allergic Reaction", "Emergency Red Flag", "Anaphylaxis with lip/throat swelling, widespread hives, and low blood pressure", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_8", "cat_emergency_critical", "Sudden Vision Loss", "Emergency Red Flag", "Instant blindness or acute ocular catastrophe", isEmergencyRedFlag = true, defaultSeverity = 9))
        add(SymptomEntity("sym_em_9", "cat_emergency_critical", "Severe Burns", "Emergency Red Flag", "Deep thermal or chemical burns with airway or extensive surface involvement", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_10", "cat_emergency_critical", "Major Trauma", "Emergency Red Flag", "Severe road crash, high fall, or penetrating thoracic/abdominal injury", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_11", "cat_emergency_critical", "Poisoning / Overdose", "Emergency Red Flag", "Lethal substance ingestion, carbon monoxide, or drug intoxication", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_12", "cat_emergency_critical", "Pregnancy Emergency", "Emergency Red Flag", "Eclampsia seizure, massive antepartum hemorrhage, or acute cord prolapse", isEmergencyRedFlag = true, defaultSeverity = 10))
        add(SymptomEntity("sym_em_13", "cat_emergency_critical", "Child Emergency", "Emergency Red Flag", "Infant apnea, grunting respiration, febrile status, or severe unresponsiveness", isEmergencyRedFlag = true, defaultSeverity = 10))
    }
}
