package de.bosch.appointment

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.bosch.appointment.model.Appointment
import de.bosch.appointment.model.Doctor
import de.bosch.appointment.model.Patient
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime


class AppointmentServiceTests {
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var appointmentService: AppointmentService

    private val doctorId = "doctorId"
    private val patientId = "patientId"

    @Before
    fun setup() {
        appointmentRepository = mock{}
        appointmentService = AppointmentService(appointmentRepository)
    }

    @Test
    fun `Create an appointment with invalid doctorId throws Exception`(){
        whenever(appointmentRepository.getDoctor(any())).thenReturn(null)
        assertThatThrownBy {
            appointmentService.createAppointment(doctorId, patientId, "09:00")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid doctor id provided")
    }

    @Test
    fun `Create an appointment with invalid patientId throws Exception`(){
        whenever(appointmentRepository.getDoctor(any())).thenReturn(Doctor(id = doctorId, specialty = ""))
        whenever(appointmentRepository.getPatient(any())).thenReturn(null)
        assertThatThrownBy {
            appointmentService.createAppointment(doctorId, patientId, "09:00")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid patient id provided")
    }

    @Test
    fun `Create an appointment with existent appointment at same time for doctor throws Exception`(){
        val doctor = Doctor(id = doctorId, specialty = "")
        val patient = Patient(id = patientId, fullName = "")
        whenever(appointmentRepository.getDoctor(any())).thenReturn(doctor)
        whenever(appointmentRepository.getPatient(any())).thenReturn(patient)
        whenever(appointmentRepository.getByDoctorIdAndScheduledTime(any(), any())).thenReturn(listOf(
            Appointment(id = "appointmentId", doctor = doctor, patient = patient, scheduledTime = LocalDateTime.now())
        ))
        assertThatThrownBy {
            appointmentService.createAppointment(doctorId, patientId, "09:00")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("The provided scheduled time is invalid, doctor already has appointments at this time")
    }

    @Test
    fun `Create an appointment with existent appointment at same time for patient throws Exception`(){
        val doctor = Doctor(id = doctorId, specialty = "")
        val patient = Patient(id = patientId, fullName = "")
        whenever(appointmentRepository.getDoctor(any())).thenReturn(doctor)
        whenever(appointmentRepository.getPatient(any())).thenReturn(patient)
        whenever(appointmentRepository.getByDoctorIdAndScheduledTime(any(), any())).thenReturn(emptyList())
        whenever(appointmentRepository.getByPatientIdAndScheduledTime(any(), any())).thenReturn(listOf(
            Appointment(id = "appointmentId", doctor = doctor, patient = patient, scheduledTime = LocalDateTime.now())
        ))
        assertThatThrownBy {
            appointmentService.createAppointment(doctorId, patientId, "09:00")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("The provided scheduled time is invalid, patient already has appointments at this time")
    }

    @Test
    fun `Create an appointment with valid data returns created appointment`(){
        val doctor = Doctor(id = doctorId, specialty = "")
        val patient = Patient(id = patientId, fullName = "")
        val appointment = Appointment(doctor = doctor, patient = patient, scheduledTime = LocalDateTime.now())
        whenever(appointmentRepository.getDoctor(any())).thenReturn(doctor)
        whenever(appointmentRepository.getPatient(any())).thenReturn(patient)
        whenever(appointmentRepository.getByDoctorIdAndScheduledTime(any(), any())).thenReturn(emptyList())
        whenever(appointmentRepository.getByPatientIdAndScheduledTime(any(), any())).thenReturn(emptyList())
        whenever(appointmentRepository.addAppointment(any())).thenReturn(appointment)
        assertThat(appointmentService.createAppointment(doctorId, patientId, "09:00"))
            .isInstanceOf(Appointment::class.java)
    }
}