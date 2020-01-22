package com.mysore.ashtanga.yoga.yogarepublic

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val TAG = "PJ MainActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)


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

            Log.e(TAG, "savedinstancestate null")

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
                Log.e(TAG, msg)
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
                    Log.e(TAG, "signInAnonymously:success")
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
