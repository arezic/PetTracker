package com.antoniosoftware.pettracker

import android.location.Location
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.pow
import kotlin.math.sqrt

object Supplier {

    var locations : MutableList<Location> = mutableListOf()

    fun setSpeeds(){
        locations[0].speed = 0F
        for (x in 1 until locations.size)
            locations[x].speed = locations[x].distanceTo(locations[x-1]) / ((locations[x].time - locations[x-1].time)/1000)
    }

    fun calculateTotalDistance() : Float {
        var sum : Float = 0F
        for (x in 1 until locations.size)
            sum += locations[x].distanceTo(locations[x-1])
        return sum
    }

    fun calculateAverageSpeedInKilometersPerHour() : Float {
        return (calculateTotalDistance()/ calculateTrackingTime())*3.6F
    }

    fun calculateTrackingTime() : Float = (locations.last().time - locations[0].time).toFloat()/1000F

    fun getHighestSpeedInKilometersPerHour() : Float {
        var highestSpeed = 0F
        for (location in locations)
            if (location.speed > highestSpeed)
                highestSpeed = location.speed
        return  highestSpeed*3.6F
    }

    fun speedOverTimeToDataPointArray() : Array<DataPoint> {
        var list = mutableListOf<DataPoint>()
        for (location in locations)
            list.add(DataPoint(location.time.toDouble()/3600000,location.speed*3.6))
        return list.toTypedArray()
    }
}