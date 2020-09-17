package com.dicoding.fort0.footballmatchschedule.source

import com.dicoding.fort0.footballmatchschedule.data.Match

class RepoSport(
        private val sportDataLocal: SportData,
        private val sportDataRemote: SportData
) : SportData {

    override fun getLastMatches(callback: SportData.LoadMatchesCallback) {
        sportDataRemote.getLastMatches(callback)
    }

    override fun getNextMatches(callback: SportData.LoadMatchesCallback) {
        sportDataRemote.getNextMatches(callback)
    }

    override fun getClub(clubId: String, callback: SportData.GetClubCallback) {
        sportDataRemote.getClub(clubId, callback)
    }

    companion object {

        private var INSTANCE: RepoSport? = null

        @JvmStatic
        fun getInstance(sportsLocalDataSource: SportDataLocal, sportsRemoteDataSource: SportDataRemote): RepoSport {
            return INSTANCE ?: RepoSport(sportsLocalDataSource, sportsRemoteDataSource)
                    .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getFavorited(callback: SportData.LoadMatchesCallback) {
        sportDataLocal.getFavorited(callback)
    }

    override fun isFavorited(match: Match, callback: SportData.CheckFavoriteCallback) {
        sportDataLocal.isFavorited(match, callback)
    }

    override fun saveFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
        sportDataLocal.saveFavorite(match, callback)
    }

    override fun deleteFavorite(match: Match, callback: SportData.ToggleFavoriteCallback) {
        sportDataLocal.deleteFavorite(match, callback)
    }

    override fun deleteAllFavorites() {
        sportDataLocal.deleteAllFavorites()
    }
}
