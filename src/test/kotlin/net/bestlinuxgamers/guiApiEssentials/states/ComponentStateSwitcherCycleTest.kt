package net.bestlinuxgamers.guiApiEssentials.states

import net.bestlinuxgamers.guiApi.component.GuiComponent
import net.bestlinuxgamers.guiApi.component.essentials.EmptyComponent
import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ComponentStateSwitcherCycleTest {

    @Test
    fun testDefault() {
        val yesterdayComp = EmptyComponent()
        val todayComp = EmptyComponent()
        val tomorrowComp = EmptyComponent()

        val states = mapOf<DayState, GuiComponent>(
            DayState.YESTERDAY to yesterdayComp,
            DayState.TOMORROW to tomorrowComp,
            DayState.TODAY to todayComp,
        )

        lateinit var lastState: DayState
        lateinit var newState: DayState

        val switcher = ComponentStateSwitcherCycle(ReservedSlots(1, 1), states, { last, new ->
            lastState = last
            newState = new
        })

        assertEquals(DayState.YESTERDAY, switcher.getState())
        assertEquals(DayState.TOMORROW, switcher.getNextState())
        assertEquals(yesterdayComp, switcher.getComponentOfIndex(0))
        assertEquals(DayState.YESTERDAY, lastState)
        assertEquals(DayState.YESTERDAY, newState)

        switcher.nextState()

        assertEquals(DayState.TOMORROW, switcher.getState())
        assertEquals(DayState.TODAY, switcher.getNextState())
        assertEquals(tomorrowComp, switcher.getComponentOfIndex(0))
        assertEquals(DayState.YESTERDAY, lastState)
        assertEquals(DayState.TOMORROW, newState)

        switcher.nextState()

        assertEquals(DayState.TODAY, switcher.getState())
        assertEquals(DayState.YESTERDAY, switcher.getNextState())
        assertEquals(todayComp, switcher.getComponentOfIndex(0))
        assertEquals(DayState.TOMORROW, lastState)
        assertEquals(DayState.TODAY, newState)

        switcher.nextState()

        assertEquals(DayState.YESTERDAY, switcher.getState())
        assertEquals(DayState.TOMORROW, switcher.getNextState())
        assertEquals(yesterdayComp, switcher.getComponentOfIndex(0))
        assertEquals(DayState.TODAY, lastState)
        assertEquals(DayState.YESTERDAY, newState)

    }

    @Test
    fun testCycle() {
        val states = mapOf<DayState, GuiComponent>(
            DayState.TOMORROW to EmptyComponent(),
            DayState.TODAY to EmptyComponent(),
            DayState.YESTERDAY to EmptyComponent(),
        )
        val cycle = listOf(
            DayState.YESTERDAY, DayState.TODAY, DayState.TOMORROW
        )
        val switcher = ComponentStateSwitcherCycle(ReservedSlots(1, 1), states, { _, _ -> }, cycle)

        assertEquals(DayState.YESTERDAY, switcher.getState())
        assertEquals(DayState.TODAY, switcher.getNextState())

        switcher.nextState()

        assertEquals(DayState.TODAY, switcher.getState())
        assertEquals(DayState.TOMORROW, switcher.getNextState())

        switcher.nextState()

        assertEquals(DayState.TOMORROW, switcher.getState())
        assertEquals(DayState.YESTERDAY, switcher.getNextState())

        switcher.nextState()
        assertEquals(DayState.YESTERDAY, switcher.getState())
        assertEquals(DayState.TODAY, switcher.getNextState())

    }

    @Test
    fun testMalformedCycle() {
        val states = mapOf<DayState, GuiComponent>(
            DayState.TOMORROW to EmptyComponent(),
            DayState.TODAY to EmptyComponent(),
            DayState.YESTERDAY to EmptyComponent(),
        )
        val cycle = listOf(
            DayState.YESTERDAY, DayState.OVER_TOMORROW
        )
        val switcher = ComponentStateSwitcherCycle(ReservedSlots(1, 1), states, { _, _ -> }, cycle)

        assertThrows<NoSuchElementException> { switcher.nextState() }
    }

    private enum class DayState {
        YESTERDAY,
        TODAY,
        TOMORROW,
        OVER_TOMORROW,
        IDK_ANYMORE
    }

}
