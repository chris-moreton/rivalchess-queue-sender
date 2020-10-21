package com.netsensia.rivalchess.recorder

import com.netsensia.rivalchess.utils.JmsSender
import com.netsensia.rivalchess.vie.model.EngineSetting
import com.netsensia.rivalchess.vie.model.EngineSettings
import com.netsensia.rivalchess.vie.model.MultiMatch
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong
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

        val newEngineSettings = EngineSettings(whiteEngine, blackEngine)

        println(newEngineSettings)
        JmsSender.send("MatchRequests", newEngineSettings)
        createMatches(engineSettings, nodeVariation, matchCount - 1)
    }
}

@RestController
class GreetingController {

    val counter = AtomicLong()

    @PostMapping("/matchRequest")
    fun matchRequest(@RequestBody matchRequestPayload: MultiMatch): ResponseEntity<MatchResponsePayload> {
        createMatches(
                matchRequestPayload.engineSettings,
                matchRequestPayload.nodeVariationPercent,
                matchRequestPayload.matchCount)

        return ResponseEntity.accepted().body(MatchResponsePayload())
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok().body("ok")
    }
}