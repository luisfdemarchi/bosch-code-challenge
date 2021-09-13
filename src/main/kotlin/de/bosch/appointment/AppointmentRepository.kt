package de.bosch.appointment

import de.bosch.appointment.mock.DoctorMock
import de.bosch.appointment.mock.PatientMock
import de.bosch.appointment.model.Appointment
import de.bosch.appointment.model.Doctor
import de.bosch.appointment.model.Patient
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AppointmentRepository {
    val appointments: MutableMap<String, Appointment> = mutableMapOf()
    val doctors: Map<String, Doctor> = DoctorMock().getDoctors()
    val patients: Map<String, Patient> = PatientMock().getPatients()

    fun getDoctor(id: String) = doctors[id];
    fun getPatient(id: String) = patients[id];

    fun getAppointments(
        doctorId: String?,
        patientId: String?
    ): List<Appointment> = appointments.values.filter { it.doctor.id == doctorId || it.patient.id == patientId }

    fun addAppointment(appointment: Appointment): Appointment {
        appointments[appointment.id] = appointment
        return appointment
    }

    fun getByDoctorIdAndScheduledTime(doctorId: String?, scheduledTime: LocalDateTime) =
        appointments.values.filter { it.doctor.id == doctorId && it.scheduledTime.isEqual(scheduledTime)}

    fun getByPatientIdAndScheduledTime(patientId: String?, scheduledTime: LocalDateTime) =
        appointments.values.filter { it.patient.id == patientId && it.scheduledTime.isEqual(scheduledTime)}
}