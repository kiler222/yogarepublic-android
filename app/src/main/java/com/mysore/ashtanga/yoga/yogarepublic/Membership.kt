package com.mysore.ashtanga.yoga.yogarepublic

import java.io.Serializable
import java.util.*


data class Membership(var membershipName: String,
                      var expirationDate: Date,
                      var isValid: Boolean
                    ): Serializable