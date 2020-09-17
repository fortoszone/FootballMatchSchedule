package com.dicoding.fort0.footballmatchschedule.source

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.dicoding.fort0.footballmatchschedule.MyDatabaseOpenHelper
import com.dicoding.fort0.footballmatchschedule.data.Favorite
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.database
import com.google.gson.Gson
import org.jetbrains.anko.db.*

class SportDataLocal private constructor(val database: MyDatabaseOpenHelper) : SportData {
    override fun getLastMatches(callback: SportData.LoadMatchesCallback) {
    }

    override fun getNextMatches(callback: SportData.LoadMatchesCallback) {
    }

    override fun getClub(clubId: String, callback: SportData.GetClubCallback) {
    }

    override fun getFavorited(callback: SportData.LoadMatchesCallback) {
        database.use {
            select(Favorite.TABLE_FAVORITE).exec {
                val matches = this.parseList(MyRowParser())
                callback.onMatchesLoaded(matches)
            }
        }
    }

    override fun isFavorited(match: Match, callback: SportData.CheckFavoriteCallback) {
        database.use {
            select(Favorite.TABLE_FAVORITE).whereArgs("${Favorite.MATCH_ID} = {matchId}",
                    "matchId" to match.idEvent).exec {
                callback.onCheckedFavorited(this.count > 0)
            }
        }
    }

    override fun saveFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
        database.use {
            insert(Favorite.TABLE_FAVORITE,
                    Favorite.MATCH_ID to match.idEvent,
                    Favorite.JSON to Gson().toJson(match),
                    Favorite.IS_PAST to if (match.matchPast) 0 else 1
            )
            callback.onToggleSuccess(match, true)
        }
    }

    override fun deleteFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
        database.use {
            delete(Favorite.TABLE_FAVORITE, "${Favorite.MATCH_ID} = {matchId}",
                    "matchId" to match.idEvent)
            callback.onToggleSuccess(match, false)
        }
    }

    override fun deleteAllFavorites() {
        database.use {
            delete(Favorite.TABLE_FAVORITE)
        }
    }

    companion object {
        private var INSTANCE: SportDataLocal? = null

        @JvmStatic
        fun getInstance(ctx: Context): SportDataLocal {
            if (INSTANCE == null) {
                synchronized(SportDataLocal::javaClass) {
                    INSTANCE = SportDataLocal(ctx.database)
                }
            }
            return INSTANCE!!
        }
        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    class MyRowParser : MapRowParser<Match> {
        override fun parseRow(columns: Map<String, Any?>): Match {
            val json: String? = columns[Favorite.JSON].toString()
            return Gson().fromJson<Match>(json, Match::class.java)
        }
    }
}