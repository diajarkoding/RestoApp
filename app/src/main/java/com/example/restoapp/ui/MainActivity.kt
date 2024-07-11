package com.example.restoapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restoapp.R
import com.example.restoapp.adapter.ListRestoAdapter
import com.example.restoapp.databinding.ActivityMainBinding
import com.example.restoapp.model.Resto
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var rvResto: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var restoList: List<Resto>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        }


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvResto = binding.rvResto
        rvResto.setHasFixedSize(true)

        getListResto()
        showRecyclerList()
    }
    private fun getJsonDataFromAsset(fileName: String): String? {
        return try {
            val inputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
   private fun getListResto() {
       val jsonString = getJsonDataFromAsset("restaurants.json")
       if (jsonString != null) {
           restoList = RestoData.loadRestoData(jsonString)
       }
    }
    private fun showRecyclerList(){
        rvResto.layoutManager = LinearLayoutManager(this)
        val listRestoAdapter = ListRestoAdapter(restoList)
        rvResto.adapter = listRestoAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_menu -> {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}