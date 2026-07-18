import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Flight service publishes SUCCESS reply after successful RESERVE"
    label "flight_reserve_success"
    input {
        triggeredBy("flight_reserve_success()")
    }
    outputMessage {
        sentTo "x.saga.replies"
        body([
                sagaId: $(producer(regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")),
                        consumer("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
                step  : "FLIGHT",
                action: "RESERVE",
                status: "SUCCESS",
                reason: null
        ])
        headers {
            header("contentType", "application/json")
        }
    }
}
