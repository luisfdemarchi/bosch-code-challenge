package de.bosch.appointment

import de.bosch.appointment.model.Appointment
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit


@Service
class AppointmentService(
    val appointmentRepository: AppointmentRepository
) {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    fun getAppointments(
        doctorId: String?,
        patientId: String?
    ): List<Appointment> = appointmentRepository.getAppointments(doctorId, patientId);

    fun createAppointment(
        doctorId: String,
        patientId: String,
        date: String
    ): Appointment {
        val doctor = appointmentRepository.getDoctor(doctorId) ?:
            throw IllegalArgumentException("Invalid doctor id provided")
        val patient = appointmentRepository.getPatient(patientId) ?:
            throw IllegalArgumentException("Invalid patient id provided")
        val scheduledTime = LocalTime.parse(date, formatter).truncatedTo(ChronoUnit.HOURS)
        val now = LocalDate.now()
        val scheduledDateTime = LocalDateTime.of(now.year, now.month, now.dayOfMonth, scheduledTime.hour,scheduledTime.minute)
        if (appointmentRepository.getByDoctorIdAndScheduledTime(doctorId, scheduledDateTime).isNotEmpty())
            throw IllegalArgumentException("The provided scheduled time is invalid, doctor already has appointments at this time")
        if (appointmentRepository.getByPatientIdAndScheduledTime(patientId, scheduledDateTime).isNotEmpty())
            throw IllegalArgumentException("The provided scheduled time is invalid, patient already has appointments at this time")
        val appointment = Appointment(
            doctor = doctor,
            patient = patient,
            scheduledTime = scheduledDateTime
        )
        return appointmentRepository.addAppointment(appointment)
    }

}