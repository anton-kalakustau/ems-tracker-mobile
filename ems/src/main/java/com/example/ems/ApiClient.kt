package com.example.ems

import sun.net.www.http.HttpClient
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


public class ApiClient {

    public fun fetchData(emsId: String): String {

        try {
            val url =
                URL("https://items.ems.post/api/publicTracking/track?language=EN&itemId=EG688165502JP")
            with(url.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "GET"

                println("URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    println("Response : $response")
                    return response.toString().substring(0, 10)
                }
            }
        } catch (e: Exception) {
            println("Exception : $e")
            return "ERROR" + e.toString()
        }
//EG688165502JP
        // return text.substring(0,10)//"STRING FROM CLIENT: " + emsId

    }
}
