package com.phellipesilva.benchmarkable.scenario2

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assertThat
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem
import kotlin.reflect.KClass

class MapperHamkrestTest {

    private val instanceOfMatcher = Matcher(this::instanceOf)
    private val hasValueMatcher = Matcher(this::hasValue)

    @Test
    fun `Process list without favorites to plain list without headers in alphabetical order`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = false),
            RawItem(value = "C", isHidden = false, isFavorite = false),
            RawItem(value = "B", isHidden = false, isFavorite = false),
            RawItem(value = "D", isHidden = false, isFavorite = false),
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertThat(listRepresentation.processedItems[0], has(ProcessedItem::value, equalTo("A")))
        assertThat(listRepresentation.processedItems[1], has(ProcessedItem::value, equalTo("B")))
        assertThat(listRepresentation.processedItems[2], has(ProcessedItem::value, equalTo("C")))
        assertThat(listRepresentation.processedItems[3], has(ProcessedItem::value, equalTo("D")))
        assertThat(listRepresentation.processedItems, hasSize(equalTo(4)))
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

        assertThat(listRepresentation.processedItems[0], instanceOfMatcher(ProcessedItem.Header::class))
        assertThat(listRepresentation.processedItems[1], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems[2], instanceOfMatcher(ProcessedItem.Header::class))
        assertThat(listRepresentation.processedItems[3], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems[4], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems[5], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems, hasSize(equalTo(6)))
    }

    @Test
    fun `Process list with favorites only to list with favorite header in alphabetical order`() {
        val rawItems = listOf(
            RawItem(value = "A", isHidden = false, isFavorite = true),
            RawItem(value = "C", isHidden = false, isFavorite = true),
            RawItem(value = "B", isHidden = false, isFavorite = true)
        )

        val listRepresentation = Mapper.processItems(rawItems)

        assertThat(listRepresentation.processedItems[0], instanceOfMatcher(ProcessedItem.Header::class))
        assertThat(listRepresentation.processedItems[1], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems[2], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems[3], instanceOfMatcher(ProcessedItem.SelectableItem::class))
        assertThat(listRepresentation.processedItems, hasSize(equalTo(4)))
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
            containsExactly(
                instanceOfMatcher(ProcessedItem.Header::class) and hasValueMatcher("Favorites"),
                instanceOfMatcher(ProcessedItem.SelectableItem::class) and hasValueMatcher("C"),
                instanceOfMatcher(ProcessedItem.Header::class) and hasValueMatcher("Non-Favorites"),
                instanceOfMatcher(ProcessedItem.SelectableItem::class) and hasValueMatcher("B")
            )
        )
    }

    private fun instanceOf(p: ProcessedItem, clazz: KClass<*>): Boolean = clazz.isInstance(p)

    private fun hasValue(p: ProcessedItem, value: String): Boolean = p.value == value

    private fun <T> containsExactly(vararg matchers: Matcher<T>) = object : Matcher.Primitive<Iterable<T>>() {
        override fun invoke(actual: Iterable<T>): MatchResult {
            if (matchers.size != actual.toList().size) return MatchResult.Mismatch("Different Sizes")

            actual.forEachIndexed { index, t ->
                if (matchers[index].invoke(t) != MatchResult.Match) return MatchResult.Mismatch(
                    "Error on index $index and item $t"
                )
            }

            return MatchResult.Match
        }

        override val description: String get() = "description"
        override val negatedDescription: String get() = "negated"
    }
}

