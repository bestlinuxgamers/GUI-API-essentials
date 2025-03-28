package net.bestlinuxgamers.guiApiEssentials.states

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots

/**
 * Komponente, welche eine feste Anzahl verschiedener Zustände durchlaufen kann.
 * Jeder Zustand verändert die Komponente nach Belieben und bestimmt den nächsten Zustand.
 * @param reservedSlots Fläche der Komponente.
 * @param stateActions Eine Map aus Zustand und zugehöriger Aktion, um den gewünschten Zustand zu erstellen.
 * Die Aktion nimmt den vorherigen Zustand und die Instanz des StateSwitcher entgegen
 * und gibt den nächsten Zustand zurück.
 * @param firstState der erste initiale Zustand. Standardmäßig das erste Element von [stateActions].
 * @param autoRender [GuiComponent.autoRender]
 */
open class StateSwitcher<T>(
    reservedSlots: ReservedSlots,
    private val stateActions: Map<T, (T, StateSwitcher<T>) -> T>, //TODO auch event und click-slot übergeben?
    private val firstState: T = stateActions.keys.toList().first(),
    autoRender: Boolean = true
) : GuiComponent(reservedSlots, componentTick = false, autoRender = autoRender) {
    private var nextState: T = firstState
    private var currentState: T = firstState


    /**
     * Ändert den Stand.
     * @param state Der Stand, der angewendet werden soll.
     * @throws NoSuchElementException Wenn der angegebene Stand nicht gefunden wurde.
     */
    fun changeState(state: T) {
        val stateChangeLambda = stateActions.getValue(state)
        val lastState = this.currentState

        this.nextState = stateChangeLambda(lastState, this)

        this.currentState = state
    }

    /**
     * Wendet den nächsten Stand an.
     */
    fun nextState() {
        changeState(nextState)
    }

    /**
     * @return Der aktuelle Zustand.
     */
    fun getState(): T = currentState

    /**
     * @return Der nächste Status, der nach einem Klick angewandt wird.
     */
    fun getNextState(): T = nextState

    override fun beforeRender(frame: Long) {}
    override fun onComponentTick(tick: Long, frame: Long) {}

    init {
        changeState(firstState)
    }
}
