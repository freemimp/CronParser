import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val inputByLine = mutableListOf<String>()
    while (input.hasNext()) {
        inputByLine.add(input.nextLine())
    }
    val currentTime = args[0]

    val cronInputs = inputByLine.map {
        val inputByValues = it.split(" ")
        CronInput(
            minute = inputByValues[0],
            hour = inputByValues[1],
            command = inputByValues[2]
        )
    }

    val cronOutputs = cronInputs.map {
        createCronOutput(cronInput = it, currentTime = currentTime)
    }

    println(cronOutputs.joinToString(separator = "\n") { "${it.hour}:${it.minute} ${it.day} ${it.command}" })
}

private fun createCronOutput(cronInput: CronInput, currentTime: String): CronOutput {
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

data class CronInput(val minute: String, val hour: String, val command: String)
data class CronOutput(val minute: String, val hour: String, val day: String, val command: String)

private const val ZERO_TIME_VALUE = "00"
private const val STAR_VALUE = "*"
private const val TODAY = "today"
private const val TOMORROW = "tomorrow"
private const val ZERO = '0'
private const val TIME_FIELD_LENGTH = 2
private const val MAX_HOUR = 23
