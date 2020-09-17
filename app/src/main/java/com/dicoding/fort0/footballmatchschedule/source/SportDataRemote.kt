package com.dicoding.fort0.footballmatchschedule.source

import android.support.annotation.VisibleForTesting
import com.dicoding.fort0.footballmatchschedule.BuildConfig
import com.dicoding.fort0.footballmatchschedule.data.Match
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SportDataRemote private constructor(private val api: SportService) : SportData {

    fun <T> callback2(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                success?.invoke(response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure?.invoke(t)
            }
        }
    }

    override fun getNextMatches(callback: SportData.LoadMatchesCallback) {
        api.listNextMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    r.body()?.let { callback.onMatchesLoaded(it.events) }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    override fun getLastMatches(callback: SportData.LoadMatchesCallback) {
        api.listPastMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    r.body()?.let { it -> callback.onMatchesLoaded(it.events.apply { forEach { it.matchPast = true } }) }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    override fun getClub(clubId: String, callback: SportData.GetClubCallback) {
        api.clubDetail(clubId).enqueue(callback2(
                { r ->
                    r.body()?.let {
                        if (it.teams != null)
                            callback.onClubLoaded(it.teams[0])
                        else
                            callback.onError("Team not found")
                    }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    override fun getFavorited(callback: SportData.LoadMatchesCallback) {
    }

    override fun isFavorited(match: Match, callback: SportData.CheckFavoriteCallback) {
    }

    override fun saveFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
    }

    override fun deleteFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
    }

    override fun deleteAllFavorites() {
    }

    companion object {
        private var INSTANCE: SportDataRemote? = null

        @JvmStatic
        fun getInstance(): SportDataRemote {
            if (INSTANCE == null) {
                synchronized(SportDataRemote::javaClass) {
                    val retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BuildConfig.BASE_API_URL)
                            .build()

                    INSTANCE = SportDataRemote(retrofit.create(SportService::class.java))
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}

