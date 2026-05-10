package net.bestlinuxgamers.guiApiEssentials.border

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.bukkit.inventory.ItemStack

open class RectangleBorder(width: Int, height: Int, item: ItemStack?) :
    GuiComponent(ReservedSlots(height) {
        when (it) {
            0, height - 1 -> {
                ReservedSlots.generateReservedRow(width, true)
            }

            else -> {
                Array(width) { it2 ->
                    it2 == 0 || it2 == width - 1
                }
            }
        }
    }, static = true, componentTick = false, renderFallback = item) {
    override fun beforeRender(frame: Long) {}

    override fun onComponentTick(tick: Long, frame: Long) {}
}
