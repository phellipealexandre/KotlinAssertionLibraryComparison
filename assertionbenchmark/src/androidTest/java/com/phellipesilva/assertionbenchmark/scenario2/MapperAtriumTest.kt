package com.phellipesilva.assertionbenchmark.scenario2

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
import com.phellipesilva.benchmarkable.scenario2.Mapper
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.junit.Rule
import org.junit.Test

class MapperAtriumTest {

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

        expect(listRepresentation.processedItems.map(ProcessedItem::value)).contains.inOrder.only.values(
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

        expect(listRepresentation.processedItems) {
            get(0).isA<ProcessedItem.Header>().and.feature({ f(it::value) }) { toBe("Favorites") }
            get(1).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("B") }
            get(2).isA<ProcessedItem.Header>().and.feature({ f(it::value) }) { toBe("Non-Favorites") }
            get(3).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("A") }
            get(4).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("C") }
            get(5).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("D") }
            hasSize(6)
        }
    }

    @Test
    fun processListWithFavoritesOnlyToListWithFavoriteHeaderInAlphabeticalOrder() = benchmarkRule.measureRepeated {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = true),
            RawItem(value = "C", isHidden = false, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        expect(listRepresentation.processedItems) {
            get(0).isA<ProcessedItem.Header>().and.feature({ f(it::value) }) { toBe("Favorites") }
            get(1).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("A") }
            get(2).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("B") }
            get(3).isA<ProcessedItem.SelectableItem>().and.feature({ f(it::value) }) { toBe("C") }
            hasSize(4)
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

        expect(listRepresentation.processedItems).containsExactlyValues(
            "Favorites",
            "C",
            "Non-Favorites",
            "B"
        ).containsExactlyTypes(
            ProcessedItem.Header::class.java,
            ProcessedItem.SelectableItem::class.java,
            ProcessedItem.Header::class.java,
            ProcessedItem.SelectableItem::class.java
        )
    }

    private fun Expect<List<ProcessedItem>>.containsExactlyTypes(vararg classTypes: Class<*>): Expect<List<ProcessedItem>> {
        return createAndAddAssertion("description", classTypes) { processedItems ->
            if (processedItems.size != classTypes.size) return@createAndAddAssertion false

            processedItems.forEachIndexed { index, processedItem ->
                if (!classTypes[index].isInstance(processedItem)) return@createAndAddAssertion false
            }

            return@createAndAddAssertion true
        }
    }

    private fun Expect<List<ProcessedItem>>.containsExactlyValues(vararg values: String): Expect<List<ProcessedItem>> {
        return createAndAddAssertion("description", values) { processedItems ->
            if (processedItems.size != values.size) return@createAndAddAssertion false

            processedItems.forEachIndexed { index, processedItem ->
                if (values[index] != processedItem.value) return@createAndAddAssertion false
            }

            return@createAndAddAssertion true
        }
    }
}