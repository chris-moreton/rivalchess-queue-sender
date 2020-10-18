package com.netsensia.rivalchess.generator

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController {

    val counter = AtomicLong()

    @PostMapping("/matchRequest")
    fun matchRequest(@RequestBody matchRequestPayload: MatchRequestPayload) = 1

}