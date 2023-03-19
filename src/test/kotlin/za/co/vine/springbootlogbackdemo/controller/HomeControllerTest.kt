package za.co.vine.springbootlogbackdemo.controller

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var bp: BuildProperties

    @Test
    @DisplayName("Sanity Test")
    fun `sanity test`() {
        assertThat(mockMvc).isNotNull
    }

    @Test
    @DisplayName("Should be able to get the home endpoint")
    fun `should be able to get the home endpoint`() {
        mockMvc.get("/home") {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { is2xxSuccessful() }
                content {
                    jsonPath("$.build-name", equalTo(bp.name))
                    jsonPath("$.build-version", equalTo(bp.version))
                    jsonPath("$.build-time", equalTo(bp.time.toString()))
                }
            }
            .andDo {
                print()
            }
    }

    @Test
    @DisplayName("Should be able to get the health endpoint with 200 response code")
    fun `should be able to get the health endpoint with 200 response code`() {
        mockMvc.get("/health") {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { is2xxSuccessful() }
                content {
                    jsonPath("$.status", equalTo("UP"))
                }
            }
            .andDo {
                print()
            }
    }

    @Test
    @DisplayName("Should be able to get the health endpoint with 500 error response code")
    fun `should be able to get the health endpoint with 500 error response code`() {
        mockMvc.get("/health") {
            accept = MediaType.APPLICATION_JSON
            param("error", "1")
        }
            .andExpect {
                status { is5xxServerError() }
                content {
                    jsonPath("$.status", equalTo("DOWN"))
                }
            }
            .andDo {
                print()
            }
    }
}
