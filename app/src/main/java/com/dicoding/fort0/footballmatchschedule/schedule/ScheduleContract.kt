package com.dicoding.fort0.footballmatchschedule.schedule

import com.dicoding.fort0.footballmatchschedule.BasePresenter
import com.dicoding.fort0.footballmatchschedule.BaseView
import com.dicoding.fort0.footballmatchschedule.data.Match

interface ScheduleContract {

    interface Presenter : BasePresenter {
        fun setType(type: ScheduleActivity.TYPE)
        fun getMatches()
    }

    interface View : BaseView<Presenter> {
        fun showMatches(matches: List<Match>)
        fun showLoading()
        fun hideLoading()
        fun showMatchDetailUi(match: Match)
    }
}