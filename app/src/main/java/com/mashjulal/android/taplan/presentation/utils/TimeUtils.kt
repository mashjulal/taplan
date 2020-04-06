package com.mashjulal.android.taplan.presentation.utils

fun minutesToTimePair(minutes: Int) = minutes / 60 to minutes % 60

fun timePairToMinutes(timePair: Pair<Int, Int>) = timePair.first * 60 + timePair.second