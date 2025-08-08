package dev.danvega.kcdc;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConferencePrompts {

    private static final Logger log = LoggerFactory.getLogger(ConferencePrompts.class);

    public List<McpServerFeatures.SyncPromptSpecification> listPrompts() {
        return List.of(sessionsPerDayPrompt());
    }

    private McpServerFeatures.SyncPromptSpecification sessionsPerDayPrompt() {
        // Define the prompt with no arguments
        var prompt = new McpSchema.Prompt(
                "kcdc-sessions-per-day",
                "Analyze KCDC 2025 conference sessions and get a breakdown by day plus total session count",
                List.of() // No arguments needed
        );

        // Create the handler
        return new McpServerFeatures.SyncPromptSpecification(
                prompt,
                (exchange, request) -> {
                    var message = new McpSchema.PromptMessage(
                            McpSchema.Role.USER,
                            new McpSchema.TextContent("As a conference data analyst, please analyze the KCDC 2025 conference sessions and tell me:\n\n" +
                                    "1. How many sessions are scheduled for each day of the conference?\n" +
                                    "2. What is the total number of sessions across all days?\n" +
                                    "3. Provide a summary showing the distribution and any insights about the schedule.\n\n" +
                                    "Use the kcdc-sessions-by-date tool to get the session counts by date. This tool returns a map with dates as keys and session counts as values. " +
                                    "Calculate the total by adding up all the daily counts.")
                    );
                    return new McpSchema.GetPromptResult(
                            "KCDC 2025 sessions analysis by day",
                            List.of(message)
                    );
                }
        );
    }

}
