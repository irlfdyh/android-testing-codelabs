package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Execute each ask synchronously using architecture components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var statisticViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var tasksRepository: FakeTestRepository

    @Before
    fun setupStatisticViewModel() {
        // Initialize the repository with no tasks
        tasksRepository = FakeTestRepository()

        statisticViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadTasks_loading() {

        // Pause dispatcher to verify initial values.
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the view model.
        statisticViewModel.refresh()

        // Then progress indicator is shown
        assertThat(statisticViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        // Execute pending coroutine actions.
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden.
        assertThat(statisticViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun loadStatisticWhenTaskAreUnavailable_callErrorToDisplay() {
        // Make the repository return errors.
        tasksRepository.setReturnError(true)
        statisticViewModel.refresh()

        // Then empty and error are true (which trigger an error message to be shown)
        assertThat(statisticViewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(statisticViewModel.error.getOrAwaitValue(), `is`(true))
    }

}