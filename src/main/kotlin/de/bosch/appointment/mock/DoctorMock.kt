package de.bosch.appointment.mock

import de.bosch.appointment.model.Doctor

class DoctorMock {
    private val doctors = listOf(
        Doctor(id = "DOC-001", specialty = "Allergy and immunology"),
        Doctor(id = "DOC-002", specialty = "Anesthesiology"),
        Doctor(id = "DOC-003", specialty = "Diagnostic radiology"),
        Doctor(id = "DOC-004", specialty = "Emergency medicine"),
        Doctor(id = "DOC-005", specialty = "Family medicine"),
        Doctor(id = "DOC-006", specialty = "Internal medicine"),
        Doctor(id = "DOC-007", specialty = "Medical genetics"),
        Doctor(id = "DOC-008", specialty = "Neurology"),
        Doctor(id = "DOC-009", specialty = "Nuclear medicine"),
        Doctor(id = "DOC-010", specialty = "Obstetrics and gynecology"),
        Doctor(id = "DOC-011", specialty = "Ophthalmology"),
        Doctor(id = "DOC-012", specialty = "Pathology"),
        Doctor(id = "DOC-013", specialty = "Pediatrics"),
        Doctor(id = "DOC-014", specialty = "Preventive medicine"),
        Doctor(id = "DOC-015", specialty = "Pediatrics"),
        Doctor(id = "DOC-016", specialty = "Psychiatry"),
        Doctor(id = "DOC-017", specialty = "Radiation oncology"),
        Doctor(id = "DOC-018", specialty = "Surgery"),
        Doctor(id = "DOC-019", specialty = "Urology"),
    )

    fun getDoctors(): Map<String, Doctor> = doctors.associateBy { it.id }
}