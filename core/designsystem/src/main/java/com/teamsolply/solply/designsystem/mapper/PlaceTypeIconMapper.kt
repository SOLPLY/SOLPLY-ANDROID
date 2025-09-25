package com.teamsolply.solply.designsystem.mapper

import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.model.PlaceType

fun PlaceType.iconRes(): Int = when (this) {
    PlaceType.CAFE -> R.drawable.ic_caffe
    PlaceType.FOOD -> R.drawable.ic_food
    PlaceType.WALKING -> R.drawable.ic_walk
    PlaceType.UNIQUE_SPACE -> R.drawable.ic_unique
    PlaceType.SHOPPING -> R.drawable.ic_shopping
    PlaceType.BOOKSTORE -> R.drawable.ic_book
    PlaceType.ALL -> R.drawable.ic_all
}
