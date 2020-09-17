package com.dicoding.fort0.footballmatchschedule.match

import com.dicoding.fort0.footballmatchschedule.any
import com.dicoding.fort0.footballmatchschedule.argumentCaptor
import com.dicoding.fort0.footballmatchschedule.capture
import com.dicoding.fort0.footballmatchschedule.data.Club
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.source.RepoSport
import com.dicoding.fort0.footballmatchschedule.source.SportData
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

class MatchPresenterUnitTest {
    @Mock
    private lateinit var RepoSport: RepoSport
    @Mock
    private lateinit var view: MatchContract.View

    private lateinit var presenter: MatchPresenter

    @Captor
    private lateinit var getHomeClubCallbackCaptor: ArgumentCaptor<SportData.GetClubCallback>

    private val match = Match("1", "1", "2", "RealMadrid", "Barcelona", strDate = "3/9/2000", strTime = "14:00:00+00:00", matchPast = false)

    private lateinit var clubHome: Club
    private lateinit var clubAway: Club

    @Before
    fun setupPresenter() {
        MockitoAnnotations.initMocks(this)

        presenter = MatchPresenter(match, RepoSport, view)

        clubHome = Club("1", "Real Madrid", "Sample Description", "http://example.com/image.jpg")
        clubAway = Club("2", "Barcelona", "Sample Description", "http://example.com/image.jpg")
    }

    @Test
    fun createPresenterAndSetItToView() {
        presenter = MatchPresenter(match, RepoSport, view)

        Mockito.verify(view).presenter = presenter
    }

    @Test
    fun loadHomeClubFromRepoIntoView() {
        with(presenter) {
            getClub()
        }

        Mockito.verify(RepoSport, Mockito.times(2)).getClub(any(), capture(getHomeClubCallbackCaptor))
        getHomeClubCallbackCaptor.value.onClubLoaded(clubHome)

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()

        val getClubArgumentCaptor = argumentCaptor<Club>()
        Mockito.verify(view).showClubAway(capture(getClubArgumentCaptor))

        Assert.assertTrue(getClubArgumentCaptor.value.idTeam == "1")
    }

}