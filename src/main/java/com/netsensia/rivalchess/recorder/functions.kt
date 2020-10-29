package com.netsensia.rivalchess.recorder

import com.netsensia.rivalchess.utils.`interface`.JmsSenderInterface
import com.netsensia.rivalchess.vie.model.EngineSetting
import com.netsensia.rivalchess.vie.model.EngineMatch
import com.netsensia.rivalchess.vie.model.TournamentType
import kotlin.random.Random

fun modifyEngineMatch(engineSetting: EngineSetting, nodeVariation: Int): EngineSetting {
    val nodes = engineSetting.maxNodes
    val halfWindow = (nodes / 100) * nodeVariation
    val r = Random.nextInt(halfWindow * 2)
    val newNodes = (nodes - halfWindow) + r
    return EngineSetting(engineSetting.version, newNodes, engineSetting.maxMillis,engineSetting.openingBook)
}

fun createMatches(engineMatch: EngineMatch, nodeVariation: Int, matchCount: Int, jmsSender: JmsSenderInterface) {
    if (matchCount > 0) {
        val whiteEngine = if (matchCount % 2 == 1) engineMatch.engine1 else engineMatch.engine2
        val blackEngine = if (matchCount % 2 == 0) engineMatch.engine1 else engineMatch.engine2

        val newEngineMatch = EngineMatch(
                modifyEngineMatch(whiteEngine, nodeVariation),
                modifyEngineMatch(blackEngine, nodeVariation))

        println(newEngineMatch)
        jmsSender.send("MatchRequested", newEngineMatch)
        createMatches(engineMatch, nodeVariation, matchCount - 1, jmsSender)
    }
}

fun createTournament(
        engineMatch: List<EngineSetting>,
        tournamentType: TournamentType,
        nodeVariation: Int,
        roundCount: Int,
        jmsSender: JmsSenderInterface) {

    (0 until roundCount).forEach {
        engineMatch.forEach { white ->
            engineMatch.forEach { black ->
                if (white.version != black.version)
                    createMatches(EngineMatch(white, black), nodeVariation, 1, jmsSender)
            }
        }
    }
}
