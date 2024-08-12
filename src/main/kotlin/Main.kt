import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.URLEncoder
import java.nio.file.FileSystems

data class RequestVariant(
    val data: JSONObject,
    val query: Map<String, String>,
    val headers: Map<String, String>
)
class DownloaderApp{
    var downloadedBytes=0L
    var totalBytes=0L
    val requestVariant=RequestVariant(
        data = JSONObject().apply {
            put("context", JSONObject(mapOf(
                "client" to mapOf(
                    "clientName" to "ANDROID_CREATOR",
                    "clientVersion" to "22.30.100",
                    "androidSdkVersion" to 30,
                    "userAgent" to "com.google.android.apps.youtube.creator/22.30.100 (Linux; U; Android 11) gzip",
                    "hl" to "en",
                    "timeZone" to "UTC",
                    "utcOffsetMinutes" to 0
                )
            )))
        },
        headers =mapOf(
            "X-YouTube-Client-Name" to "14",
            "X-YouTube-Client-Version" to "22.30.100",
            "content-type" to "application/json",
            "Origin" to "https://www.youtube.com",
        ),
        query =mapOf("key" to "AIzaSyD_qjV8zaaUMehtLkrKFgVeSX_Iqbtyws8")
    )
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
        val keY=requestVariant.query["key"].toString()
        val vid=extractVideoId(youtubeUrl)
        if (vid!=null){
            val url = "https://www.youtube.com/youtubei/v1/player?${encodeParams(mapOf("videoId" to vid, "key" to keY, "contentCheckOk" to true, "racyCheckOk" to true))}"
            val requestBody =requestVariant.data.toString()
            val request = Request.Builder()
                .url(url)
                .apply {
                    requestVariant.headers.forEach { (key, value) ->
                        addHeader(key, value)
                    }
                }
                .post(requestBody.toRequestBody())
                .build()
            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                if (responseBody != null) {
                    val json=JSONObject(responseBody.string())
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
                            println("itags are 140,135,136,160,133 etc ")
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
            if (item.getString("mimeType").contains("audio")){
                if (item.getInt("itag")==140){
                    audioUrl=item.getString("url")
                    audioContentLength=item.getString("contentLength")

                }
                val resolution="${item.getInt("itag")}  ${convertBytes(item.getString("contentLength").toLong())} ${convertBytes(item.getInt("bitrate").toLong())} audioOnly"
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
    fun downloadas9mb(url:String,fos:FileOutputStream){
        val client = OkHttpClient()
        val enbyte= minOf(downloadedBytes+9437184,totalBytes)
        val request = Request.Builder()
        .url(url)
        .addHeader("Range", "bytes=$downloadedBytes-$enbyte")
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
        if (downloadedBytes==totalBytes){
            println("\nDownload Finished")
            fos.close()
        }else{
            downloadas9mb(url,fos)
        }

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



fun main() {
 val dpa=DownloaderApp()
    println("Enter Url>>>")
    val input= readln()
    dpa.start(input)
}
