package com.example.data.datasource

import com.example.data.local.MedicineAvailabilityEntity
import com.example.data.local.MedicineEntity
import com.example.data.model.StockStatus

data class MedicineMetadata(
    val id: String,
    val name: String,
    val genericName: String,
    val category: String,
    val purpose: String,
    val associatedConditions: List<String>,
    val usageInfo: String,
    val precautions: String,
    val standardDosage: String,
    val isOtc: Boolean = true,
    val safetyNote: String = "Follow label / clinician guidance."
)

object ComprehensiveMedicineCatalog {

    val commonConditions: List<String> = listOf(
        "Fever",
        "Headache",
        "Common Cold",
        "Cough (Dry / Wet)",
        "Sore Throat",
        "Acidity / Heartburn",
        "Indigestion",
        "Gas / Bloating",
        "Constipation",
        "Diarrhea",
        "Nausea / Vomiting",
        "Mild Body Pain",
        "Muscle Pain",
        "Joint Pain",
        "Toothache",
        "Minor Skin Irritation",
        "Minor Allergic Symptoms",
        "Period Pain / Menstrual Cramps",
        "Mild Dehydration / ORS",
        "Minor Cuts / Wounds",
        "Eye Strain / Dry Eyes",
        "Mouth Ulcers",
        "Motion Sickness"
    )

    private val criticalRedFlags = listOf(
        "chest pain", "heart attack", "stroke", "bleeding", "poison", "poisoning",
        "seizure", "unconscious", "shortness of breath", "severe asthma", "fracture",
        "head injury", "anaphylaxis", "blood in vomit", "severe infection", "overdose",
        "chest pressure", "paralysis", "hemorrhage", "suicide", "coma"
    )

    fun isEmergencyQuery(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isBlank()) return false
        return criticalRedFlags.any { q.contains(it) }
    }

    val catalogItems: List<MedicineMetadata> = listOf(
        MedicineMetadata(
            id = "med_1",
            name = "Paracetamol 650mg",
            genericName = "Acetaminophen / Paracetamol",
            category = "Analgesic & Antipyretic",
            purpose = "Temporary relief of mild-to-moderate fever and body ache",
            associatedConditions = listOf("Fever", "Headache", "Mild Body Pain", "Toothache", "Period Pain / Menstrual Cramps"),
            usageInfo = "1 tablet every 6 to 8 hours as needed. Max 4 tablets in 24 hours.",
            precautions = "Do not exceed recommended dose. Avoid combining with other paracetamol products.",
            standardDosage = "650 mg",
            safetyNote = "Avoid exceeding 4g/day. Safe with food."
        ),
        MedicineMetadata(
            id = "med_2",
            name = "Ibuprofen 400mg",
            genericName = "Ibuprofen",
            category = "NSAID (Anti-Inflammatory)",
            purpose = "Anti-inflammatory relief for pain, cramps, swelling, and fever",
            associatedConditions = listOf("Headache", "Period Pain / Menstrual Cramps", "Muscle Pain", "Joint Pain", "Toothache", "Fever"),
            usageInfo = "1 tablet with or after food with a full glass of water. 1 to 2 times daily.",
            precautions = "Always take after meals. Avoid if you have active ulcers, kidney disease, or third-trimester pregnancy.",
            standardDosage = "400 mg",
            safetyNote = "Take strictly with food to protect stomach."
        ),
        MedicineMetadata(
            id = "med_3",
            name = "Mefenamic Acid 500mg",
            genericName = "Mefenamic Acid",
            category = "NSAID / Antispasmodic Analgesic",
            purpose = "Targeted relief for primary menstrual pain, dysmenorrhea & pelvic cramps",
            associatedConditions = listOf("Period Pain / Menstrual Cramps", "Mild Body Pain"),
            usageInfo = "1 tablet with or immediately after food at the onset of menstrual cramps.",
            precautions = "Do not take on an empty stomach. Consult a gynecologist if pain is unusually severe or abnormal.",
            standardDosage = "500 mg",
            safetyNote = "Dedicated for short-term menstrual discomfort."
        ),
        MedicineMetadata(
            id = "med_4",
            name = "Drotaverine 80mg",
            genericName = "Drotaverine Hydrochloride",
            category = "Smooth Muscle Antispasmodic",
            purpose = "Relieves smooth muscle spasms, menstrual cramps & abdominal colic",
            associatedConditions = listOf("Period Pain / Menstrual Cramps", "Gas / Bloating", "Indigestion"),
            usageInfo = "1 tablet 2 to 3 times daily as advised.",
            precautions = "May cause mild dizziness. Not recommended in severe liver or kidney disease.",
            standardDosage = "80 mg",
            safetyNote = "Relieves smooth muscle contractions directly."
        ),
        MedicineMetadata(
            id = "med_5",
            name = "Dicyclomine + Paracetamol",
            genericName = "Dicyclomine HCl (20mg) + Paracetamol (500mg)",
            category = "Antispasmodic & Analgesic",
            purpose = "Relieves spasmodic abdominal pain, intestinal colic & menstrual cramping",
            associatedConditions = listOf("Period Pain / Menstrual Cramps", "Gas / Bloating", "Indigestion"),
            usageInfo = "1 tablet after meals during active cramping.",
            precautions = "May cause mild dry mouth or sleepiness. Avoid driving.",
            standardDosage = "20mg + 500mg",
            safetyNote = "Dual-action spasm and pain reliever."
        ),
        MedicineMetadata(
            id = "med_6",
            name = "Pantoprazole 40mg",
            genericName = "Pantoprazole Sodium",
            category = "Proton Pump Inhibitor (PPI)",
            purpose = "Reduces excess stomach acid, relieves heartburn and acid reflux (GERD)",
            associatedConditions = listOf("Acidity / Heartburn", "Indigestion", "Gas / Bloating"),
            usageInfo = "1 tablet in the morning 30-45 minutes before breakfast with plain water.",
            precautions = "Swallow whole, do not crush or chew. Consult clinician for chronic use.",
            standardDosage = "40 mg",
            safetyNote = "Best taken first thing on empty stomach."
        ),
        MedicineMetadata(
            id = "med_7",
            name = "Omeprazole 20mg",
            genericName = "Omeprazole",
            category = "Proton Pump Inhibitor (PPI)",
            purpose = "Relieves acid regurgitation, stomach burning & gastritis",
            associatedConditions = listOf("Acidity / Heartburn", "Indigestion"),
            usageInfo = "1 capsule daily in the morning before food.",
            precautions = "Avoid triggering spicy foods and late-night heavy meals.",
            standardDosage = "20 mg",
            safetyNote = "Take 30 mins before first meal."
        ),
        MedicineMetadata(
            id = "med_8",
            name = "Famotidine 20mg",
            genericName = "Famotidine",
            category = "H2 Receptor Blocker",
            purpose = "Fast-acting acid reduction for acute indigestion and night-time heartburn",
            associatedConditions = listOf("Acidity / Heartburn", "Indigestion"),
            usageInfo = "1 tablet 30 minutes before meal or at bedtime.",
            precautions = "Adjust dose in renal impairment.",
            standardDosage = "20 mg",
            safetyNote = "Fast-acting relief for evening acid flare-ups."
        ),
        MedicineMetadata(
            id = "med_9",
            name = "Aluminium + Magnesium Hydroxide Gel",
            genericName = "Aluminium Hydroxide + Magnesium Hydroxide + Simethicone",
            category = "Neutralizing Antacid & Antiflatulent",
            purpose = "Immediate physical neutralization of stomach acid and entrapment of gas bubbles",
            associatedConditions = listOf("Acidity / Heartburn", "Gas / Bloating", "Indigestion"),
            usageInfo = "10-15 ml (2-3 teaspoons) after meals and at bedtime as needed.",
            precautions = "Shake bottle well before use. Separate by 2 hours from other medications.",
            standardDosage = "10 - 15 ml",
            safetyNote = "Acts immediately upon contact in stomach."
        ),
        MedicineMetadata(
            id = "med_10",
            name = "Simethicone 80mg Chewable",
            genericName = "Simethicone",
            category = "Antiflatulent / Anti-Gas",
            purpose = "Breaks up gas bubbles in the gut to relieve bloating, fullness and flatulence",
            associatedConditions = listOf("Gas / Bloating", "Indigestion"),
            usageInfo = "Chew 1 to 2 tablets thoroughly after meals and at bedtime.",
            precautions = "Non-systemic action. Safe for most individuals.",
            standardDosage = "80 mg Chewable",
            safetyNote = "Non-absorbable local surface-tension agent."
        ),
        MedicineMetadata(
            id = "med_11",
            name = "Antacid Chewable Tablet (Digene / Gelusil)",
            genericName = "Magnesium Silicate + Dried Aluminium Hydroxide",
            category = "OTC Antacid",
            purpose = "Quick relief from sour stomach, acidic belching, and burning throat",
            associatedConditions = listOf("Acidity / Heartburn", "Gas / Bloating", "Indigestion"),
            usageInfo = "Chew 1-2 tablets when symptoms arise. Do not swallow whole.",
            precautions = "Drink water afterwards. Not for continuous use exceeding 2 weeks.",
            standardDosage = "2 Tablets",
            safetyNote = "Chew thoroughly for quick acid neutralization."
        ),
        MedicineMetadata(
            id = "med_12",
            name = "Oral Rehydration Salts (WHO Formula)",
            genericName = "Sodium Chloride, Potassium Chloride, Sodium Citrate, Glucose",
            category = "Essential Electrolyte Solution",
            purpose = "Rapidly replenishes fluid and vital electrolytes lost in diarrhea, vomiting or heat",
            associatedConditions = listOf("Mild Dehydration / ORS", "Diarrhea", "Nausea / Vomiting"),
            usageInfo = "Dissolve 1 complete sachet in exactly 1 Liter of clean drinking water. Sip regularly.",
            precautions = "Do not boil the prepared solution. Discard leftover prepared liquid after 24 hours.",
            standardDosage = "1 Sachet / 1L",
            safetyNote = "Vital first line for dehydration management."
        ),
        MedicineMetadata(
            id = "med_13",
            name = "Loperamide 2mg",
            genericName = "Loperamide Hydrochloride",
            category = "Antimotility / Antidiarrheal",
            purpose = "Slows gut motility to reduce frequency of loose watery stools in acute diarrhea",
            associatedConditions = listOf("Diarrhea"),
            usageInfo = "2 tablets initially, then 1 tablet after each unformed loose stool (max 8mg/day).",
            precautions = "Do NOT use if stool contains blood, high fever is present, or in bacterial dysentery.",
            standardDosage = "2 mg",
            safetyNote = "Never take if fever or blood in stool is present."
        ),
        MedicineMetadata(
            id = "med_14",
            name = "Racecadotril 100mg",
            genericName = "Racecadotril",
            category = "Antisecretory Antidiarrheal",
            purpose = "Reduces excessive intestinal water and electrolyte secretion without stopping natural gut motility",
            associatedConditions = listOf("Diarrhea"),
            usageInfo = "1 capsule 3 times daily before meals along with ORS.",
            precautions = "Maintain oral rehydration therapy alongside.",
            standardDosage = "100 mg",
            safetyNote = "Antisecretory action; preserves gut motility."
        ),
        MedicineMetadata(
            id = "med_15",
            name = "Domperidone 10mg",
            genericName = "Domperidone",
            category = "Prokinetic / Antiemetic",
            purpose = "Relieves feeling of fullness, nausea, bloating, and upper abdominal heaviness",
            associatedConditions = listOf("Nausea / Vomiting", "Indigestion", "Gas / Bloating"),
            usageInfo = "1 tablet 15-30 minutes before meals.",
            precautions = "Use shortest duration possible. Consult physician if pregnant or with cardiac history.",
            standardDosage = "10 mg",
            safetyNote = "Take 15 minutes before meals."
        ),
        MedicineMetadata(
            id = "med_16",
            name = "Ondansetron 4mg MD",
            genericName = "Ondansetron (Mouth Dissolving)",
            category = "5-HT3 Antagonist Antiemetic",
            purpose = "Prevents and relieves acute nausea and vomiting episodes",
            associatedConditions = listOf("Nausea / Vomiting"),
            usageInfo = "Place 1 tablet on the tongue and let it dissolve without chewing.",
            precautions = "May cause mild headache or constipation. Ensure rehydration with fluids.",
            standardDosage = "4 mg MD",
            safetyNote = "Dissolves on tongue without water."
        ),
        MedicineMetadata(
            id = "med_17",
            name = "Cetirizine 10mg",
            genericName = "Cetirizine Hydrochloride",
            category = "Second-Generation Antihistamine",
            purpose = "Relieves runny nose, sneezing, itchy watery eyes, hives & skin allergies",
            associatedConditions = listOf("Minor Allergic Symptoms", "Common Cold", "Minor Skin Irritation"),
            usageInfo = "1 tablet once daily, preferably in the evening.",
            precautions = "May cause mild drowsiness in some individuals. Avoid alcohol.",
            standardDosage = "10 mg",
            safetyNote = "Preferably take at bedtime."
        ),
        MedicineMetadata(
            id = "med_18",
            name = "Levocetirizine 5mg",
            genericName = "Levocetirizine",
            category = "Non-Sedating Antihistamine",
            purpose = "High-potency relief for allergic rhinitis, hay fever, and allergic skin rashes",
            associatedConditions = listOf("Minor Allergic Symptoms", "Common Cold", "Minor Skin Irritation"),
            usageInfo = "1 tablet once daily at night.",
            precautions = "Low sedative profile, but caution when operating machinery until response is known.",
            standardDosage = "5 mg",
            safetyNote = "Targeted non-drowsy antihistamine."
        ),
        MedicineMetadata(
            id = "med_19",
            name = "Chlorpheniramine Maleate (CPM) 4mg",
            genericName = "Chlorpheniramine Maleate",
            category = "First-Generation Antihistamine",
            purpose = "Fast relief of acute allergy symptoms, intense itching, and cold-induced sneezing",
            associatedConditions = listOf("Common Cold", "Minor Allergic Symptoms", "Minor Skin Irritation"),
            usageInfo = "1 tablet every 6 to 8 hours as needed.",
            precautions = "Causes notable drowsiness. Do not drive or operate machinery.",
            standardDosage = "4 mg",
            safetyNote = "High sedative effect; avoid driving."
        ),
        MedicineMetadata(
            id = "med_20",
            name = "Dextromethorphan Cough Syrup",
            genericName = "Dextromethorphan Hydrobromide (10mg/5ml)",
            category = "Antitussive (Cough Suppressant)",
            purpose = "Suppresses irritating dry, tickly, non-productive cough",
            associatedConditions = listOf("Cough (Dry / Wet)", "Common Cold", "Sore Throat"),
            usageInfo = "5 - 10 ml every 6 to 8 hours as needed.",
            precautions = "Do not use for chesty productive phlegm cough. Do not exceed stated dosage.",
            standardDosage = "10 mg / 5 ml",
            safetyNote = "Specifically formulated for dry, hacking cough."
        ),
        MedicineMetadata(
            id = "med_21",
            name = "Ambroxol + Guaifenesin Expectorant",
            genericName = "Ambroxol HCl + Guaifenesin + Terbutaline",
            category = "Mucolytic & Expectorant",
            purpose = "Thins thick mucus and loosens chest congestion for productive wet cough",
            associatedConditions = listOf("Cough (Dry / Wet)", "Common Cold"),
            usageInfo = "5 - 10 ml 3 times daily after meals with a glass of warm water.",
            precautions = "Drink plenty of fluids to help clear mucus from air passages.",
            standardDosage = "10 ml Syrup",
            safetyNote = "Drink warm fluids to aid mucus clearance."
        ),
        MedicineMetadata(
            id = "med_22",
            name = "Antiseptic Throat Lozenges",
            genericName = "Amylmetacresol + 2,4-Dichlorobenzyl Alcohol",
            category = "Oral Antiseptic & Soothing Agent",
            purpose = "Soothes irritated throat, reduces scratchiness and minor bacterial throat discomfort",
            associatedConditions = listOf("Sore Throat", "Common Cold", "Cough (Dry / Wet)"),
            usageInfo = "Dissolve 1 lozenge slowly in the mouth every 2 to 3 hours.",
            precautions = "Do not swallow whole. Not recommended for children under 6 without supervision.",
            standardDosage = "1 Lozenge",
            safetyNote = "Dissolve slowly; do not chew."
        ),
        MedicineMetadata(
            id = "med_23",
            name = "Povidone-Iodine 2% Gargle",
            genericName = "Povidone-Iodine Germicide (2% w/v)",
            category = "Topical Antiseptic Gargle",
            purpose = "Clears bacterial and viral pathogens from throat mucosa and relieves pharyngitis",
            associatedConditions = listOf("Sore Throat", "Mouth Ulcers"),
            usageInfo = "Dilute with an equal volume of warm water. Gargle for 30 seconds and spit out.",
            precautions = "DO NOT SWALLOW. Avoid if allergic to iodine or with active thyroid disease.",
            standardDosage = "5 - 10 ml Diluted",
            safetyNote = "Spit out completely after 30 seconds gargle."
        ),
        MedicineMetadata(
            id = "med_24",
            name = "Isotonic Saline Nasal Spray",
            genericName = "Sodium Chloride (0.9% w/v)",
            category = "Nasal Moisturizer & Cleanser",
            purpose = "Flushes out pollen, dust and allergens, moisturizes dry nasal passages and thins crusts",
            associatedConditions = listOf("Common Cold", "Minor Allergic Symptoms"),
            usageInfo = "2 to 3 sprays in each nostril 2-4 times daily as required.",
            precautions = "100% drug-free and safe for regular daily use across all ages.",
            standardDosage = "2-3 Sprays",
            safetyNote = "Drug-free saline wash; safe for daily use."
        ),
        MedicineMetadata(
            id = "med_25",
            name = "Xylometazoline Nasal Drops 0.1%",
            genericName = "Xylometazoline Hydrochloride",
            category = "Topical Decongestant",
            purpose = "Quick 2-minute relief from severe nasal block and sinus stuffiness",
            associatedConditions = listOf("Common Cold", "Minor Allergic Symptoms"),
            usageInfo = "1 to 2 drops in each nostril twice daily.",
            precautions = "Do NOT use for more than 4-5 consecutive days to prevent rebound congestion.",
            standardDosage = "1-2 Drops",
            safetyNote = "Limit use to max 4-5 days."
        ),
        MedicineMetadata(
            id = "med_26",
            name = "Diclofenac Topical Pain Gel",
            genericName = "Diclofenac Diethylamine 1.16% + Linseed Oil + Menthol",
            category = "Topical NSAID Analgesic",
            purpose = "Localized relief from muscle sprains, joint stiffness, neck strain and backache",
            associatedConditions = listOf("Muscle Pain", "Joint Pain", "Mild Body Pain"),
            usageInfo = "Apply a thin layer to the affected painful area 3-4 times daily. Gently massage.",
            precautions = "For external use only. Do not apply on open cuts, abrasions or near eyes.",
            standardDosage = "2 - 4 g Topical",
            safetyNote = "External use only. Wash hands after use."
        ),
        MedicineMetadata(
            id = "med_27",
            name = "Herbal Pain & Vaporizing Balm",
            genericName = "Camphor + Menthol + Eucalyptus Oil",
            category = "Counter-Irritant & Inhalant",
            purpose = "Soothes tension headaches, back stiffness and eases nasal inhalation",
            associatedConditions = listOf("Headache", "Muscle Pain", "Common Cold"),
            usageInfo = "Apply lightly to forehead, temples, chest, or neck.",
            precautions = "Keep away from nostrils and broken skin. Wash hands after application.",
            standardDosage = "Topical Dab",
            safetyNote = "Avoid contact with eyes and open wounds."
        ),
        MedicineMetadata(
            id = "med_28",
            name = "Naproxen 250mg",
            genericName = "Naproxen",
            category = "Long-Acting NSAID",
            purpose = "Sustained 8-12 hour relief for joint aches, muscular stiffness and menstrual pain",
            associatedConditions = listOf("Joint Pain", "Muscle Pain", "Headache", "Period Pain / Menstrual Cramps"),
            usageInfo = "1 tablet with food or milk every 8 to 12 hours.",
            precautions = "Take with food. Avoid if with prior stomach ulcer or aspirin sensitivity.",
            standardDosage = "250 mg",
            safetyNote = "Longer duration of pain relief; take with meals."
        ),
        MedicineMetadata(
            id = "med_29",
            name = "Povidone-Iodine 5% Ointment",
            genericName = "Povidone-Iodine USP",
            category = "Broad-Spectrum Microbicidal Ointment",
            purpose = "Prevents bacterial infection in minor cuts, scrapes, abrasions and superficial burns",
            associatedConditions = listOf("Minor Cuts / Wounds", "Minor Skin Irritation"),
            usageInfo = "Clean the wound with water, pat dry, and apply a thin layer 1-2 times daily.",
            precautions = "For minor external wounds only. For deep punctures or animal bites, seek ER care.",
            standardDosage = "Topical Layer",
            safetyNote = "Antiseptic barrier for small skin wounds."
        ),
        MedicineMetadata(
            id = "med_30",
            name = "Triple Antibiotic First-Aid Ointment",
            genericName = "Neomycin + Polymyxin B + Bacitracin",
            category = "Topical Antibacterial",
            purpose = "First-aid defense to ward off local skin infection on small scrapes and scratches",
            associatedConditions = listOf("Minor Cuts / Wounds"),
            usageInfo = "Apply a small amount to the affected area 1 to 3 times daily.",
            precautions = "Discontinue if redness, stinging or rash occurs. For minor superficial cuts only.",
            standardDosage = "Topical Dab",
            safetyNote = "For clean, minor scratches and abrasions."
        ),
        MedicineMetadata(
            id = "med_31",
            name = "Calamine Soothing Lotion",
            genericName = "Calamine (8%) + Zinc Oxide + Glycerin",
            category = "Soothing Antipruritic & Protectant",
            purpose = "Calms itching, prickly heat, insect bites, sunburn, and contact dermatitis",
            associatedConditions = listOf("Minor Skin Irritation", "Minor Allergic Symptoms"),
            usageInfo = "Shake well. Apply to affected itchy skin using clean cotton wool.",
            precautions = "For external use only. Avoid contact with eyes and mucous membranes.",
            standardDosage = "Topical Lotion",
            safetyNote = "Cooling comfort for itchy skin flare-ups."
        ),
        MedicineMetadata(
            id = "med_32",
            name = "Hydrocortisone 1% Cream",
            genericName = "Hydrocortisone",
            category = "Mild Topical Corticosteroid",
            purpose = "Reduces redness, swelling, and itchiness from eczema patches and insect bites",
            associatedConditions = listOf("Minor Skin Irritation", "Minor Allergic Symptoms"),
            usageInfo = "Apply sparingly to affected area 1-2 times daily for up to 5 days.",
            precautions = "Do not use on broken infected skin or face without medical consultation.",
            standardDosage = "1% Cream",
            safetyNote = "Use sparingly for short durations."
        ),
        MedicineMetadata(
            id = "med_33",
            name = "Clotrimazole 1% Antifungal Cream",
            genericName = "Clotrimazole USP",
            category = "Topical Antifungal",
            purpose = "Relieves fungal itching, ringworm (tinea), athlete's foot, and sweat rashes",
            associatedConditions = listOf("Minor Skin Irritation"),
            usageInfo = "Clean and dry area, apply 2 times daily. Continue for 1-2 weeks as advised.",
            precautions = "External use only. Maintain dry skin hygiene.",
            standardDosage = "1% Cream",
            safetyNote = "Keep application area dry and clean."
        ),
        MedicineMetadata(
            id = "med_34",
            name = "Clove Oil (Eugenol) Drops",
            genericName = "Pure Clove Essential Oil (Eugenol)",
            category = "Natural Dental Analgesic",
            purpose = "Temporary numbing relief for acute throbbing toothache until dental visit",
            associatedConditions = listOf("Toothache"),
            usageInfo = "Put 1-2 drops on a tiny cotton ball and hold gently against the aching tooth.",
            precautions = "Avoid swallowing or placing directly on gums. Consult a dentist promptly.",
            standardDosage = "1-2 Drops",
            safetyNote = "Temporary dental relief; does not treat cavity."
        ),
        MedicineMetadata(
            id = "med_35",
            name = "Choline Salicylate + Lignocaine Oral Gel",
            genericName = "Choline Salicylate (8.7%) + Lignocaine HCl (2%)",
            category = "Oral Analgesic & Anesthetic Gel",
            purpose = "Rapid pain relief and anti-inflammatory action for painful aphthous mouth ulcers",
            associatedConditions = listOf("Mouth Ulcers", "Toothache"),
            usageInfo = "Wash hands. Apply a small amount to ulcer 3-4 times daily before meals.",
            precautions = "Do not eat or drink for 15 minutes after application.",
            standardDosage = "Topical Gel",
            safetyNote = "Numbing agent for oral canker sores."
        ),
        MedicineMetadata(
            id = "med_36",
            name = "Triamcinolone Acetonide 0.1% Oral Paste",
            genericName = "Triamcinolone Acetonide",
            category = "Anti-inflammatory Dental Paste",
            purpose = "Creates a protective adhesive barrier over mouth ulcers to speed up healing",
            associatedConditions = listOf("Mouth Ulcers"),
            usageInfo = "Press a small dab gently onto the ulcer at bedtime without rubbing.",
            precautions = "For oral mucosal use only. Not for viral cold sores or fungal thrush.",
            standardDosage = "0.1% Paste",
            safetyNote = "Protective mucosal adherence paste."
        ),
        MedicineMetadata(
            id = "med_37",
            name = "Psyllium Husk (Isabgol)",
            genericName = "Plantago Ovata (Psyllium Husk)",
            category = "Bulk-Forming Natural Laxative",
            purpose = "Adds dietary soluble fiber to soften stool and promote regular gentle bowel movement",
            associatedConditions = listOf("Constipation", "Indigestion"),
            usageInfo = "Mix 1-2 tablespoons in a glass of warm water/milk, drink immediately at bedtime.",
            precautions = "Always follow with an additional full glass of plain water.",
            standardDosage = "5 - 10 g",
            safetyNote = "Natural fiber; drink ample water."
        ),
        MedicineMetadata(
            id = "med_38",
            name = "Lactulose Oral Solution 10g/15ml",
            genericName = "Lactulose",
            category = "Osmotic Laxative",
            purpose = "Draws water into the bowel to soften hard stools and ease straining",
            associatedConditions = listOf("Constipation"),
            usageInfo = "15 to 30 ml once daily in the morning or at bedtime.",
            precautions = "May take 24-48 hours for full effect. May produce initial mild gas.",
            standardDosage = "15 - 30 ml",
            safetyNote = "Gentle osmotic action."
        ),
        MedicineMetadata(
            id = "med_39",
            name = "Bisacodyl 5mg Tablet",
            genericName = "Bisacodyl",
            category = "Stimulant Laxative",
            purpose = "Predictable overnight relief for stubborn short-term constipation",
            associatedConditions = listOf("Constipation"),
            usageInfo = "1 to 2 tablets at bedtime with water (produces bowel action in 6-12 hours).",
            precautions = "Do not take with milk or antacids. Do not use for more than 5 consecutive days.",
            standardDosage = "5 mg",
            safetyNote = "Short-term overnight stimulant."
        ),
        MedicineMetadata(
            id = "med_40",
            name = "Carboxymethylcellulose 0.5% Eye Drops",
            genericName = "Carboxymethylcellulose Sodium (CMC 0.5%)",
            category = "Lubricant Eye Drops",
            purpose = "Relieves dryness, burning, gritty sensation, and digital eye strain",
            associatedConditions = listOf("Eye Strain / Dry Eyes"),
            usageInfo = "Instill 1 to 2 drops in affected eye(s) 3-4 times daily as needed.",
            precautions = "Do not touch dropper tip to eye surface. Discard bottle 1 month after opening.",
            standardDosage = "1-2 Drops",
            safetyNote = "Artificial tears for screen fatigue & dry eyes."
        ),
        MedicineMetadata(
            id = "med_41",
            name = "Dimenhydrinate 50mg",
            genericName = "Dimenhydrinate",
            category = "Antiemetic & Antihistamine",
            purpose = "Prevents and treats motion sickness, travel nausea, vomiting and dizziness",
            associatedConditions = listOf("Motion Sickness", "Nausea / Vomiting"),
            usageInfo = "1 tablet 30 to 60 minutes before traveling.",
            precautions = "May cause noticeable drowsiness. Avoid driving or operating machinery.",
            standardDosage = "50 mg",
            safetyNote = "Take 30-60 mins prior to travel."
        ),
        MedicineMetadata(
            id = "med_42",
            name = "Meclizine 25mg",
            genericName = "Meclizine Hydrochloride",
            category = "Antivertigo & Motion Sickness Agent",
            purpose = "Long-lasting 24-hour protection against travel-induced vertigo and nausea",
            associatedConditions = listOf("Motion Sickness", "Nausea / Vomiting"),
            usageInfo = "1 tablet 1 hour prior to travel.",
            precautions = "May cause dry mouth and mild sleepiness.",
            standardDosage = "25 mg",
            safetyNote = "Longer-acting motion sickness protection."
        ),
        MedicineMetadata(
            id = "med_43",
            name = "Zinc Sulfate Dispersible 20mg",
            genericName = "Zinc Sulfate Monohydrate",
            category = "Mineral Supplement & Gut Support",
            purpose = "Shortens duration of diarrheal illness and promotes intestinal mucosal recovery",
            associatedConditions = listOf("Diarrhea", "Mild Dehydration / ORS"),
            usageInfo = "Dissolve 1 tablet in 1 teaspoon clean water/ORS and drink once daily for 10-14 days.",
            precautions = "Take with food to avoid minor stomach upset.",
            standardDosage = "20 mg Dispersible",
            safetyNote = "Co-administered with ORS during diarrhea."
        ),
        MedicineMetadata(
            id = "med_44",
            name = "Aspirin 300mg Soluble",
            genericName = "Acetylsalicylic Acid",
            category = "Analgesic & Antipyretic",
            purpose = "Temporary relief of common headache, toothache, and mild aches",
            associatedConditions = listOf("Headache", "Mild Body Pain", "Toothache"),
            usageInfo = "Dissolve in water and drink after food.",
            precautions = "NEVER give to children/teens with viral fever (Reye's syndrome). Avoid in asthma/ulcers.",
            standardDosage = "300 mg",
            safetyNote = "Adults only; take with full glass of water."
        ),
        MedicineMetadata(
            id = "med_45",
            name = "Chlorhexidine 0.2% Oral Mouthwash",
            genericName = "Chlorhexidine Gluconate 0.2% w/v",
            category = "Dental Antiseptic Rinse",
            purpose = "Fights gum inflammation (gingivitis), bacterial plaque and reduces tooth/gum irritation",
            associatedConditions = listOf("Toothache", "Mouth Ulcers"),
            usageInfo = "Swish 10 ml in mouth for 1 minute twice daily after brushing and spit out.",
            precautions = "Do not swallow. Avoid eating or drinking for 30 minutes after use.",
            standardDosage = "10 ml Rinse",
            safetyNote = "Do not swallow; spit out completely."
        )
    )

    // Convert to Room entities for database consistency
    val defaultMedicineEntities: List<MedicineEntity> = catalogItems.map { item ->
        MedicineEntity(
            id = item.id,
            name = item.name,
            genericName = item.genericName,
            purpose = item.purpose,
            usageInfo = item.usageInfo,
            precautions = item.precautions,
            category = item.category,
            standardDosage = item.standardDosage
        )
    }

    // Default realistic stock availabilities across hospitals
    val defaultAvailabilities: List<MedicineAvailabilityEntity> = listOf(
        MedicineAvailabilityEntity("ma_1", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Paracetamol 650mg", "Acetaminophen / Paracetamol", "Govt. Free Pharmacy Counter", StockStatus.IN_STOCK, 450, "Free", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_2", "hosp_peerless", "Peerless Hospital", "Ibuprofen 400mg", "Ibuprofen", "Peerless 24x7 Pharmacy", StockStatus.IN_STOCK, 180, "₹28.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_3", "hosp_medica", "Medica Superspecialty Hospital", "Pantoprazole 40mg", "Pantoprazole Sodium", "Medica In-house Pharmacy", StockStatus.IN_STOCK, 220, "₹110.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_4", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Oral Rehydration Salts (WHO Formula)", "Sodium Chloride, Potassium Chloride, Sodium Citrate, Glucose", "Govt. Free Pharmacy Counter", StockStatus.IN_STOCK, 800, "Free", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_5", "hosp_rntics", "NH RTIICS", "Mefenamic Acid 500mg", "Mefenamic Acid", "Narayana Pharmacy", StockStatus.IN_STOCK, 95, "₹45.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_6", "hosp_subhasgram_govt", "Subhasgram Rural Hospital", "Cetirizine 10mg", "Cetirizine Hydrochloride", "Govt. OPD Pharmacy", StockStatus.IN_STOCK, 320, "Free", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_7", "hosp_peerless", "Peerless Hospital", "Drotaverine 80mg", "Drotaverine Hydrochloride", "Peerless 24x7 Pharmacy", StockStatus.IN_STOCK, 140, "₹82.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_8", "hosp_medica", "Medica Superspecialty Hospital", "Dicyclomine + Paracetamol", "Dicyclomine HCl (20mg) + Paracetamol (500mg)", "Medica 24x7 Pharmacy", StockStatus.IN_STOCK, 160, "₹52.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_9", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Aluminium + Magnesium Hydroxide Gel", "Aluminium Hydroxide + Magnesium Hydroxide + Simethicone", "Govt. Free Pharmacy Counter", StockStatus.IN_STOCK, 110, "Free", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_10", "hosp_rntics", "NH RTIICS", "Diclofenac Topical Pain Gel", "Diclofenac Diethylamine 1.16% + Linseed Oil + Menthol", "Narayana Pharmacy", StockStatus.IN_STOCK, 240, "₹65.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_11", "hosp_peerless", "Peerless Hospital", "Loperamide 2mg", "Loperamide Hydrochloride", "Peerless 24x7 Pharmacy", StockStatus.IN_STOCK, 190, "₹22.00", System.currentTimeMillis()),
        MedicineAvailabilityEntity("ma_12", "hosp_sonarpur_rural", "Sonarpur Rural Hospital", "Povidone-Iodine 5% Ointment", "Povidone-Iodine USP", "Govt. Free Pharmacy Counter", StockStatus.IN_STOCK, 350, "Free", System.currentTimeMillis())
    )
}
