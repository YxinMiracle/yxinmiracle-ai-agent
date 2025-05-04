package com.yxinmiracle.yxinmiracleaiagent.advisor;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.MessageAggregator;
import org.springframework.ai.model.ModelOptionsUtils;

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
