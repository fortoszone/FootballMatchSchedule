package com.dicoding.fort0.footballmatchschedule.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Match(
        val idEvent:String,
        val idHomeTeam: String, val idAwayTeam: String, val strHomeTeam: String, val strAwayTeam: String,
        val intHomeScore: Int? = null, val intAwayScore: Int? = null, val strDate: String, val strTime: String,
        val strHomeFormation: String? = null, val strAwayFormation: String? = null,
        val strHomeGoalDetails: String? = null, val strAwayGoalDetails: String? = null,
        val intHomeShots: Int? = null, val intAwayShots: Int? = null,
        val strHomeLineupGoalkeeper: String? = null, val strHomeLineupDefense: String? = null,
        val strHomeLineupMidfield: String? = null, val strHomeLineupForward: String? = null, val strHomeLineupSubstitutes: String? = null,
        val strAwayLineupGoalkeeper: String? = null, val strAwayLineupDefense: String? = null,
        val strAwayLineupMidfield: String? = null, val strAwayLineupForward: String? = null, val strAwayLineupSubstitutes: String? = null,
        var matchPast: Boolean) : Parcelable {

    fun matchDate(): String {
        val dateEvent = SimpleDateFormat("dd/MM/yy HH:mm:ssXXX", Locale.ENGLISH).parse("${strDate} ${strTime}")
        return SimpleDateFormat("EEEE, dd MMM yyyy - HH:mm", Locale.getDefault()).format(dateEvent)
    }
}