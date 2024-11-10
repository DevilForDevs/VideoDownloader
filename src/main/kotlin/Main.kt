import java.io.File

val playlistLinks = mutableListOf<String>()

fun playlist_repeater(continuation:String,file: File){
    val scrapper=Interact()
    val fromConti=scrapper.playlist(continuation)
    val playListIds = fromConti?.getJSONArray("playListIds")
    if (playListIds != null) {
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playlistLinks.add(playlistLink)
        }

    }
    if (fromConti != null) {
        if (fromConti.has("nextContinuation")){
            println(fromConti.getString("nextContinuation"))
        }else{
            val uniquePlaylistLinks = playlistLinks.distinct().toMutableList()
            file.writeText(uniquePlaylistLinks.joinToString("\n"))
            println("scrapped alll links")
        }
    }else{
        println("from conti is null")
    }



}
fun playlistLinks(channelUrl:String){
    val scrapper=Interact()
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
    val initial_page = scrapper.playListIds(playlistsPage)
    if (initial_page != null) {
        val file=File("$idOrUsername playlist.txt")
        println(file.absolutePath)
        val playListIds = initial_page.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playlistLinks.add(playlistLink)
            println(playlistLink)
        }
        if (initial_page.has("nextContinuation")){
            println(initial_page.getString("nextContinuation"))
           playlist_repeater(initial_page.getString("nextContinuation"),file)
        }else{
            val uniquePlaylistLinks = playlistLinks.distinct().toMutableList()
            file.writeText(uniquePlaylistLinks.joinToString("\n"))
        }
    }
}
fun main() {
playlistLinks("https://www.youtube.com/@TheMysticaLand")

}


