package com.phellipesilva.benchmarkable.scenario2

import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import com.winterbe.expekt.should
import org.junit.Test

/*
 * Difficult to perform customization
 */
class MapperExpektTest {

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
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
    fun `Process list with favorites and non-favorites to list with headers in alphabetical order`() {
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
    fun `Process list with favorites only to list with favorite header in alphabetical order`() {
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
    fun `Remove hidden items from processed list`() {
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