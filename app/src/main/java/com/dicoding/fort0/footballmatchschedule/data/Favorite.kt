package com.dicoding.fort0.footballmatchschedule.data

data class Favorite(val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "HOME_MATCH_ID"
        const val JSON: String = "JSON"
        const val IS_PAST: String = "IS_PAST"
    }
}