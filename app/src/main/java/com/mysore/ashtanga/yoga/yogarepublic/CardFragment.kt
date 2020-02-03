package com.mysore.ashtanga.yoga.yogarepublic

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_card.*


// TODO: Rename parameter arguments, choose names that match
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
    // TODO: Rename and change types of parameters
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


        userNumber.visibility = View.GONE
        barcodeImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        userName.text = ""




        logoutButton.setOnClickListener {


            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle(getString(R.string.logout_title))

            // Display a message on alert dialog
//            builder.setMessage("Are you want to set the app background color to RED?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton(getString(R.string.yes)){dialog, which ->

                barcodeImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                userName.visibility = View.GONE
                userName.text = ""
                emailField.setText("")
                passwordField.setText("")
                userNumber.text = ""
                emailField.visibility = View.VISIBLE
                passwordField.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
                logoutButton.visibility = View.GONE

            }





            // Display a neutral button on alert dialog
            builder.setNeutralButton(getString(R.string.no)){_,_ ->
                Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()





        }

        button.setOnClickListener {


            hideKeyboard()

            var loginName = emailField.text.toString().trim().takeUnless { it.isNullOrEmpty() } //?: usernameError()
            val password = passwordField.text.toString().trim().takeUnless { it.isNullOrEmpty() } //?: return passwordError()


            if (loginName.isNullOrEmpty() || password.isNullOrEmpty()) {

                Toast.makeText(activity?.baseContext, getString(R.string.fill_credentials), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }




            loginName = "pjobkiewicz@gmail.com"


            Log.e(TAG, "klikniety button: $loginName, $password")





            login(loginName, "xiubofwo", getString(R.string.api_access_token)){memberToken ->


                if (memberToken.startsWith("PJerror", false)) {

                    Log.e(TAG, "error logowania: $memberToken")
                    Log.e(TAG, "activity: ${activity.toString()}")

                    Toast.makeText(activity?.baseContext, getString(R.string.login_error), Toast.LENGTH_LONG).show()




                    activity?.runOnUiThread {
                        Toast.makeText(
                            context,
                            "Your Message here",
                            Toast.LENGTH_SHORT
                        ).show()
                    }



                } else {

                    activity?.runOnUiThread {
                        button.visibility = View.GONE
                        emailField.visibility = View.GONE
                        passwordField.visibility = View.GONE
                        logoutButton.visibility = View.VISIBLE
                    }


                    Log.e(TAG, "nie ma błędu logowania: $memberToken")

                    getUserData(loginName!!){cardNumber ->
                        Log.e(TAG, "odczytany numer karty: $cardNumber")

                        val bitMatrix: BitMatrix
                        try {
                            bitMatrix = MultiFormatWriter().encode(
                                cardNumber,
                                BarcodeFormat.ITF,
                                300, 80, null
                            )

                            val bEnc = BarcodeEncoder()

                            val bitmap = bEnc.createBitmap(bitMatrix)

                            barcodeImageView.setImageBitmap(bitmap)
                            barcodeImageView.visibility = View.VISIBLE
                            userNumber.text = cardNumber
                            userNumber.visibility = View.VISIBLE

                        } catch (Illegalargumentexception: IllegalArgumentException) {
                            Log.e(TAG, Illegalargumentexception.localizedMessage)
                        }


                    }

                    getMembership(memberToken, getString(R.string.api_access_token)){

                        Log.e(TAG, "get member: $it")

                        getPersonalData(memberToken, getString(R.string.api_access_token)) {

                            Log.e(TAG, "get personal: $it")

                            userName.text = it


                            Log.e(TAG, "zakonczone odpytanie efitnesu")


                        }
                    }

                }

            }
        }


    }


    fun usernameError() : String{
        Toast.makeText(context, "zly user", Toast.LENGTH_SHORT).show()
        return "zly user"
    }


    fun passwordError(){
        Toast.makeText(context, "zle haslo", Toast.LENGTH_SHORT).show()
    }





    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CardFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
