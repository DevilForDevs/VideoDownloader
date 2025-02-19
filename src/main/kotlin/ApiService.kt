import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject
fun logout(apiUrl: String, token: String) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(apiUrl)
          // Expect JSON response
        .build()

    // Execute the request asynchronously
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Logout failed: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                println("Logout successful: ${response.body?.string()}")
            } else {
                println(response.body?.string())
                println("Logout failed: ${response.code} ${response.message}")
            }
            response.close()
        }
    })
}
fun getr(){
    val client = OkHttpClient()
    val url = "http://127.0.0.1:8000/getItems"
    val request = Request.Builder()
        .url(url)
        .build()
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        println(response.body?.string())
    }
}
/*1|YzAsJqHUfDdOd8QQnXNG64jcfgV8tXBUkhqKDyCse92fa5c6*/
fun y2mate(){
    val url = "https://y2mate.lol/convert/?videoId=1McKEMB5Qjo"

    // Create OkHttpClient instance
    val client = OkHttpClient()

    // Create the RequestBody (if there are parameters, include them in the body)
    val requestBody = FormBody.Builder()
        // Add form fields as needed, example:
        // .add("key", "value")
        .build()

    // Build the request
    val request = Request.Builder()
        .url(url)
        .build()

    // Execute the request
    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            println("Response: ${response.body?.string()}")
        } else {
            println("Request failed: ${response.code}")
        }
    }
}
fun addItem() {
    val client = OkHttpClient()

    val jsonObject = JSONObject().apply {
        put("name", "Sample Item")
        put("description", "This is a test item.")
        put("price", 19.99)
        put("stock_quantity", 10)
        put("image_url", "http://example.com/image.jpg")
        put("unit", "pcs")
    }

    val requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

    val request = Request.Builder()
        .url("http://127.0.0.1:8000/api/addItem") // Replace with your actual API URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Request Failed: ${'$'}{e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Unexpected code ${'$'}response")
                } else {
                    println(response.body?.string())
                }
            }
        }
    })
}
fun updateOrderStatus(orderId: Int, deliveryStatus: String) {
    val client = OkHttpClient()

    val jsonObject = JSONObject().apply {
        put("order_id", orderId)
        put("delivery_status", deliveryStatus)
    }

    val requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

    val request = Request.Builder()
        .url("http://127.0.0.1:8000/api/upDateOrder")
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Request Failed: ${'$'}{e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Unexpected code ${'$'}response")
                } else {
                    println(response.body?.string())
                }
            }
        }
    })
}

fun makeRequest():String?{
    val client=OkHttpClient()
    /*val mj=JSONObject()
    mj.put("username","ranjankumar")*/
    val json = JSONObject().apply {
        put("email", "ranjanpanpura@gmail.com")
        put("name","ranjankumar")
        put("password", "docviga20ts")
    }
    val postRequest = Request.Builder()
        .url("https://jalebi.shop/api/signup")  
        .post(json.toString().toRequestBody("application/json".toMediaTypeOrNull()))
        .build()

    val postResponse = client.newCall(postRequest).execute()

    return if (postResponse.isSuccessful) {
        val responseString=postResponse.body?.string()
        println(responseString)
        responseString
    } else {
        val responseString=postResponse.body?.string()
        println(responseString)
        responseString

    }


}
fun getOrders(orderId: Int? = null) {
    val client = OkHttpClient()

    val url = if (orderId != null) {
        "http://127.0.0.1:8000/api/goa?order_id=$orderId"
    } else {
        "http://127.0.0.1:8000/api/goa"
    }

    val request = Request.Builder()
        .url(url)
        .get()
        .addHeader("Accept", "application/json")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Request Failed: ${'$'}{e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Unexpected code ${'$'}response")
                } else {
                    println(response.body?.string())
                }
            }
        }
    })
}
fun updateItem(){
    val client = OkHttpClient()

// Define the URL of your Laravel route for updating the item
    val url = "http://127.0.0.1:8000/api/uia"  // Replace with your actual URL

// Create the JSON object to send in the request body
    val jsonObject = JSONObject().apply {
        put("id", 1)
        put("name", "Updated Item Name")
        put("description", "Updated description")
        put("price", 2000)
        put("stock_quantity", 100)
        put("image_url", "https://example.com/image.jpg")
        put("unit", "kg")
    }

// Create a RequestBody with JSON content
    val body = RequestBody.create(
        "application/json; charset=utf-8".toMediaType(),
        jsonObject.toString()
    )

// Build the request
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

// Execute the request asynchronously
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()  // Handle failure
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                println("Item updated successfully: ${response.body?.string()}")
            } else {
                println("Failed to update item: ${response.body?.string()}")
            }
        }
    })
}
fun getItem(itemId: Int? = null) {
    val client = OkHttpClient()

    val url = if (itemId != null) {
        "http://127.0.0.1:8000/api/gia?id=$itemId"
    } else {
        "http://127.0.0.1:8000/api/gia"
    }

    val request = Request.Builder()
        .url(url)
        .get()
        .addHeader("Accept", "application/json")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Request Failed: ${'$'}{e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Unexpected code ${'$'}response")
                } else {
                    println(response.body?.string())
                }
            }
        }
    })
}
fun  removeItem(itemId:Int){
    val client = OkHttpClient()

    val url="http://127.0.0.1:8000/api/ria?id=$itemId"

    val request = Request.Builder()
        .url(url)
        .get()
        .addHeader("Accept", "application/json")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Request Failed: ${'$'}{e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Unexpected code ${'$'}response")
                } else {
                    println(response.body?.string())
                }
            }
        }
    })
}
/*.addHeader("Authorization", "Bearer $7|flHZUC0n47N0d0rKIQh4u2dAAh8rclsKbcJVyBC61cbf64bd")*/
/*{"username":"ranjankumar","user":{"name":"ranjankumar","email":"ranjanpanpura@gmail.com","username":"ranjankumar","updated_at":"2025-01-21T04:41:17.000000Z","created_at":"2025-01-21T04:41:17.000000Z","id":3},"token":"7|flHZUC0n47N0d0rKIQh4u2dAAh8rclsKbcJVyBC61cbf64bd"}*/