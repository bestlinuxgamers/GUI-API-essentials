package net.bestlinuxgamers.guiApiEssentials.input.button

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import net.bestlinuxgamers.guiApiEssentials.states.ComponentStateSwitcherCycle

/**
 * [ComponentStateSwitcherCycle], aber der Zustand ändert sich bei einem Klick.
 */
open class ComponentStateSwitcherCycleButton<T>(
    reservedSlots: ReservedSlots,
    stateComponents: Map<T, GuiComponent>,
    stateSwitchAction: (T, T) -> Unit = { _, _ -> },
    cycle: List<T> = stateComponents.keys.toList(),
    firstState: T = cycle[0]
) : ComponentStateSwitcherCycle<T>(reservedSlots, stateComponents, stateSwitchAction, cycle, firstState) {
    init {
        setClickable { _, _ -> nextState() }
    }
}
