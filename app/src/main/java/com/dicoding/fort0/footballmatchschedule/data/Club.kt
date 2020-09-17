package com.dicoding.fort0.footballmatchschedule.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club (val idTeam: String, val strTeam: String, val strDescriptionEN: String, val strTeamLogo: String?) : Parcelable