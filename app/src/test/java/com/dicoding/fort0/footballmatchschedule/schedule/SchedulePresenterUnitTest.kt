package com.dicoding.fort0.footballmatchschedule.schedule

import com.dicoding.fort0.footballmatchschedule.argumentCaptor
import com.dicoding.fort0.footballmatchschedule.capture
import com.dicoding.fort0.footballmatchschedule.data.Match
import com.dicoding.fort0.footballmatchschedule.source.RepoSport
import com.dicoding.fort0.footballmatchschedule.source.SportData
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

class SchedulePresenterUnitTest {
    @Mock
    private lateinit var RepoSport: RepoSport
    @Mock
    private lateinit var view: ScheduleContract.View

    private lateinit var presenter: SchedulePresenter

    @Captor
    private lateinit var loadCallbackCaptorTask: ArgumentCaptor<SportData.LoadMatchesCallback>

    private lateinit var match: MutableList<Match>

    @Before
    fun setupPresenter() {
        MockitoAnnotations.initMocks(this)

        presenter = SchedulePresenter(RepoSport, view)
        match = arrayListOf(
                Match("1", "1", "2", "Real Madrid", "Barcelona", strDate = "3/9/2000", strTime = "21:00:00+00:00", matchPast = false),
                Match("1", "3", "4", "Munyuk", "Tayo", strDate = "14/2/2017", strTime = "21:00:00+00:00", matchPast = false))
    }

    @Test
    fun loadNextMatchesFromRepoIntoView() {
        with(presenter) {
            setType(ScheduleActivity.TYPE.NEXT)
            getMatches()
        }

        Mockito.verify(RepoSport).getNextMatches(capture(loadCallbackCaptorTask))
        loadCallbackCaptorTask.value.onMatchesLoaded(match)

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()

        val getMatchesArgumentCaptor = argumentCaptor<List<Match>>()
        Mockito.verify(view).showMatches(capture(getMatchesArgumentCaptor))

        Assert.assertTrue(getMatchesArgumentCaptor.value.size == 2)
    }

    @Test
    fun loadPastMatchesFromRepoIntoView() {
        with(presenter) {
            setType(ScheduleActivity.TYPE.PAST)
            getMatches()
        }

        Mockito.verify(RepoSport).getLastMatches(capture(loadCallbackCaptorTask))
        loadCallbackCaptorTask.value.onMatchesLoaded(match)

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()

        val getMatchesArgumentCaptor = argumentCaptor<List<Match>>()
        Mockito.verify(view).showMatches(capture(getMatchesArgumentCaptor))

        Assert.assertTrue(getMatchesArgumentCaptor.value.size == 2)
    }
}