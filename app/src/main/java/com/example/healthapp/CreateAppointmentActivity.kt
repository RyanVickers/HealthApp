package com.example.healthapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.healthapp.databinding.ActivityCreateAppointmentBinding
import com.example.healthapp.model.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CreateAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAppointmentBinding
    var formate = SimpleDateFormat("MMM dd, YYYY",Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val now = Calendar.getInstance()
           val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth  ->
               val selectedDate = Calendar.getInstance()
               selectedDate.set(Calendar.YEAR,year)
              selectedDate.set(Calendar.MONTH,month)
             selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
               binding.appointmentDate.setText(date)
           },
                   now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
          datePicker.show()

        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            val time = timeFormat.format(selectedTime.time)
            binding.appointmentTime.setText(time)
        },
            now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
        timePicker.show()


        //add appointment button
        binding.addAppointmentButton.setOnClickListener {
            if (binding.appointmentName.text.toString().isNotEmpty()
            ) {
                //creating appointment object
                val appointment = Appointment()
                appointment.userId = FirebaseAuth.getInstance().currentUser!!.uid
                appointment.name = binding.appointmentName.text.toString()
                appointment.date =binding.appointmentDate.text.toString()
                appointment.time = binding.appointmentTime.text.toString()


                //Adding goal to collection in firebase
                val db = FirebaseFirestore.getInstance().collection("appointments")
                appointment.id = db.document().id

                db.document(appointment.id!!).set(appointment)
                    .addOnSuccessListener {
                        //show confirmation and clear the fields
                        Toast.makeText(this, "Appointment Added", Toast.LENGTH_LONG).show()
                        binding.appointmentName.setText("")
                        binding.appointmentTime.setText("")
                        binding.appointmentDate.setText("")

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Appointment Needed", Toast.LENGTH_LONG).show()

            }

        }
    }
}