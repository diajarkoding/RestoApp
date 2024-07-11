package com.example.restoapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.restoapp.R
import com.example.restoapp.databinding.ActivityDetailBinding
import com.example.restoapp.model.Resto

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_RESTO = "extra_resto"
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val dataResto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra<Resto>(EXTRA_RESTO, Resto::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Resto>(EXTRA_RESTO)
        }
        if (dataResto != null) {
            supportActionBar?.title = dataResto.name
            supportActionBar?.setDisplayHomeAsUpEnabled(true)


            Glide.with(this@DetailActivity).load(dataResto.pictureId).into(binding.imgDetailResto)
            binding.tvDetailDescription.text = dataResto.description
            binding.tvKota.text = dataResto.city
            binding.tvRating.text = dataResto.rating.toString()
            val foods = dataResto.menus.foods.map { food -> "  ${food.name}" }
            val drinks = dataResto.menus.drinks.map { drink -> "  ${drink.name}" }
            binding.tvFood.text = buildBulletList(foods)
            binding.tvDrink.text = buildBulletList(drinks)
        }

        binding.fabShare.setOnClickListener {
            val foods = dataResto?.menus?.foods?.map { food -> "  ${food.name}" }
            val drinks = dataResto?.menus?.drinks?.map { drink -> "  ${drink.name}" }

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, ("${dataResto?.name} merupakan restoran di daerah ${dataResto?.city} dengan rating ${dataResto?.rating.toString()}. \n\nBerikut menu nya : \n\nMakanan : \n${foods?.let { food -> buildBulletList(food) }} \n\nMinuman : \n${drinks?.let { drink -> buildBulletList(drink) }}"))
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Send To"))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun buildBulletList(items: List<String>): CharSequence {
        val bulletGapWidth = 10
        val ssb = SpannableStringBuilder("")
        items.forEachIndexed { index, item ->
            val spannableString = SpannableString(item)
            spannableString.setSpan(BulletSpan(bulletGapWidth), 0, spannableString.length, 0)
            ssb.append(spannableString)
            if (index < items.size - 1) {
                ssb.append("\n")
            }
        }
        return ssb
    }
}