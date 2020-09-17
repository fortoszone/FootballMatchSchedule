package com.dicoding.fort0.footballmatchschedule.match

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.view.Gravity
import com.dicoding.fort0.footballmatchschedule.R
import com.dicoding.fort0.footballmatchschedule.data.Match
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchFragmentUI constructor(var match: Match?) : AnkoComponent<Fragment> {
    companion object {
        val swipeId = 1
        val homeLogoId = 2
        val awayLogoId = 3
    }

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        swipeRefreshLayout {
            id = swipeId

            scrollView {
                verticalLayout {
                    lparams(matchParent, matchParent)
                    gravity = Gravity.CENTER_HORIZONTAL
                    padding = dip(16)

                    textView {
                        textSize = 16f
                        textColorResource = R.color.colorPrimary
                        text = match?.matchDate()
                    }.lparams{
                        gravity = Gravity.CENTER
                    }

                    view {
                        backgroundColorResource = R.color.colorBackgroundGrey
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(16)
                        bottomMargin = dip(16)
                    }

                    linearLayout {

                        textView {
                            textSize = 24f
                            text = if (match?.matchPast == true) match?.intHomeScore.toString() else "-"
                            gravity = Gravity.END
                        }.lparams {
                            weight = 1f
                        }

                        textView {
                            textSize = 20f
                            text = ctx.getString(R.string.schedule_opponent)
                        }.lparams {
                            leftMargin = dip(16)
                            rightMargin = dip(16)
                        }

                        textView {
                            textSize = 24f
                            text = if (match?.matchPast == true) match?.intAwayScore.toString() else "-"
                        }.lparams {
                            weight = 1f
                        }
                    }

                    linearLayout {
                        verticalLayout {
                            imageView {
                                id = homeLogoId
                                imageResource = R.color.colorError
                            }.lparams(dip(72), dip(72)) {
                                gravity = Gravity.CENTER
                            }

                            textView {
                                textSize = 12f
                                textColorResource = R.color.colorPrimary
                                text = match?.strHomeTeam
                            }.lparams {
                                gravity = Gravity.CENTER
                            }

                            textView {
                                text = match?.strHomeFormation
                            }.lparams {
                                gravity = Gravity.CENTER
                            }
                        }.lparams {
                            weight = 1f
                        }


                        space {
                        }.lparams(width = dip(48))

                        verticalLayout {
                            gravity = Gravity.CENTER

                            imageView {
                                id = awayLogoId
                                imageResource = R.color.colorError
                            }.lparams(dip(72), dip(72)) {
                                gravity = Gravity.CENTER
                            }

                            textView {
                                textSize = 12f
                                textColorResource = R.color.colorPrimary
                                text = match?.strAwayTeam
                            }.lparams {
                                gravity = Gravity.CENTER
                            }

                            textView {
                                text = match?.strAwayFormation
                            }.lparams {
                                gravity = Gravity.CENTER
                            }
                        }.lparams {
                            weight = 1f
                        }
                    }

                    if (match?.matchPast == true) {
                        view {
                            backgroundColorResource = R.color.colorBackgroundGrey
                        }.lparams(matchParent, dip(1)) {
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            verticalLayout {
                                match?.strHomeGoalDetails?.split(";")?.forEach {
                                    textView {
                                        text = it
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_goals)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            verticalLayout {
                                match?.strAwayGoalDetails?.split(";")?.forEach {
                                    textView {
                                        text = it
                                    }.lparams {
                                        gravity = Gravity.END
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }

                        linearLayout {
                            textView {
                                text = match?.intHomeShots.toString()
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_shots)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            textView {
                                text = match?.intAwayShots.toString()
                                gravity = Gravity.END
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }

                        view {
                            backgroundColorResource = R.color.colorBackgroundGrey
                        }.lparams(matchParent, dip(1)) {
                            topMargin = dip(16)
                            bottomMargin = dip(16)
                        }

                        textView {
                            typeface = Typeface.DEFAULT_BOLD
                            textSize = 20f
                            text = context.getString(R.string.match_lineups)
                        }.lparams {
                            gravity = Gravity.CENTER
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            textView {
                                text = match?.strHomeLineupGoalkeeper?.replace(";", "")
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_gk)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            textView {
                                text = match?.strAwayLineupGoalkeeper
                                gravity = Gravity.END
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }.lparams(matchParent, wrapContent) {
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            verticalLayout {
                                match?.strHomeLineupDefense?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_def)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            verticalLayout {
                                match?.strAwayLineupDefense?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }.lparams {
                                        gravity = Gravity.END
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }.lparams(matchParent, wrapContent) {
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            verticalLayout {
                                match?.strHomeLineupMidfield?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_mid)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            verticalLayout {
                                match?.strAwayLineupMidfield?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }.lparams {
                                        gravity = Gravity.END
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }.lparams(matchParent, wrapContent) {
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            verticalLayout {
                                match?.strHomeLineupForward?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_fwd)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            verticalLayout {
                                match?.strAwayLineupForward?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }.lparams {
                                        gravity = Gravity.END
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }.lparams(matchParent, wrapContent) {
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            verticalLayout {
                                match?.strHomeLineupSubstitutes?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }

                            textView {
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                                text = context.getString(R.string.match_sub)
                            }.lparams {
                                leftMargin = dip(4)
                                rightMargin = dip(4)
                            }

                            verticalLayout {
                                match?.strAwayLineupSubstitutes?.split("; ")?.forEach {
                                    textView {
                                        text = it
                                    }.lparams {
                                        gravity = Gravity.END
                                    }
                                }
                            }.lparams {
                                weight = 1f
                                width = 0
                            }
                        }.lparams(matchParent, wrapContent) {
                            bottomMargin = dip(16)
                        }
                    }
                }
            }
        }
    }
}