package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import assertk.Assert
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.phellipesilva.benchmarkable.scenario2.Mapper
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.junit.Rule
import org.junit.Test

class MapperAssertKTest {

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

        assertThat(listRepresentation.processedItems)
            .extracting(ProcessedItem::value)
            .containsExactly("A", "B", "C", "D")
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

        assertThat(listRepresentation.processedItems)
            .containsExactlyTypes(
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java
            )
            .extracting(ProcessedItem::value)
            .containsExactly(
                "Favorites",
                "B",
                "Non-Favorites",
                "A",
                "C",
                "D"
            )
    }

    @Test
    fun processListWithFavoritesOnlyToListWithFavoriteHeaderInAlphabeticalOrder() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = true),
            RawItem(value = "C", isHidden = false, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertThat(listRepresentation.processedItems)
            .containsExactlyTypes(
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java
            )
            .extracting(ProcessedItem::value)
            .containsExactly(
                "Favorites",
                "A",
                "B",
                "C"
            )
    }

    @Test
    fun removeHiddenItemsFromProcessedList() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = true, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertThat(listRepresentation.processedItems)
            .containsExactlyTypes(
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java
            )
            .extracting(ProcessedItem::value)
            .containsExactly(
                "Favorites",
                "C",
                "Non-Favorites",
                "B"
            )
    }

    private fun Assert<List<ProcessedItem>>.containsExactlyTypes(vararg classTypes: Class<*>): Assert<List<ProcessedItem>> {
        given { list ->
            assertThat(list.size).isEqualTo(classTypes.size)

            list.forEachIndexed { index, processedItem ->
                assertThat(processedItem).isInstanceOf(classTypes[index])
            }
        }

        return this
    }
}