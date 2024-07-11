package com.example.restoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val name: String
) : Parcelable

@Parcelize
data class Menus(
    val foods: List<Menu>,
    val drinks: List<Menu>
) : Parcelable

@Parcelize
data class Resto(
    val id: String,
    val name: String,
    val description: String,
    val pictureId: String,
    val city: String,
    val rating: Float,
    val menus: Menus
) : Parcelable
