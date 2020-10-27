package com.netsensia.rivalchess.recorder

import com.netsensia.rivalchess.vie.model.MultiMatch
import com.netsensia.rivalchess.vie.model.Tournament
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class Controller {

    val counter = AtomicLong()

    @PostMapping("/matchRequest")
    fun matchRequest(@RequestBody matchRequestPayload: MultiMatch): ResponseEntity<MatchResponsePayload> {
        createMatches(
                matchRequestPayload.engineSettings,
                matchRequestPayload.nodeVariationPercent,
                matchRequestPayload.matchCount)

        return ResponseEntity.accepted().body(MatchResponsePayload())
    }

    @PostMapping("/tournamentRequest")
    fun tournamentRequest(@RequestBody tournamentPayload: Tournament): ResponseEntity<MatchResponsePayload> {
//        createTournament(
//                tournamentPayload.engines,
//                tournamentPayload.tournamentType,
//                tournamentPayload.nodeVariationPercent,
//                tournamentPayload.roundCount)

        return ResponseEntity.accepted().body(MatchResponsePayload())
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok().body("ok")
    }
}