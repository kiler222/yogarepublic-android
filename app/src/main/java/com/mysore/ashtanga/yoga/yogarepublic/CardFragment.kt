package com.mysore.ashtanga.yoga.yogarepublic


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.DisplayMetrics
import android.util.Log
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


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {

    external fun stringFromJNI():String

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val TAG = "PJ CardFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

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
        SharedDate.membershipName = sharedPref.getString("membershipName", "") ?: ""
        SharedDate.userID = sharedPref.getString("userID", "") ?: ""

        Log.e(TAG, "jaki jest shared.islogged: ${SharedDate.isLogged}, zalogowany jest: ${SharedDate.userName}, karta: ${SharedDate.cardNumber}")
        if (SharedDate.isLogged) {

            cardNumber.visibility = View.VISIBLE
            barcodeImageView.visibility = View.VISIBLE
            logoutButton.visibility = View.VISIBLE
            userName.text = SharedDate.userName
            membershipNameField.text= SharedDate.membershipName
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
                    Log.e(TAG, Illegalargumentexception.localizedMessage)
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
            membershipNameField.text = ""
            emailField.visibility = View.VISIBLE
            forgetPasswordButton.visibility = View.VISIBLE
            passwordField.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
        }



        membershipsButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val refreshToken = sharedPref.getString("refreshToken", "") ?: ""
            val memberToken = sharedPref.getString("memberToken", "") ?: ""


            val mainScreen = activity!!.mainAppView.rootView // findViewById<View>(R.id.m)
            val bm = getBitmapFromView(mainScreen)
//            var bStream  =  ByteArrayOutputStream()
//            bm?.compress(Bitmap.CompressFormat.PNG, 50, bStream)
//            val byteArray = bStream.toByteArray()
            SharedDate.backgroudImage = bm

//            Log.e(TAG, "bm width: ${bm?.width} i height: ${bm?.height}")
            refreshAccessToken(refreshToken, memberToken, apiToken) { newMemberToken, _, newRefreshToken ->


                if (newMemberToken.startsWith("PJerror", false)) {

                        Toast.makeText(
                            context,
                            getString(R.string.login_error),
                            Toast.LENGTH_LONG
                        ).show()
                    progressBar.visibility = View.GONE

                } else {

                    getMembership(newMemberToken, apiToken, context!!){ memberships ->

                        memberships.sortBy { it.expirationDate }
                        memberships.reverse()



                        var memberships2 = memberships
//                        memberships2.removeAt(4)
//                        memberships2.removeAt(3)
//                        memberships2.removeAt(2)
//                        memberships2.removeAt(1)
//                        memberships2.add(memberships[1])
//                        memberships2.add(memberships[5])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[2])
//                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[1])
                        memberships2.add(memberships[0])
                        memberships2.add(memberships[0])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[2])
//                        memberships2.add(memberships[1])
//                        memberships2.add(memberships[4])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[0])
//                        memberships2.add(memberships[4])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[5])
//                        memberships2.add(memberships[0])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[5])
//                        memberships2.add(memberships[3])
//                        memberships2.add(memberships[0])
//                        memberships2.add(memberships[0])

                        memberships2.add(0, Membership("header", Date(), false))


                        SharedDate.membershipsForRecyclerView = memberships2

                        val intent = Intent(activity, MembershipsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

//                        intent.putExtra("backgroundImage", byteArray )
                        startActivity(intent)
                        progressBar.visibility = View.GONE


                    }
                    sharedPref.edit().putString("refreshToken", newRefreshToken).apply()
                    sharedPref.edit().putString("memberToken", newMemberToken).apply()
                }

            }










        }

        logoutButton.setOnClickListener {


            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle(getString(R.string.logout_title))

            // Display a message on alert dialog
//            builder.setMessage("Are you want to set the app background color to RED?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton(getString(R.string.yes)){_, _ ->

                barcodeImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                userName.visibility = View.GONE
                cardNumber.visibility = View.GONE
                userName.text = ""
                membershipNameField.text = ""
                emailField.setText("")
                passwordField.setText("")
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
                emailField.visibility = View.VISIBLE
                forgetPasswordButton.visibility = View.VISIBLE
                passwordField.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
                logoutButton.visibility = View.GONE
                membershipsButton.visibility = View.GONE

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


            emailField.text.clear()
            emailField.text = Editable.Factory.getInstance().newEditable("pjobkiewicz@gmail.com")
            passwordField.text.clear()
            passwordField.text = Editable.Factory.getInstance().newEditable("xiubofwo")

            var loginName = emailField.text.toString().toLowerCase().trim().takeUnless { it.isNullOrEmpty() } //?: usernameError()
            var password = passwordField.text.toString().trim().takeUnless { it.isNullOrEmpty() } //?: return passwordError()



            if (loginName.isNullOrEmpty() || password.isNullOrEmpty()) {


                Toast.makeText(activity?.baseContext, getString(R.string.fill_credentials), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }





            progressBar.visibility = View.VISIBLE
            emailField.visibility = View.GONE
            forgetPasswordButton.visibility = View.GONE
            passwordField.visibility = View.GONE
            button.visibility = View.GONE





            Log.e(TAG, "klikniety button: $loginName, $password")



//


            login(loginName, password, apiToken){memberToken, id, refreshToken ->


                if (memberToken.startsWith("PJerror", false)) {

//                    Log.e(TAG, "error logowania: $memberToken")
                    Log.e(TAG, "PJ activity: ${activity.toString()}, context: ${view.context}")

//


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
                    sharedPref.edit().putString("userID", id).apply()
                    sharedPref.edit().putBoolean("isLogged", true).apply()
                    sharedPref.edit().putString("refreshToken", refreshToken).apply()
                    sharedPref.edit().putString("memberToken", memberToken).apply()



                    checkIfExist(id){exists ->

                        if (exists) {
                            setUserLastLogin(id){}
                        }
                    }


                    getUserData(id){cNumber ->

                        SharedDate.cardNumber = cNumber
                        sharedPref.edit().putString("cardNumber", cNumber).apply()

                        Log.e(TAG, "card number: $cNumber")
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
                                Log.e(TAG, Illegalargumentexception.localizedMessage)
                            }
                        } else {

                            activity?.runOnUiThread {
                                barcodeImageView.visibility = View.GONE
                                cardNumber.visibility = View.GONE
                            }


                        }

                    }


                        getPersonalData(memberToken, apiToken) {

                            Log.e(TAG, "username: $it")
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

//                                if (SharedDate.cardNumber != "-1"){
//                                    barcodeImageView.visibility = View.VISIBLE
//                                    cardNumber.visibility = View.VISIBLE
//                                } else {
//                                    barcodeImageView.visibility = View.GONE
//                                    cardNumber.visibility = View.GONE
//                                }



                            }

                        }
//                    }

                }

            }
        }


    }

//
//    fun usernameError() : String{
//        Toast.makeText(context, "zly user", Toast.LENGTH_SHORT).show()
//        return "zly user"
//    }
//
//
//    fun passwordError(){
//        Toast.makeText(context, "zle haslo", Toast.LENGTH_SHORT).show()
//    }






    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */

        @JvmStatic
        fun newInstance() =
            CardFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun createBitmapFromView(
        context: Context,
        view: View
    ): Bitmap? {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
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
