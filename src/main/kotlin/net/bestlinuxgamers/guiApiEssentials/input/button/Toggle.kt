package net.bestlinuxgamers.guiApiEssentials.input.button

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots

/**
 * Eine Komponente mit zwei Zuständen. Bei einem Klick wird zwischen diesen Zuständen gewechselt.
 * @param reservedSlots Fläche der Komponente.
 * @param toggledView Komponente, die beim aktivierten Zustand angezeigt wird.
 * @param unToggledView Komponente, die beim deaktivierten Zustand angezeigt wird.
 * @param toggleAction Funktion, die bei der Aktivierung ausgeführt wird.
 * @param unToggleAction Funktion, die bei der Deaktivierung ausgeführt wird.
 */
open class Toggle(
    reservedSlots: ReservedSlots,
    private val toggledView: GuiComponent,
    private val unToggledView: GuiComponent,
    private val toggleAction: () -> Unit,
    private val unToggleAction: () -> Unit
) : GuiComponent(reservedSlots, componentTick = false) {
    private var toggled = false

    override fun beforeRender(frame: Long) {}
    override fun onComponentTick(tick: Long, frame: Long) {}

    init {
        setComponent(unToggledView, 0)
        setClickable { _, _ -> //TODO event und slot weitergeben?
            toggle()
        }
    }

    /**
     * Wechselt den aktuellen Zustand.
     */
    fun toggle() {
        if (toggled) {
            setComponent(unToggledView, 0, override = true)
        } else {
            setComponent(toggledView, 0, override = true)
        }
        toggled = !toggled
        triggerReRender()
    }

    fun isToggled() = toggled

}
