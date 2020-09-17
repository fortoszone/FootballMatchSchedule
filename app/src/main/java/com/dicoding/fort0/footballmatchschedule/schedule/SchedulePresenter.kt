package com.dicoding.fort0.footballmatchschedule.schedule

import android.support.coordinatorlayout.R
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.source.RepoSport
import com.dicoding.fort0.footballmatchschedule.source.SportData

class SchedulePresenter(private val RepoSport: RepoSport, private val view: ScheduleContract.View) : ScheduleContract.Presenter {
    private lateinit var type: ScheduleActivity.TYPE

    init {
        view.presenter = this
    }

    override fun setType(type: ScheduleActivity.TYPE) {
        this.type = type
    }

    override fun getMatches() {
        view.showLoading()
        val cb = object : SportData.LoadMatchesCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onMatchesLoaded(matches: List<Match>) {
                view.hideLoading()
                view.showMatches(matches)
            }
        }

        when (type) {
            ScheduleActivity.TYPE.PAST -> RepoSport.getLastMatches(cb)
            ScheduleActivity.TYPE.NEXT -> RepoSport.getNextMatches(cb)
            ScheduleActivity.TYPE.FAV -> RepoSport.getFavorited(cb)
        }
    }

    override fun start() {
        getMatches()
    }

}