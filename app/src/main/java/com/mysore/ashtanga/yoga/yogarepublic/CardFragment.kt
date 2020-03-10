package com.mysore.ashtanga.yoga.yogarepublic



import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_card.*
import java.util.*



class CardFragment : Fragment() {

    external fun stringFromJNI():String

    private val TAG = "PJ CardFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_card, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        System.loadLibrary("card-lib")
        val apiToken = stringFromJNI()

         progressBar.visibility = View.GONE

        val sharedPref = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        SharedDate.isLogged = sharedPref!!.getBoolean("isLogged", false)
        SharedDate.userName = sharedPref.getString("userName", "") ?: ""
        SharedDate.cardNumber = sharedPref.getString("cardNumber", "") ?: ""
        SharedDate.userID = sharedPref.getString("userID", "") ?: ""

        if (SharedDate.isLogged) {

            cardNumber.visibility = View.VISIBLE
            barcodeImageView.visibility = View.VISIBLE
            logoutButton.visibility = View.VISIBLE
            userName.text = SharedDate.userName
            cardNumber.text = SharedDate.cardNumber
            emailField.visibility = View.GONE
            forgetPasswordButton.visibility = View.GONE
            passwordField.visibility = View.GONE
            button.visibility = View.GONE
            userName.visibility = View.VISIBLE
            membershipsButton.visibility = View.VISIBLE


            if (SharedDate.cardNumber != "-1"){
                val bitMatrix: BitMatrix
                try {
                    bitMatrix = MultiFormatWriter().encode(
                        SharedDate.cardNumber,
                        BarcodeFormat.ITF,
                        300, 80, null
                    )

                    val bEnc = BarcodeEncoder()

                    val bitmap = bEnc.createBitmap(bitMatrix)

                    barcodeImageView.setImageBitmap(bitmap)
                    barcodeImageView.visibility = View.VISIBLE

                    cardNumber.text = SharedDate.cardNumber
                    cardNumber.visibility = View.VISIBLE

                } catch (Illegalargumentexception: IllegalArgumentException) {
                    Log.e(TAG, Illegalargumentexception.localizedMessage ?: "Error generating barcode")
                }
            } else {

                    cardNumber.visibility = View.GONE
                    barcodeImageView.visibility = View.GONE

            }

        } else {

            cardNumber.visibility = View.GONE
            barcodeImageView.visibility = View.GONE
            logoutButton.visibility = View.GONE
            membershipsButton.visibility = View.GONE
            userName.text = ""
            emailField.visibility = View.VISIBLE
            forgetPasswordButton.visibility = View.VISIBLE
            passwordField.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
        }


        passwordField.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) if (
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                Log.e(TAG, "klikniety enter")
                button.performClick()


                return@OnKeyListener true
            }
            false
        })

        membershipsButton.setOnClickListener {

            //1161526 a: 1122334455

            val mainScreen = activity!!.mainAppView.rootView // findViewById<View>(R.id.m)
            val bm = getBitmapFromView(mainScreen)
            SharedDate.backgroudImage = bm
            if (SharedDate.membershipsForRecyclerView.size >= 2) {
                (activity as MainActivity).MyMethod()

            } else {
                Toast.makeText(
                            context,
                            getString(R.string.you_dont_have_memberships),
                            Toast.LENGTH_LONG
                        ).show()
            }


        }

        logoutButton.setOnClickListener {


            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle(getString(R.string.logout_title))

            // Display a message on alert dialog
//            builder.setMessage("Are you want to set the app background color to RED?")


            builder.setPositiveButton(getString(R.string.yes)){_, _ ->


                    barcodeImageView.visibility = View.GONE
                    logoutButton.visibility = View.GONE
                    userName.visibility = View.GONE
                    cardNumber.visibility = View.GONE
                    emailField.visibility = View.VISIBLE
                    forgetPasswordButton.visibility = View.VISIBLE
                    passwordField.visibility = View.VISIBLE
                    button.visibility = View.VISIBLE
                    logoutButton.visibility = View.GONE
                    membershipsButton.visibility = View.GONE

                userName.text = ""
                emailField.text.clear() //.setText("")
                passwordField.text.clear() //.setText("")
                cardNumber.text = ""
                SharedDate.login = ""
                SharedDate.cardNumber = ""
                SharedDate.userName = ""
                sharedPref.edit().remove("login").apply()
                sharedPref.edit().remove("membershipName").apply()
                sharedPref.edit().remove("userID").apply()
                sharedPref.edit().remove("cardNumber").apply()
                sharedPref.edit().remove("userName").apply()
                sharedPref.edit().remove("memberToken").apply()
                sharedPref.edit().remove("refreshToken").apply()
                sharedPref.edit().putBoolean("isLogged", false).apply()


              //TODO teraz w momencie startu apki jest logowanie do firebase; trzeba dodac wylogowywanie
                // i potem dodoac logowanie do firebase po nacisniecu login button
              //  FirebaseAuth.getInstance().signOut()


            }





            // Display a neutral button on alert dialog
            builder.setNeutralButton(getString(R.string.no)){_,_ ->
//                Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()





        }

        forgetPasswordButton.setOnClickListener {

            val uris = Uri.parse("https://yogarepublic-cms.efitness.com.pl/Login/SystemResetPassword?returnurl=https%3A%2F%2Fyogarepublic-cms.efitness.com.pl%2F")
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)


        }

        button.setOnClickListener {


            hideKeyboard()


            val loginName = emailField.text.toString().toLowerCase().trim().takeUnless { it.isEmpty() } //?: usernameError()
            val password = passwordField.text.toString().trim().takeUnless { it.isEmpty() } //?: return passwordError()



            if (loginName.isNullOrEmpty() || password.isNullOrEmpty()) {


                Toast.makeText(activity?.baseContext, getString(R.string.fill_credentials), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            emailField.visibility = View.GONE
            forgetPasswordButton.visibility = View.GONE
            passwordField.visibility = View.GONE
            button.visibility = View.GONE


            login(loginName, password, apiToken){memberToken, id, refreshToken ->


                if (memberToken.startsWith("PJerror", false)) {

                    activity?.runOnUiThread {
                        progressBar.visibility = View.GONE
                        emailField.visibility = View.VISIBLE
                        forgetPasswordButton.visibility = View.VISIBLE
                        passwordField.visibility = View.VISIBLE
                        button.visibility = View.VISIBLE
                        Toast.makeText(activity?.baseContext, getString(R.string.login_error), Toast.LENGTH_LONG).show()

                    }


                } else {

                    SharedDate.login = loginName
                    SharedDate.isLogged = true
                    SharedDate.userID = id
//                    Log.e(TAG, "taki refreshtokne po zalogowani: $refreshToken")
//                    Log.e(TAG, "taki memberToken po zalogowani: $memberToken")
                    sharedPref.edit().putString("login", loginName).apply()
                    sharedPref.edit().putString("password", password).apply()
                    sharedPref.edit().putString("userID", id).apply()
                    sharedPref.edit().putBoolean("isLogged", true).apply()
                    sharedPref.edit().putString("refreshToken", refreshToken).apply()
                    sharedPref.edit().putString("memberToken", memberToken).apply()



                    checkIfExist(id){exists ->

                        if (exists) {
                            setUserLastLogin(id){}
                        }
                    }


                    getMembership(memberToken, apiToken, context!!.getString(R.string.no_membership)) { memberships ->

                        memberships.sortBy { it.expirationDate }
                        memberships.reverse()
                        val memberships2 = memberships
                        memberships2.add(0, Membership("header", Date(), false))
                        SharedDate.membershipsForRecyclerView = memberships2

                    }

                    getUserData(id){cNumber ->

                        SharedDate.cardNumber = cNumber
                        sharedPref.edit().putString("cardNumber", cNumber).apply()

//                        Log.e(TAG, "card number: $cNumber")
                        if (cNumber != "No such document" && cNumber != "failed" && cNumber != "-1") {


                            val bitMatrix: BitMatrix
                            try {
                                bitMatrix = MultiFormatWriter().encode(
                                    cNumber,
                                    BarcodeFormat.ITF,
                                    300, 80, null
                                )

                                val bEnc = BarcodeEncoder()

                                val bitmap = bEnc.createBitmap(bitMatrix)

                                barcodeImageView.setImageBitmap(bitmap)
                                cardNumber.text = cNumber

                                activity?.runOnUiThread {
                                    barcodeImageView.visibility = View.VISIBLE
                                    cardNumber.visibility = View.VISIBLE
                                }


                            } catch (Illegalargumentexception: IllegalArgumentException) {
                                Log.e(TAG, Illegalargumentexception.localizedMessage ?: "Error creating barcode")
                            }
                        } else {

                            activity?.runOnUiThread {
                                barcodeImageView.visibility = View.GONE
                                cardNumber.visibility = View.GONE
                            }


                        }

                    }


                        getPersonalData(memberToken, apiToken) {

//                            Log.e(TAG, "username: $it")
                            sharedPref.edit().putString("userName", it).apply()
                            SharedDate.userName = it

                            activity?.runOnUiThread {

                                button.visibility = View.GONE
                                emailField.visibility = View.GONE
                                forgetPasswordButton.visibility = View.GONE
                                passwordField.visibility = View.GONE
                                userName.text = it
                                userName.visibility = View.VISIBLE
                                logoutButton.visibility = View.VISIBLE
                                membershipsButton.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                cardNumber.text = SharedDate.cardNumber




                            }

                        }


                }

            }
        }


    }


    fun getBitmapFromView(view: View): Bitmap? {
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

}
