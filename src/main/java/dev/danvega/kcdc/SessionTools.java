package dev.danvega.kcdc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SessionTools {

    private static final Logger log = LoggerFactory.getLogger(SessionTools.class);
    private Conference conference;
    private final ObjectMapper objectMapper;

    public SessionTools(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // TIP: Descriptions are important because the model decides what tools to call, not the user
    @Tool(name = "kcdc-sessions-by-date", description = "Returns the count of sessions by date")
    public Map<String, Long> countSessionsByDate() {
        return conference.sessions().stream()
                .collect(Collectors.groupingBy(
                        Session::day,
                        Collectors.counting()
                ));
    }

    @Tool(name = "kcdc-sessions", description = "Returns all sessions for KCDC 2025")
    public List<Session> findAllSessions() {
        return conference.sessions();
    }

    // Additional Tools

    @PostConstruct
    public void init() {
        log.info("Loading Sessions from JSON file 'sessions.json'");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/sessions.json")) {
            this.conference = objectMapper.readValue(inputStream, Conference.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }

}
