package com.mysore.ashtanga.yoga.yogarepublic


import android.util.Log

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun login(loginName: String, password: String, token: String, callback: (String) -> Unit) {

    val TAG = "PJ efitlogin"

    val json = JSONObject()
    json.put("login", loginName)
    json.put("password", password)


    Fuel.post("https://api-frontend2.efitness.com.pl/api/clubs/324/token/member")
        .header("Accept" to "application/json")
        .header("Content-type" to "application/json")
        .header("api-access-token" to token)
        .body(json.toString())
//            .header("member-token" to "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMTYxNTI2Iiwic3ViIjoicGpvYmtpZXdpY3pAZ21haWwuY29tIiwianRpIjoiNTlhMWEyNGYtZTA0OS00ZmUwLWJkZWEtNDdhMjZkNjVmNjZkIiwiaWF0IjoxNTgwMzMwNjkxLCJpZCI6IjExNjE1MjYiLCJuYmYiOjE1ODAzMzA2OTAsImV4cCI6MTU4MDMzNzg5MCwiaXNzIjoiYXBpRnJvbnRlbmQiLCJhdWQiOiJodHRwczovL2FwaS1mcm9udGVuZDIuZWZpdG5lc3MuY29tLnBsIn0.mwEwqQBlIaYp57313VsvsBWYrmDVuBwhuiN1ZjoVfdmcsgXBk8IgtNm_pu2KL1j7DOXeyIZYIbvTHwoXUqb5Xcwk5blVg3LgP6hPtE2CiCTqeQu3AxkISUCDYXvdkhQGEoG_hVg-gJ3yTGdJFZdQ0i2hE_sGI2W97-PHNl8oqWgOn13QYN7OWGQ0rlICr0MJIlpoxjD0Cw97O2h1kV32f1KPSP-uhlEYNTZQEQ-79c-GAxBWeTYwSqYWx4PqFxbH5sodCpWghvAWeyqrxvFdDADPdNNPQpkYXHI2AOeFSFATBVQ3VZ0z__3bBZtWx_W7SC22mSZOS-jwzA6kbX4G8w")
        .also { println(it) }
        .responseString { _, response, result ->


            val (data, error) = result


            val err = error.let { it?.localizedMessage } ?: "null"


            if (err == "null") {
                Log.e(TAG, "jest NULLL")
            } else {
                Log.e(TAG, "nie jest NULLL")
            }




                if (err == "null") {
                Log.e(TAG, "jest success")

                var obj = JSONObject(data)
                val memberToken = obj.getString("accessToken")
                val refreshToken = obj.getString("refreshToken")
                val expiresIn = obj.getLong("expiresIn")
                callback(memberToken)

            } else {

                Log.e(TAG, "jest błąd ${err}; response code: ${response.responseMessage}")
                callback("PJerror ${err}")
            }











            }


}


fun getMembership(memberToken: String, token: String, callback: (String) -> Unit) {

    val TAG = "PJ getmembership"



    Fuel.get("https://api-frontend2.efitness.com.pl/api/clubs/324/members/memberships")
        .header("Accept" to "application/json")
//        .header("Content-type" to "application/json")
        .header("api-access-token" to token)
        .header("member-token" to "bearer $memberToken")
//        .body(json.toString())
//            .header("member-token" to "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMTYxNTI2Iiwic3ViIjoicGpvYmtpZXdpY3pAZ21haWwuY29tIiwianRpIjoiNTlhMWEyNGYtZTA0OS00ZmUwLWJkZWEtNDdhMjZkNjVmNjZkIiwiaWF0IjoxNTgwMzMwNjkxLCJpZCI6IjExNjE1MjYiLCJuYmYiOjE1ODAzMzA2OTAsImV4cCI6MTU4MDMzNzg5MCwiaXNzIjoiYXBpRnJvbnRlbmQiLCJhdWQiOiJodHRwczovL2FwaS1mcm9udGVuZDIuZWZpdG5lc3MuY29tLnBsIn0.mwEwqQBlIaYp57313VsvsBWYrmDVuBwhuiN1ZjoVfdmcsgXBk8IgtNm_pu2KL1j7DOXeyIZYIbvTHwoXUqb5Xcwk5blVg3LgP6hPtE2CiCTqeQu3AxkISUCDYXvdkhQGEoG_hVg-gJ3yTGdJFZdQ0i2hE_sGI2W97-PHNl8oqWgOn13QYN7OWGQ0rlICr0MJIlpoxjD0Cw97O2h1kV32f1KPSP-uhlEYNTZQEQ-79c-GAxBWeTYwSqYWx4PqFxbH5sodCpWghvAWeyqrxvFdDADPdNNPQpkYXHI2AOeFSFATBVQ3VZ0z__3bBZtWx_W7SC22mSZOS-jwzA6kbX4G8w")
        .also { println(it) }
        .responseString { _, response, result ->


            val (data, error) = result

//            Log.e(TAG, "pobrany member - ${error}")
//            Log.e(TAG, "response - ${JSONArray(data)}")

            var obj = JSONObject(data)
//            Log.e(TAG, obj.toString())

            val membershipArray = obj.getJSONArray("results")

            val ileMemberships = membershipArray.length()

//            Log.e(TAG, ileMemberships.toString())

            for (z in 0..ileMemberships-1){

                val memberShip = membershipArray[z] as JSONObject

                val membershipName = memberShip.getString("name")
                val membershipIsValid = memberShip.getBoolean("isValid")
                val membershipValidTo = memberShip.get("to")
                Log.e(TAG, "$membershipName i czy aktywne $membershipIsValid do $membershipValidTo")
            }

            callback("membership")

        }


}



fun getPersonalData(memberToken: String, token: String, callback: (String) -> Unit) {

    val TAG = "PJ personalDat"



    Fuel.get("https://api-frontend2.efitness.com.pl/api/clubs/324/members")
        .header("Accept" to "application/json")
//        .header("Content-type" to "application/json")
        .header("api-access-token" to token)
        .header("member-token" to "bearer $memberToken")
//        .body(json.toString())
//            .header("member-token" to "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMTYxNTI2Iiwic3ViIjoicGpvYmtpZXdpY3pAZ21haWwuY29tIiwianRpIjoiNTlhMWEyNGYtZTA0OS00ZmUwLWJkZWEtNDdhMjZkNjVmNjZkIiwiaWF0IjoxNTgwMzMwNjkxLCJpZCI6IjExNjE1MjYiLCJuYmYiOjE1ODAzMzA2OTAsImV4cCI6MTU4MDMzNzg5MCwiaXNzIjoiYXBpRnJvbnRlbmQiLCJhdWQiOiJodHRwczovL2FwaS1mcm9udGVuZDIuZWZpdG5lc3MuY29tLnBsIn0.mwEwqQBlIaYp57313VsvsBWYrmDVuBwhuiN1ZjoVfdmcsgXBk8IgtNm_pu2KL1j7DOXeyIZYIbvTHwoXUqb5Xcwk5blVg3LgP6hPtE2CiCTqeQu3AxkISUCDYXvdkhQGEoG_hVg-gJ3yTGdJFZdQ0i2hE_sGI2W97-PHNl8oqWgOn13QYN7OWGQ0rlICr0MJIlpoxjD0Cw97O2h1kV32f1KPSP-uhlEYNTZQEQ-79c-GAxBWeTYwSqYWx4PqFxbH5sodCpWghvAWeyqrxvFdDADPdNNPQpkYXHI2AOeFSFATBVQ3VZ0z__3bBZtWx_W7SC22mSZOS-jwzA6kbX4G8w")
        .also { println(it) }
        .responseString { _, response, result ->


            val (data, error) = result

//            Log.e(TAG, "pobrany member - ${error}")
//            Log.e(TAG, "response - ${JSONArray(data)}")

            var obj = JSONObject(data)

            val firstName = obj.getString("firstName")
            val lastName = obj.getString("lastName")

//            Log.e(TAG, obj.toString())
//            Log.e(TAG, firstName + " " + lastName)

            callback(firstName + " " + lastName)

        }


}