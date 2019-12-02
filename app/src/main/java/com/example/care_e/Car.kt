package com.example.care_e

open class Car {
    var make: String? = null
    var model: String? = null
    var year: Int = 0
    var mpg: Int = 0


    constructor(make: String, model: String, year: Int, MPG: Int) {
        this.make = make
        this.model = model
        this.year = year
        this.mpg = MPG
    }

    override fun toString(): String {
        return "make:$make model: $model year: $year mpg $mpg"
    }
}