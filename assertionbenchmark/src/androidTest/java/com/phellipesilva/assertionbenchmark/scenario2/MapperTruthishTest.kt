package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario2.Mapper
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import com.varabyte.truthish.assertThat
import com.varabyte.truthish.subjects.IterableSubject
import org.junit.Rule
import kotlin.reflect.KClass

class MapperTruthishTest {

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

        assertThat(listRepresentation.processedItems.map(ProcessedItem::value)).containsExactly(
            "A", "B", "C", "D"
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

        ProcessedItemsKotlinSubject.assertThat(listRepresentation.processedItems).containsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )

        assertThat(listRepresentation.processedItems.map(ProcessedItem::value)).containsExactly(
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

        ProcessedItemsKotlinSubject.assertThat(listRepresentation.processedItems).apply {
            containsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
            containsExactlyValues(
                "Favorites",
                "A",
                "B",
                "C"
            )
        }
    }

    @Test
    fun removeHiddenItemsFromProcessedList() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = true, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        ProcessedItemsKotlinSubject.assertThat(listRepresentation.processedItems).containsExactlyTypesWithValues(
            ProcessedItem.Header::class to "Favorites",
            ProcessedItem.SelectableItem::class to "C",
            ProcessedItem.Header::class to "Non-Favorites",
            ProcessedItem.SelectableItem::class to "B"
        )
    }
}

class ProcessedItemsKotlinSubject internal constructor(
    private val items: List<ProcessedItem>
): IterableSubject<ProcessedItem>(items) {

    companion object {
        fun assertThat(processedItems: List<ProcessedItem>): ProcessedItemsKotlinSubject {
            return ProcessedItemsKotlinSubject(processedItems)
        }
    }

    fun containsExactlyTypes(vararg classTypes: KClass<*>) {
        assertThat(classTypes.size).isEqualTo(items.size)

        items.forEachIndexed { index, processedItem ->
            assertThat(processedItem).isInstanceOf(classTypes[index])
        }
    }

    fun containsExactlyValues(vararg values: String) {
        assertThat(values.size).isEqualTo(items.size)

        items.forEachIndexed { index, processedItem ->
            assertThat(processedItem.value).isEqualTo(values[index])
        }
    }

    fun containsExactlyTypesWithValues(vararg typesWithValues: Pair<KClass<*>, String>) {
        assertThat(typesWithValues.size).isEqualTo(items.size)

        items.forEachIndexed { index, processedItem ->
            assertThat(processedItem).isInstanceOf(typesWithValues[index].first)
            assertThat(processedItem.value).isEqualTo(typesWithValues[index].second)
        }
    }
}