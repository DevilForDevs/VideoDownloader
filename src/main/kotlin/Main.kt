import java.io.File

fun main() {
    singlePlaylist("https://youtube.com/playlist?list=PL8828Z-IEhFErzy-eKRAiTta0GLrzn6fM")
   /* playlistLinks("https://www.youtube.com/@TheMysticaLand")*/
}
fun singlePlooper(continuation: String){
    println("using looper")
    val scrapper=Interact()
    val allItems=scrapper.playlist(continuation)
    println(allItems)
    if (allItems != null) {
        val playListIds = allItems.getJSONArray("videos")
        for (l in 0 until playListIds.length()) {
            println(playListIds[l])
        }
        if (allItems.has("nextContinuation")){
            println("repeating")
        }else{
            println("going to write first looped page")
        }


    }
}
fun singlePlaylist(url:String){
    val scrapper=Interact()
    val allItems=scrapper.channelandplaylsit(url)
    if (allItems != null) {
        val playListIds = allItems.getJSONArray("videos")
        for (l in 0 until playListIds.length()) {
            println(playListIds[l])
        }
        if (allItems.has("nextContinuation")){
            singlePlooper(allItems.getString("nextContinuation"))
        }else{
            println("going to write first looped page")
        }


    }

}
fun looper(continuation:String){
    val scrapper=Interact()
    val allitem=scrapper.playlist(continuation)

    if (allitem != null) {
        val playListIds = allitem.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            println(playlistLink)
        }
        if (allitem.has("nextContinuation")){
            println("repeating")
        }else{
            println("going to write first looped page")
        }


    }

}

fun playlistLinks(channelUrl: String) {
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
        val playListIds = initial_page.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            println(playlistLink)
        }
        if (initial_page.has("nextContinuation")){
            println("going to next page")
            looper(initial_page.getString("nextContinuation"))
        }else{
            println("going to write first page")
        }


    }

}


