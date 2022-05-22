package com.phellipesilva.benchmarkable.scenario2

import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.assertj.core.api.ListAssert
import org.junit.Test

class MapperAssertJTest {

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
            .hasSize(4)
            .hasOnlyElementsOfType(ProcessedItem.SelectableItem::class.java)
            .extracting(ProcessedItem::value)
            .containsExactly(tuple("A"), tuple("B"), tuple("C"), tuple("D"))
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
                tuple("Favorites"),
                tuple("B"),
                tuple("Non-Favorites"),
                tuple("A"),
                tuple("C"),
                tuple("D")
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
                tuple("Favorites"),
                tuple("A"),
                tuple("B"),
                tuple("C")
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
                tuple("Favorites"),
                tuple("C"),
                tuple("Non-Favorites"),
                tuple("B")
            )
    }

    private fun <T> ListAssert<T>.containsExactlyTypes(vararg types: Class<*>): ListAssert<T> {
        hasSize(types.size)

        types.forEachIndexed { index, clazz ->
            element(index).isInstanceOf(clazz)
        }

        return this
    }
}