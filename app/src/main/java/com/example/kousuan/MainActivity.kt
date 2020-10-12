package com.example.kousuan
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
class MainActivity : AppCompatActivity() {
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, controller)
    }
    override fun onSupportNavigateUp(): Boolean {
        if (controller.currentDestination!!.id == R.id.welcomeFragment) {
            finish()
        } else controller.navigate(R.id.welcomeFragment)
        return super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}