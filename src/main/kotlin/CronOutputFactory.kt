import model.CronInput
import model.CronOutput

interface CronOutputFactory {
    fun createCronOutput(cronInput: CronInput, currentTime: String): CronOutput
}
