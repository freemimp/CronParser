import model.CronInput
import model.CronOutput

class CronOutputFactoryImpl : CronOutputFactory {

    override fun createCronOutput(cronInput: CronInput, currentTime: String): CronOutput {
        val currentMinute = currentTime.substringAfter(":").toInt()
        val currentHour = currentTime.substringBefore(":").toInt()
        val (minutes, hour, command) = cronInput
        return when {
            minutes != STAR_VALUE && hour != STAR_VALUE && hour.toInt() < currentHour -> CronOutput(
                minutes,
                hour,
                TOMORROW,
                command
            )
            minutes != STAR_VALUE && hour != STAR_VALUE && hour.toInt() > currentHour -> CronOutput(
                minutes,
                hour,
                TODAY,
                command
            )
            minutes == STAR_VALUE && hour != STAR_VALUE && hour.toInt() < currentHour -> CronOutput(
                ZERO_TIME_VALUE,
                hour,
                TOMORROW,
                command
            )
            minutes == STAR_VALUE && hour != STAR_VALUE && hour.toInt() > currentHour -> CronOutput(
                ZERO_TIME_VALUE,
                hour,
                TODAY,
                command
            )
            minutes != STAR_VALUE && hour == STAR_VALUE && minutes.toInt() < currentMinute && currentHour < MAX_HOUR -> CronOutput(
                minutes,
                currentHour.inc().toString().padStart(TIME_FIELD_LENGTH, ZERO),
                TODAY,
                command
            )
            minutes != STAR_VALUE && hour == STAR_VALUE && minutes.toInt() > currentMinute && currentHour < MAX_HOUR -> CronOutput(
                minutes,
                currentHour.toString().padStart(TIME_FIELD_LENGTH, ZERO),
                TODAY,
                command
            )
            minutes != STAR_VALUE && hour == STAR_VALUE && minutes.toInt() > currentMinute && currentHour == MAX_HOUR -> CronOutput(
                minutes,
                currentHour.toString().padStart(TIME_FIELD_LENGTH, ZERO),
                TODAY,
                command
            )
            minutes != STAR_VALUE && hour == STAR_VALUE && minutes.toInt() < currentMinute && currentHour == MAX_HOUR -> CronOutput(
                minutes,
                ZERO_TIME_VALUE,
                TOMORROW,
                command
            )
            else -> CronOutput(
                currentMinute.toString().padStart(TIME_FIELD_LENGTH, ZERO),
                currentHour.toString().padStart(TIME_FIELD_LENGTH, ZERO),
                TODAY,
                command
            )

        }
    }
}

private const val ZERO_TIME_VALUE = "00"
private const val STAR_VALUE = "*"
private const val TODAY = "today"
private const val TOMORROW = "tomorrow"
private const val ZERO = '0'
private const val TIME_FIELD_LENGTH = 2
private const val MAX_HOUR = 23
