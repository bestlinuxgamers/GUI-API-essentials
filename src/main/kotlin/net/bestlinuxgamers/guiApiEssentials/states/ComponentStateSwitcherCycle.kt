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
) : ComponentStateSwitcherLambda<T>(
    reservedSlots,
    generateStateComponents(stateComponents, stateSwitchAction, cycle),
    firstState
)

private fun <T> generateStateComponents(
    stateComponents: Map<T, GuiComponent>,
    stateSwitchAction: (T, T) -> Unit,
    cycle: List<T>
): Map<T, Pair<GuiComponent, (T) -> T>> {
    return stateComponents.mapValues { entry ->
        val state = entry.key

        val cycleIndex = cycle.indexOf(state)
        val nextState = if (cycleIndex > -1) {
            cycle[(cycleIndex + 1) % cycle.size] ?: state
        } else state

        return@mapValues Pair(entry.value) { oldState ->
            stateSwitchAction(oldState, state)
            nextState
        }
    }

}
