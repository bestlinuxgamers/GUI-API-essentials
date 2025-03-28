package net.bestlinuxgamers.guiApiEssentials.states

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots

/**
 * Komponente, welche eine feste Anzahl verschiedener Zustände durchlaufen kann
 * und eine dem Zustand entsprechende Komponente anzeigt.
 * Jeder Zustand bestimmt dynamisch den nächsten Zustand.
 * @param reservedSlots Fläche der Komponente.
 * @param stateComponents Alle Zustände mit einer zugehörigen Komponente und einer Zustand-übergangs-funktion.
 * Die Funktion nimmt den vorherigen Zustand an und gibt den nächsten Zustand zurück.
 * @param firstState Der initiale Zustand.
 */
open class ComponentStateSwitcherLambda<T>(
    reservedSlots: ReservedSlots,
    stateComponents: Map<T, Pair<GuiComponent, (T) -> T>>,
    firstState: T
) : StateSwitcher<T>(
    reservedSlots,
    generateActions(stateComponents),
    firstState = firstState,
    autoRender = false
)

private fun <T> generateActions(
    stateComponents: Map<T, Pair<GuiComponent, (T) -> T>>,
): Map<T, (T, StateSwitcher<T>) -> T> {
    return stateComponents.mapValues { entry ->
        return@mapValues stateLambda@{ oldState: T, instance: StateSwitcher<T> ->
            val (component, transformationLambda) = entry.value

            instance.setComponent(component, 0, override = true)
            instance.triggerReRender()

            return@stateLambda transformationLambda(oldState)
        }
    }
}
