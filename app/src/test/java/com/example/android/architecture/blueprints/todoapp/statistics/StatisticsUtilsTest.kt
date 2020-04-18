package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    /**
     * Test Names
     *
     * Test names are meant to be descriptive. The naming convention in this codelab is:
     *
     * subjectUnderTest_actionOrInput_resultState
     *
     * Subject under test is the method or class that is being tested (getActiveAndCompletedStats).
     * Next is the action or input (noCompleted).
     * Finally you have the expected result (returnsHundredZero).
     */

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Create an active task
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = false)
        )

        // Calling function
        val result = getActiveAndCompletedStats(tasks)

//        // Check the result
//        assertEquals(result.completedTasksPercent, 0f)
//        assertEquals(result.activeTasksPercent, 100f)

        // Using Hamcrest library
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsOneThousand() {
        // Given one completed task
        val tasks = listOf<Task>(
                Task("Title", "desc", isCompleted = true)
        )

        // When the task is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 100-0
        assertThat(result.completedTasksPercent, `is`(100f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        // Given 2 completed tasks and 3 active tasks
        val tasks = listOf<Task>(
                Task("Title", "desc", isCompleted = true),
                Task("Title", "desc", isCompleted = true),
                Task("Title", "desc", isCompleted = false),
                Task("Title", "desc", isCompleted = false),
                Task("Title", "desc", isCompleted = false)
        )

        // When the list tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 60-40
        assertThat(result.activeTasksPercent, `is`(60f))
        assertThat(result.completedTasksPercent, `is`(40f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnZeros() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both Active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}