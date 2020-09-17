package com.dicoding.fort0.footballmatchschedule.schedule

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.fort0.footballmatchschedule.R
import com.dicoding.fort0.footballmatchschedule.data.Match
import kotlinx.android.synthetic.main.schedule.view.*
import org.jetbrains.anko.layoutInflater

class ScheduleAdapter(private var scheculeList: List<Match>, private val itemListener: ScheduleItemListener) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(parent.context.layoutInflater.inflate(R.layout.schedule, parent, false))
    }

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var matchDate: TextView = itemView.matchDate
        var homeTeam: TextView = itemView.homeTeam
        var homeTeamScore: TextView = itemView.homeTeamScore
        var awayTeam: TextView = itemView.awayTeam
        var awayTeamScore: TextView = itemView.awayTeamScore
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val match = scheculeList[position]
        holder.homeTeam.text = match.strHomeTeam
        holder.awayTeam.text = match.strAwayTeam

        if (match.matchPast) {
            holder.homeTeamScore.text = String.format(match.intHomeScore.toString())
            holder.awayTeamScore.text = String.format(match.intAwayScore.toString())
        } else {
            holder.homeTeamScore.visibility = View.GONE
            holder.awayTeamScore.visibility = View.GONE
        }

        holder.matchDate.text = match.matchDate()

        holder.itemView.setOnClickListener {
            itemListener.onMatchClick(match)
        }
    }

    override fun getItemCount(): Int {
        return scheculeList.size
    }

    interface ScheduleItemListener {
        fun onMatchClick(clickedMatch: Match)
    }
}