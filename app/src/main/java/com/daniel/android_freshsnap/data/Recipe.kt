package com.daniel.android_freshsnap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var name_recipe: String,
    var photo_recipe: Int
) : Parcelable
