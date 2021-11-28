package com.example.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.healthapp.databinding.ActivityAppointmentBinding
import com.example.healthapp.model.Appointment

class AppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //appointment viewmodel
        val model: AppointmentListViewModel by viewModels()
        model.getAppointments().observe(this, Observer<List<Appointment>> { appointments ->
            var appointmentsAdapter = AppointmentViewAdapter(this, appointments)
            binding.verticalRecyclerView.adapter = appointmentsAdapter
        })

        //create appointment button
        binding.createAppointmentButton.setOnClickListener {
            val intent = Intent(applicationContext, CreateAppointmentActivity::class.java)
            startActivity(intent)
        }
    }

}