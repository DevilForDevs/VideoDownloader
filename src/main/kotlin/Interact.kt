import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URLEncoder
/*welcome to this project written by Ranjan Kumar*/
/*Instagram id devilfordevs*/
/*is type or bhi confiugration create karne ke liye use kare pycharm,yt-dlp,pytube*/
class Interact {
    data class RequestVariant(
        val data: JSONObject,
        val query: Map<String, String>,
        val headers: Map<String, String>
    )
    private val variants = listOf(
        RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "WEB",
                        "clientVersion" to "2.20200720.00.02",
                    )
                )))
            },
            headers =mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "Mozilla/5.0",
                "Origin" to "https://www.youtube.com",
            ),
            query =mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),

            ),
        RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "ANDROID_EMBEDDED_PLAYER",
                        "clientVersion" to "17.31.35",
                        "androidSdkVersion" to 30,
                        "userAgent" to "com.google.android.youtube/17.31.35 (Linux; U; Android 11) gzip",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    ),
                    "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                )))
            },
            headers =mapOf(
                "X-YouTube-Client-Name" to "55",
                "X-YouTube-Client-Version" to "17.31.35",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query =mapOf("key" to "AIzaSyCjc_pVEDi4qsv5MtC2dMXzpIaDoRFLsxw")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "TVHTML5_SIMPLY_EMBEDDED_PLAYER",
                        "clientVersion" to "2.0",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    ),
                    "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                )))
            },
            headers =mapOf(
                "X-YouTube-Client-Name" to "85",
                "X-YouTube-Client-Version" to "2.0",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query =mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        )
        , RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "WEB_CREATOR",
                        "clientVersion" to "1.20220726.00.00", /*can't be used for search but for playlist browsing*/
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    )
                )))
            },
            headers =mapOf(
                "X-YouTube-Client-Name" to "62",
                "X-YouTube-Client-Version" to "1.20220726.00.00",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json",
                "User-Agent" to "Mozilla/5.0"
            ),
            query = mapOf("key" to "AIzaSyBUPetSUmoZL-OhlxA7wSac5XinrygCqMo")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "MWEB",
                        "clientVersion" to "2.20220801.00.00",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    ),
                    "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                )))
            },
            headers =mapOf(
                "X-YouTube-Client-Name" to "2",
                "X-YouTube-Client-Version" to "2.20220801.00.00",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ),
        RequestVariant(
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
        ),
        RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "IOS_CREATOR",
                        "clientVersion" to "22.33.101",
                        "deviceModel" to "iPhone14,3",
                        "userAgent" to "com.google.ios.ytcreator/22.33.101 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    )
                )))
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "15",
                "X-YouTube-Client-Version" to "22.33.101",
                "userAgent" to "com.google.ios.ytcreator/22.33.101 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query =mapOf("key" to "AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "IOS_MESSAGES_EXTENSION",
                        "clientVersion" to "17.33.2",
                        "deviceModel" to "iPhone14,3",
                        "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    ),
                    "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                )))
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "66",
                "X-YouTube-Client-Version" to "17.33.2",
                "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "IOS",
                        "clientVersion" to "17.33.2",
                        "deviceModel" to "iPhone14,3",
                        "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                        "hl" to "en",
                        "timeZone" to "UTC",
                        "utcOffsetMinutes" to 0
                    )
                )))
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "5",
                "X-YouTube-Client-Version" to "17.33.2",
                "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query =mapOf("key" to "AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "WEB_REMIX",
                        "clientVersion" to "1.20220727.01.00"
                    )
                )))
            },
            headers =mapOf(
                "Origin" to "https://www.youtube.com",
                "Content-Type" to "application/json",
                "User-Agent" to "Mozilla/5.0",
            ),
            query =mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "ANDROID_MUSIC",
                        "clientVersion" to "5.16.51",
                        "androidSdkVersion" to 30
                    )
                )))
            },
            headers =mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "com.google.android.apps.youtube.music/",
                "Origin" to "https://www.youtube.com",
            ),
            query =mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ), RequestVariant(
            data = JSONObject().apply {
                put("context", JSONObject(mapOf(
                    "client" to mapOf(
                        "clientName" to "IOS_MUSIC",
                        "clientVersion" to "5.21",
                        "deviceModel" to "iPhone14,3"
                    )
                )))
            },
            headers =mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "com.google.ios.youtubemusic/",
                "Origin" to "https://www.youtube.com",
            ),
            query =mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),

            )
    )
    private fun encodeParams(params: Map<String, Any>): String {
        return params.entries.joinToString("&") { "${URLEncoder.encode(it.key, "UTF-8")}=${URLEncoder.encode(it.value.toString(), "UTF-8")}" }
    }
    @OptIn(ExperimentalStdlibApi::class)
    fun getStreamingData(videoId:String): JSONObject? {
        val indexes= mutableListOf(1,5,6,7,8,9,10,11)
        for ( clientIndex in indexes){
            val variant=variants[clientIndex]
            val keY= variant.query["key"].toString()
            val url = "https://www.youtube.com/youtubei/v1/player?${encodeParams(mapOf("videoId" to videoId, "key" to keY, "contentCheckOk" to true, "racyCheckOk" to true))}"
            val requestBody = variant.data.toString()
            val request = Request.Builder()
                .url(url)
                .apply {
                    variant.headers.forEach { (key, value) ->
                        addHeader(key, value)
                    }
                }
                .post(requestBody.toRequestBody())
                .build()
            println(url)
            println(variant.data.getJSONObject("context").getJSONObject("client").getString("clientName"))
            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                val jsonResponse = JSONObject(responseBody!!.string())
                if (jsonResponse.has("streamingData")){
                    val streamingData=jsonResponse.getJSONObject("streamingData")
                    if (streamingData.has("adaptiveFormats")){
                        val adaptiveFormats=jsonResponse.getJSONObject("streamingData").getJSONArray("adaptiveFormats")
                        for (index in 0..<adaptiveFormats.length()) {
                            if(adaptiveFormats.getJSONObject(index).has("url")){
                                return jsonResponse
                            }
                        }
                    }
                    if (streamingData.has("formats")){
                        val adaptiveFormats=jsonResponse.getJSONObject("streamingData").getJSONArray("formats")
                        for (index in 0..<adaptiveFormats.length()) {
                            if(adaptiveFormats.getJSONObject(index).has("url")){
                                return jsonResponse
                            }
                        }
                    }
                }

            }

        }
        return null

    }
    fun videoId(url: String): String? {
        val regex = """^.*(?:(?:youtu\.be\/|v\/|vi\/|u\/\w\/|embed\/|shorts\/|live\/)|(?:(?:watch)?\?v(?:i)?=|\&v(?:i)?=))([^#\&\?]*).*""".toRegex()
        val matchResult = regex.find(url)
        if (matchResult != null) {
            val videoId = matchResult.groupValues[1]
            return videoId
        }
        return null
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