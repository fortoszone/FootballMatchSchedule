package com.dicoding.fort0.footballmatchschedule.schedule

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.LinearLayout
import androidx.test.espresso.IdlingResource
import com.dicoding.fort0.footballmatchschedule.EspressoIdlingResource
import com.dicoding.fort0.footballmatchschedule.Inject
import com.dicoding.fort0.footballmatchschedule.R
import com.dicoding.fort0.footballmatchschedule.replaceFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class ScheduleActivity : AppCompatActivity() {

    private val MENU_PREV_MATCH = 1
    private val MENU_NEXT_MATCH = 2
    private val MENU_FAV_MATCH = 3

    private val fragmentMap: HashMap<String, ScheduleFragment> = HashMap()

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.countingIdlingResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScheduleActivityUI().setContentView(this)

        initBottomBar()
    }

    class ScheduleActivityUI : AnkoComponent<ScheduleActivity> {
        companion object {
            val contentFrame = 1
            val navigationView = 2
        }

        override fun createView(ui: AnkoContext<ScheduleActivity>) = with(ui) {
            verticalLayout {

                frameLayout {
                    id = contentFrame
                }.lparams(width = LinearLayout.LayoutParams.MATCH_PARENT, height = 0, weight = 1F)

                bottomNavigationView {
                    id = navigationView
                    backgroundColorResource = R.color.colorPrimary

                    val states = arrayOf(
                            intArrayOf(android.R.attr.state_selected),
                            intArrayOf(android.R.attr.state_enabled)
                    )

                    val colors = intArrayOf(Color.WHITE, Color.DKGRAY)

                    val colorStateList = ColorStateList(states, colors)
                    itemTextColor = colorStateList
                    itemIconTintList = colorStateList

                }.lparams(width = LinearLayout.LayoutParams.MATCH_PARENT)
            }
        }
    }

    private fun initBottomBar() {
        with(findViewById<BottomNavigationView>(ScheduleActivityUI.navigationView)) {
            menu.add(Menu.NONE, MENU_PREV_MATCH, Menu.NONE, R.string.menu_past_match).setIcon(R.drawable.past)
            menu.add(Menu.NONE, MENU_NEXT_MATCH, Menu.NONE, R.string.menu_next_match).setIcon(R.drawable.next)
            menu.add(Menu.NONE, MENU_FAV_MATCH, Menu.NONE, R.string.fav_menu).setIcon(R.drawable.favorite)

            setOnNavigationItemSelectedListener {
                val selectedFragment: ScheduleFragment?
                when (it.itemId) {
                    MENU_PREV_MATCH -> {
                        selectedFragment = fragmentMap.get("prev") ?: ScheduleFragment.newInstance(TYPE.PAST)
                                .apply {
                                    fragmentMap.put("prev", this)
                                    SchedulePresenter(Inject.provideRepoSport(ctx), this)
                                }
                    }
                    MENU_NEXT_MATCH -> {
                        selectedFragment = fragmentMap.get("next") ?: ScheduleFragment.newInstance(TYPE.NEXT)
                                .apply {
                                    fragmentMap.put("next", this)
                                    SchedulePresenter(Inject.provideRepoSport(ctx), this)
                                }
                    }

                    MENU_FAV_MATCH -> {
                        selectedFragment = fragmentMap.get("fav") ?: ScheduleFragment.newInstance(TYPE.FAV)
                                .apply {
                                    fragmentMap.put("fav", this)
                                    SchedulePresenter(Inject.provideRepoSport(ctx), this)
                                }
                    }
                    else -> selectedFragment = null
                }

                if (selectedFragment != null) {
                    replaceFragment(selectedFragment, ScheduleActivityUI.contentFrame)
                    true
                } else {
                    false
                }
            }

            ScheduleFragment.newInstance(TYPE.PAST).apply {
                replaceFragment(this, ScheduleActivityUI.contentFrame)
                SchedulePresenter(Inject.provideRepoSport(ctx), this)
            }
        }
    }
    enum class TYPE {
        PAST, NEXT, FAV
    }
}