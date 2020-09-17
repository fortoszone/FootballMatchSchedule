package com.dicoding.fort0.footballmatchschedule.source

import com.dicoding.fort0.footballmatchschedule.data.Schedule
import com.dicoding.fort0.footballmatchschedule.data.Team
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SportService {
    @GET("eventspastleague.php")
    fun listPastMatches(@Query("id") leagueId: String): Call<Schedule>

    @GET("eventsnextleague.php")
    fun listNextMatches(@Query("id") leagueId: String): Call<Schedule>

    @GET("lookupteam.php")
    fun clubDetail(@Query("id") clubId: String): Call<Team>
}