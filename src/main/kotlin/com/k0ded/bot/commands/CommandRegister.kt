package com.k0ded.bot.commands

import com.k0ded.bot.getLogger
import net.dv8tion.jda.api.JDA

object CommandRegister {
    private val logger = getLogger("production")

    /**
     * Registers the commands for static usage later on
     */
    fun registerCommands(jda: JDA) {
        for (cmd in commands) {
            cmdMap[cmd.name] = cmd
            for (trigger in cmd.triggers) {
                cmdMap[trigger] = cmd
            }

            jda.upsertCommand(cmd.name, cmd.description)
                .addOptions(cmd.options)
        }

        logger.info("Command(s) registered!")
    }

    var cmdMap: MutableMap<String, Command> = mutableMapOf()

    fun getCommandFromTrigger(trigger: String): Command? {
        return if(cmdMap.containsKey(trigger)) {
            cmdMap[trigger]
        } else null
    }
}

val commands = setOf(
    McAvatar()
)