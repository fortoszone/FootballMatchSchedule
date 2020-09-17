package com.dicoding.fort0.footballmatchschedule.match

import com.dicoding.fort0.footballmatchschedule.BasePresenter
import com.dicoding.fort0.footballmatchschedule.BaseView
import com.dicoding.fort0.footballmatchschedule.data.Club

interface MatchContract {

    interface Presenter : BasePresenter {
        fun getClub()

        fun isFavorite(): Boolean
        fun addToFavorite()
        fun removeFromFavorite()
    }

    interface View : BaseView<Presenter> {
        fun showClubHome(club: Club)
        fun showClubAway(club: Club)
        fun showLoading()
        fun hideLoading()

        fun showAddFavoriteSuccess()
        fun showRemoveFavoriteSuccess()
        fun showToggleFavoriteError()
        fun invalidateMenu()
    }
}