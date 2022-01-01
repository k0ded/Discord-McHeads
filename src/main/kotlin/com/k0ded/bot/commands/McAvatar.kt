package com.k0ded.bot.commands

import com.k0ded.bot.config
import com.k0ded.bot.utils.Emojis
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.awt.image.BufferedImage
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class McAvatar: Command(
    "mcavatar",
    "Displays a minecraft avatar",
    setOf("mca"),
    setOf(OptionData(
        OptionType.STRING, "username", "Name of the player of which you want an avatar", false
    ))
) {
    val avatar = "https://mc-heads.net/avatar/"
    val userCheck = "https://mc-heads.net/minecraft/profile/"

    override fun execute(ctx: CommandContext) {
        if(ctx.args.isNotEmpty()) {
            if(ctx.args.size > 1) {
                ctx.reply("${Emojis.X} **Too many arguments! Try** `${config.prefix}$name <username>`")
            }else if(checkUsername(ctx.args[0])) {
                sendAvatarMessage(ctx, ctx.args[0])
            }else {
                ctx.reply("${Emojis.X} **Couldn't find anyone with the name** `${ctx.args[0]}`")
            }
        }else {
            val nickname = ctx.textChannel.guild.getMember(ctx.author)?.nickname
            val name = when {
                checkUsername(ctx.author.name) -> ctx.author.name
                nickname?.let { checkUsername(it) } == true -> nickname
                else -> null
            }

            name?.let { sendAvatarMessage(ctx, it) } ?: ctx.reply("${Emojis.X} **Your discord name doesn't match your minecraft username! Try** `${config.prefix}${this.name} <username>`")
        }
    }

    private fun sendAvatarMessage(ctx: CommandContext, name: String) {
        ctx.textChannel.sendTyping().queue()
        val msg = getImageFile(name)
            ?.let { ctx.textChannel.sendFile(it) }
            ?: ctx.textChannel.sendMessage("${Emojis.SAD} **Something went wrong trying to fetch your avatar!**")
        msg.queue()
    }

    /**
     * The mc-heads api sends us a 204 if the player does not exist which is very convenient and easy
     * for double checking a usernames existance.
     *
     * @returns true if username exists otherwise false.
     */
    private fun checkUsername(name: String): Boolean {
        return try {
            val link = URL(userCheck + name)
            return with(link.openConnection() as HttpURLConnection) {
                responseCode
            } != 204
        }catch (_: Exception) {
            false
        }
    }

    private fun getImageFile(name: String): File? {
        return try {
            val link = URL(avatar + name)
            val conn = link.openConnection() as HttpURLConnection
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
            val img: BufferedImage = ImageIO.read(conn.inputStream)
            val file = File("downloaded.png")
            ImageIO.write(img, "png", file)
            file
        }catch (e: Exception) {
            logger.error(e.message)
            null
        }
    }

}