package com.example.care_e

import java.io.Serializable

data class UserandCarInfo (


var car: Car = null!!,
var firstName: String = null!!,
var lastName: String = null!!,
var uid: Serializable = null!!
)