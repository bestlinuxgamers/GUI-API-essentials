package net.bestlinuxgamers.guiApiEssentials.states

import net.bestlinuxgamers.guiApi.component.util.ReservedSlots
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StateSwitcherTest {

    @Test
    fun testDefault() {
        lateinit var instance: StateSwitcher<DayState>
        val stateActions = mapOf<DayState, (DayState, StateSwitcher<DayState>) -> DayState>(
            DayState.TODAY to { last, inst -> DayState.TOMORROW },
            DayState.YESTERDAY to { last, inst -> DayState.TODAY },
            DayState.TOMORROW to { last, inst -> DayState.YESTERDAY },
            DayState.OVER_TOMORROW to { last, inst ->
                instance = inst
                DayState.IDK_ANYMORE
            }
        )
        val switcher = StateSwitcher(ReservedSlots(1, 1), stateActions, DayState.YESTERDAY)

        for (i in 1..3) {
            assertEquals(DayState.YESTERDAY, switcher.getState())
            assertEquals(DayState.TODAY, switcher.getNextState())

            switcher.nextState()

            assertEquals(DayState.TODAY, switcher.getState())
            assertEquals(DayState.TOMORROW, switcher.getNextState())

            switcher.nextState()

            assertEquals(DayState.TOMORROW, switcher.getState())
            assertEquals(DayState.YESTERDAY, switcher.getNextState())

            switcher.nextState()
        }

        assertEquals(DayState.YESTERDAY, switcher.getState())
        assertEquals(DayState.TODAY, switcher.getNextState())

        switcher.changeState(DayState.TOMORROW)

        assertEquals(DayState.TOMORROW, switcher.getState())
        assertEquals(DayState.YESTERDAY, switcher.getNextState())

        switcher.changeState(DayState.OVER_TOMORROW)

        assertEquals(DayState.OVER_TOMORROW, switcher.getState())
        assertEquals(DayState.IDK_ANYMORE, switcher.getNextState())
        assertEquals(switcher, instance)

        assertThrows<NoSuchElementException> { switcher.changeState(DayState.IDK_ANYMORE) }

    }


    @Test
    fun testString() {
        val stateActions = mapOf<String, (String, StateSwitcher<String>) -> String>(
            "TODAY" to { last, inst -> "TOMORROW" },
            "YESTERDAY" to { last, inst -> "TODAY" },
            "TOMORROW" to { last, inst -> "YESTERDAY" },
            "OVER_TOMORROW" to { last, inst -> "IDK_ANYMORE" }
        )
        val switcher = StateSwitcher(ReservedSlots(1, 1), stateActions, "YESTERDAY")

        for (i in 1..3) {
            assertEquals("YESTERDAY", switcher.getState())
            assertEquals("TODAY", switcher.getNextState())

            switcher.nextState()

            assertEquals("TODAY", switcher.getState())
            assertEquals("TOMORROW", switcher.getNextState())

            switcher.nextState()

            assertEquals("TOMORROW", switcher.getState())
            assertEquals("YESTERDAY", switcher.getNextState())

            switcher.nextState()
        }


    }

    @Test
    fun testWrongStart() {
        val stateActions = mapOf<DayState, (DayState, StateSwitcher<DayState>) -> DayState>(
            DayState.YESTERDAY to { last, inst -> DayState.TODAY },
            DayState.TODAY to { last, inst -> DayState.TOMORROW },
            DayState.TOMORROW to { last, inst -> DayState.YESTERDAY },
        )
        assertThrows<NoSuchElementException> {
            StateSwitcher(ReservedSlots(1, 1), stateActions, DayState.OVER_TOMORROW)
        }
    }

    private enum class DayState {
        YESTERDAY,
        TODAY,
        TOMORROW,
        OVER_TOMORROW,
        IDK_ANYMORE
    }
}
