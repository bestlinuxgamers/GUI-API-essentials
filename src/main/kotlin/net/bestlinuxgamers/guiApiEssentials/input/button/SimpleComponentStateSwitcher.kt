package net.bestlinuxgamers.guiApiEssentials.input.button

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots

@Deprecated(
    message = "Replaced by more generic implementation.",
    replaceWith = ReplaceWith("ComponentStateSwitcherCycleButton", imports = ["net.bestlinuxgamers.guiApiEssentials.input.button"])
)
open class SimpleComponentStateSwitcher<T>(
    reservedSlots: ReservedSlots,
    private val stateComponents: Map<T, GuiComponent>,
    private val stateSwitchAction: (T, T) -> Unit
) : GuiComponent(reservedSlots, componentTick = false) {
    private val states = stateComponents.keys.toList()

    private var state: T = states.first()
    private var currentIndex = -1


    fun getState(): T = state

    /**
     * @throws NoSuchElementException Wenn der Stand nicht gefunden wurde.
     */
    fun setState(nextState: T) {
        val lastState = this.state
        changeState(nextState)
        if (lastState !== nextState) {
            stateSwitchAction(lastState, nextState)
        }
    }

    private fun changeState(nextState: T) {
        setComponent(stateComponents.getValue(nextState), 0, override = true)
        triggerReRender()
        this.state = nextState
    }

    fun nextState() {
        currentIndex = (currentIndex + 1) % states.size
        setState(states[currentIndex])
    }

    override fun beforeRender(frame: Long) {}
    override fun onComponentTick(tick: Long, frame: Long) {}

    init {
        changeState(state)
        setClickable { _, _ -> nextState() }
    }
}
