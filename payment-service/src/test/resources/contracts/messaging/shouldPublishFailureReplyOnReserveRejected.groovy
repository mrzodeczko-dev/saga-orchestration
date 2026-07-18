import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Payment service publishes FAILURE reply when charge is rejected"
    label "payment_reserve_failure"
    input {
        triggeredBy("payment_reserve_failure()")
    }
    outputMessage {
        sentTo "x.saga.replies"
        body([
                sagaId: $(producer(regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")),
                        consumer("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
                step  : "PAYMENT",
                action: "RESERVE",
                status: "FAILURE",
                reason: $(producer(regex(".+")),
                        consumer("Credit limit exceeded"))
        ])
        headers {
            header("contentType", "application/json")
        }
    }
}
