package com.phellipesilva.benchmarkable.scenario2

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem

class MapperHamcrestTest {

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
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
    fun `Process list with favorites and non-favorites to list with headers in alphabetical order`() {
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
    fun `Process list with favorites only to list with favorite header in alphabetical order`() {
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
    fun `Remove hidden items from processed list`() {
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