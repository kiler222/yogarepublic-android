package com.mysore.ashtanga.yoga.yogarepublic

import android.util.Log


val yrTest = "rgerfido@gmail.com,889477"

val yr = ""
val yr2 = ""


fun addCardNumbers(){

    val TAG = "PJ update"

//    val batch = SharedDate.db.batch()


    var tabYR = yr2.split(";")

    tabYR.forEach{
        val userData= it.split(",")
        val email = userData[0].toLowerCase()
        val cardNumber = userData[1]
//        val newUser = SharedDate.db.collection("users").document(email)
        val setCardNumber = hashMapOf("a" to cardNumber)

//        batch.set(newUser, setCardNumber)

        val docRef = SharedDate.db.collection("users").document(email)

        docRef.set(setCardNumber)
            .addOnSuccessListener { Log.e(TAG, "cardnumber $cardNumber set for $email") }
            .addOnFailureListener { e -> Log.e(TAG, "Error updating lastlogin", e) }



//        Log.e(TAG, "rekordy: $email i karta: $cardNumber")
    }

    Log.e(TAG, "już po foreach")



//
//    batch.commit().addOnCompleteListener {
//
//        if (it.isSuccessful) {
//            Log.e(TAG, "dodane cardNumbers ")
////            callback("OK")
//        } else {
//            Log.e(TAG, "nieudany commit batcha ${it.exception.toString()}")
////            callback("ERROR")
//        }
//    }



}