import RandomStringGenerator.generateContentPlaybackNonce
import RandomStringGenerator.generateTParameter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.URLEncoder
import java.nio.file.FileSystems

/*for issue contact me ranjanpanpura@gmail.com,https://www.instagram.com/devilfordevs/?hl=en,*/
data class RequestVariant(
    val data: JSONObject,
    val query: Map<String, String>,
    val headers: Map<String, String>
)
class DownloaderApp{
    var downloadedBytes=0L
    var totalBytes=0L
    fun convertBytes(sizeInBytes: Long): String {
        val kilobyte = 1024L
        val megabyte = kilobyte * 1024
        val gigabyte = megabyte * 1024

        return when {
            sizeInBytes >= gigabyte -> String.format("%.2f GB", sizeInBytes.toDouble() / gigabyte)
            sizeInBytes >= megabyte -> String.format("%.2f MB", sizeInBytes.toDouble() / megabyte)
            sizeInBytes >= kilobyte -> String.format("%.2f KB", sizeInBytes.toDouble() / kilobyte)
            else -> "$sizeInBytes Bytes"
        }
    }

    fun start(youtubeUrl: String){
        val vid=extractVideoId(youtubeUrl)
        if (vid!=null){
            /*Youtbe expects vistor id for every streamingData retrival*/
            val visitorData=getVisitorId()
            /*cpn or nonce string returned in formats url so its required*/
            val cpn=generateContentPlaybackNonce()
            /*the t paramerter i dont what its used but seems important*/
            val tp=generateTParameter()
            val client = OkHttpClient()
            val request=androidPlayerResponse(cpn,visitorData,vid,tp)
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                if (responseBody != null) {
                    val playerResponse=JSONObject(responseBody.string())
                    val json=playerResponse.getJSONObject("playerResponse")
                    if (json.has("streamingData")){
                        val std=json.getJSONObject("streamingData")
                        val adaptiveFormats=json.getJSONObject("streamingData").getJSONArray("adaptiveFormats")
                        val title=txt2filename(json.getJSONObject("videoDetails").getString("title"))
                        val userHome = System.getProperty("user.home")
                        val downloadsFolder = FileSystems.getDefault().getPath(userHome, "Downloads").toString()
                        val videoFile="$downloadsFolder/video.mp4"
                        val audioFile="$downloadsFolder/audio.mp3"
                        val finalFile="$downloadsFolder/$title.mp4"
                        if (std.has("adaptiveFormats")){
                            println("$title")
                            println("itags are 140,135,136,160,133 etc")
                            val item=selectFormat(adaptiveFormats)
                            if (item==null){
                                println("Unable to select resolution")
                                println("\nEnter Url>>>")
                                val nextV= readln()
                                start(nextV)
                            }
                            if (item != null) {
                                if (item.getString("mimeType").contains("audio/mp4")){
                                    downloadedBytes=0
                                    totalBytes=item.getString("contentLength").toLong()
                                    println("Downloading  Audio")
                                    println(audioFile)
                                    downloadas9mb(item.getString("url"),FileOutputStream(audioFile))
                                    println("\nEnter Url>>>")
                                    val nextV= readln()
                                    start(nextV)
                                }else{
                                    println(title)
                                    downloadedBytes=0
                                    totalBytes=item.getString("contentLength").toLong()
                                    println("Downloading  Video")
                                    downloadas9mb(item.getString("url"),FileOutputStream(videoFile))
                                    downloadedBytes=0
                                    totalBytes=item.getString("audioLength").toLong()
                                    println("Downloading  Audio")
                                    downloadas9mb(item.getString("audioUrl"),FileOutputStream(audioFile))
                                    meregeVideoAudio(audioFile,videoFile,finalFile)
                                    println("\nEnter Url>>>")
                                    val nextV= readln()
                                    start(nextV)
                                }
                            }

                        }
                    }else{
                        println("Failed to get Streaming Data")
                        println("\nEnter Url>>>")
                        val nextV= readln()
                        start(nextV)
                    }


                }

            }
        }else{
            println("Unable to Extract VideoId")
            println("\nEnter Url>>>")
            val nextV= readln()
            start(nextV)
        }

    }
    fun selectFormat(adaptiveFormats:JSONArray): JSONObject? {
        var audioUrl:String?=null
        var audioContentLength:String?=null
        for (index in 0 ..adaptiveFormats.length()-1){
            val item=adaptiveFormats.getJSONObject(index)
            if (item.getString("mimeType").contains("video/mp4")){
                val resolution="${item.getInt("itag")}    ${item.getString("qualityLabel")}  ${convertBytes(item.getString("contentLength").toLong())} VideoOnly"
                println(resolution)
            }
            if (item.getString("mimeType").contains("audio/mp4")){
                if (item.getInt("itag")==140){
                    audioUrl=item.getString("url")
                    audioContentLength=item.getString("contentLength")

                }
                val resolution="${item.getInt("itag")}  ${convertBytes(item.getString("contentLength").toLong())} audioOnly"
                println(resolution)
            }
        }

        println("Enter Itag>>>")
        val input= readln()
        for (index in 0 ..adaptiveFormats.length()-1){
            val item=adaptiveFormats.getJSONObject(index)
            if (item.getInt("itag")==input.toInt()){
                if (item.getString("mimeType").contains("audio/mp4")){
                    return item
                }else{
                    item.put("audioUrl",audioUrl)
                    item.put("audioLength",audioContentLength)
                    return item
                }
            }
        }
        selectFormat(adaptiveFormats)
        return null
    }

    fun meregeVideoAudio(audio:String,video:String,final:String): Boolean {
        val cmd = mutableListOf(
            "ffmpeg.exe",
            "-i", "\"$video\"",
            "-i", "\"$audio\"",
            "-c:v", "copy",
            "-y",
            "-map", "0:v:0",
            "-map", "1:a:0",
            "\"$final\""
        )
        val processBuilder = ProcessBuilder(cmd)
        processBuilder.redirectErrorStream(false)
        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        processBuilder.redirectError(ProcessBuilder.Redirect.PIPE)
        val process = processBuilder.start()
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))
        val progressRegex = Regex("time=([\\d:.]+)")
        val durationRegex = Regex("Duration: ([\\d:.]+),")
        var line: String?
        var totalDuration: String? = null

        while (errorReader.readLine().also { line = it } != null) {
            if (totalDuration == null) {
                val durationMatch = durationRegex.find(line ?: "")
                if (durationMatch != null) {
                    totalDuration = durationMatch.groupValues[1]
                }
            }
            val matchResult = progressRegex.find(line ?: "")
            if (matchResult != null) {
                val progress = matchResult.groupValues[1]
                if (totalDuration != null) {
                    print("\rMerging: $progress/$totalDuration")
                }
            }
        }
        return true
    }
    //this method is failing due to ssl error on android same method works finely but on my machine failing
    /*If you use different language like python on win 10,or c# this function works well*/
    fun downloadas9mb(url: String, fos: OutputStream) {

        val client = OkHttpClient()
        val enbyte= minOf(downloadedBytes+9437184,totalBytes)
        /*to use endbyte put range=$downloadedBytes-$endBytes and uncomment downloadasmb(url,fos),after resonse block*/
        val request = Request.Builder()
            .url(url)
            .addHeader("Range", "bytes=0-")
            .build()
        val response=client.newCall(request).execute()
        response.body?.byteStream().use { inputStream->
            val buffer=ByteArray(1024)
            var bytesRead: Int
            if (inputStream != null) {
                while (inputStream.read(buffer).also { bytesRead=it }!=-1){
                    fos.write(buffer,0,bytesRead)
                    downloadedBytes+=bytesRead
                    print("\r${convertBytes(downloadedBytes)}/${convertBytes(totalBytes)}")
                }
            }
        }
       /* if (downloadedBytes==totalBytes){
            println("\nDownload Finished")
            fos.close()
        }else{
            downloadas9mb(url,fos)
        }*/
    }
    fun extractVideoId(ytUrl: String): String? {
        val regex = """^.*(?:(?:youtu\.be\/|v\/|vi\/|u\/\w\/|embed\/|shorts\/|live\/)|(?:(?:watch)?\?v(?:i)?=|\&v(?:i)?=))([^#\&\?]*).*""".toRegex()
        val matchResult = regex.find(ytUrl)
        if (matchResult != null) {
            return matchResult.groupValues[1]
        }
        return null
    }
    fun encodeParams(params: Map<String, Any>): String {
        return params.entries.joinToString("&") { "${URLEncoder.encode(it.key, "UTF-8")}=${URLEncoder.encode(it.value.toString(), "UTF-8")}" }
    }
    fun txt2filename(txt: String): String {
        val specialCharacters = listOf(
            "@", "#", "$", "*", "&", "<", ">", "/", "\\b", "|", "?", "CON", "PRN", "AUX", "NUL",
            "COM0", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT0",
            "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9", ":", "\"", "'"
        )

        var normalString = txt
        for (sc in specialCharacters) {
            normalString = normalString.replace(sc, "")
        }

        return normalString
    }

}
fun androidPlayerResponse(cpn:String,visitorData:String,videoId:String,t:String): Request {
    val url = "https://youtubei.googleapis.com/youtubei/v1/reel/reel_item_watch?prettyPrint=false&t=$t&id=$videoId&fields=playerResponse"

    // Create the JSON request body
    val jsonBody = JSONObject().apply {
        put("cpn", cpn)
        put("contentCheckOk", true)
        put("context", JSONObject().apply {
            put("request", JSONObject().apply {
                put("internalExperimentFlags", JSONArray())

            })
            put("client", JSONObject().apply {
                put("androidSdkVersion", 35)
                put("utcOffsetMinutes", 0)
                put("osVersion", "15")
                put("hl", "en-GB")
                put("clientName", "ANDROID")
                put("gl", "GB")
                put("clientScreen", "WATCH")
                put("clientVersion", "19.28.35")
                put("osName", "Android")
                put("platform", "MOBILE")
                put("visitorData", visitorData)
            })
            put("user", JSONObject().apply {
                put("lockedSafetyMode", false)
            })
        })
        put("racyCheckOk", true)
        put("videoId", videoId)
        put("playerRequest", JSONObject().apply {
            put("videoId", videoId)
        })
        put("disablePlayerResponse", false)
    }

    // Define the request headers
    val headers = mapOf(
        "User-Agent" to "com.google.android.youtube/19.28.35 (Linux; U; Android 15; GB) gzip",
        "X-Goog-Api-Format-Version" to "2",
        "Content-Type" to "application/json",
        "Accept-Language" to "en-GB, en;q=0.9"
    )
    val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
    val requestBuilder = Request.Builder()
        .url(url)
        .post(requestBody)
    headers.forEach { (key, value) ->
        requestBuilder.addHeader(key, value)
    }
    val request = requestBuilder.build()
    return request

}
fun getVisitorId(): String {
    val client = OkHttpClient()
    val url = "https://youtubei.googleapis.com/youtubei/v1/visitor_id?prettyPrint=false"

    // JSON Body
    val jsonBody = JSONObject(
        mapOf(
            "context" to mapOf(
                "request" to mapOf(
                    "internalExperimentFlags" to emptyList<Any>(),

                ),
                "client" to mapOf(
                    "androidSdkVersion" to 35,
                    "utcOffsetMinutes" to 0,
                    "osVersion" to "15",
                    "hl" to "en-GB",
                    "clientName" to "ANDROID",
                    "gl" to "GB",
                    "clientScreen" to "WATCH",
                    "clientVersion" to "19.28.35",
                    "osName" to "Android",
                    "platform" to "MOBILE"
                ),
                "user" to mapOf(
                    "lockedSafetyMode" to false
                )
            )
        )
    )

    // Headers
    val headers = mapOf(
        "User-Agent" to "com.google.android.youtube/19.28.35 (Linux; U; Android 15; GB) gzip",
        "X-Goog-Api-Format-Version" to "2",
        "Content-Type" to "application/json",
        "Accept-Language" to "en-GB, en;q=0.9"
    )

    // Convert JSON to Request Body
    val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

    // Build Request
    val requestBuilder = Request.Builder()
        .url(url)
        .post(requestBody)

    // Add Headers
    headers.forEach { (key, value) ->
        requestBuilder.addHeader(key, value)
    }

    // Final Request
    val request = requestBuilder.build()
    val respons=client.newCall(request).execute()
    val responseString=respons.body?.string()
    val responseJson=JSONObject(responseString)
    return responseJson.getJSONObject("responseContext").getString("visitorData")

}
fun getContentLength(url: String):Long{

    val requestBuilder = Request.Builder()
        .url(url)
        .method("HEAD", null)
    val request = requestBuilder.build()
    val client = OkHttpClient()
    val respons=client.newCall(request).execute()
    return respons.headers.get("Content-Length")?.toLong() ?: 0L


}









fun main() {
    val dpa=DownloaderApp()
    println("Enter Url>>>")
    val input= readln()
    dpa.start(input)
}