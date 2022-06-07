import model.CronInput
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {
        val input = Scanner(System.`in`)
        val inputByLine = mutableListOf<String>()
        while (input.hasNext()) {
            inputByLine.add(input.nextLine())
        }
        if (inputByLine.isEmpty()) throw IOException("no std input provided")
        val currentTime = args[0]

        val cronInputs = inputByLine.map {
            val inputByValues = it.split(whiteSpaceRegex)
            CronInput(
                minute = inputByValues[0],
                hour = inputByValues[1],
                command = inputByValues[2]
            )
        }

        val factory: CronOutputFactory = CronOutputFactoryImpl()

        val cronOutputs = cronInputs.map {
            factory.createCronOutput(cronInput = it, currentTime = currentTime)
        }

        println(cronOutputs.toFormattedString())

    } catch (e: ArrayIndexOutOfBoundsException) {
        println("Missing arguments\nUsage: [cat input.txt | java -jar nameOfTheJar.jar] [HH:MM]")
        exitProcess(1)
    } catch (e: IOException) {
        println("No input provided\nUsage: [cat input.txt | java -jar nameOfTheJar.jar] [HH:MM]")
    }
}

private val whiteSpaceRegex by lazy { "\\s".toRegex() }
