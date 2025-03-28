package net.bestlinuxgamers.guiApiEssentials.states

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots

/**
 * Komponente, welche eine feste Anzahl verschiedener Zustände durchlaufen kann
 * und eine dem Zustand entsprechende Komponente anzeigt.
 * Die Zustände werden in einer festen Reihenfolge durchlaufen.
 * @param reservedSlots Fläche der Komponente.
 * @param stateComponents Alle Zustände und ihre zugehörigen Komponenten.
 * @param stateSwitchAction Eine Funktion, die immer bei der Änderung eines Zustandes ausgeführt wird.
 * Die Funktion nimmt den alten Zustand und den gerade angewendeten Zustand an.
 * @param cycle Die Reihenfolge, in welcher die Zustände abgegangen werden.
 * Standardmäßig die Reihenfolge der Zustände in [stateComponents].
 * @param firstState Der initiale Zustand. Standardmäßig das erste Element von [cycle].
 */
open class ComponentStateSwitcherCycle<T>(
    reservedSlots: ReservedSlots,
    stateComponents: Map<T, GuiComponent>,
    stateSwitchAction: (T, T) -> Unit = { _, _ -> },
    cycle: List<T> = stateComponents.keys.toList(),
    firstState: T = cycle[0]
) : StateSwitcher<T>(
    reservedSlots,
    generateActions(stateComponents, stateSwitchAction, cycle),
    firstState = firstState,
    autoRender = false
)

private fun <T> generateActions(
    stateComponents: Map<T, GuiComponent>,
    stateSwitchAction: (T, T) -> Unit,
    cycle: List<T>
): Map<T, (T, StateSwitcher<T>) -> T> {
    return stateComponents.mapValues { entry ->
        return@mapValues stateLambda@{ oldState: T, instance: StateSwitcher<T> ->
            val state = entry.key
            instance.setComponent(entry.value, 0, override = true)
            stateSwitchAction(oldState, state)
            instance.triggerReRender()

            val cycleIndex = cycle.indexOf(state)
            if (cycleIndex < 0) return@stateLambda state

            return@stateLambda cycle[(cycleIndex + 1) % cycle.size] ?: state
        }
    }
}
