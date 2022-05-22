package com.phellipesilva.benchmarkable.scenario2

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import kotlin.reflect.KClass

class MapperKluentTest {

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
            .map(ProcessedItem::value) `should be equal to` listOf("A", "B", "C", "D")
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

        listRepresentation.processedItems.shouldContainExactlyTypes(
            listOf(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
        ).map(ProcessedItem::value).shouldBeEqualTo(
            listOf(
                "Favorites",
                "B",
                "Non-Favorites",
                "A",
                "C",
                "D"
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

        listRepresentation.processedItems.shouldContainExactlyTypes(
            listOf(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.SelectableItem::class
            )
        ).map(ProcessedItem::value).shouldBeEqualTo(
            listOf(
                "Favorites",
                "A",
                "B",
                "C",
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

        listRepresentation.processedItems.shouldContainExactlyTypes(
            listOf(
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class,
                ProcessedItem.Header::class,
                ProcessedItem.SelectableItem::class
            )
        ).map(ProcessedItem::value).shouldBeEqualTo(
            listOf(
                "Favorites",
                "C",
                "Non-Favorites",
                "B",
            )
        )
    }

    private infix fun <T, I : Iterable<T>> I.shouldContainExactlyTypes(clazzList: List<KClass<*>>): I {
        this.forEachIndexed { index, item ->
            item shouldBeInstanceOf clazzList[index]
        }

        return this
    }
}