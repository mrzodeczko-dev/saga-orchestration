import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Hotel service publishes FAILURE reply when reservation is rejected"
    label "hotel_reserve_failure"
    input {
        triggeredBy("hotel_reserve_failure()")
    }
    outputMessage {
        sentTo "x.saga.replies"
        body([
                sagaId: $(producer(regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")),
                        consumer("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
                step  : "HOTEL",
                action: "RESERVE",
                status: "FAILURE",
                reason: $(producer(regex(".+")),
                        consumer("No cabins available at destination"))
        ])
        headers {
            header("contentType", "application/json")
        }
    }
}
