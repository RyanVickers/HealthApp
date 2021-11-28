package com.example.healthapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthapp.model.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentListViewModel : ViewModel() {
    private val appointments = MutableLiveData<List<Appointment>>()

    init {
        loadAppointments()
    }

    /*get appointments*/
    fun getAppointments(): LiveData<List<Appointment>> {
        return appointments
    }

    /*Load appointments from database */
    private fun loadAppointments() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance().collection("appointments").whereEqualTo("userId", userId)


        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")
            if (exception != null) {
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val appointmentList = ArrayList<Appointment>()
            documents?.let {
                for (document in documents) {
                    val appointment = document.toObject(Appointment::class.java)
                    appointmentList.add(appointment)
                }
                appointments.value = appointmentList
            }
        }
    }
}