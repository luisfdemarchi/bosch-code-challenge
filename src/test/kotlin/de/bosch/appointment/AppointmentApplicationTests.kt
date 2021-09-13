package de.bosch.appointment

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AppointmentApplicationTests {
	@Autowired
	private val testRestTemplate: TestRestTemplate? = null

	@Test
	fun contextLoads() {
	}
}
