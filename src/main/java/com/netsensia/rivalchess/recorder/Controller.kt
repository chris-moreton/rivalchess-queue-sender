package com.netsensia.rivalchess.recorder

import com.netsensia.rivalchess.utils.JmsSender
import com.netsensia.rivalchess.vie.model.MultiMatch
import com.netsensia.rivalchess.vie.model.Tournament
import jdk.nashorn.internal.objects.Global
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.*

@RestController
class Controller {

    val jmsSender = JmsSender

    @PostMapping("/matchRequest")
    fun matchRequest(@RequestBody matchRequestPayload: MultiMatch): ResponseEntity<MatchResponsePayload> {
        GlobalScope.async {
            createMatches(
                    matchRequestPayload.engineMatch,
                    matchRequestPayload.nodeVariationPercent,
                    matchRequestPayload.matchCount,
                    jmsSender)
        }

        return ResponseEntity.accepted().body(MatchResponsePayload())
    }

    @PostMapping("/tournamentRequest")
    fun tournamentRequest(@RequestBody tournamentPayload: Tournament): ResponseEntity<MatchResponsePayload> {
        GlobalScope.async {
            createTournament(
                    tournamentPayload.engineSettings,
                    tournamentPayload.tournamentType,
                    tournamentPayload.nodeVariationPercent,
                    tournamentPayload.roundCount,
                    jmsSender)
        }

        return ResponseEntity.accepted().body(MatchResponsePayload())
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok().body("ok")
    }
}