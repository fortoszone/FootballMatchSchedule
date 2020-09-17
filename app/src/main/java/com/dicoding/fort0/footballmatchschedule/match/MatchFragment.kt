package com.dicoding.fort0.footballmatchschedule.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.ImageView
import com.dicoding.fort0.footballmatchschedule.R
import com.dicoding.fort0.footballmatchschedule.data.Club
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MatchFragment : Fragment(), MatchContract.View {

    override lateinit var presenter: MatchContract.Presenter

    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val match = arguments?.get(ARGUMENT_MATCH) as Match
        val view = MatchFragmentUI(match).createView(AnkoContext.create(ctx, this))

        with(view.findViewById<SwipeRefreshLayout>(MatchFragmentUI.swipeId)) {
            swipeLayout = this
            setOnRefreshListener { presenter.getClub() }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showClubHome(club: Club) {
        view?.findViewById<ImageView>(MatchFragmentUI.homeLogoId)?.let { showLogo(club, it) }
    }

    override fun showClubAway(club: Club) {
        view?.findViewById<ImageView>(MatchFragmentUI.awayLogoId)?.let { showLogo(club, it) }
    }

    private fun showLogo(club: Club, view: ImageView) {
        if (!club.strTeamLogo.isNullOrEmpty()) {
            with(view) {
                Picasso.get()
                        .load(club.strTeamLogo)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.color.colorError)
                        .into(this)
            }
        }
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

    override fun showError(message: String?) {
        message?.let { toast(it) }
    }

    override fun invalidateMenu() {
        act.invalidateOptionsMenu()
    }

    companion object {
        private const val ARGUMENT_MATCH = "MATCH"

        fun newInstance(match: Match) =
                MatchFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARGUMENT_MATCH, match)
                    }
                }
    }

    override fun showAddFavoriteSuccess() {
        view?.let { snackbar(it, getString(R.string.fav_match_added)) }
    }

    override fun showRemoveFavoriteSuccess() {
        view?.let { snackbar(it, getString(R.string.fav_match_removed)) }
    }

    override fun showToggleFavoriteError() {
        view?.let { snackbar(it, getString(R.string.fav_match_error)) }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            R.id.add_to_favorite -> {
                if (presenter.isFavorite()) presenter.removeFromFavorite() else presenter.addToFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.add_to_favorite).apply {
            if (presenter.isFavorite()) {
                this?.setIcon(R.drawable.ic_added_to_favorite)
            } else {
                this?.setIcon(R.drawable.ic_add_to_favorite)
            }
        }
    }
}