package com.example.care_e

import android.util.JsonReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class DataParser {

    var offset: Int = 0
    var isMore: Boolean = false

    constructor() {
        offset = 0
        isMore = true
    }

    constructor(offset: Int, hasMore: Boolean) {
        this.offset = offset
        this.isMore = hasMore
    }

    companion object {

        @Throws(IOException::class)
        fun readJSON(json: InputStream): ArrayList<Car> {
            val reader = JsonReader(InputStreamReader(json, "UTF-8"))

            reader.isLenient = true

            try {
                reader.beginObject()

                reader.nextName()

                return readCars(reader)

            } finally {
                reader.close()
            }

        }

        @Throws(IOException::class)
        fun readJSON2(json: InputStream): ArrayList<ElectricCar> {
            val reader = JsonReader(InputStreamReader(json, "UTF-8"))

            reader.isLenient = true

            try {
                reader.beginObject()

                reader.nextName()

                return readElectricCars(reader)

            } finally {
                reader.close()
            }

        }

        @Throws(IOException::class)
        fun readCars(reader: JsonReader): ArrayList<Car> {
            val cars = ArrayList<Car>()

            reader.beginArray()

            while (reader.hasNext()) {
                //check that there are more objects to add
                cars.add(readCar(reader))
            }

            reader.endArray()
            return cars
        }

        @Throws(IOException::class)
        fun readElectricCars(reader: JsonReader): ArrayList<ElectricCar> {
            val cars = ArrayList<ElectricCar>()

            reader.beginArray()

            while (reader.hasNext()) {
                //check that there are more objects to add
                cars.add(readElectricCar(reader))
            }

            reader.endArray()
            return cars
        }

        @Throws(IOException::class)
        fun readCar(reader: JsonReader): Car {
            //all possible data to get from a deviation as variables

            var make = ""
            var model = ""
            var year = 0
            var MPG = 0

            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                when (name) {
                    "make" -> make = reader.nextString() //app does not support buying prints now.
                    "model" -> model = reader.nextString() //app does not support buying prints now.
                    "year" -> year = reader.nextInt() //app does not support buying prints now.
                    "MPG" -> MPG = reader.nextInt() //app does not support buying prints now.
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Car(make, model, year, MPG)

        }

        @Throws(IOException::class)
        fun readElectricCar(reader: JsonReader): ElectricCar {
            //all possible data to get from a deviation as variables

            var make = ""
            var model = ""
            var year = 0
            var MPG = 0
            var pricePoint = 0

            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                when (name) {
                    "make" -> make = reader.nextString() //app does not support buying prints now.
                    "model" -> model = reader.nextString() //app does not support buying prints now.
                    "year" -> year = reader.nextInt() //app does not support buying prints now.
                    "MPG" -> MPG = reader.nextInt() //app does not support buying prints now.
                    "pricePoint" -> pricePoint = reader.nextInt()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return ElectricCar(make, model, year, MPG, pricePoint)

        }
    }

}
