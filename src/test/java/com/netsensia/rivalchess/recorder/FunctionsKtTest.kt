package com.netsensia.rivalchess.utils

import com.netsensia.rivalchess.recorder.createTournament
import com.netsensia.rivalchess.utils.mock.MockJmsSender
import com.netsensia.rivalchess.vie.model.EngineMatch
import com.netsensia.rivalchess.vie.model.EngineSetting
import com.netsensia.rivalchess.vie.model.TournamentType
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

@kotlin.ExperimentalUnsignedTypes
class FunctionsKtTest : TestCase() {
    val mockJmsSender = MockJmsSender

    @Test
    fun testTournamentCreation() {
        val players = listOf(
            EngineSetting("1", 1000, 1000, "Test"),
            EngineSetting("2", 1000, 1000, "Test"),
            EngineSetting("3", 1000, 1000, "Test"),
            EngineSetting("4", 1000, 1000, "Test")
        )

        mockJmsSender.engineSettingsList.clear()

        createTournament(players, TournamentType.ROUND_ROBIN, 10, 1, mockJmsSender)

        Assert.assertEquals(12, mockJmsSender.engineSettingsList.size)
    }

    @Test
    fun testTournamentCreationMultipleRounds() {
        val players = listOf(
                EngineSetting("1", 1000, 1000, "Test"),
                EngineSetting("2", 1000, 1000, "Test"),
                EngineSetting("3", 1000, 1000, "Test"),
                EngineSetting("4", 1000, 1000, "Test")
        )

        mockJmsSender.engineSettingsList.clear()

        createTournament(players, TournamentType.ROUND_ROBIN, 10, 3, mockJmsSender)

        Assert.assertEquals(36, mockJmsSender.engineSettingsList.size)
    }
}