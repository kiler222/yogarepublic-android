package com.mysore.ashtanga.yoga.yogarepublic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

import java.lang.reflect.Type
import kotlin.collections.ArrayList

object SharedDate{
    var mondayBig = ArrayList<Map<String, Any>>()
    var mondaySmall = ArrayList<Map<String, Any>>()
    var mondaySmallTest = ArrayList<Map<String, Any>>()
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
    var mLongerHeaders = ArrayList<String>()
    var cardNumber = ""
    val db = FirebaseFirestore.getInstance()
    var isLogged = false
    var login = ""
    var userName = ""
    var membershipName = ""

}



class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    val TAG = "PJ MainActivity"

//    var Grafik = ArrayList<Grafik>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        SharedDate.mLongerHeaders.add(getString(R.string.small_room))
        SharedDate.mLongerHeaders.add(getString(R.string.big_room))



//        var tabYR = yr.split("NL")
//
//        tabYR.forEach{
//            val userData= it.split(",")
//            val email = userData[0]
//            val cardNumber = userData[1]
//            Log.e(TAG, "rekordy: $email i karta: $cardNumber")
//        }




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

//                    Toast.makeText(this, "Card item click", Toast.LENGTH_SHORT).show()

//                    readTimetable(this@MainActivity)

                    Log.e(TAG, "sprawdzenie zaraz mondayBig")

                    val sharedPref = this@MainActivity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)


                    val sunBig = sharedPref?.getString("sunBig", "")
                    if (sunBig.isNullOrEmpty()){

                        Toast.makeText(this, getString(R.string.loading_data), Toast.LENGTH_SHORT).show()
                        false

                    } else {
                        val fragment = TimetableFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                        true
                    }


                }

                else -> false
            }
        }


        if (SharedDate.isLogged == true) {


            Log.e(TAG, "isLogged: ${SharedDate.isLogged}")
            val fragment = TimetableFragment()

            supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .add(R.id.fragment, fragment, "")
                // 5
                .commit()


        } else {
            val fragment = CardFragment()

            supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .add(R.id.fragment, fragment, "")
                // 5
                .commit()


        }


        auth = FirebaseAuth.getInstance()

        signInAnonymously()




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


                    val sharedPref = this@MainActivity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

                    if (sharedPref!!.getBoolean("isLogged", false)) {

                        val login = sharedPref.getString("login", "")!!
                        checkIfExist(login){exists ->

                            if (exists) {
                                setUserLastLogin(login){}
                            }
                        }


                    }


//                    addCardNumbers()


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





fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun readTimetable(context: Context) {


    val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: return

    val gson = Gson()
    val type: Type = object : com.google.common.reflect.TypeToken<java.util.ArrayList<Map<String, Any>>>() {}.getType()


    val monSmall = sharedPref?.getString("monSmall", "")
    SharedDate.mondaySmall = gson.fromJson(monSmall, type)

    val monBig = sharedPref?.getString("monBig", "")
    SharedDate.mondayBig = gson.fromJson(monBig, type)

    val tueSmall = sharedPref?.getString("tueSmall", "")
    SharedDate.tuesdaySmall= gson.fromJson(tueSmall, type)

    val tueBig = sharedPref?.getString("tueBig", "")
    SharedDate.tuesdayBig = gson.fromJson(tueBig, type)

    val wedSmall = sharedPref?.getString("wedSmall", "")
    SharedDate.wednesdaySmall= gson.fromJson(wedSmall, type)

    val wedBig = sharedPref?.getString("wedBig", "")
    SharedDate.wednesdayBig = gson.fromJson(wedBig, type)

    val thuSmall = sharedPref?.getString("thuSmall", "")
    SharedDate.thursdaySmall= gson.fromJson(thuSmall, type)

    val thuBig = sharedPref?.getString("thuBig", "")
    SharedDate.thursdayBig = gson.fromJson(thuBig, type)

    val friSmall = sharedPref?.getString("friSmall", "")
    SharedDate.fridaySmall= gson.fromJson(friSmall, type)

    val friBig = sharedPref?.getString("friBig", "")
    SharedDate.fridayBig = gson.fromJson(friBig, type)

    val satSmall = sharedPref?.getString("satSmall", "")
    SharedDate.saturdaySmall= gson.fromJson(satSmall, type)

    val satBig = sharedPref?.getString("satBig", "")
    SharedDate.saturdayBig = gson.fromJson(satBig, type)

    val sunSmall = sharedPref?.getString("sunSmall", "")
    SharedDate.sundaySmall= gson.fromJson(sunSmall, type)

    val sunBig = sharedPref?.getString("sunBig", "")
    SharedDate.sundayBig = gson.fromJson(sunBig, type)

    Log.e("PJ MainActivity", "koniec readTimetable")

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
