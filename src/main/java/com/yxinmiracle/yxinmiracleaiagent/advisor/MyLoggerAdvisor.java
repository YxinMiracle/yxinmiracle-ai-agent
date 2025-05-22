package com.yxinmiracle.yxinmiracleaiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * A simple logger advisor that logs the request and response messages.
 *
 * @author Christian Tzolov
 */

@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return 0;
	}

	private AdvisedRequest before(AdvisedRequest request) { // adviseContext
		log.info("AI Request: {}", request.userText());
		return request;
	}

	private void observeAfter(AdvisedResponse advisedResponse) {
		log.info("AI Response:{}", advisedResponse.response().getResult().getOutput().getText());
	}

	@Override
	public String toString() {
		return MyLoggerAdvisor.class.getSimpleName();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {

		advisedRequest = before(advisedRequest);

		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

		// 获取上下文
		Object contextMessage = advisedResponse.adviseContext().get("contextMessage");

		observeAfter(advisedResponse);

		return advisedResponse;
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {

		advisedRequest = before(advisedRequest);

		Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);

		return new MessageAggregator().aggregateAdvisedResponse(advisedResponses, this::observeAfter);
	}

}
