package de.bosch.appointment

import de.bosch.appointment.model.Appointment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/appointments")
class AppointmentController(
    val appointmentService: AppointmentService
) {

    @GetMapping
    fun getAppointments(
        @RequestParam doctorId: String?,
        @RequestParam patientId: String?
    ): ResponseEntity<*>? =
        if (doctorId == null && patientId == null){
            ResponseEntity<List<Appointment>>(HttpStatus.BAD_REQUEST)
        } else appointmentService.getAppointments(doctorId, patientId).let { appointments ->
            ResponseEntity(appointments, HttpStatus.OK)
        }

    @PostMapping
    fun createAppointment(
        @RequestBody appointmentRequest: AppointmentRequest
    ): ResponseEntity<*> = try {
        val appointment = appointmentService.createAppointment(
            appointmentRequest.doctorId,
            appointmentRequest.patientId,
            appointmentRequest.scheduledDateTime
        )
        ResponseEntity(appointment, HttpStatus.OK)
    } catch (e: IllegalArgumentException){
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}