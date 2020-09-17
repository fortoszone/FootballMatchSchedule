package com.dicoding.fort0.footballmatchschedule.source

import com.dicoding.fort0.footballmatchschedule.data.Club
import com.dicoding.fort0.footballmatchschedule.data.Match

interface SportData {

    interface BaseCallback {
        fun onError(message: String?)
    }

    interface LoadMatchesCallback : BaseCallback {
        fun onMatchesLoaded(matches: List<Match>)
    }

    interface GetClubCallback : BaseCallback {
        fun onClubLoaded(club: Club)
    }

    fun getLastMatches(callback: LoadMatchesCallback)

    fun getNextMatches(callback: LoadMatchesCallback)

    fun getClub(clubId: String, callback: GetClubCallback)

    interface ToggleFavoriteCallback : BaseCallback {
        fun onToggleSuccess(match: Match, isFavoritedNow: Boolean)
    }

    interface CheckFavoriteCallback : BaseCallback {
        fun onCheckedFavorited(isFavoritedNow: Boolean)
    }

    fun getFavorited(callback: LoadMatchesCallback)

    fun saveFavorite(match: Match, callback: ToggleFavoriteCallback)

    fun deleteFavorite(match: Match, callback: ToggleFavoriteCallback)

    fun deleteAllFavorites()

    fun isFavorited(match: Match, callback: CheckFavoriteCallback)
}