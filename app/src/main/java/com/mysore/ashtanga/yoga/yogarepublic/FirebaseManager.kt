package com.mysore.ashtanga.yoga.yogarepublic

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import javax.security.auth.callback.Callback


fun getUserData(user: String, callback: (String) -> Unit) {
    val TAG = "PJ get userdata"

    val docRef = SharedDate.db.collection("users").document(user)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document.data != null) {


//                Log.e(TAG, "DocumentSnapshot data: ${document.data}")
                val cardNumber = document.data!!.get("cardNumber") as String


                callback(cardNumber)
            } else {
                Log.e(TAG, "No such document")
                callback("No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, "get failed with ", exception)
            callback("failed")
        }

}


fun setUserLastLogin(user: String, callback: (String) -> Unit) {
    val TAG = "PJ set userlastlogin"

    val docRef = SharedDate.db.collection("users").document(user)

    docRef.update("lastLogin", Timestamp.now())
    .addOnSuccessListener { Log.e(TAG, "last login updated!") }
        .addOnFailureListener { e -> Log.e(TAG, "Error updating lastlogin", e) }


}