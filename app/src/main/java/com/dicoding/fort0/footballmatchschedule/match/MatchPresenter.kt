package com.dicoding.fort0.footballmatchschedule.match

import com.dicoding.fort0.footballmatchschedule.data.Club
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.source.SportData
import com.dicoding.fort0.footballmatchschedule.source.RepoSport

class MatchPresenter(private val match: Match, private val RepoSport: RepoSport, private val view: MatchContract.View) : MatchContract.Presenter {

    private var isFavorite: Boolean = false

    init {
        view.presenter = this
    }

    override fun start() {
        getClub()
        checkIsFavorite()
    }

    override fun getClub() {
        view.showLoading()
        RepoSport.getClub(match.idHomeTeam, object : SportData.GetClubCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onClubLoaded(club: Club) {
                view.hideLoading()
                view.showClubHome(club)
            }

        })

        RepoSport.getClub(match.idAwayTeam, object : SportData.GetClubCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onClubLoaded(club: Club) {
                view.hideLoading()
                view.showClubAway(club)
            }

        })
    }

    private fun checkIsFavorite(){
        RepoSport.isFavorited(match, object : SportData.CheckFavoriteCallback {
            override fun onError(message: String?) {
                view.showError(message)
            }

            override fun onCheckedFavorited(isFavoritedNow: Boolean) {
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }

        })
    }

    override fun isFavorite(): Boolean {
        return isFavorite
    }

    override fun addToFavorite() {
        RepoSport.saveFavorite(match,  object : SportData.ToggleFavoriteCallback {
            override fun onError(message: String?) {
                view.showToggleFavoriteError()
            }

            override fun onToggleSuccess(match: Match, isFavoritedNow: Boolean) {
                view.showAddFavoriteSuccess()
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }

        })
    }

    override fun removeFromFavorite() {
        RepoSport.deleteFavorite(match,  object : SportData.ToggleFavoriteCallback {
            override fun onError(message: String?) {
                view.showToggleFavoriteError()
            }

            override fun onToggleSuccess(match: Match, isFavoritedNow: Boolean) {
                view.showRemoveFavoriteSuccess()
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }
        })
    }
}