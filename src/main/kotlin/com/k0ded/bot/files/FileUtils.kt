package com.k0ded.bot.files

import com.google.gson.Gson
import java.io.File

class FileUtils {

    companion object {

        /*
        Loads the file with an existing file, or with a default file if one exists.
         */
        fun <T>loadFile(filePath: String, fileType: Class<T>): T {
            val gson = Gson()
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()

                val url = this::class.java.classLoader.getResource(filePath)
                if (url != null) {
                    file.writeText(url.readText())
                }
            }

            return gson.fromJson(file.readLines().joinToString(separator = ""), fileType)
        }
    }
}