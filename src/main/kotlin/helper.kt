import java.io.File


val playlistLinks = mutableListOf<String>()

fun createChannelPagesUrl(channelUrl: String) {
    val baseUrl = when {
        channelUrl.contains("/channel/") -> channelUrl.substringBefore("/videos")
        channelUrl.contains("/@") -> channelUrl
        else -> throw IllegalArgumentException("Invalid YouTube channel URL format")
    }
    val idOrUsername = when {
        channelUrl.contains("/channel/") -> baseUrl.substringAfter("/channel/")
        channelUrl.contains("/@") -> baseUrl.substringAfter("/@")
        else -> "Unknown"
    }

    val playlistsPage = "$baseUrl/playlists"
    val allItem = scrapper.playListIds(playlistsPage)

    if (allItem != null) {
        val playListIds = allItem.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playlistLinks.add(playlistLink)
            println(playlistLink)
        }

        if (allItem.has("nextContinuation")) {
            println("Continuation found for $idOrUsername")
            plalistRepeapeter(allItem.getString("nextContinuation"), idOrUsername)
        } else {
            println("No continuation, writing to file")
            writeToFile(idOrUsername)
        }
    } else {
        println("No playlists found for $idOrUsername")
    }
}

fun plalistRepeapeter(continuation: String, idOrUsername: String) {
    val allItem = scrapper.playlist(continuation)

    if (allItem != null) {
        val playListIds = allItem.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playlistLinks.add(playlistLink)
            println(playlistLink)
        }

        if (allItem.has("nextContinuation")) {
            plalistRepeapeter(allItem.getString("nextContinuation"), idOrUsername)
        } else {
            println("writtting to file")
            println("No continuation, writing to file")
            writeToFile(idOrUsername)
        }
    } else {
        println("No more playlists for $idOrUsername")
    }
}

fun writeToFile(idOrUsername: String) {
    if (playlistLinks.isNotEmpty()) {
        val uniquePlaylistLinks = playlistLinks.distinct().toMutableList()
        val file = File("$idOrUsername playlist.txt")
        file.writeText(uniquePlaylistLinks.joinToString("\n"))
        println("File created successfully: ${file.absolutePath}")
    } else {
        println("No unique playlist links to write")
    }
}

fun getAllLinksOfChannel(url: String) {
    createChannelPagesUrl(url)
}
