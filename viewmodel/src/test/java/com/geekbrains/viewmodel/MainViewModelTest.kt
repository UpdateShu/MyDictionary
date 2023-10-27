package com.geekbrains.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word
import com.geekbrains.repo.FakeRepository
import com.geekbrains.repo.FavoriteRepository
import com.geekbrains.utils.BODY_EMPTY
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var mainInteractor: MainInteractor

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(mainInteractor)
    }

    @Test
    fun coroutines_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = mainViewModel.getDataViewModel()

            Mockito.`when`(mainInteractor.getDataInteractor(SEARCH_QUERY, true)).thenReturn(
                getWords()
            )
            try {
                liveData.observeForever(observer)
                mainViewModel.getDataViewModel(SEARCH_QUERY)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = mainViewModel.getDataViewModel()

            Mockito.`when`(mainInteractor.getDataInteractor(SEARCH_QUERY, true)).thenReturn(
                listOf<Word>()
            )
            try {
                liveData.observeForever(observer)
                mainViewModel.getDataViewModel(SEARCH_QUERY)
                Assert.assertEquals(liveData.value, AppState.Error(BODY_EMPTY))
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = mainViewModel.getDataViewModel()

            try {
                liveData.observeForever(observer)
                mainViewModel.getDataViewModel(SEARCH_QUERY)
                Assert.assertEquals(liveData.value, AppState.Error(EXCEPTION_TEXT))
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    fun getWords(): List<Word> {
        val words: MutableList<Word> = mutableListOf()
        for (index in 1..20) {
            val wordIndex = index.toString()
            words.add(
                Word(wordIndex,
                    "Word:${wordIndex}",
                    Word.Meanings(
                        Word.Meanings.Translation("Translation:${wordIndex}"))
                )
            )
        }
        return words.toList()
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "Search results or total count are null"
        private const val EXCEPTION_TEXT = "Response is null or unsuccessful"
    }
}