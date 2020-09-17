package com.dicoding.fort0.footballmatchschedule.match

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dicoding.fort0.footballmatchschedule.Inject
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.replaceFragment
import org.jetbrains.anko.*

class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchActivityUI().setContentView(this)

        intent.getParcelableExtra<Match>(EXTRA_MATCH).apply {
            val fragment = MatchFragment.newInstance(this).apply {
                replaceFragment(this, MatchActivityUI.contentFrameId)
            }
            MatchPresenter(this, Inject.provideRepoSport(ctx), fragment)
        }
    }

    class MatchActivityUI : AnkoComponent<MatchActivity> {
        companion object {
            const val contentFrameId = 1
        }

        override fun createView(ui: AnkoContext<MatchActivity>) = with(ui) {
            frameLayout {
                id = contentFrameId
            }
        }
    }

    companion object {
        const val EXTRA_MATCH = "MATCH"
    }
}
