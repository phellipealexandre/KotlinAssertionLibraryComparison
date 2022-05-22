package com.phellipesilva.benchmarkable.scenario2

import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.map
import kotlin.reflect.KClass

class MapperStriktTest {

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = false),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "D", isHidden = false, isFavorite = false),
        )

        val listRepresentation = Mapper.processItems(rawItems)

        expectThat(listRepresentation.processedItems)
            .hasSize(4)
            .map(ProcessedItem::value)
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

        expectThat(listRepresentation.processedItems)
            .hasSize(6)
            .containsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
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

        expectThat(listRepresentation.processedItems)
            .hasSize(4)
            .containsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
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

        expectThat(listRepresentation.processedItems)
            .hasSize(4)
            .containsExactlyTypes(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class
            )
            .map(ProcessedItem::value)
            .containsExactly(
                "Favorites",
                "C",
                "Non-Favorites",
                "B"
            )
    }

    private fun Assertion.Builder<List<ProcessedItem>>.containsExactlyTypes(vararg elements: KClass<*>): Assertion.Builder<List<ProcessedItem>> {
        assert("") {
            it.forEachIndexed { index, processedItem ->
                if (!elements[index].isInstance(processedItem)) fail()
            }

            pass()
        }

        return this
    }
}