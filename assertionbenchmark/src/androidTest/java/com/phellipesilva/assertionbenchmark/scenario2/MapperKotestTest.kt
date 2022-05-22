package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario2.Mapper
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.junit.Rule
import kotlin.reflect.KClass

class MapperKotestTest {

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

        listRepresentation.processedItems
            .map(ProcessedItem::value)
            .shouldHaveSize(4)
            .shouldContainExactly("A", "B", "C", "D")
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

        listRepresentation.processedItems
            .shouldContainsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
            .shouldHaveSize(6)
            .shouldContainExactly(
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

        listRepresentation.processedItems
            .shouldContainsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
            .shouldHaveSize(4)
            .shouldContainExactly(
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

        listRepresentation.processedItems
            .shouldContainsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
            .shouldHaveSize(4)
            .shouldContainExactly(
                "Favorites",
                "C",
                "Non-Favorites",
                "B"
            )
    }

    private fun List<ProcessedItem>.shouldContainsExactlyTypes(vararg elements: KClass<*>): List<ProcessedItem> {
        this should containsExactlyTypes(*elements)
        return this
    }

    private fun containsExactlyTypes(vararg elements: KClass<*>) = object : Matcher<List<ProcessedItem>> {
        override fun test(value: List<ProcessedItem>): MatcherResult {
            value.forEachIndexed { index, processedItem ->
                if (!elements[index].isInstance(processedItem)) return MatcherResult(false, "", "")
            }

            return MatcherResult(true, "", "")
        }
    }
}