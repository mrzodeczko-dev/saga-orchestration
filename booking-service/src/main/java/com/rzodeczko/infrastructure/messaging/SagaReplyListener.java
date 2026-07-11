package com.rzodeczko.infrastructure.messaging;

import com.rzodeczko.application.event.ReplyStatus;
import com.rzodeczko.application.event.SagaAction;
import com.rzodeczko.application.event.SagaReply;
import com.rzodeczko.application.port.in.HandleSagaReplyUseCase;
import com.rzodeczko.domain.model.saga.SagaStepName;
import com.rzodeczko.infrastructure.messaging.dto.SagaReplyMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SagaReplyListener {
    private final HandleSagaReplyUseCase handleSagaReplyUseCase;

    public SagaReplyListener(@Qualifier("transactionalSagaOrchestrator") HandleSagaReplyUseCase useCase) {
        this.handleSagaReplyUseCase = useCase;
    }

    @RabbitListener(queues = "#{@sagaTopologyProperties.replyQueue}")
    public void onReply(SagaReplyMessage message) {
        handleSagaReplyUseCase.handle(new SagaReply(
                message.sagaId(),
                SagaStepName.valueOf(message.step()),
                SagaAction.valueOf(message.action()),
                ReplyStatus.valueOf(message.status()),
                message.reason()
        ));
    }
}
