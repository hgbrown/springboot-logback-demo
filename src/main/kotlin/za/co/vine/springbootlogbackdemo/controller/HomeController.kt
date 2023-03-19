package za.co.vine.springbootlogbackdemo.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.info.BuildProperties
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class HomeController(
    private val buildProperties: BuildProperties,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping(
        path = ["/", "/home", "/version", "/index"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun home(): String {
        log.debug("Request to home at: ${LocalDateTime.now()}")
        return """
            {
                "build-version": "${buildProperties.version}",
                "build-time": "${buildProperties.time}",
                "build-name": "${buildProperties.name}"
            }
        """.trimIndent()
    }

    @GetMapping(
        path = ["/health"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun health(@RequestParam("error") error: String? = null): ResponseEntity<String> {
        log.info("Request to /health at: ${LocalDateTime.now()} with error param: $error")

        return if (error == null) {
            ResponseEntity.ok(healthBody("UP"))
        } else {
            log.error("Simulating error on health endpoint at: ${LocalDateTime.now()}")

            ResponseEntity.internalServerError().body(healthBody("DOWN"))
        }
    }

    private fun healthBody(status: String): String = """
                {
                    "status": "$status"
                }
            """.trimIndent()
}
