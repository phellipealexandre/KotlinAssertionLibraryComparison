package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario2.Mapper
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import com.winterbe.expekt.should
import org.junit.Rule
import org.junit.Test

/*
 * Difficult to perform customization
 */
class MapperExpektTest {

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
            .should.contain.same.elements("A", "B", "C")
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

        listRepresentation.processedItems.apply {
            get(0).should.satisfy { it is ProcessedItem.Header }
            get(1).should.satisfy { it is ProcessedItem.SelectableItem }
            get(2).should.satisfy { it is ProcessedItem.Header }
            get(3).should.satisfy { it is ProcessedItem.SelectableItem }
            get(4).should.satisfy { it is ProcessedItem.SelectableItem }
            get(5).should.satisfy { it is ProcessedItem.SelectableItem }
        }
            .map(ProcessedItem::value)
            .should.contain.same.elements(
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

        listRepresentation.processedItems.apply {
            get(0).should.satisfy { it is ProcessedItem.Header }
            get(1).should.satisfy { it is ProcessedItem.SelectableItem }
            get(2).should.satisfy { it is ProcessedItem.SelectableItem }
            get(3).should.satisfy { it is ProcessedItem.SelectableItem }
        }
            .map(ProcessedItem::value)
            .should.contain.same.elements(
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

        listRepresentation.processedItems.apply {
            get(0).should.satisfy { it is ProcessedItem.Header }
            get(1).should.satisfy { it is ProcessedItem.SelectableItem }
            get(2).should.satisfy { it is ProcessedItem.Header }
            get(3).should.satisfy { it is ProcessedItem.SelectableItem }
        }
            .map(ProcessedItem::value)
            .should.contain.same.elements(
                "Favorites",
                "C",
                "Non-Favorites",
                "B"
            )
    }
}