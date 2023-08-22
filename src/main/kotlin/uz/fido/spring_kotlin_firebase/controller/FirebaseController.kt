package uz.fido.spring_kotlin_firebase.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import uz.fido.spring_kotlin_firebase.entity.DataLink
import uz.fido.spring_kotlin_firebase.entity.FirebasePageLink
import com.google.gson.Gson
import org.apache.http.client.utils.URIBuilder
import org.json.JSONException
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.fido.spring_kotlin_firebase.entity.LongLinkData
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

@RestController
@RequestMapping("/api")
class FirebaseController{
    fun errorCatch(msg: String): ResponseEntity<*> {
        val errorResult: MutableMap<String, Any> = java.util.HashMap()
        errorResult["code"] = 1
        errorResult["msg"] = msg
        return ResponseEntity(errorResult.toString(), HttpStatus.OK)
    }
   @PostMapping(path = ["/page_link"])
    fun  firebasePageLink(@RequestBody request:String):ResponseEntity<*>{
       var objectMapper = ObjectMapper()
       var jsonObject = JSONObject(request)


       var jsonString: String
       var url_txt: String
       var result: String
       var firebasePageLink: FirebasePageLink

       url_txt = if (jsonObject.getString("url_link") != null) {
           jsonObject.getString("url_link")
       } else {
           return errorCatch("url not given")
       }

       if (jsonObject.getJSONObject("request_body") != null) {
           firebasePageLink = Gson().fromJson(jsonObject.getJSONObject("request_body").toString(), FirebasePageLink::class.java)
       } else {
           return errorCatch("request_body not given")
       }

       jsonString = try {
           objectMapper.writeValueAsString(firebasePageLink)
       } catch (e: JsonProcessingException) {
           return errorCatch(e.originalMessage)
       }

       result = sendingOutputRequest(url_txt, jsonString)
       val jsonObject1: JSONObject
       return try {
           jsonObject1= JSONObject(result)
           jsonString = jsonObject1.getString("shortLink")
           val mapResult: MutableMap<String, Any> = HashMap()
           mapResult["shortLink"] = jsonString
           mapResult["code"] = 0
           mapResult["msg"] = ""
           ResponseEntity(Gson().toJson(mapResult), HttpStatus.OK)
       }catch (e:JSONException){
           e.printStackTrace()
           errorCatch(e.localizedMessage)
       }

    }

    private fun sendingOutputRequest(url_txt: String, request: String): String {
        val serverURL: String = url_txt
        val url = URL(serverURL)
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 180
        connection.doOutput = true
        val postData: ByteArray = request.toByteArray(StandardCharsets.UTF_8)
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-Type", "application/json")

        try {
            val outputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postData)
            outputStream.flush()
        } catch (exception: Exception) {
            return "Exception while outputstreaming  $exception.message"
        }

        if (connection.responseCode == HttpURLConnection.HTTP_OK){
            try {
                val inputStream: DataInputStream = DataInputStream(connection.inputStream)
                val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuffer()
                var responseLine: String? = null
                while (reader.readLine().also { responseLine = it } != null) {
                    sb.append(responseLine!!.trim { it <= ' ' })
                }
             //   logger.info(sb.toString())

                return sb.toString()

            } catch (exception: Exception) {
                return "Exception while push the notification  $exception.message"
            }
    } else {
        return connection.responseMessage
    }
    }


    @PostMapping(path = ["/page_long"])
    private fun firebasePageLongList(request:String): ResponseEntity<*> {
        var objectMapper = ObjectMapper()
        var jsonObject = JSONObject(request)
        var jsonString: String
        var firebasePageLink: LongLinkData = LongLinkData("")
        var dataLink: DataLink
        var url_txt: String = if (jsonObject.getString("url_link") != null) {
            jsonObject.getString("url_link")
        } else {
            return errorCatch("url not given")
        }
        if (jsonObject.getJSONObject("request_body") != null) {
            dataLink = Gson().fromJson(jsonObject.getJSONObject("request_body").toString(), DataLink::class.java)
        } else {
            return errorCatch("request_body not given")
        }

        try {
            var uriBuilder = URIBuilder(dataLink.domainUri)
            uriBuilder.addParameter("apn", dataLink.apn)
            uriBuilder.addParameter("isi", dataLink.isi)
            uriBuilder.addParameter("ibi", dataLink.ibi)
            uriBuilder.addParameter("st", dataLink.st)
            uriBuilder.addParameter("sd", dataLink.sd)
            uriBuilder.addParameter("si", dataLink.si)
            uriBuilder.addParameter("link", dataLink.link)
            firebasePageLink.longDynamicLink = uriBuilder.build().toString()
        } catch (e: Exception) {
           // logger.error(e.message)
        }

        jsonString = try {
            objectMapper.writeValueAsString(firebasePageLink)
        } catch (e: JsonProcessingException) {
            return errorCatch(e.originalMessage)
        }
        var result: String = sendingOutputRequest(url_txt, jsonString)
        val jsonObject1: JSONObject
        return try {
            jsonObject1 = JSONObject(result)
            jsonString = jsonObject1.getString("shortLink")
            val mapResult: MutableMap<String, Any> = HashMap()
            mapResult["shortLink"] = jsonString
            mapResult["code"] = 0
            mapResult["msg"] = ""
            ResponseEntity(Gson().toJson(mapResult), HttpStatus.OK)
        } catch (e: JSONException) {
            e.printStackTrace()
            errorCatch(e.localizedMessage)
        }
    }
}