package com.mysore.ashtanga.yoga.yogarepublic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.Fuel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    var mLongerHeaders = ArrayList<String>()
    var clubCardNumber = String
    val db = FirebaseFirestore.getInstance()


}



class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    val TAG = "PJ MainActivity"

//    var Grafik = ArrayList<Grafik>()

    var Timetable = ArrayList<Timetable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        SharedDate.mLongerHeaders.add(getString(R.string.small_room))
        SharedDate.mLongerHeaders.add(getString(R.string.big_room))












        bottom_app_bar.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.app_bar_card -> {
                    Toast.makeText(this, "Card item click", Toast.LENGTH_SHORT).show()
//
                    val fragment = CardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    true
                }
                R.id.app_bar_timetable -> {

                    Toast.makeText(this, "Card item click", Toast.LENGTH_SHORT).show()

                    if (SharedDate.mondayBig.isNullOrEmpty()){

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
