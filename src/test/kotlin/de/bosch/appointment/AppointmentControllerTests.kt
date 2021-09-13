package de.bosch.appointment

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import de.bosch.appointment.model.Appointment
import de.bosch.appointment.model.Doctor
import de.bosch.appointment.model.Patient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime
import java.util.*

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTests {
    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    private val path = "/v1/appointments"
    private val doctorId = UUID.randomUUID().toString()
    private val patientId = UUID.randomUUID().toString()

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var appointmentService: AppointmentService

    @Test
    fun `List appointments returns bad request when both parameters are not provided` (){
        mockMvc.perform(get(path)).andExpect(status().isBadRequest)
    }

    @Test
    fun `List appointments returns OK when appointments are found` (){
        val doctor = Doctor(id = doctorId, specialty = "")
        val patient = Patient(id = patientId, fullName = "")
        val appointment = Appointment(doctor = doctor, patient = patient, scheduledTime = LocalDateTime.now())
        Mockito.`when`(appointmentService.getAppointments(any(), any())).thenReturn(listOf(appointment))
        mockMvc.perform(get("$path?doctorId=$doctorId"))
            .andExpect(status().isOk)
    }

    @Test
    fun `Add appointment with invalid data returns bad request` (){
        Mockito.`when`(appointmentService.createAppointment(any(), any(), any())).thenThrow(IllegalArgumentException::class.java)
        val jsonString = ObjectMapper().writeValueAsString(AppointmentRequest(doctorId, patientId, "11:00"))
        mockMvc.perform(post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `Add appointment with valid data returns OK` (){
        val doctor = Doctor(id = doctorId, specialty = "")
        val patient = Patient(id = patientId, fullName = "")
        val appointment = Appointment(doctor = doctor, patient = patient, scheduledTime = LocalDateTime.now())
        Mockito.`when`(appointmentService.createAppointment(any(), any(), any())).thenReturn(appointment)
        val jsonString = ObjectMapper().writeValueAsString(AppointmentRequest(doctorId, patientId, "11:00"))

        mockMvc.perform(post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString)
        ).andExpect(status().isOk)
    }
}