package com.example.care_e

class ElectricCar : Car {

        var pricePoint: Int = 0

     //   internal constructor() {}

        internal constructor(make: String, model: String, year: Int, MPG: Int, pricePoint: Int) : super(make, model, year, MPG) {

            this.pricePoint = pricePoint
        }
}