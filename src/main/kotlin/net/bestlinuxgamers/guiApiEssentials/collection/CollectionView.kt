package net.bestlinuxgamers.guiApiEssentials.collection

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.essentials.EmptyComponent
import net.bestlinuxgamers.guiApi.component.essentials.ItemComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.bukkit.inventory.ItemStack

/**
 * Komponente zum Darstellen einer [Collection] aus [ItemStack]s in einem GUI.
 * @param reservedSlots Definition der Fläche, auf welcher die Collection dargestellt werden soll.
 * @param collection Collection, welche dargestellt werden soll.
 * @param background Item, welches auf Slots gesetzt wird, welche nicht durch die Collection abgedeckt werden.
 */
class CollectionView(
    reservedSlots: ReservedSlots,
    private val collection: Collection<ItemStack?>,
    background: ItemStack? = null
) : GuiComponent(reservedSlots, static = true, componentTick = false, renderFallback = background) {

    override fun beforeRender(frame: Long) {}
    override fun onComponentTick(tick: Long, frame: Long) {}

    override fun setUp() {
        collection.forEachIndexed { index, it ->
            it?.let { itNn ->
                setComponent(ItemComponent(itNn), index)
            } ?: setComponent(EmptyComponent(), index)
            if (index + 1 >= reservedSlots.totalReserved) return
        }
    }
}
