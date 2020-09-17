package com.dicoding.fort0.footballmatchschedule

import android.content.Context
import com.dicoding.fort0.footballmatchschedule.source.RepoSport
import com.dicoding.fort0.footballmatchschedule.source.SportData
import com.dicoding.fort0.footballmatchschedule.source.SportDataLocal
import com.dicoding.fort0.footballmatchschedule.source.SportDataRemote

object Inject {
    fun provideRepoSport(ctx: Context): RepoSport {
        return RepoSport.getInstance(SportDataLocal.getInstance(ctx), SportDataRemote.getInstance())
    }
}