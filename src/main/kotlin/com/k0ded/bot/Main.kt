package com.k0ded.bot

import com.k0ded.bot.files.Config
import com.k0ded.bot.files.FileUtils

val config: Config = FileUtils.loadFile("config.json", Config::class.java)

fun main() {
    val bot = Bot(config)
    bot.run()

    // Could do with sharding depending on the estimated size of the bot
}
