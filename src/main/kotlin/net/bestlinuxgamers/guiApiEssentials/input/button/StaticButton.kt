package net.bestlinuxgamers.guiApiEssentials.input.button

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * Ein einfacher, statischer Knopf.
 * @param reservedSlots Fläche des Knopfes.
 * @param material Item, aus dem der Knopf bestehen soll.
 * @param clickAction Aktion, die bei einem Klick ausgeführt werden soll.
 * Die Funktion nimmt das click-event und den geklickten Slot an.
 */
open class StaticButton(
    reservedSlots: ReservedSlots,
    material: ItemStack?,
    private val clickAction: (InventoryClickEvent, Int) -> Unit
) : GuiComponent(reservedSlots, static = true, componentTick = false, renderFallback = material) {

    constructor(material: ItemStack?, clickAction: (InventoryClickEvent, Int) -> Unit) : this(
        ReservedSlots(DEFAULT_BUTTON_SIZE, DEFAULT_BUTTON_SIZE),
        material,
        clickAction
    )

    init {
        setClickable { event, clickedComponentSlot -> onButtonClick(event, clickedComponentSlot) }
    }

    override fun beforeRender(frame: Long) {}
    override fun onComponentTick(tick: Long, frame: Long) {}

    open fun onButtonClick(event: InventoryClickEvent, clickedComponentSlot: Int) {
        clickAction(event, clickedComponentSlot)
    }

    companion object {
        private const val DEFAULT_BUTTON_SIZE = 1
    }
}
