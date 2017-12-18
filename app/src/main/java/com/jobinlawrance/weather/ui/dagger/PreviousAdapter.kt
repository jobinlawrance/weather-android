package com.jobinlawrance.weather.ui.dagger

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jobinlawrance.weather.R
import com.jobinlawrance.weather.data.WeatherData

/**
 * Created by jobinlawrance on 18/12/17.
 */
class PreviousAdapter: RecyclerView.Adapter<PreviousAdapter.PreviousHolder>() {

    var weatherList : List<WeatherData> = emptyList()

    override fun onBindViewHolder(holder: PreviousHolder, position: Int) {
        holder.bind(weatherList.get(position))
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_row,parent,false)
        return PreviousHolder(view)
    }

    fun updateList(weatherList: List<WeatherData>) {
        this.weatherList = weatherList
        notifyDataSetChanged()
    }

    class PreviousHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val locationName = itemView.findViewById<TextView>(R.id.location_name)
        val temp = itemView.findViewById<TextView>(R.id.temperature)

        fun bind(weather: WeatherData){
            locationName.text = weather.locationName
            temp.text = itemView.context.getString(R.string.temperature,weather.temp)
        }
    }
}