package com.mysore.ashtanga.yoga.yogarepublic

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import org.json.JSONArray
import org.json.JSONObject

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object SharedDate{
    var mondayBig = ArrayList<Map<String, Any>>()
    var mondaySmall = ArrayList<Map<String, Any>>()
    var tuesdayBig = ArrayList<Map<String, Any>>()
    var tuesdaySmall = ArrayList<Map<String, Any>>()
    var wednesdayBig = ArrayList<Map<String, Any>>()
    var wednesdaySmall = ArrayList<Map<String, Any>>()
    var thursdayBig = ArrayList<Map<String, Any>>()
    var thursdaySmall = ArrayList<Map<String, Any>>()
    var fridayBig = ArrayList<Map<String, Any>>()
    var fridaySmall = ArrayList<Map<String, Any>>()
    var saturdayBig = ArrayList<Map<String, Any>>()
    var saturdaySmall = ArrayList<Map<String, Any>>()
    var sundayBig = ArrayList<Map<String, Any>>()
    var sundaySmall = ArrayList<Map<String, Any>>()

}

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val TAG = "PJ MainActivity"

//    var Grafik = ArrayList<Grafik>()

    var Timetable = ArrayList<Timetable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val now = DateTime.now()
        val mNowLong = now.withTimeAtStartOfDay().millis
        val mNowplus7Long = now.withTimeAtStartOfDay().plusDays(7).minusSeconds(1).millis

        val mNowHuman = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(mNowLong)
        val mNowplus7Human = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(mNowplus7Long)

        Log.e(TAG, "dzisiaj: $mNowLong i za 7 dni: $mNowplus7Long")
        Log.e(TAG, "dzisiaj: $mNowHuman i za 7 dni: $mNowplus7Human")

        val dOwInt = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val dOwName = SimpleDateFormat("EEEE").format(now.millis)




        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val startDate: String = simpleDateFormat.format(Date())

        Log.e(TAG, "data: $startDate")


        Fuel.get("https://api-frontend2.efitness.com.pl/api/clubs/324/schedules/classes?dateFrom=${mNowHuman}&dateTo=${mNowplus7Human}")
            .header("Accept" to "application/json")
            .header("api-access-token" to "bih/AiXX0k2mqZGz44y+Ag==")
//            .header("member-token" to "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMTYxNTI2Iiwic3ViIjoicGpvYmtpZXdpY3pAZ21haWwuY29tIiwianRpIjoiNTlhMWEyNGYtZTA0OS00ZmUwLWJkZWEtNDdhMjZkNjVmNjZkIiwiaWF0IjoxNTgwMzMwNjkxLCJpZCI6IjExNjE1MjYiLCJuYmYiOjE1ODAzMzA2OTAsImV4cCI6MTU4MDMzNzg5MCwiaXNzIjoiYXBpRnJvbnRlbmQiLCJhdWQiOiJodHRwczovL2FwaS1mcm9udGVuZDIuZWZpdG5lc3MuY29tLnBsIn0.mwEwqQBlIaYp57313VsvsBWYrmDVuBwhuiN1ZjoVfdmcsgXBk8IgtNm_pu2KL1j7DOXeyIZYIbvTHwoXUqb5Xcwk5blVg3LgP6hPtE2CiCTqeQu3AxkISUCDYXvdkhQGEoG_hVg-gJ3yTGdJFZdQ0i2hE_sGI2W97-PHNl8oqWgOn13QYN7OWGQ0rlICr0MJIlpoxjD0Cw97O2h1kV32f1KPSP-uhlEYNTZQEQ-79c-GAxBWeTYwSqYWx4PqFxbH5sodCpWghvAWeyqrxvFdDADPdNNPQpkYXHI2AOeFSFATBVQ3VZ0z__3bBZtWx_W7SC22mSZOS-jwzA6kbX4G8w")
            .also { println(it) }
            .responseString { _, reponse, result ->


                val (data, error) = result
                Log.e(TAG, "pobrany grafik - ${error}")
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


                        SharedDate.mondaySmall = monSmall
                        SharedDate.mondayBig = monBig
                        SharedDate.tuesdaySmall = tueSmall
                        SharedDate.tuesdayBig = tueBig
                        SharedDate.wednesdaySmall = wedSmall
                        SharedDate.wednesdayBig = wedBig
                        SharedDate.thursdaySmall = thuSmall
                        SharedDate.thursdayBig = thuBig
                        SharedDate.fridaySmall = friSmall
                        SharedDate.fridayBig = friBig
                        SharedDate.saturdaySmall = satSmall
                        SharedDate.saturdayBig = satBig
                        SharedDate.sundaySmall = sunSmall
                        SharedDate.sundayBig = sunBig

                        monBig.forEach {
                            Log.e(TAG, "bigforeach: ${it.toString()}")
                        }

                        monSmall.forEach {
                            Log.e(TAG, "Smallforeach: ${it.toString()}")
                        }

                    }

                }





            }








        bottom_app_bar.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.app_bar_card -> {
//                    Toast.makeText(this, "Card item click", Toast.LENGTH_SHORT).show()
//
                    val fragment = CardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    true
                }
                R.id.app_bar_timetable -> {
                    val fragment = TimetableFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    true
                }

                else -> false
            }
        }


        if (savedInstanceState == null) {
            // 2

//            Log.e(TAG, "savedinstancestate null")

            val fragment = CardFragment()

            supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .add(R.id.fragment, fragment, "dogList")
                // 5
                .commit()
        }


        auth = FirebaseAuth.getInstance()

        signInAnonymously()

//        val userID = 2754
//        userNumber.text = userID.toString()
//        val bitMatrix: BitMatrix
//        try {
//            bitMatrix = MultiFormatWriter().encode(
//                userID.toString(),
//                BarcodeFormat.ITF,
//                300, 80, null
//            )
//
//            val bEnc = BarcodeEncoder()
//
//            val bitmap = bEnc.createBitmap(bitMatrix)
//
//            barcodeImageView.setImageBitmap(bitmap)
//
//        } catch (Illegalargumentexception: IllegalArgumentException) {
//            return
//        }


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
//                Log.e(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })




    }

    private fun signInAnonymously() {
//        progressBar.isVisible = true
        // [START signin_anonymously]
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.e(TAG, "signInAnonymously:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }

                // [START_EXCLUDE]
//                progressBar.isVisible = false
                // [END_EXCLUDE]
            }
        // [END signin_anonymously]
    }

    private fun signOut() {
        auth.signOut()

    }






}


 fun selectColor(nameClass: String) : Int {

    when (nameClass) {
        "Regeneracja" ->{
            return R.color.regeneracjacolor
        }
        "Ashtanga I" ->{
            return R.color.ashtanga1color
        }
        "Ashtanga II" ->{
            return R.color.ashtanga2color
        }
        "Ashtanga III" ->{
            return R.color.ashtanga3color
        }
        "Mysore" ->{
            return R.color.mysorecolor
        }
        "Początkujące" ->{
            return R.color.poczatkujacacolor
        }
        "Kurs jogi dla Początkujących" ->{
            return R.color.kurscolor
        }
        "Lunch Time Yoga" ->{
            return R.color.lunchcolor
        }
        "Szczęśliwy kręgosłup" ->{
            return R.color.kregoslupcolor
        }
        "Joga w Ciąży" ->{
            return R.color.ciazacolor
        }
        else -> {
            return R.color.kregoslupcolor
        }
    }

}
