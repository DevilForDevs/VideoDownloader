
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray

import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.URLEncoder

class Interact {
    data class RequestVariant(
        val data: JSONObject,
        val query: Map<String, String>,
        val headers: Map<String, String>
    )

    private val variants = listOf(
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "WEB",
                                "clientVersion" to "2.20200720.00.02",
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "Mozilla/5.0",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),

            ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
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
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "55",
                "X-YouTube-Client-Version" to "17.31.35",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query = mapOf("key" to "AIzaSyCjc_pVEDi4qsv5MtC2dMXzpIaDoRFLsxw")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "TVHTML5_SIMPLY_EMBEDDED_PLAYER",
                                "clientVersion" to "2.0",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            ),
                            "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "85",
                "X-YouTube-Client-Version" to "2.0",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "WEB_CREATOR",
                                "clientVersion" to "1.20220726.00.00", /*can't be used for search but for playlist browsing*/
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "62",
                "X-YouTube-Client-Version" to "1.20220726.00.00",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json",
                "User-Agent" to "Mozilla/5.0"
            ),
            query = mapOf("key" to "AIzaSyBUPetSUmoZL-OhlxA7wSac5XinrygCqMo")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "MWEB",
                                "clientVersion" to "2.20220801.00.00",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            ),
                            "thirdParty" to mapOf("embedUrl" to "https://www.youtube.com/")
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "2",
                "X-YouTube-Client-Version" to "2.20220801.00.00",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "ANDROID_CREATOR",
                                "clientVersion" to "22.30.100",
                                "androidSdkVersion" to 30,
                                "userAgent" to "com.google.android.apps.youtube.creator/22.30.100 (Linux; U; Android 11) gzip",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "14",
                "X-YouTube-Client-Version" to "22.30.100",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyD_qjV8zaaUMehtLkrKFgVeSX_Iqbtyws8")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "IOS_CREATOR",
                                "clientVersion" to "22.33.101",
                                "deviceModel" to "iPhone14,3",
                                "userAgent" to "com.google.ios.ytcreator/22.33.101 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "15",
                "X-YouTube-Client-Version" to "22.33.101",
                "userAgent" to "com.google.ios.ytcreator/22.33.101 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
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
                        )
                    )
                )
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
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "IOS",
                                "clientVersion" to "17.33.2",
                                "deviceModel" to "iPhone14,3",
                                "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "5",
                "X-YouTube-Client-Version" to "17.33.2",
                "userAgent" to "com.google.ios.youtube/17.33.2 (iPhone14,3; U; CPU iOS 15_6 like Mac OS X)",
                "content-type" to "application/json",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyDCU8hByM-4DrUqRUYnGn-3llEO78bcxq8")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "WEB_REMIX",
                                "clientVersion" to "1.20220727.01.00"
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "Origin" to "https://www.youtube.com",
                "Content-Type" to "application/json",
                "User-Agent" to "Mozilla/5.0",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "ANDROID_MUSIC",
                                "clientVersion" to "7.11.50",
                                "androidSdkVersion" to 30
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "com.google.android.apps.youtube.music/",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ), RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "IOS_MUSIC",
                                "clientVersion" to "5.21",
                                "deviceModel" to "iPhone14,3"
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "Content-Type" to "application/json",
                "User-Agent" to "com.google.ios.youtubemusic/",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "WEB",
                                "clientVersion" to "2.20240726.00.00",
                                "userAgent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15,gzip(gfe)",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0,

                                )
                        )
                    )
                )
                put("videoId", "cYnhtCBMb5c")
                put("contentCheckOk", true)
                put("racyCheckOk", true)
                put("playbackContext", JSONObject().apply {
                    put(
                        "contentPlaybackContext", JSONObject(
                            mapOf(
                                "html5Preference" to "HTML5_PREF_WANTS",
                                "signatureTimestamp" to 20026
                            )
                        )
                    )
                })


            },
            headers = mapOf(
                "Content-Type" to "application/json",
                "Origin" to "https://www.youtube.com",
                "X-YouTube-Client-Name" to "1",
                "clientVersion" to "2.20240726.00.00",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "WEB_EMBEDDED_PLAYER",
                                "clientVersion" to "1.20241009.01.00",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            )
                        )
                    )
                )

            },
            headers = mapOf(
                "Origin" to "https://www.youtube.com",
                "X-YouTube-Client-Name" to "56",
                "X-YouTube-Client-Version" to "1.20241009.01.00",
                "Content-Type" to "application/json",
                "userAgent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36,gzip(gfe)"
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"),
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "ANDROID_VR",/*not uses descipher*/
                                "clientVersion" to "1.57.29",
                                "deviceMake" to "Oculus",
                                "deviceModel" to "Quest 3",
                                "androidSdkVersion" to 32,
                                "osName" to "Android",
                                "osVersion" to "12L"
                            )
                        )
                    )
                )
            },
            headers = mapOf(
                "Content-Type" to "application/json",
                "userAgent" to "com.google.android.apps.youtube.vr.oculus/1.57.29 (Linux; U; Android 12L; eureka-user Build/SQ3A.220605.009.A1) gzip",
                "Origin" to "https://www.youtube.com",
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "TVHTML5",
                                "clientVersion" to "7.20240724.13.00",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            ),
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "7",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        ),
        RequestVariant(
            data = JSONObject().apply {
                put(
                    "context", JSONObject(
                        mapOf(
                            "client" to mapOf(
                                "clientName" to "MEDIA_CONNECT_FRONTEND",
                                "clientVersion" to "0.1",
                                "hl" to "en",
                                "timeZone" to "UTC",
                                "utcOffsetMinutes" to 0
                            ),
                        )
                    )
                )
            },
            headers = mapOf(
                "X-YouTube-Client-Name" to "97",
                "Origin" to "https://www.youtube.com",
                "content-type" to "application/json"
            ),
            query = mapOf("key" to "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8")
        )


    )

    private fun encodeParams(params: Map<String, Any>): String {
        return params.entries.joinToString("&") {
            "${
                URLEncoder.encode(
                    it.key,
                    "UTF-8"
                )
            }=${URLEncoder.encode(it.value.toString(), "UTF-8")}"
        }
    }

    fun websafari() {
        val client = OkHttpClient()

        // Define the request URL and headers
        val url = "https://www.youtube.com/youtubei/v1/player?prettyPrint=false"
        val headers = Headers.Builder()
            .add("Content-Type", "application/json")
            .add("Origin", "https://www.youtube.com")
            .add("X-YouTube-Client-Name", "1")
            .add("X-YouTube-Client-Version", "2.20240726.00.00")
            .add("X-Goog-Visitor-Id", "CgtPVlVBX1JSdThjOCjvxqC5BjIKCgJJThIEGgAgWw%3D%3D")
            .build()

        // Build the JSON body
        val data = JSONObject().apply {
            put("context", JSONObject().apply {
                put(
                    "client", JSONObject(
                        mapOf(
                            "clientName" to "WEB",
                            "clientVersion" to "2.20240726.00.00",
                            "userAgent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15,gzip(gfe)",
                            "hl" to "en",
                            "timeZone" to "UTC",
                            "utcOffsetMinutes" to 0
                        )
                    )
                )
            })
            put("playbackContext", JSONObject().apply {
                put(
                    "contentPlaybackContext", JSONObject(
                        mapOf(
                            "html5Preference" to "HTML5_PREF_WANTS",
                            "signatureTimestamp" to 20026
                        )
                    )
                )
            })
            put("videoId", "cYnhtCBMb5c")
            put("contentCheckOk", true)
            put("racyCheckOk", true)
        }

        // Build the request
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), data.toString())
        val request = Request.Builder()
            .url(url)
            .headers(headers)
            .post(requestBody)
            .build()

        // Make the request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        println("Unexpected code $response")
                    } else {
                        println("Response: ${response.body?.string()}")
                    }
                }
            }
        })
    }

    @OptIn(ExperimentalStdlibApi::class)/*4k 12,14 12,13 require timestamp*/
    fun getStreamingData(videoId: String): JSONObject? {
        val indexes = mutableListOf(14, 16, 8, 9, 10, 11)
        for (clientIndex in indexes) {
            val variant = variants[0]
            websafari()
            val keY = variant.query["key"].toString()
            val url = "https://www.youtube.com/youtubei/v1/player?${
                encodeParams(
                    mapOf(
                        "videoId" to videoId,
                        "key" to keY,
                        "contentCheckOk" to true,
                        "racyCheckOk" to true
                    )
                )
            }"
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
            println(clientIndex)
            println(url)
            println(variant.data.getJSONObject("context").getJSONObject("client").getString("clientName"))

            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                val jsonResponse = JSONObject(responseBody!!.string())
                println(jsonResponse)
                if (jsonResponse.has("streamingData")) {
                    val streamingData = jsonResponse.getJSONObject("streamingData")
                    if (streamingData.has("adaptiveFormats")) {
                        val adaptiveFormats =
                            jsonResponse.getJSONObject("streamingData").getJSONArray("adaptiveFormats")
                        for (index in 0..<adaptiveFormats.length()) {
                            if (adaptiveFormats.getJSONObject(index).has("url")) {
                                return jsonResponse
                            }
                        }
                    }
                    if (streamingData.has("formats")) {
                        val adaptiveFormats = jsonResponse.getJSONObject("streamingData").getJSONArray("formats")
                        for (index in 0..<adaptiveFormats.length()) {
                            if (adaptiveFormats.getJSONObject(index).has("url")) {
                                return jsonResponse
                            }
                        }
                    }
                } else {
                    println(jsonResponse)
                }

            }
            break


        }
        return null

    }

    private fun getTitle(source: JSONObject): String? {
        return source.getJSONArray("runs").getJSONObject(0).getString("text")

    }

    private fun getDuration(source: JSONObject): String? {
        return source.getJSONArray("runs").getJSONObject(0).getString("text")
    }

    private fun getContinuation(source: JSONObject): String? {
        return source.getJSONObject("continuationEndpoint").getJSONObject("continuationCommand").getString("token")

    }

    fun videoId(url: String): String? {
        val regex =
            """^.*(?:(?:youtu\.be\/|v\/|vi\/|u\/\w\/|embed\/|shorts\/|live\/)|(?:(?:watch)?\?v(?:i)?=|\&v(?:i)?=))([^#\&\?]*).*""".toRegex()
        val matchResult = regex.find(url)
        if (matchResult != null) {
            val videoId = matchResult.groupValues[1]
            return videoId
        }
        return null
    }

    private fun getThumbnail(source: JSONObject): String? {
        val thumbs = source.getJSONArray("thumbnails")
        if (thumbs.length() > 2) {
            return thumbs.getJSONObject(1).getString("url")
        }
        return source.getJSONArray("thumbnails").getJSONObject(0).getString("url")
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

    fun getshelfRenderer(source: JSONObject): MutableList<JSONObject> {
        val itemsVideo = mutableListOf<JSONObject>()
        val vitems = source.getJSONObject("shelfRenderer").getJSONObject("content")
        if (vitems.has("verticalListRenderer")) {
            println("verticalListRenderer")
            val ivds = vitems.getJSONObject("verticalListRenderer").getJSONArray("items")
            for (z in 0 until ivds.length()) {
                val ite = JSONObject()
                val rl = ivds.getJSONObject(z)
                ite.put("videoId", rl.getJSONObject("videoRenderer").getString("videoId"))
                ite.put(
                    "title",
                    txt2filename(
                        rl.getJSONObject("videoRenderer").getJSONObject("title").getJSONArray("runs").getJSONObject(0)
                            .getString("text")
                    )
                )
                if (rl.getJSONObject("videoRenderer").has("lengthText")) {
                    ite.put(
                        "duration",
                        rl.getJSONObject("videoRenderer").getJSONObject("lengthText").get("simpleText").toString()
                    )
                } else {
                    ite.put("duration", "Unknown")
                }
                ite.put(
                    "thumbnail",
                    rl.getJSONObject("videoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails")
                        .getJSONObject(0).getString("url")
                )
                itemsVideo.add(ite)

            }
        }
        if (vitems.has("horizontalListRenderer")) {
            println("horizontalListRenderer")
            val hzi = vitems.getJSONObject("horizontalListRenderer").getJSONArray("items")
            for (l in 0 until hzi.length()) {
                val ite = JSONObject()
                val rl = hzi.getJSONObject(l)
                if (rl.has("gridVideoRenderer")) {
                    ite.put("videoId", rl.getJSONObject("gridVideoRenderer").getString("videoId"))
                    if (rl.getJSONObject("gridVideoRenderer").getJSONObject("title").has("simpleText")) {
                        ite.put(
                            "title",
                            txt2filename(
                                rl.getJSONObject("gridVideoRenderer").getJSONObject("title").getString("simpleText")
                            )
                        )
                    }
                    if (rl.getJSONObject("gridVideoRenderer").getJSONObject("title").has("runs")) {
                        ite.put(
                            "title",
                            txt2filename(
                                rl.getJSONObject("gridVideoRenderer").getJSONObject("title").getJSONArray("runs")
                                    .getJSONObject(0).getString("text")
                            )
                        )
                    }
                    if (rl.getJSONObject("gridVideoRenderer").has("lengthText")) {
                        ite.put(
                            "duration",
                            rl.getJSONObject("gridVideoRenderer").getJSONObject("lengthText").get("simpleText")
                                .toString()
                        )
                    } else {
                        if (rl.getJSONObject("gridVideoRenderer").has("thumbnailOverlays")) {
                            ite.put(
                                "duration",
                                rl.getJSONObject("gridVideoRenderer").getJSONArray("thumbnailOverlays").getJSONObject(0)
                                    .getJSONObject("thumbnailOverlayTimeStatusRenderer").getJSONObject("text")
                                    .getString("simpleText")
                            )
                        } else {
                            ite.put("duration", "Unknown")
                        }

                    }
                    ite.put(
                        "thumbnail",
                        rl.getJSONObject("gridVideoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails")
                            .getJSONObject(0).getString("url")
                    )
                    itemsVideo.add(ite)
                }
            }
        }
        return itemsVideo


    }

    fun getreelShelfRenderer(source: JSONObject): MutableList<JSONObject> {
        val itemsVideo = mutableListOf<JSONObject>()
        val reels = source.getJSONObject("reelShelfRenderer").getJSONArray("items")
        for (reel in 0 until reels.length()) {
            val ite = JSONObject()
            val rl = reels.getJSONObject(reel)
            if (rl.has("shortsLockupViewModel")) {
                val tit = rl.getJSONObject("shortsLockupViewModel").getJSONObject("overlayMetadata")
                    .getJSONObject("primaryText").getString("content")
                val vid =
                    rl.getJSONObject("shortsLockupViewModel").getJSONObject("onTap").getJSONObject("innertubeCommand")
                        .getJSONObject("reelWatchEndpoint").getString("videoId")
                ite.put("title", tit)
                ite.put("videoId", vid)
                ite.put("duration", "shorts")
                itemsVideo.add(ite)


            }
            if (rl.has("reelItemRenderer")) {
                ite.put("videoId", rl.getJSONObject("reelItemRenderer").getString("videoId"))
                try {
                    ite.put(
                        "title",
                        txt2filename(
                            rl.getJSONObject("reelItemRenderer").getJSONObject("headline").getString("simpleText")
                        )
                    )
                } catch (e: Exception) {
                    ite.put(
                        "title",
                        txt2filename(
                            rl.getJSONObject("reelItemRenderer").getJSONObject("headline").getJSONArray("runs")
                                .getJSONObject(0).getString("text")
                        )
                    )
                }
                ite.put(
                    "thumbnail",
                    rl.getJSONObject("reelItemRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails")
                        .getJSONObject(0).getString("url")
                )
                ite.put("duration", "Shorts")
                itemsVideo.add(ite)
            }

        }
        return itemsVideo
    }

    fun elementRenderer(source: JSONObject): JSONObject {
        val jsonObject = JSONObject()
        if (source.getJSONObject("elementRenderer").getJSONObject("newElement").getJSONObject("type")
                .getJSONObject("componentType").getJSONObject("model").has("compactVideoModel")
        ) {
            val videoDetails = source.getJSONObject("elementRenderer").getJSONObject("newElement").getJSONObject("type")
                .getJSONObject("componentType").getJSONObject("model").getJSONObject("compactVideoModel")
                .getJSONObject("compactVideoData").getJSONObject("videoData")
            val thumb =
                videoDetails.getJSONObject("thumbnail").getJSONObject("image").getJSONArray("sources").getJSONObject(0)
                    .getString("url")
            jsonObject.put("title", videoDetails.getJSONObject("metadata").getString("title"))
            jsonObject.put("duration", videoDetails.getJSONObject("thumbnail").get("timestampText"))
            jsonObject.put("thumbnail", thumb)
            jsonObject.put("videoId", videoId(videoDetails.getString("dragAndDropUrl")))
        } else {
            println(
                source.getJSONObject("elementRenderer").getJSONObject("newElement").getJSONObject("type")
                    .getJSONObject("componentType").getJSONObject("model")
            )
        }
        return jsonObject

    }

    private fun compactVideoRenderer(source: JSONObject): JSONObject {
        val itemTo = JSONObject()
        val js = source.getJSONObject("compactVideoRenderer")
        itemTo.put("title", getTitle(js.getJSONObject("title")))
        itemTo.put("duration", getDuration(js.getJSONObject("lengthText")))
        itemTo.put("thumbnail", getThumbnail(js.getJSONObject("thumbnail")))
        itemTo.put("videoId", js.getString("videoId"))
        return itemTo
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun search(term: String, continuation: String?): JSONObject? {
        val allItems = JSONObject()
        val indexes = mutableListOf(0, 1, 4, 7, 8)
        val videosCollected = JSONArray()
        var nextContinuation: String? = null
        var sugggestion = JSONArray()
        for (index in indexes) {
            val variant = variants[index]
            val client = OkHttpClient()
            val requestBody = variant.data
            if (continuation != null) {
                requestBody.put("continuation", continuation)
            }
            val keY = variant.query["key"].toString()
            val queryUrl = "https://www.youtube.com/youtubei/v1/search?${
                encodeParams(
                    mapOf(
                        "query" to term,
                        "key" to keY,
                        "contentCheckOk" to true,
                        "racyCheckOk" to true
                    )
                )
            }"
            val urlWithQuery = StringBuilder(queryUrl)
            println(urlWithQuery)
            println(requestBody.get("context"))
            val request = Request.Builder()
                .url(urlWithQuery.toString())
                .apply {
                    variant.headers.forEach { (key, value) ->
                        addHeader(key, value)
                    }
                }
                .post(requestBody.toString().toRequestBody())
                .build()
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                val jsonResponse = JSONObject(responseBody!!.string())
                if (jsonResponse.has("refinements")) {
                    sugggestion = jsonResponse.getJSONArray("refinements")
                }
                if (jsonResponse.has("onResponseReceivedCommands")) {
                    if (jsonResponse.getJSONArray("onResponseReceivedCommands").getJSONObject(0)
                            .has("appendContinuationItemsAction")
                    ) {
                        val sections = jsonResponse.getJSONArray("onResponseReceivedCommands").getJSONObject(0)
                            .getJSONObject("appendContinuationItemsAction").getJSONArray("continuationItems")
                        val collections =
                            sections.getJSONObject(0).getJSONObject("itemSectionRenderer").getJSONArray("contents")
                        for (su in 0..<collections.length()) {
                            val s = collections.getJSONObject(su)
                            if (s.has("videoRenderer")) {
                                val ite = JSONObject()
                                ite.put("videoId", s.getJSONObject("videoRenderer").getString("videoId"))
                                ite.put(
                                    "title",
                                    txt2filename(
                                        s.getJSONObject("videoRenderer").getJSONObject("title").getJSONArray("runs")
                                            .getJSONObject(0).getString("text")
                                    )
                                )
                                if (s.getJSONObject("videoRenderer").has("lengthText")) {
                                    ite.put(
                                        "duration",
                                        s.getJSONObject("videoRenderer").getJSONObject("lengthText").get("simpleText")
                                            .toString()
                                    )
                                } else {
                                    ite.put("duration", "Unknown")
                                }
                                ite.put(
                                    "thumbnail",
                                    s.getJSONObject("videoRenderer").getJSONObject("thumbnail")
                                        .getJSONArray("thumbnails").getJSONObject(0).getString("url")
                                )
                                videosCollected.put(ite)


                            }
                            if (s.has("reelShelfRenderer")) {
                                val videos = getreelShelfRenderer(s)
                                for (vip in videos) {
                                    videosCollected.put(vip)
                                }
                            }
                            if (s.has("shelfRenderer")) {
                                val videos = getshelfRenderer(s)
                                for (vip in videos) {
                                    videosCollected.put(vip)
                                }
                            }

                        }
                        if (sections.length() > 1) {
                            nextContinuation = sections.getJSONObject(1).getJSONObject("continuationItemRenderer")
                                .getJSONObject("continuationEndpoint").getJSONObject("continuationCommand")
                                .getString("token")
                        }
                    }

                }
                if (jsonResponse.has("contents")) {
                    if (jsonResponse.getJSONObject("contents").has("sectionListRenderer")) {
                        val conts = jsonResponse.getJSONObject("contents").getJSONObject("sectionListRenderer")
                            .getJSONArray("contents")
                        for (vu in 0..<conts.length()) {
                            val kitem = conts.getJSONObject(vu)
                            if (kitem.has("continuationItemRenderer")) {
                                val cotni = getContinuation(kitem.getJSONObject("continuationItemRenderer"))
                                if (cotni != null) {
                                    nextContinuation = cotni
                                }
                            }
                            if (kitem.has("itemSectionRenderer")) {
                                if (kitem.getJSONObject("itemSectionRenderer").has("continuations")) {
                                    nextContinuation =
                                        kitem.getJSONObject("itemSectionRenderer").getJSONArray("continuations")
                                            .getJSONObject(0).getJSONObject("nextContinuationData")
                                            .getString("continuation")
                                }
                                val compactVideoRendere =
                                    jsonResponse.getJSONObject("contents").getJSONObject("sectionListRenderer")
                                        .getJSONArray("contents").getJSONObject(0).getJSONObject("itemSectionRenderer")
                                        .getJSONArray("contents")
                                for (su in 0..<compactVideoRendere.length()) {
                                    val itemTo = JSONObject()
                                    if (compactVideoRendere.getJSONObject(su).has("compactVideoRenderer")) {
                                        val js = compactVideoRenderer(compactVideoRendere.getJSONObject(su))
                                        videosCollected.put(js)
                                    }
                                    if (compactVideoRendere.getJSONObject(su).has("videoWithContextRenderer")) {
                                        val item = compactVideoRendere.getJSONObject(su)
                                            .getJSONObject("videoWithContextRenderer")
                                        if (item.has("lengthText")) {
                                            itemTo.put("duration", getDuration(item.getJSONObject("lengthText")))
                                        } else {
                                            itemTo.put("duration", "Unknown")
                                        }
                                        itemTo.put("thumbnail", getThumbnail(item.getJSONObject("thumbnail")))
                                        itemTo.put("videoId", item.getString("videoId"))
                                        itemTo.put("title", getTitle(item.getJSONObject("headline")))
                                        videosCollected.put(itemTo)
                                    }
                                    if (compactVideoRendere.getJSONObject(su).has("elementRenderer")) {
                                        if (compactVideoRendere.getJSONObject(su).getJSONObject("elementRenderer")
                                                .getJSONObject("newElement").getJSONObject("type").has("componentType")
                                        ) {
                                            val item = elementRenderer(compactVideoRendere.getJSONObject(su))
                                            videosCollected.put(item)

                                        }

                                    }
                                }

                            }
                            if (kitem.has("elementRenderer")) {
                                val item = elementRenderer((kitem))
                                videosCollected.put(item)
                            }
                            if (kitem.has("shelfRenderer")) {
                                val items = kitem.getJSONObject("shelfRenderer").getJSONObject("content")
                                    .getJSONObject("verticalListRenderer").getJSONArray("items")
                                for (su in 0..<items.length()) {
                                    if (items.getJSONObject(su).has("elementRenderer")) {
                                        val item = elementRenderer(items.getJSONObject(su))
                                        videosCollected.put(item)
                                    }

                                }
                            }

                        }
                    }
                    if (jsonResponse.getJSONObject("contents").has("twoColumnSearchResultsRenderer")) {

                        val sections =
                            jsonResponse.getJSONObject("contents").getJSONObject("twoColumnSearchResultsRenderer")
                                .getJSONObject("primaryContents").getJSONObject("sectionListRenderer")
                                .getJSONArray("contents")
                        val collections =
                            sections.getJSONObject(0).getJSONObject("itemSectionRenderer").getJSONArray("contents")
                        for (su in 0..<collections.length()) {
                            val s = collections.getJSONObject(su)
                            if (s.has("videoRenderer")) {
                                val ite = JSONObject()
                                ite.put("videoId", s.getJSONObject("videoRenderer").getString("videoId"))
                                ite.put(
                                    "title",
                                    txt2filename(
                                        s.getJSONObject("videoRenderer").getJSONObject("title").getJSONArray("runs")
                                            .getJSONObject(0).getString("text")
                                    )
                                )
                                if (s.getJSONObject("videoRenderer").has("lengthText")) {
                                    ite.put(
                                        "duration",
                                        s.getJSONObject("videoRenderer").getJSONObject("lengthText").get("simpleText")
                                            .toString()
                                    )
                                } else {
                                    ite.put("duration", "Unknown")
                                }
                                ite.put(
                                    "thumbnail",
                                    s.getJSONObject("videoRenderer").getJSONObject("thumbnail")
                                        .getJSONArray("thumbnails").getJSONObject(0).getString("url")
                                )
                                videosCollected.put(ite)

                            }
                            if (s.has("reelShelfRenderer")) {
                                val videos = getreelShelfRenderer(s)
                                for (vip in videos) {
                                    videosCollected.put(vip)
                                }
                            }
                            if (s.has("shelfRenderer")) {
                                val videos = getshelfRenderer(s)
                                for (vip in videos) {
                                    videosCollected.put(vip)
                                }
                            }

                        }
                        if (sections.length() > 1) {
                            /*    println(sections.getJSONObject(1).getJSONObject("continuationItemRenderer").getJSONObject("continuationEndpoint").getJSONObject("continuationCommand").getString("token"))*/
                            nextContinuation = sections.getJSONObject(1).getJSONObject("continuationItemRenderer")
                                .getJSONObject("continuationEndpoint").getJSONObject("continuationCommand")
                                .getString("token")
                        }
                    }

                }
                if (jsonResponse.has("continuationContents")) {
                    val items =
                        jsonResponse.getJSONObject("continuationContents").getJSONObject("sectionListContinuation")
                            .getJSONArray("contents").getJSONObject(0).getJSONObject("itemSectionRenderer")
                            .getJSONArray("contents")
                    for (su in 0..<items.length()) {
                        val itemTo = JSONObject()
                        if (items.getJSONObject(su).has("compactVideoRenderer")) {
                            val js = compactVideoRenderer(items.getJSONObject(su))
                            videosCollected.put(js)
                        }
                        if (items.getJSONObject(su).has("elementRenderer")) {
                            val item = elementRenderer(items.getJSONObject(su))
                            videosCollected.put(item)
                        }
                    }
                    if (jsonResponse.getJSONObject("continuationContents").getJSONObject("sectionListContinuation")
                            .has("continuations")
                    ) {
                        nextContinuation =
                            jsonResponse.getJSONObject("continuationContents").getJSONObject("sectionListContinuation")
                                .getJSONArray("continuations").getJSONObject(0).getJSONObject("reloadContinuationData")
                                .getString("continuation")
                    }
                }

            }
            if (!response.isSuccessful) {
                response.close()
            }
            if (videosCollected.length() != 0) {
                allItems.put("videos", videosCollected)
                allItems.put("nextContinuation", nextContinuation)
                allItems.put("suggestion", sugggestion)
                return allItems
            }
        }
        return null

    }

    @OptIn(ExperimentalStdlibApi::class)
    fun playlistVideoRendrer(source: JSONArray): Pair<JSONArray, String?> {
        val allitems = JSONObject()
        val videos = JSONArray()
        var continuation: String? = null
        for (su in 0..<source.length()) {
            val item = source.getJSONObject(su)

            if (item.has("playlistVideoRenderer")) {
                val ite = JSONObject()
                if (item.getJSONObject("playlistVideoRenderer").getJSONObject("lengthText").has("simpleText")) {
                    ite.put(
                        "duration",
                        item.getJSONObject("playlistVideoRenderer").getJSONObject("lengthText").get("simpleText")
                    )
                }
                if (item.getJSONObject("playlistVideoRenderer").getJSONObject("lengthText").has("runs")) {
                    ite.put(
                        "duration",
                        item.getJSONObject("playlistVideoRenderer").getJSONObject("lengthText").getJSONArray("runs")
                            .getJSONObject(0).getString("text")
                    )
                }
                ite.put(
                    "thumbnail",
                    getThumbnail(item.getJSONObject("playlistVideoRenderer").getJSONObject("thumbnail"))
                )
                ite.put("title", getTitle(item.getJSONObject("playlistVideoRenderer").getJSONObject("title")))
                ite.put("videoId", item.getJSONObject("playlistVideoRenderer").get("videoId"))
                videos.put(ite)
            }
            if (item.has("richItemRenderer")) {
                val ite = JSONObject()
                val rl = item.getJSONObject("richItemRenderer").getJSONObject("content")
                if (rl.has("shortsLockupViewModel")) {
                    val tit = rl.getJSONObject("shortsLockupViewModel").getJSONObject("overlayMetadata")
                        .getJSONObject("primaryText").getString("content")
                    val vid = rl.getJSONObject("shortsLockupViewModel").getJSONObject("onTap")
                        .getJSONObject("innertubeCommand").getJSONObject("reelWatchEndpoint").getString("videoId")
                    ite.put("title", tit)
                    ite.put("videoId", vid)
                    ite.put("duration", "shorts")
                    videos.put(ite)


                }
                if (rl.has("videoWithContextRenderer")) {
                    val itemTo = JSONObject()
                    val itemT = rl.getJSONObject("videoWithContextRenderer")
                    if (item.has("lengthText")) {
                        itemTo.put("duration", getDuration(itemT.getJSONObject("lengthText")))
                    } else {
                        itemTo.put("duration", "Unknown")
                    }
                    itemTo.put("thumbnail", getThumbnail(itemT.getJSONObject("thumbnail")))
                    itemTo.put("videoId", itemT.getString("videoId"))
                    itemTo.put("title", getTitle(itemT.getJSONObject("headline")))
                    videos.put(itemTo)
                }
                if (rl.has("videoRenderer")) {

                    ite.put("videoId", rl.getJSONObject("videoRenderer").getString("videoId"))
                    ite.put(
                        "title",
                        txt2filename(
                            rl.getJSONObject("videoRenderer").getJSONObject("title").getJSONArray("runs")
                                .getJSONObject(0).getString("text")
                        )
                    )
                    if (rl.getJSONObject("videoRenderer").has("lengthText")) {
                        ite.put(
                            "duration",
                            rl.getJSONObject("videoRenderer").getJSONObject("lengthText").get("simpleText").toString()
                        )
                    } else {
                        ite.put("duration", "Unknown")
                    }
                    ite.put(
                        "thumbnail",
                        rl.getJSONObject("videoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails")
                            .getJSONObject(0).getString("url")
                    )
                    videos.put(ite)
                }
            }

            if (item.has("continuationItemRenderer")) {
                val conti = getContinuation(item.getJSONObject("continuationItemRenderer"))
                if (conti != null) {
                    continuation = conti
                }
            }
        }
        allitems.put("videos", videos)
        allitems.put("nextContinuation", continuation)
        return Pair(videos, continuation)
    }

    fun getChannelId(url: String): String? {
        try {
            val doc = Jsoup.connect(url).get()
            val scriptTags = doc.select("script")
            for (scriptTag in scriptTags) {
                val scriptContent = scriptTag.data().trim()
                if (scriptContent.startsWith("var ytInitialData")) {
                    val jsonString = scriptContent.substringAfter("{").substringBeforeLast("}")
                    val jsonObject = JSONObject("{$jsonString}")
                    val cid = jsonObject.getJSONObject("metadata").getJSONObject("channelMetadataRenderer")
                        .getString("externalId")
                    return cid
                }
            }

        } catch (e: Exception) {
            return null
        }
        return null

    }

    fun playlist(continuation: String?): JSONObject? {
        val allItems = JSONObject()
        var videosCollected = JSONArray()
        var nextContinuation: String? = null
        val indedxes = mutableListOf(0, 4, 1, 7, 8)
        for (index in indedxes) {
            val variant = variants[index]
            val client = OkHttpClient()
            val baseApiUrl = "https://www.youtube.com/youtubei/v1/browse"
            val requestBody = variant.data
            if (continuation != null) {
                requestBody.put("continuation", continuation)
            }
            val urlWithQuery = StringBuilder(baseApiUrl)
            if (variant.query.isNotEmpty()) {
                urlWithQuery.append("?")
                variant.query.forEach { (key, value) ->
                    urlWithQuery.append("$key=$value&")
                }
                urlWithQuery.deleteCharAt(urlWithQuery.length - 1)
            }
            val request = Request.Builder()
                .url(urlWithQuery.toString())
                .apply {
                    variant.headers.forEach { (key, value) ->
                        addHeader(key, value)
                    }
                }
                .post(requestBody.toString().toRequestBody())
                .build()
            val response = client.newCall(request).execute()
            response.body.use { responseBody ->
                val jsonResponse = JSONObject(responseBody?.string())
                val regex = Regex("/playlist\\?list=[\\w-]+")

                // Find matches
                val matches = regex.findAll(jsonResponse.toString())
                val idsp=JSONArray()
                // Print matches
                for (match in matches) {
                    idsp.put(match.value)
                }
                val regexForConti = Regex("\"token\":\"([\\w-]+)\"")
                val match = regexForConti.find(jsonResponse.toString())
                allItems.put("playListIds",idsp)
                if (match != null) {
                    allItems.put("nextContinuation",match.groupValues[1])
                    return allItems

                }
                if (jsonResponse.has("onResponseReceivedActions")) {
                    val sections = jsonResponse.getJSONArray("onResponseReceivedActions").getJSONObject(0)
                        .getJSONObject("appendContinuationItemsAction").getJSONArray("continuationItems")
                    val videos = playlistVideoRendrer(sections)
                    videosCollected = videos.first
                    nextContinuation = videos.second
                }
                if (jsonResponse.has("continuationContents")) {
                    val cmpvi = jsonResponse.getJSONObject("continuationContents")
                        .getJSONObject("playlistVideoListContinuation")
                    val videos = playlistVideoRendrer(cmpvi.getJSONArray("contents"))
                    videosCollected = videos.first
                    nextContinuation =
                        cmpvi.getJSONArray("continuations").getJSONObject(0).getJSONObject("nextContinuationData")
                            .getString("continuation")
                }

            }
            if (videosCollected.length() != 0) {
                allItems.put("videos", videosCollected)
                allItems.put("nextContinuation", nextContinuation)
                return allItems
            }
        }

        return allItems

    }
    fun regexPraser(url:String){
        val allItems = JSONObject()
        try {
            val doc = Jsoup.connect(url).get()
            val scriptTags = doc.select("script")
            for (scriptTag in scriptTags) {
                val scriptContent = scriptTag.data().trim()
                if (scriptContent.startsWith("var ytInitialData")) {
                    val jsonString = scriptContent.substringAfter("{").substringBeforeLast("}")
                    val jsonObject = JSONObject("{$jsonString}")
                    println(jsonObject)
                }
            }
        }catch (e: Exception) {
            println(e.printStackTrace())
        }
    }

    fun channelandplaylsit(url: String): JSONObject? {
        val allItems = JSONObject()
        try {
            val doc = Jsoup.connect(url).get()
            val scriptTags = doc.select("script")
            for (scriptTag in scriptTags) {
                val scriptContent = scriptTag.data().trim()
                if (scriptContent.startsWith("var ytInitialData")) {
                    val jsonString = scriptContent.substringAfter("{").substringBeforeLast("}")
                    val jsonObject = JSONObject("{$jsonString}")
                    var title = "Unknown"
                    if (jsonObject.getJSONObject("metadata").has("channelMetadataRenderer")) {
                        val id = jsonObject.getJSONObject("metadata").getJSONObject("channelMetadataRenderer")
                            .getString("externalId")
                        val name = jsonObject.getJSONObject("metadata").getJSONObject("channelMetadataRenderer")
                            .getString("title")

                        title = "$name($id)"
                        allItems.put("title", title)

                    }
                    if (jsonObject.getJSONObject("metadata").has("playlistMetadataRenderer")) {
                        title = jsonObject.getJSONObject("metadata").getJSONObject("playlistMetadataRenderer")
                            .getString("title")
                        allItems.put("title", title)

                    }
                    try {
                        val tabs = jsonObject.getJSONObject("contents").getJSONObject("twoColumnBrowseResultsRenderer")
                            .getJSONArray("tabs")
                        for (l in 0..<tabs.length()) {
                            if (tabs.getJSONObject(l).has("tabRenderer")) {
                                if (tabs.getJSONObject(l).getJSONObject("tabRenderer").has("content")) {
                                    val contAndItems =
                                        tabs.getJSONObject(l).getJSONObject("tabRenderer").getJSONObject("content")
                                    val videos = JSONArray()
                                    if (contAndItems.has("sectionListRenderer")) {
                                        val jk =
                                            contAndItems.getJSONObject("sectionListRenderer").getJSONArray("contents")
                                                .getJSONObject(0).getJSONObject("itemSectionRenderer")
                                                .getJSONArray("contents").getJSONObject(0)
                                        if (jk.has("richGridRenderer")) {
                                            if (jk.has("richGridRenderer")) {
                                                val conts =
                                                    jk.getJSONObject("richGridRenderer").getJSONArray("contents")
                                                for (k in 0..<conts.length()) {
                                                    if (conts.getJSONObject(k).has("richItemRenderer")) {
                                                        if (conts.getJSONObject(k).getJSONObject("richItemRenderer")
                                                                .has("header")
                                                        ) {
                                                            val nextc = contAndItems.getJSONObject("richGridRenderer")
                                                                .getJSONObject("header")
                                                                .getJSONObject("feedFilterChipBarRenderer")
                                                                .getJSONArray("contents").getJSONObject(0)
                                                                .getJSONObject("chipCloudChipRenderer")
                                                                .getJSONObject("navigationEndpoint")
                                                                .getJSONObject("continuationCommand").getString("token")
                                                            allItems.put("nextContinuation", nextc)
                                                        }
                                                        val ite = JSONObject()
                                                        val rl =
                                                            conts.getJSONObject(k).getJSONObject("richItemRenderer")
                                                                .getJSONObject("content")
                                                        if (rl.has("shortsLockupViewModel")) {
                                                            val tit = rl.getJSONObject("shortsLockupViewModel")
                                                                .getJSONObject("overlayMetadata")
                                                                .getJSONObject("primaryText").getString("content")
                                                            val vid = rl.getJSONObject("shortsLockupViewModel")
                                                                .getJSONObject("onTap")
                                                                .getJSONObject("innertubeCommand")
                                                                .getJSONObject("reelWatchEndpoint").getString("videoId")
                                                            ite.put("title", tit)
                                                            ite.put("videoId", vid)
                                                            ite.put("duration", "shorts")
                                                            videos.put(ite)


                                                        }
                                                        if (rl.has("videoRenderer")) {
                                                            ite.put(
                                                                "videoId",
                                                                rl.getJSONObject("videoRenderer").getString("videoId")
                                                            )
                                                            ite.put(
                                                                "title",
                                                                txt2filename(
                                                                    rl.getJSONObject("videoRenderer")
                                                                        .getJSONObject("title").getJSONArray("runs")
                                                                        .getJSONObject(0).getString("text")
                                                                )
                                                            )
                                                            if (rl.getJSONObject("videoRenderer").has("lengthText")) {
                                                                ite.put(
                                                                    "duration",
                                                                    rl.getJSONObject("videoRenderer")
                                                                        .getJSONObject("lengthText").get("simpleText")
                                                                        .toString()
                                                                )
                                                            } else {
                                                                ite.put("duration", "Unknown")
                                                            }
                                                            /*ite.put("thumbnail",rl.getJSONObject("videoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails").getJSONObject(0).getString("url"))*/
                                                            videos.put(ite)

                                                        }


                                                    }
                                                    if (conts.getJSONObject(k).has("continuationItemRenderer")) {
                                                        val nxtc = conts.getJSONObject(k)
                                                            .getJSONObject("continuationItemRenderer")
                                                            .getJSONObject("continuationEndpoint")
                                                            .getJSONObject("continuationCommand").getString("token")
                                                        allItems.put("nextContinuation", nxtc)

                                                    }
                                                }
                                                allItems.put("videos", videos)
                                                return allItems


                                            }
                                        }
                                        if (jk.has("playlistVideoListRenderer")) {
                                            val plv = playlistVideoRendrer(
                                                jk.getJSONObject("playlistVideoListRenderer").getJSONArray("contents")
                                            )

                                            if (plv.first.length() != 0) {
                                                allItems.put("videos", plv.first)
                                                allItems.put("nextContinuation", plv.second)
                                                allItems.put("title", title)
                                                return allItems
                                            }
                                        }
                                    }

                                    if (contAndItems.has("richGridRenderer")) {
                                        val conts =
                                            contAndItems.getJSONObject("richGridRenderer").getJSONArray("contents")

                                        for (k in 0..<conts.length()) {
                                            if (conts.getJSONObject(k).has("richItemRenderer")) {
                                                if (conts.getJSONObject(k).getJSONObject("richItemRenderer")
                                                        .has("header")
                                                ) {
                                                    val nextc = contAndItems.getJSONObject("richGridRenderer")
                                                        .getJSONObject("header")
                                                        .getJSONObject("feedFilterChipBarRenderer")
                                                        .getJSONArray("contents").getJSONObject(0)
                                                        .getJSONObject("chipCloudChipRenderer")
                                                        .getJSONObject("navigationEndpoint")
                                                        .getJSONObject("continuationCommand").getString("token")
                                                    allItems.put("nextContinuation", nextc)
                                                }
                                                val ite = JSONObject()
                                                val rl = conts.getJSONObject(k).getJSONObject("richItemRenderer")
                                                    .getJSONObject("content")
                                                if (rl.has("shortsLockupViewModel")) {
                                                    val tit = rl.getJSONObject("shortsLockupViewModel")
                                                        .getJSONObject("overlayMetadata").getJSONObject("primaryText")
                                                        .getString("content")
                                                    val vid =
                                                        rl.getJSONObject("shortsLockupViewModel").getJSONObject("onTap")
                                                            .getJSONObject("innertubeCommand")
                                                            .getJSONObject("reelWatchEndpoint").getString("videoId")
                                                    ite.put("title", tit)
                                                    ite.put("videoId", vid)
                                                    ite.put("duration", "shorts")
                                                    videos.put(ite)


                                                }
                                                if (rl.has("videoRenderer")) {
                                                    ite.put(
                                                        "videoId",
                                                        rl.getJSONObject("videoRenderer").getString("videoId")
                                                    )
                                                    ite.put(
                                                        "title",
                                                        txt2filename(
                                                            rl.getJSONObject("videoRenderer").getJSONObject("title")
                                                                .getJSONArray("runs").getJSONObject(0).getString("text")
                                                        )
                                                    )
                                                    if (rl.getJSONObject("videoRenderer").has("lengthText")) {
                                                        ite.put(
                                                            "duration",
                                                            rl.getJSONObject("videoRenderer")
                                                                .getJSONObject("lengthText").get("simpleText")
                                                                .toString()
                                                        )
                                                    } else {
                                                        ite.put("duration", "Unknown")
                                                    }
                                                    /*ite.put("thumbnail",rl.getJSONObject("videoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails").getJSONObject(0).getString("url"))*/
                                                    videos.put(ite)

                                                }


                                            }
                                            if (conts.getJSONObject(k).has("continuationItemRenderer")) {
                                                val nxtc =
                                                    conts.getJSONObject(k).getJSONObject("continuationItemRenderer")
                                                        .getJSONObject("continuationEndpoint")
                                                        .getJSONObject("continuationCommand").getString("token")
                                                allItems.put("nextContinuation", nxtc)

                                            }
                                        }
                                        allItems.put("videos", videos)
                                        return allItems


                                    }

                                }
                            }

                        }
                    } catch (e: Exception) {
                        println(e.printStackTrace())
                        return null
                    }

                }
            }

        } catch (e: IOException) {
            println("Error fetching the web page: ${e.message}")
            return null
        }
        return null

    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getFromChannelHtml(url: String): JSONObject? {
        val cm = getChannelId(url)
        val reUrl = "https://www.youtube.com/channel/${cm}/videos"
        val allItems = JSONObject()
        val videosCollected = JSONArray()
        var nextContinuation: String? = null
        try {
            val doc = Jsoup.connect(reUrl).get()
            val scriptTags = doc.select("script")
            for (scriptTag in scriptTags) {
                val scriptContent = scriptTag.data().trim()
                if (scriptContent.startsWith("var ytInitialData")) {
                    val jsonString = scriptContent.substringAfter("{").substringBeforeLast("}")
                    val jsonObject = JSONObject("{$jsonString}")

                    val videos = jsonObject.getJSONObject("contents").getJSONObject("twoColumnBrowseResultsRenderer")
                        .getJSONArray("tabs").getJSONObject(1).getJSONObject("tabRenderer").getJSONObject("content")
                        .getJSONObject("richGridRenderer").getJSONArray("contents")
                    for (l in 0..<videos.length()) {
                        val v = videos.getJSONObject(l)
                        if (v.has("richItemRenderer")) {
                            val ite = JSONObject()
                            val rl = v.getJSONObject("richItemRenderer").getJSONObject("content")
                            ite.put("videoId", rl.getJSONObject("videoRenderer").getString("videoId"))
                            ite.put(
                                "title",
                                txt2filename(
                                    rl.getJSONObject("videoRenderer").getJSONObject("title").getJSONArray("runs")
                                        .getJSONObject(0).getString("text")
                                )
                            )
                            if (rl.getJSONObject("videoRenderer").has("lengthText")) {
                                ite.put(
                                    "duration",
                                    rl.getJSONObject("videoRenderer").getJSONObject("lengthText").get("simpleText")
                                        .toString()
                                )
                            } else {
                                ite.put("duration", "Unknown")
                            }
                            ite.put(
                                "thumbnail",
                                rl.getJSONObject("videoRenderer").getJSONObject("thumbnail").getJSONArray("thumbnails")
                                    .getJSONObject(0).getString("url")
                            )
                            videosCollected.put(ite)
                        }
                        if (v.has("continuationItemRenderer")) {
                            nextContinuation =
                                v.getJSONObject("continuationItemRenderer").getJSONObject("continuationEndpoint")
                                    .getJSONObject("continuationCommand").getString("token")
                        }

                    }

                }
            }
            allItems.put("videos", videosCollected)
            allItems.put("nextContinuation", nextContinuation)
            return allItems

        } catch (e: IOException) {
            println("Error fetching the web page: ${e.message}")
            return null

        }


    }

    fun formatSpeed(speedT: Long): String {
        val speed = speedT.toDouble()
        return when {
            speed > 1e9 -> String.format("%.2f GB", speed / 1e9)
            speed > 1e6 -> String.format("%.2f MB", speed / 1e6)
            speed > 1e3 -> String.format("%.2f KB", speed / 1e3)
            else -> String.format("%.2f B", speed)
        }
    }

    fun playListIds(url: String) :JSONObject?{
        val doc = Jsoup.connect(url).get()
        val allItem=JSONObject()
        val scriptTags = doc.select("script")
        for (scriptTag in scriptTags) {
            val scriptContent = scriptTag.data().trim()
            if (scriptContent.startsWith("var ytInitialData")) {
                val jsonString = scriptContent.substringAfter("{").substringBeforeLast("}")
                val regex = Regex("/playlist\\?list=[\\w-]+")
                val matches = regex.findAll(jsonString)
                val playLIstids=JSONArray()
                for (match in matches) {
                    playLIstids.put(match.value)
                }
                val regexForConti = Regex("\"token\":\"([\\w-]+)\"")
                val match = regexForConti.find(jsonString)
                if (match != null) {
                    allItem.put("nextContinuation",match.groupValues[1])
                    allItem.put("playListIds",playLIstids)
                    return allItem
                } else {
                    allItem.put("playListIds",playLIstids)
                    return allItem
                }

            }
        }
        return null


    }


}




