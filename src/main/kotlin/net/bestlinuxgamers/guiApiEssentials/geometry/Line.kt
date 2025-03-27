package net.bestlinuxgamers.guiApiEssentials.geometry

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.bukkit.inventory.ItemStack

/**
 * Komponente zum Darstellen einer statischen Linie.
 * @param length Länge der Linie.
 * @param horizontal Ob die linie horizontal sein soll (sonst vertikal).
 * @param item Item, aus der die Komponente bestehen soll.
 */
open class Line(length: Int, horizontal: Boolean, item: ItemStack?) : GuiComponent(
    if (horizontal) {
        ReservedSlots(1, length)
    } else {
        ReservedSlots(length) { _ -> arrayOf(true) }
    }, static = true, componentTick = false, renderFallback = item
) {
    override fun beforeRender(frame: Long) {}

    override fun onComponentTick(tick: Long, frame: Long) {}
}
