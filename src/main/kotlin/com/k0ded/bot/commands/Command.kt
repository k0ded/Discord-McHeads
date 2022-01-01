package com.k0ded.bot.commands

import com.k0ded.bot.getLogger
import net.dv8tion.jda.api.interactions.commands.build.OptionData

abstract class Command(
    val name: String,
    val description: String,

    // Triggers are aliases to the command!
    val triggers: Set<String>,
    val options: Set<OptionData>
) {
    val logger = getLogger("production")

    abstract fun execute(ctx: CommandContext)

}