package com.mysore.ashtanga.yoga.yogarepublic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTime
import org.json.JSONArray
import org.json.JSONObject

import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SplashActivity : AppCompatActivity() {
    private val TAG = "PJ splash"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPref = this@SplashActivity.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

        val sunBig = sharedPref?.getString("sunBig", "")
        if (!sunBig.isNullOrEmpty()) {
            readTimetable(this@SplashActivity)
        }


        val now = DateTime.now()
        val mNowLong = now.withTimeAtStartOfDay().millis
        val mNowplus7Long = now.withTimeAtStartOfDay().plusDays(6).millis //.minusSeconds(1).millis

        val mNowHuman = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(mNowLong)
        val mNowplus7Human = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(mNowplus7Long)



        Log.e(TAG, "now: $mNowHuman, plus7: $mNowplus7Human")

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val startDate: String = simpleDateFormat.format(Date())


//        Log.e(TAG, "data: $startDate - zaraz odpytamy o grafik")


        Fuel.get("https://api-frontend2.efitness.com.pl/api/clubs/324/schedules/classes?dateFrom=${mNowHuman}&dateTo=${mNowplus7Human}")
            .header("Accept" to "application/json")
            .header("api-access-token" to getString(R.string.api_access_token))
//            .header("member-token" to "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMTYxNTI2Iiwic3ViIjoicGpvYmtpZXdpY3pAZ21haWwuY29tIiwianRpIjoiNTlhMWEyNGYtZTA0OS00ZmUwLWJkZWEtNDdhMjZkNjVmNjZkIiwiaWF0IjoxNTgwMzMwNjkxLCJpZCI6IjExNjE1MjYiLCJuYmYiOjE1ODAzMzA2OTAsImV4cCI6MTU4MDMzNzg5MCwiaXNzIjoiYXBpRnJvbnRlbmQiLCJhdWQiOiJodHRwczovL2FwaS1mcm9udGVuZDIuZWZpdG5lc3MuY29tLnBsIn0.mwEwqQBlIaYp57313VsvsBWYrmDVuBwhuiN1ZjoVfdmcsgXBk8IgtNm_pu2KL1j7DOXeyIZYIbvTHwoXUqb5Xcwk5blVg3LgP6hPtE2CiCTqeQu3AxkISUCDYXvdkhQGEoG_hVg-gJ3yTGdJFZdQ0i2hE_sGI2W97-PHNl8oqWgOn13QYN7OWGQ0rlICr0MJIlpoxjD0Cw97O2h1kV32f1KPSP-uhlEYNTZQEQ-79c-GAxBWeTYwSqYWx4PqFxbH5sodCpWghvAWeyqrxvFdDADPdNNPQpkYXHI2AOeFSFATBVQ3VZ0z__3bBZtWx_W7SC22mSZOS-jwzA6kbX4G8w")
            .also { println(it) }
            .responseString { _, reponse, result ->


                val (data, error) = result

//                Log.e(TAG, "pobrany grafik - error=${error}")
                var obj = JSONObject(data)

                val wynikArray = obj.getJSONArray("results")

                val ileZajec = wynikArray.length()


                var monBig = ArrayList<Map<String, Any>>()
                var monSmall = ArrayList<Map<String, Any>>()
                var tueBig = ArrayList<Map<String, Any>>()
                var tueSmall = ArrayList<Map<String, Any>>()
                var wedBig = ArrayList<Map<String, Any>>()
                var wedSmall = ArrayList<Map<String, Any>>()
                var thuBig = ArrayList<Map<String, Any>>()
                var thuSmall = ArrayList<Map<String, Any>>()
                var friBig = ArrayList<Map<String, Any>>()
                var friSmall = ArrayList<Map<String, Any>>()
                var satBig = ArrayList<Map<String, Any>>()
                var satSmall = ArrayList<Map<String, Any>>()
                var sunBig = ArrayList<Map<String, Any>>()
                var sunSmall = ArrayList<Map<String, Any>>()

//                val obj = JSONObject()
                for (z in 0..ileZajec-1){

                    val zajeciaTemp = wynikArray[z] as JSONObject


                    var arrayTemp = JSONArray()

                    val startD = zajeciaTemp.getString("startDate")
                    val endD = zajeciaTemp.getString("endDate")
                    val name = zajeciaTemp.getString("name")
                    val nameIns = zajeciaTemp.getString("instructorName")
                    val duration = zajeciaTemp.get("duration")
                    val roomName = zajeciaTemp.get("roomName").toString()
                    val color = zajeciaTemp.get("backgroundColor").toString()
                    val isReplacement = zajeciaTemp.get("isReplacement")
                    val participantsLimit = zajeciaTemp.get("participantsLimit")
//                    val x = color.split('#')[1].toInt()


                    val startDataLong = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(startD).getTime()
                    val endDatalong = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(endD).getTime()
                    val dayName = SimpleDateFormat("EEEE", Locale.US).format(startDataLong)

                    var oneClass = mapOf<String, Any>()



                    when (dayName) {
                        "Monday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                monBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                monSmall.add(oneClass)

                            }
                        }
                        "Tuesday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                tueBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                tueSmall.add(oneClass)

                            }
                        }
                        "Wednesday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                wedBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                wedSmall.add(oneClass)

                            }
                        }
                        "Thursday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                thuBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                thuSmall.add(oneClass)

                            }
                        }
                        "Friday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
//                                Log.e(TAG, "zajecia: ${zajeciaTemp.toString()}")
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                friBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                friSmall.add(oneClass)

                            }
                        }
                        "Saturday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                satBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                satSmall.add(oneClass)

                            }
                        }
                        "Sunday" -> {
                            if (roomName == "Duża Sala" || roomName == "null") {
                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                sunBig.add(oneClass)
                            } else {

                                oneClass = mapOf("start" to startDataLong, "end" to endDatalong, "name" to name,
                                    "teacher" to nameIns, "roomName" to roomName, "color" to color )
                                sunSmall.add(oneClass)

                            }
                        }
                        else -> { // Note the block
//                            Log.e(TAG, "else dayname: $dayName")
                        }



                    }

//                    val sortedMonSmall =



                    if (z == ileZajec-1) {

                        monBig.sortWith(compareBy({it.get("start").toString()}))
                        monSmall.sortWith(compareBy({it.get("start").toString()}))
                        tueBig.sortWith(compareBy({it.get("start").toString()}))
                        tueSmall.sortWith(compareBy({it.get("start").toString()}))
                        wedBig.sortWith(compareBy({it.get("start").toString()}))
                        wedSmall.sortWith(compareBy({it.get("start").toString()}))
                        thuBig.sortWith(compareBy({it.get("start").toString()}))
                        thuSmall.sortWith(compareBy({it.get("start").toString()}))
                        friBig.sortWith(compareBy({it.get("start").toString()}))
                        friSmall.sortWith(compareBy({it.get("start").toString()}))
                        satBig.sortWith(compareBy({it.get("start").toString()}))
                        satSmall.sortWith(compareBy({it.get("start").toString()}))
                        sunBig.sortWith(compareBy({it.get("start").toString()}))
                        sunSmall.sortWith(compareBy({it.get("start").toString()}))


//                        SharedDate.mondaySmall = monSmall
//                        SharedDate.mondayBig = monBig
//                        SharedDate.tuesdaySmall = tueSmall
//                        SharedDate.tuesdayBig = tueBig
//                        SharedDate.wednesdaySmall = wedSmall
//                        SharedDate.wednesdayBig = wedBig
//                        SharedDate.thursdaySmall = thuSmall
//                        SharedDate.thursdayBig = thuBig
//                        SharedDate.fridaySmall = friSmall
//                        SharedDate.fridayBig = friBig
//                        SharedDate.saturdaySmall = satSmall
//                        SharedDate.saturdayBig = satBig
//                        SharedDate.sundaySmall = sunSmall
//                        SharedDate.sundayBig = sunBig


//                        val type: Type = object : TypeToken<ArrayList<Map<String, Any>>>() {}.getType()


                        val editor = sharedPref.edit()
                        val gson = Gson()

                        val jsonMonSmall = gson.toJson(monSmall)
                        val jsonMonBig = gson.toJson(monBig)
                        val jsonTueSmall = gson.toJson(tueSmall)
                        val jsonTueBig = gson.toJson(tueBig)
                        val jsonWedSmall = gson.toJson(wedSmall)
                        val jsonWedBig = gson.toJson(wedBig)
                        val jsonThuSmall = gson.toJson(thuSmall)
                        val jsonThuBig = gson.toJson(thuBig)
                        val jsonFriSmall = gson.toJson(friSmall)
                        val jsonFriBig = gson.toJson(friBig)
                        val jsonSatSmall = gson.toJson(satSmall)
                        val jsonSatBig = gson.toJson(satBig)
                        val jsonSunSmall = gson.toJson(sunSmall)
                        val jsonSunBig = gson.toJson(sunBig)

                        editor.putString("monSmall", jsonMonSmall)
                        editor.putString("monBig", jsonMonBig)
                        editor.putString("tueSmall", jsonTueSmall)
                        editor.putString("tueBig", jsonTueBig)
                        editor.putString("wedSmall", jsonWedSmall)
                        editor.putString("wedBig", jsonWedBig)
                        editor.putString("thuSmall", jsonThuSmall)
                        editor.putString("thuBig", jsonThuBig)
                        editor.putString("friSmall", jsonFriSmall)
                        editor.putString("friBig", jsonFriBig)
                        editor.putString("satSmall", jsonSatSmall)
                        editor.putString("satBig", jsonSatBig)
                        editor.putString("sunSmall", jsonSunSmall)
                        editor.putString("sunBig", jsonSunBig)
                        editor.apply()




                        readTimetable(this@SplashActivity)

                        Log.e(TAG, "jest zbudowany grafik")

//                        tueBig.forEach {
//                            Log.e(TAG, "tueBigForeach: ${it.toString()}")
//                        }
//
//                        monSmall.forEach {
//                            Log.e(TAG, "Smallforeach: ${it.toString()}")
//                        }

                    }

                }





            }








        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
