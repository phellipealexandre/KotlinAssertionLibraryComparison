package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario2.Mapper
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.junit.Rule
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/*
 * Difficult to perform customization
 */
class MapperKotlinJUnitTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun processListWithoutFavoritesToPlainListWithoutHeadersInAlphabeticalOrder() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = false),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "D", isHidden = false, isFavorite = false),
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertEquals(expected = "A", actual = listRepresentation.processedItems[0].value)
        assertEquals(expected = "B", actual = listRepresentation.processedItems[1].value)
        assertEquals(expected = "C", actual = listRepresentation.processedItems[2].value)
        assertEquals(expected = "D", actual = listRepresentation.processedItems[3].value)
        assertEquals(expected = 4, actual = listRepresentation.processedItems.size)
    }

    @Test
    fun processListWithFavoritesAndNonFavoritesToListWithHeadersInAlphabeticalOrder() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = false),
            RawItem(value = "B", isHidden = false, isFavorite = true),
            RawItem(value = "D", isHidden = false, isFavorite = false),
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertTrue { listRepresentation.processedItems[0] is ProcessedItem.Header }
        assertTrue { listRepresentation.processedItems[1] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[2] is ProcessedItem.Header }
        assertTrue { listRepresentation.processedItems[3] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[4] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[5] is ProcessedItem.SelectableItem }
    }

    @Test
    fun processListWithFavoritesOnlyToListWithFavoriteHeaderInAlphabeticalOrder() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = true),
            RawItem(value = "C", isHidden = false, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertTrue { listRepresentation.processedItems[0] is ProcessedItem.Header }
        assertTrue { listRepresentation.processedItems[1] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[2] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[3] is ProcessedItem.SelectableItem }
    }

    @Test
    fun removeHiddenItemsFromProcessedList() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = true, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertTrue { listRepresentation.processedItems[0] is ProcessedItem.Header }
        assertTrue { listRepresentation.processedItems[1] is ProcessedItem.SelectableItem }
        assertTrue { listRepresentation.processedItems[2] is ProcessedItem.Header }
        assertTrue { listRepresentation.processedItems[3] is ProcessedItem.SelectableItem }
    }
}