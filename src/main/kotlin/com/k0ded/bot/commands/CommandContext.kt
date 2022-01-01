package com.k0ded.bot.commands

import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User

data class CommandContext(
    val author: User,
    val trigger: String,
    val args: List<String>,
    val textChannel: TextChannel
) {

    fun reply(msg: CharSequence) {
        textChannel.sendMessage(msg).queue()
    }

}
