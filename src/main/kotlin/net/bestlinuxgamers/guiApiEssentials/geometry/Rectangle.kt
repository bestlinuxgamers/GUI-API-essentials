package net.bestlinuxgamers.guiApiEssentials.geometry

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.bukkit.inventory.ItemStack

/**
 * Komponente zum Darstellen eines statischen Rechtecks.
 * @param width Breite des Rechtecks.
 * @param height Höhe des Rechtecks.
 * @param item Item, aus der die Komponente bestehen soll.
 */
open class Rectangle(width: Int, height: Int, item: ItemStack?) :
    GuiComponent(ReservedSlots(height, width), renderFallback = item, static = true, componentTick = false) {
    override fun beforeRender(frame: Long) {}

    override fun onComponentTick(tick: Long, frame: Long) {}
}
