package com.example.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.healthapp.databinding.ActivityGoalsBinding

class GoalsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //goal viewmodel
        val model: GoalListViewModel by viewModels()
        model.getGoals().observe(this, Observer<List<Goal>> { goals ->
            var goalsAdapter = GoalsViewAdapter(this, goals)
            binding.verticalRecyclerView.adapter = goalsAdapter
        })

        //create goal button
        binding.createGoalButton.setOnClickListener {
            val intent = Intent(applicationContext, CreateGoalActivity::class.java)
            startActivity(intent)
        }
    }

}