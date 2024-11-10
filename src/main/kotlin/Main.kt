import java.io.File
val videoUrls = mutableListOf<String>()
val playListUrls=mutableListOf<String>()
fun main() {
  playlistLinks("https://www.youtube.com/@CodeWithHarry")
}
fun singlePlooper(continuation: String){
    println("usig continuation of a playlist")
    val scrapper=Interact()
    val allItems=scrapper.playlist(continuation)
    if (allItems != null) {
        val playListIds = allItems.getJSONArray("videos")
        for (l in 0 until playListIds.length()) {
            val videoLink = "https://www.youtube.com/watch?v=" +playListIds.getJSONObject(l).getString("videoId")
            println(videoLink)
        }
        if (allItems.has("nextContinuation")){
            singlePlooper(allItems.getString("nextContinuation"))
        }


    }
}

fun singlePlaylist(url:String){
    val scrapper=Interact()
    val allItems=scrapper.channelandplaylsit(url)
    if (allItems != null) {
        val playListIds = allItems.getJSONArray("videos")
        if (allItems.has("title")){
            println(allItems.getString("title"))
        }
        for (l in 0 until playListIds.length()) {
            val videoLink = "https://www.youtube.com/watch?v=" +playListIds.getJSONObject(l).getString("videoId")
            println(videoLink)
        }
        if (allItems.has("nextContinuation")){
            singlePlooper(allItems.getString("nextContinuation"))
        }


    }

}
fun looper(continuation:String){
    println("using continuation of playlist page")
    val scrapper=Interact()
    val allitem=scrapper.palylistPage(continuation)

    if (allitem != null) {
        val playListIds = allitem.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playListUrls.add(playlistLink)
        }
        if (allitem.has("nextContinuation")){
            looper(allitem.getString("nextContinuation"))
        }else{
            for (link in playListUrls){
                println("$link   urls of the following link")
                singlePlaylist(link)
            }

        }


    }else{
        println("playlist returnded null")
    }


}

fun videoPageLooper(continuation: String){
    val scrapper=Interact()
    val allitem=scrapper.playlist(continuation)

    if (allitem != null) {
        val videos = allitem.getJSONArray("videos")
        for (l in 0 until videos.length()) {
            val videoLink = "https://www.youtube.com/watch?v=" +videos.getJSONObject(l).getString("videoId")
            println(videoLink)
        }
        if (allitem.has("nextContinuation")){
            videoPageLooper(allitem.getString("nextContinuation"))
        }else{
            println("printed all videos from video page")
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
    val videosPage = "$baseUrl/videos"
    val shortsPage = "$baseUrl/shorts"
    val playlistsPage = "$baseUrl/playlists"
    val initial_page = scrapper.playListIds(playlistsPage)
    if (initial_page != null) {
        val file=File("$idOrUsername playlist.txt")
        val playListIds = initial_page.getJSONArray("playListIds")
        for (l in 0 until playListIds.length()) {
            val playlistLink = "https://www.youtube.com" + playListIds[l]
            playListUrls.add(playlistLink)
        }
        if (initial_page.has("nextContinuation")){
            looper(initial_page.getString("nextContinuation"))
        }else{
            for (link in playListUrls){
                println(link)
                singlePlaylist(link)
            }
        }



    }
   /* println(videosPage)
    val videoPageData=scrapper.channelandplaylsit(videosPage)
    if (videoPageData != null) {
        val file=File("$idOrUsername playlist.txt")
        val videos= videoPageData.getJSONArray("videos")
        if (videos != null) {
            for (l in 0 until videos.length()) {
                val videoLink = "https://www.youtube.com/watch?v=" +videos.getJSONObject(l).getString("videoId")
                println(videoLink)
            }
        }
        if (videoPageData.has("nextContinuation")){
            videoPageLooper(videoPageData.getString("nextContinuation"))
        }else{
            println("printed all videos from videosPage")
        }


    }
    println(shortsPage)
    val shortsPageData=scrapper.channelandplaylsit(shortsPage)
    if (shortsPageData != null) {
        val file=File("$idOrUsername playlist.txt")
        val videos= shortsPageData.getJSONArray("videos")
        if (videos != null) {
            for (l in 0 until videos.length()) {
                val videoLink = "https://www.youtube.com/watch?v=" +videos.getJSONObject(l).getString("videoId")
                println(videoLink)
            }
        }
        if (shortsPageData.has("nextContinuation")){
            videoPageLooper(shortsPageData.getString("nextContinuation"))
        }else{
            println("printed all videos from shortspage")
        }


    }*/



}


