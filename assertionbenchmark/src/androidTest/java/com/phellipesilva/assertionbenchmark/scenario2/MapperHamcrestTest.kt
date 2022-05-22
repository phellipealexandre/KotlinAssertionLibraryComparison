package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario2.Mapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.hamcrest.Matchers.*
import org.junit.Rule

class MapperHamcrestTest {

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

        assertThat(
            listRepresentation.processedItems,
            contains(
                hasProperty(ProcessedItem::value.name, equalTo("A")),
                hasProperty(ProcessedItem::value.name, equalTo("B")),
                hasProperty(ProcessedItem::value.name, equalTo("C")),
                hasProperty(ProcessedItem::value.name, equalTo("D")),
            )
        )
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

        assertThat(
            listRepresentation.processedItems,
            contains(
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("Favorites")),
                    instanceOf(ProcessedItem.Header::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("B")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("Non-Favorites")),
                    instanceOf(ProcessedItem.Header::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("A")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("C")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("D")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                )
            )
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

        assertThat(
            listRepresentation.processedItems,
            contains(
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("Favorites")),
                    instanceOf(ProcessedItem.Header::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("A")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("B")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("C")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                )
            )
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

        assertThat(
            listRepresentation.processedItems,
            contains(
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("Favorites")),
                    instanceOf(ProcessedItem.Header::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("C")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("Non-Favorites")),
                    instanceOf(ProcessedItem.Header::class.java)
                ),
                allOf(
                    hasProperty(ProcessedItem::value.name, equalTo("B")),
                    instanceOf(ProcessedItem.SelectableItem::class.java)
                )
            )
        )
    }
}