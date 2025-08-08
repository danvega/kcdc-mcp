package dev.danvega.kcdc;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public List<ToolCallback> kcdcSessionTools(SessionTools sessionTools) {
		return List.of(ToolCallbacks.from(sessionTools));
	}

	@Bean
	public List<McpServerFeatures.SyncPromptSpecification> kcdcPrompts(ConferencePrompts prompts) {
		return prompts.listPrompts();
	}

	@Bean
	public List<McpServerFeatures.SyncResourceSpecification> kcdcResources(SpeakersResource speakersResource) {
		return speakersResource.listResources();
	}

}
