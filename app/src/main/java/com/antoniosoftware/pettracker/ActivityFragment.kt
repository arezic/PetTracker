package com.antoniosoftware.pettracker

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlinx.android.synthetic.main.list_item.*
import java.text.NumberFormat
import java.util.*

class ActivityFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + "."
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH).toString() + "."
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString() + "."
        button_select_date.text = currentDay + currentMonth + currentYear

        pickDate()
        var distance = Supplier.calculateTotalDistance()
        var distanceString =  distance.toInt().toString() + "m"
        if (distance >= 1000){
            distance = distance/1000
            distanceString = "%.2f".format(distance) + "km"
        }

        text_view_distance.text = text_view_distance.text.toString() + distanceString
        text_view_average_speed.text = text_view_average_speed.text.toString() + "%.2f".format(Supplier.calculateAverageSpeedInKilometersPerHour()) + "km/h"
        text_view_highest_speed.text = text_view_highest_speed.text.toString() + "%.2f".format(Supplier.getHighestSpeedInKilometersPerHour()) + "km/h"
        text_view_tracking_time.text = text_view_tracking_time.text.toString() + Supplier.calculateTrackingTime()/3600 + "hours"
        text_view_activity_pet_name.text = "Rocky"        //CHANGE THIS

        var series = LineGraphSeries<DataPoint>(Supplier.speedOverTimeToDataPointArray())
        graph_view.addSeries(series)

        graph_view.gridLabelRenderer.labelFormatter = DefaultLabelFormatter(NumberFormat.getNumberInstance(), NumberFormat.getNumberInstance())

        graph_view.gridLabelRenderer.verticalAxisTitle = "speed (km/h)"
        graph_view.gridLabelRenderer.horizontalAxisTitle = "time (h)"

        graph_view.viewport.isXAxisBoundsManual = true
        graph_view.viewport.maxXAxisSize = (Supplier.calculateTrackingTime()/3600).toDouble()

    }

    private fun pickDate() {
       button_select_date.setOnClickListener {
           getDateTimeCalendar()

           DatePickerDialog(this.context!!,this,year,month,day).show()
       }
    }

    private fun getDateTimeCalendar(){
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
    }



    companion object {
        @JvmStatic
        fun newInstance(): ActivityFragment = ActivityFragment()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        button_select_date.text = "$savedDay.$savedMonth.$savedYear"

    }
}