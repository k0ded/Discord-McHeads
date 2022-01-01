package com.k0ded.bot

import com.k0ded.bot.commands.CommandContext
import com.k0ded.bot.commands.CommandRegister
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener(private val prefix: String): ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.contentRaw
        if(event.author.isBot || !content.startsWith(prefix) || content.length <= prefix.length)
            return

        val noPrefix = content.removePrefix(prefix)
        val split = noPrefix.split(" ")
        val trigger = split[0]
        var args = listOf<String>()

        if(split.size > 1) {
            args = split.subList(1, split.size)
        }

        val ctx = CommandContext(event.author, trigger, args, event.textChannel)
        CommandRegister.getCommandFromTrigger(trigger)?.execute(ctx)
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        val ctx = CommandContext(event.user, event.commandString, event.options.map { it.asString }, event.textChannel)
        CommandRegister.getCommandFromTrigger(event.commandString)?.execute(ctx)
    }

}