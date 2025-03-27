package net.bestlinuxgamers.guiApiEssentials.geometry

import org.bukkit.inventory.ItemStack

/**
 * Komponente zum Darstellen einer statischen Linie.
 * @param length Länge der Linie.
 * @param horizontal Ob die linie horizontal sein soll (sonst vertikal).
 * @param item Item, aus der die Komponente bestehen soll.
 */
open class Line(length: Int, horizontal: Boolean, item: ItemStack?) : Rectangle(
    if (horizontal) LINE_WIDTH else length,
    if (horizontal) length else LINE_WIDTH,
    item
) {
    override fun beforeRender(frame: Long) {}

    override fun onComponentTick(tick: Long, frame: Long) {}

    companion object {
        const val LINE_WIDTH = 1
    }
}
