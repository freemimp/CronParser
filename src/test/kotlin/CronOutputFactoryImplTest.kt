import model.CronInput
import model.CronOutput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CronOutputFactoryImplTest {

    private lateinit var sut: CronOutputFactoryImpl

    @BeforeEach
    internal fun setUp() {
        sut = CronOutputFactoryImpl()
    }

    @Test
    fun `given cron input with minutes and hours as 'star' return cron output with current minutes and hours with today and command`() {
        val input = CronInput("*", "*", "command")
        val currentTime = "00:00"

        val actual = sut.createCronOutput(input, currentTime)
        val expected = CronOutput("00", "00", "today", "command")

        assertEquals(expected, actual)
    }

    @Test
    fun `given cron input with minutes after current minutes, but hours as 'star' return cron output with minutes and current hours with today and command`() {
        val input = CronInput("30", "*", "command")
        val currentTime = "00:00"

        val actual = sut.createCronOutput(input, currentTime)
        val expected = CronOutput("30", "00", "today", "command")

        assertEquals(expected, actual)
    }
}
