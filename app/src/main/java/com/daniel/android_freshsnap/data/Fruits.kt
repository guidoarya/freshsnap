package com.daniel.android_freshsnap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruits(
    var name_fruit: String,
    var photo_fruit: Int
) : Parcelable
