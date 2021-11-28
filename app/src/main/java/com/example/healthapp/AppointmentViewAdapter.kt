package com.example.healthapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.model.Appointment
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentViewAdapter(
    val context: Context,
    val appointments: List<Appointment>

) : RecyclerView.Adapter<AppointmentViewAdapter.AppointmentViewHolder>() {
    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appointmentTextView = itemView.findViewById<TextView>(R.id.appointmentTextView)
        val timeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
        val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

    }

    /*Get item count*/
    override fun getItemCount(): Int {
        return appointments.size
    }

    /*Function to create viewholder*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    /*Function to bind view holder*/
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        with(holder) {
            appointmentTextView.text = appointment.name
            timeTextView.text = appointment.time
            dateTextView.text = appointment.date
            deleteButton.setOnClickListener {
                val id =appointment.id
                FirebaseFirestore.getInstance().collection("appointments").document(id!!).delete()
            }

        }
    }
}