package com.phellipesilva.benchmarkable.scenario2

import com.phellipesilva.benchmarkable.scenario2.processed.ListRepresentation
import com.phellipesilva.benchmarkable.scenario2.processed.ProcessedItem
import com.phellipesilva.benchmarkable.scenario2.raw.RawItem

/**
 * This is the system under test:
 * It main responsibility is to map a list of raw items as a view representation with headers for favorites and
 * non-favorites when they exists. RawItems can be hidden as well.
 *
 * Ex: [A*, B*, C, D] being A and B favorites. This list should be mapped to -> [Fav Header, A, B, Non-Fav Header, C, D]
 *
 * Use case 1 - No favorites: Should show plain list without headers in alphabetical order.
 * Use case 2 - Mix favorites and non-favorites: Should show list with both headers (favorites first) and in alphabetical order.
 * Use case 3 - Favorites only: Should show list with favorites headers only and in alphabetical order.
 * Use case 4 - Hidden items: Should hide hidden items from the list and obey rules above.
 */
object Mapper {

    fun processItems(rawItems: List<RawItem>): ListRepresentation {
        val nonHiddenItems = rawItems.filterNot { it.isHidden }
        val favoritesRaw = nonHiddenItems.filter { it.isFavorite }.sortedBy { it.value }
        val nonFavoritesRaw = nonHiddenItems.filterNot { it.isFavorite }.sortedBy { it.value }

        val favoritesSection = if (favoritesRaw.isNotEmpty()) {
            listOf(ProcessedItem.Header("Favorites")) + favoritesRaw.map { ProcessedItem.SelectableItem(it.value) }
        } else {
            listOf()
        }

        val nonFavoritesItems = nonFavoritesRaw.map { ProcessedItem.SelectableItem(it.value) }

        val nonFavoritesSection = if (favoritesRaw.isNotEmpty() && nonFavoritesItems.isNotEmpty()) {
            listOf(ProcessedItem.Header("Non-Favorites")) + nonFavoritesItems
        } else {
            nonFavoritesItems
        }

        return ListRepresentation(favoritesSection + nonFavoritesSection)
    }
}