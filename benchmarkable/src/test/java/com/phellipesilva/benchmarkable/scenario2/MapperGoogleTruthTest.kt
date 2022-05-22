package com.phellipesilva.benchmarkable.scenario2

import com.google.common.truth.FailureMetadata
import com.google.common.truth.IterableSubject
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem

class MapperGoogleTruthTest {

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
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
    fun `Process list with favorites and non-favorites to list with headers in alphabetical order`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = false),
            RawItem(value = "B", isHidden = false, isFavorite = true),
            RawItem(value = "D", isHidden = false, isFavorite = false),
        )

        val listRepresentation = Mapper.processItems(rawItems)

        ProcessedItemsSubject.assertThat(listRepresentation.processedItems).containsExactlyTypes(
            ProcessedItem.Header::class.java,
            ProcessedItem.SelectableItem::class.java,
            ProcessedItem.Header::class.java,
            ProcessedItem.SelectableItem::class.java,
            ProcessedItem.SelectableItem::class.java,
            ProcessedItem.SelectableItem::class.java
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
    fun `Process list with favorites only to list with favorite header in alphabetical order`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = true),
            RawItem(value = "C", isHidden = false, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        ProcessedItemsSubject.assertThat(listRepresentation.processedItems).apply {
            containsExactlyTypes(
                ProcessedItem.Header::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java,
                ProcessedItem.SelectableItem::class.java
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
    fun `Remove hidden items from processed list`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = true, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        ProcessedItemsSubject.assertThat(listRepresentation.processedItems).containsExactlyTypesWithValues(
            ProcessedItem.Header::class.java to "Favorites",
            ProcessedItem.SelectableItem::class.java to "C",
            ProcessedItem.Header::class.java to "Non-Favorites",
            ProcessedItem.SelectableItem::class.java to "B"
        )
    }
}

class ProcessedItemsSubject internal constructor(
    failureMetadata: FailureMetadata,
    private val items: List<ProcessedItem>
): IterableSubject(failureMetadata, items) {

    companion object {
        private val factory = Factory<ProcessedItemsSubject, List<ProcessedItem>> { metadata, actual ->
            ProcessedItemsSubject(metadata, actual)
        }

        fun assertThat(processedItems: List<ProcessedItem>): ProcessedItemsSubject {
            return assertAbout(factory).that(processedItems)
        }
    }

    fun containsExactlyTypes(vararg classTypes: Class<*>) {
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

    fun containsExactlyTypesWithValues(vararg typesWithValues: Pair<Class<*>, String>) {
        assertThat(typesWithValues.size).isEqualTo(items.size)

        items.forEachIndexed { index, processedItem ->
            assertThat(processedItem).isInstanceOf(typesWithValues[index].first)
            assertThat(processedItem.value).isEqualTo(typesWithValues[index].second)
        }
    }
}

