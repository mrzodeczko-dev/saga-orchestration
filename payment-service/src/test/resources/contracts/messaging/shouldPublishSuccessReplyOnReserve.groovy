import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Payment service publishes SUCCESS reply after successful RESERVE (charge)"
    label "payment_reserve_success"
    input {
        triggeredBy("payment_reserve_success()")
    }
    outputMessage {
        sentTo "x.saga.replies"
        body([
                sagaId: $(producer(regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")),
                        consumer("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
                step  : "PAYMENT",
                action: "RESERVE",
                status: "SUCCESS",
                reason: null
        ])
        headers {
            header("contentType", "application/json")
        }
    }
}
