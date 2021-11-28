package com.example.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.nav_header.view.*
import java.awt.font.TextAttribute


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val userNameText: TextView = findViewById(R.id.nameTextView)
        val newsButton: Button = findViewById(R.id.newsButton)
        val goalsButton: Button = findViewById(R.id.goalsButton)
        val appointmentsButton: Button = findViewById(R.id.appointmentButton)
        val user = FirebaseAuth.getInstance().currentUser
        val userName = user!!.displayName
        val emailName = user.email
        userNameText.text = userName
        navView.getHeaderView(0).user_name.text = userName
        navView.getHeaderView(0).email.text = emailName

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        goalsButton.setOnClickListener {
            val intent = Intent(this, GoalsListActivity::class.java)
            startActivity(intent)
        }

        appointmentsButton.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.news -> {
                    val intent = Intent(this, NewsActivity::class.java)
                    startActivity(intent)
                }
                R.id.goals -> {
                    val intent = Intent(this, GoalsListActivity::class.java)
                    startActivity(intent)
                }
                R.id.appointments -> {
                    val intent = Intent(this, AppointmentActivity::class.java)
                    startActivity(intent)
                }
                R.id.settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
