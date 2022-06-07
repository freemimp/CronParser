import model.CronOutput

fun List<CronOutput>.toFormattedString() = this.joinToString(separator = "\n") { "${it.hour}:${it.minute} ${it.day} ${it.command}" }
