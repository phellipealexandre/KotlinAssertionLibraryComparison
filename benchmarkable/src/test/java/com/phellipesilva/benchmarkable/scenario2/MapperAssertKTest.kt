package com.phellipesilva.benchmarkable.scenario2

import assertk.Assert
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.junit.Test

class MapperAssertKTest {

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
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
    fun `Process list with favorites and non-favorites to list with headers in alphabetical order`() {
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
    fun `Process list with favorites only to list with favorite header in alphabetical order`() {
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
    fun `Remove hidden items from processed list`() {
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