import java.io.File

val videoUrls = mutableListOf<String>()
val scrapper = Interact()
fun repeater(conti:String,file: File) {
    val allItems=scrapper.playlist(conti)
    val videos= allItems?.getJSONArray("videos")
    if (videos != null) {
        for (l in 0..<videos.length()) {
            val videoId=videos.getJSONObject(l).getString("videoId")
            val urlT="https://www.youtube.com/watch?v=$videoId"
            videoUrls.add(urlT)
            println(urlT)
        }
    }
    if (allItems != null) {
        if (allItems.has("nextContinuation")){
            println(allItems.getString("nextContinuation"))
            repeater(allItems.getString("nextContinuation"),file)

        }else{
            file.writeText(videoUrls.joinToString("\n"))
        }
    }
}
fun classi(url:String): String? {
    if (url.contains("shorts")) {
        return " shorts"
    }
    if (url.contains("playlist")) {
        return " playlist"
    }
    if (url.contains("videos")) {
        return " vidoes"
    }
    return null
}
fun getAllPlaylistLinks(){


}

fun main() {
    getAllLinksOfChannel("https://www.youtube.com/@freecodecamp")
    val plc="4qmFsgJ4EhhVQzhidXRJU0Z3VC1XbDdFVjBoVUswQlEaKkVnbHdiR0Y1YkdsemRITVlBeUFBTUFFNEFlb0RCME5uVGtSUmFsRSUzRJoCL2Jyb3dzZS1mZWVkVUM4YnV0SVNGd1QtV2w3RVYwaFVLMEJRcGxheWxpc3RzMTA0"
    /*println(scrapper.channelandplaylsit("https://youtube.com/playlist?list=PL-aJ7oEAuZ3f1QS4qQY5dOlroi52EnkVg&si=OkJFMWusFuy-jtsG"))*/
   /* val allItem=scrapper.playlist(plc)*/
   /* val allItem=scrapper.playListIds("https://www.youtube.com/@freecodecamp/playlists")
    if (allItem != null) {
        if (allItem.has("nextContinuation")){
            println(allItem.getString("nextContinuation"))
        }
        println(allItem.getJSONArray("playListIds"))
    }*/
        /*val url = "https://youtube.com/playlist?list=PL-aJ7oEAuZ3f1QS4qQY5dOlroi52EnkVg&si=OkJFMWusFuy-jtsG"
    val allItems=scrapper.channelandplaylsit(url)
    val ijl=classi(url)
    if (allItems != null) {
        val file = File(scrapper.txt2filename(allItems.getString("title"))+"$ijl.txt")
        if (allItems.has("nextContinuation")){
            println(allItems.getString("nextContinuation"))
        }
        val videos=allItems.getJSONArray("videos")
        for (l in 0..<videos.length()) {
            val videoId=videos.getJSONObject(l).getString("videoId")
            val urlT="https://www.youtube.com/watch?v=$videoId"
            videoUrls.add(urlT)
            println(urlT)
        }
        if (allItems.has("nextContinuation")){
            repeater(allItems.getString("nextContinuation"),file)
        }else{
            file.writeText(videoUrls.joinToString("\n"))
        }

    }*/


}


