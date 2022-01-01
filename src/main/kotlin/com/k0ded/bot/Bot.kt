package com.k0ded.bot

import com.k0ded.bot.commands.CommandRegister
import com.k0ded.bot.files.Config
import net.dv8tion.jda.api.JDABuilder

class Bot(
    val config: Config
) {
    private val logger = getLogger("production")
    private lateinit var commandRegister: CommandRegister

    fun start() {
        if(config.token == "TOKEN-HERE") {
            logger.error("""Please replace "TOKEN-HERE" in "config.json" with the token you're given by "https://discord.com/developers/applications"""")
            return
        }

        val jda = JDABuilder.createLight(config.token)
            .addEventListeners(MessageListener(config.prefix))
            .build()

        commandRegister = CommandRegister(jda)
        commandRegister.registerCommands()
    }

}