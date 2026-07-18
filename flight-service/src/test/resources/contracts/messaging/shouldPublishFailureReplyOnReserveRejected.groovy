import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Flight service publishes FAILURE reply when reservation is rejected"
    label "flight_reserve_failure"
    input {
        triggeredBy("flight_reserve_failure()")
    }
    outputMessage {
        sentTo "x.saga.replies"
        body([
                sagaId: $(producer(regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")),
                        consumer("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
                step  : "FLIGHT",
                action: "RESERVE",
                status: "FAILURE",
                reason: $(producer(regex(".+")),
                        consumer("No rocket seats available - passenger on watchlist"))
        ])
        headers {
            header("contentType", "application/json")
        }
    }
}
