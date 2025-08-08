package dev.danvega.kcdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SpeakersResource {

    private static final Logger log = LoggerFactory.getLogger(SpeakersResource.class);
    private final ObjectMapper objectMapper;

    public SpeakersResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<McpServerFeatures.SyncResourceSpecification> listResources() {
        // Define the Dan Vega speaker resource
        var danVegaResource = new McpSchema.Resource(
                "kcdc://speakers/dan-vega",
                "Biographical information and expertise details for speaker Dan Vega - use when users ask about Dan Vega or want speaker background information",
                "application/json",
                "kcdc://speakers/dan-vega",
                null
        );

        // Create the handler
        return List.of(
            new McpServerFeatures.SyncResourceSpecification(
                danVegaResource,
                (exchange, request) -> new McpSchema.ReadResourceResult(
                    List.of(new McpSchema.TextResourceContents(
                        "kcdc://speakers/dan-vega",
                        "application/json",
                        getDanVegaData()
                    ))
                )
            )
        );
    }

    private String getDanVegaData() {
        try {
            Map<String, Object> danVegaInfo = Map.of(
                    "name", "Dan Vega",
                    "title", "Spring Developer Advocate at VMware",
                    "bio", "Dan Vega is a Spring Developer Advocate at VMware with over 20 years of experience in software development. He's passionate about helping developers learn and build amazing applications with Spring Boot, Spring Framework, and the broader Java ecosystem.",
                    "expertise", List.of(
                            "Spring Boot & Spring Framework",
                            "Java Development",
                            "Web Development",
                            "Content Creation & Developer Education",
                            "Spring AI & AI Integration",
                            "Model Context Protocol (MCP)",
                            "Conference Speaking"
                    ),
                    "experience", Map.of(
                            "currentRole", "Spring Developer Advocate at VMware",
                            "yearsExperience", "20+",
                            "background", "Full-stack developer turned developer advocate and content creator"
                    ),
                    "content", Map.of(
                            "youtube", "https://www.youtube.com/@danvega",
                            "blog", "https://www.danvega.dev",
                            "newsletter", "https://www.danvega.dev/newsletter",
                            "twitter", "@therealdanvega",
                            "github", "https://github.com/danvega"
                    ),
                    "speaking", Map.of(
                            "conferences", List.of("KCDC", "SpringOne", "DevNexus", "Connect.Tech"),
                            "topics", List.of(
                                    "Spring Boot & Spring Framework",
                                    "Java Development Best Practices",
                                    "Spring AI and AI Integration",
                                    "Modern Web Development",
                                    "Developer Productivity"
                            )
                    ),
                    "education", Map.of(
                            "focus", "Helping developers learn Spring, Java, and modern development practices",
                            "approach", "Practical, hands-on tutorials and real-world examples",
                            "audience", "Java developers, Spring enthusiasts, and those new to the Spring ecosystem"
                    ),
                    "kcdcSessions", List.of(
                            Map.of(
                                    "title", "Spring into MCP: Model Context Protocol for Java Developers",
                                    "day", "2025-08-14",
                                    "time", "11:15",
                                    "room", "2104A",
                                    "track", "AI and Data Science",
                                    "description", "Explore the Model Context Protocol (MCP) and how Java developers can leverage it to build more intelligent applications with Spring AI"
                            ),
                            Map.of(
                                    "title", "My Favorite Spring Boot 3 Features (And First Look at 4)",
                                    "day", "2025-08-15",
                                    "time", "13:00",
                                    "room", "2211",
                                    "track", "Java",
                                    "description", "Deep dive into the best features of Spring Boot 3 and get an exclusive preview of what's coming in Spring Boot 4"
                            )
                    ),
                    "personalNote", "Known for his clear teaching style, practical approach to complex topics, and genuine enthusiasm for helping developers succeed with Spring and Java technologies."
            );

            return objectMapper.writeValueAsString(danVegaInfo);
        } catch (Exception e) {
            log.error("Error generating Dan Vega speaker data", e);
            return "{}";
        }
    }
}