package com.mysore.ashtanga.yoga.yogarepublic

import android.util.Log
import com.google.firebase.Timestamp


fun getUserData(user: String, callback: (String) -> Unit) {
    val TAG = "PJ get userdata"

    val docRef = SharedDate.db.collection("users").document(user)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document.data != null) {


                Log.e(TAG, "DocumentSnapshot data: ${document.data}")
                val cardNumber = document.data!!.get("a") as String

                if (cardNumber.length % 2 == 0) {
                    callback(cardNumber)
                } else {
                    callback("-1")
                }



            } else {
                Log.e(TAG, "No such document")
                callback("-1")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, "get failed with ", exception)
            callback("-1")
        }

}


fun checkIfExist(userName: String, callback: (Boolean) -> Unit){
    val TAG = "PJ checkIfExist"
    val usersRef = SharedDate.db.collection("users").document(userName)

    usersRef.get()
        .addOnSuccessListener { document ->

            //                Log.e(TAG, " w document mamy: ${document}")

            if (document.data != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                callback(true)
            } else {
                Log.d(TAG, "No such document: ${document}")
                callback(false)
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
            callback(false)
        }
}


fun setUserLastLogin(user: String, callback: (String) -> Unit) {
    val TAG = "PJ set userlastlogin"

    val docRef = SharedDate.db.collection("users").document(user)

    docRef.update("ll", Timestamp.now())
    .addOnSuccessListener { Log.e(TAG, "last login updated!") }
        .addOnFailureListener { e -> Log.e(TAG, "Error updating lastlogin", e) }


}




