package de.bosch.appointment.mock

import de.bosch.appointment.model.Patient

class PatientMock {
    private val patients = listOf(
        Patient(id = "PATIENT-001", fullName = "Leone Boyer"),
        Patient(id = "PATIENT-002", fullName = "Natalia Bernier"),
        Patient(id = "PATIENT-003", fullName = "Louie Gottlieb"),
        Patient(id = "PATIENT-004", fullName = "Kristina Kristina"),
        Patient(id = "PATIENT-005", fullName = "Brennan Schumm"),
        Patient(id = "PATIENT-006", fullName = "Orpha Dickinson"),
        Patient(id = "PATIENT-007", fullName = "Margarita Margarita"),
        Patient(id = "PATIENT-008", fullName = "Derrick Hammil"),
        Patient(id = "PATIENT-009", fullName = "Meghan Witting")
    )
    fun getPatients(): Map<String, Patient> = patients.associateBy { it.id }
}