import org.json.JSONObject
import java.io.File

fun main() {
    /*shorts continuaion,home ok*/
    val scrapper = Interact()
    val url = "https://www.youtube.com/@zeemusiccompany/videos"
    val ctc ="4qmFsgKrCBIYVUNGRmJ3bnZlM3lGNjItdFZYa1R5SHFnGo4IOGdhRUJocUJCbnItQlFyNUJRclFCVUZrY2tnelRFNUVkbkJoTkVkSFNqbDVjR000ZVRCTU9XWnBMWEJKYzAxR1NuVm5hMmN5TkMxV2JVUjFlSEJtVWt4dFVtSlZhbHBGVHpObGNYZDRhVlZFY2psRWJ6a3pkRjluVXpGdFQwOUpTVjgwTkhrNFYxZFNNM1ZNYjFKRGFreExabE5oVEhGeFJWOVRNalpYT1U1Vk1rNTZOMHgzVUd4NWQyZE9helV6V2t0RmJEQXllamRGUVd4ak5EaGlaRWxsUkc1b1RUQm9UV1JMTmtKVk1UUlljUzFqU0hrd2VqUjJXV2R0VEhvd2RIRlJaSFZhTjFRMVlYVkdOM05sT1d4RFVHcHRNRXBmUm5ZMFJVbHBURkJETWxSWFlqVkpPRzFqTkUweVlqRnBjMFpPWm1kNmNqSmFWazQyZEc5eVNuUXRkbFpVV0VKU1IzaE5jM2xaV0V0VE5VWmxiekppUVMxNWJuZ3lTbkJ6VkU1a2RGcERTVEpTZWpFeFRXZGFNRk5sTVdWcVJrbG1Va2hHYWpSZmFtbGhNVmhCTUVoTWN5MXRWR2xoUTBFMFUzaFJkaTAwTlU1UlFXTnRXR0pxUWtOTFJHWnpibTU2VFdGb1UxRkpVakpCVWpGSmMwNXVRVFE0VWxCUlZtVlpUVUUxUjI5a1RtVklUVkpyZGpnMVJrOUJXRE00VmpKVFUwNU1jRlpxZUMxV09HMDVXbWx1YzNGR1UyRmZiMUJVWjJsNlpXMDRaVzF4VG0wME1HRlJWV0l6TUhaRVlWQjBXazF2UkZFeWVrOWxYMVZtU0RWbVpuUkpTRFpQYm1FNFNXMXFhWFpMWlc1UFkwaE5jazlDWXpNd1VreGthMncxVUVGU2JHOVdPVmxFV0c5WFUwOUlVRWRUUkhWa2JVeDNSazB3ZUVKWFZpMHpVRWxGV2w5cE5ESkJObk5oYW14T1JFTjRaa2t5V0RORWVIVm1XbkpXV21zMVVVZDViVVpsTFhkcE9VdFRhbU53UkY5cmRHOU9abE14WW5wV1RWZEdhbVF0T1ZsNExXdFBMVlJwYW5scWVsUkZUbVZRUld4MWNrdDRNMkZaTkZoaVMzaEdYMFJmVldWNVRFMXFaM1ZZYXprMGVUVnZTa05ITWt4V016a3RlVGhzWmxKRmIxcHVTSFJGVlhSVFNqbGlTbmhRWWpWNlpXeGpYMmRMZFMxcVZXSm9WMkZoVjAxVWRqRlFkRmN5T0ZGT1JsZzJNa28xVjBaeFJVOXBkV0pHT0dOeFFYRlBNQklrTmpkaFkyUmlObVV0TURBd01DMHlOR016TFdFNE56UXRNVFF5TWpOaVltRmlZVFV5SUFRJTNE"
    val allItems=scrapper.playlist(ctc)

   /* val allItems=scrapper.channelandplaylsit(url)*/
    if (allItems != null) {
       /* println(allItems.getString("title"))*/
        if (allItems.has("nextContinuation")){
            println(allItems.getString("nextContinuation"))
        }
        println(allItems.getJSONArray("videos"))
    }
    /*val file=File("src/main/resources/json.txt")
    val contents: String = file.readText()
    val jsonResponse= JSONObject(contents)
    println(jsonResponse.getJSONObject("onResponseReceivedActions"))
*/




}