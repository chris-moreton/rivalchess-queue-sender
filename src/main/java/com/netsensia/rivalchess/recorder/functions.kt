package com.netsensia.rivalchess.recorder

import com.netsensia.rivalchess.utils.JmsSender
import com.netsensia.rivalchess.vie.model.EngineSetting
import com.netsensia.rivalchess.vie.model.EngineSettings
import com.netsensia.rivalchess.vie.model.TournamentType
import kotlin.random.Random

fun modifyEngineSettings(engineSetting: EngineSetting, nodeVariation: Int): EngineSetting {
    val nodes = engineSetting.maxNodes
    val halfWindow = (nodes / 100) * nodeVariation
    val r = Random.nextInt(halfWindow * 2)
    val newNodes = (nodes - halfWindow) + r
    return EngineSetting(engineSetting.version, newNodes, engineSetting.maxMillis,engineSetting.openingBook)
}

fun createMatches(engineSettings: EngineSettings, nodeVariation: Int, matchCount: Int) {
    if (matchCount > 0) {
        val whiteEngine = if (matchCount % 2 == 1) engineSettings.engine1 else engineSettings.engine2
        val blackEngine = if (matchCount % 2 == 0) engineSettings.engine1 else engineSettings.engine2

        val newEngineSettings = EngineSettings(
                modifyEngineSettings(whiteEngine, nodeVariation),
                modifyEngineSettings(blackEngine, nodeVariation))

        println(newEngineSettings)
        JmsSender.send("MatchRequests", newEngineSettings)
        createMatches(engineSettings, nodeVariation, matchCount - 1)
    }
}

fun createTournament(engineSettings: List<EngineSetting>, tournamentType: TournamentType, nodeVariation: Int, roundCount: Int) {

}