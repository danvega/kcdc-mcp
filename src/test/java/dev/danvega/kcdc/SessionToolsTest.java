package dev.danvega.kcdc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionToolsTest {

    @Autowired
    SessionTools sessionTools;

    @Test
    void testCountSessionsByDate() {
        Map<String, Long> sessionsByDate = sessionTools.countSessionsByDate();

        assertThat(sessionsByDate).isNotEmpty();
        assertThat(sessionsByDate.keySet()).allMatch(date -> date != null && !date.isEmpty());
        assertThat(sessionsByDate.values()).allMatch(count -> count > 0);

        // Verify we have sessions for KCDC 2025 dates
        assertThat(sessionsByDate.keySet()).contains("2025-08-13", "2025-08-14", "2025-08-15");
    }


    @Test
    void testFindAllSessions() {
        var allSessions = sessionTools.findAllSessions();
        
        assertThat(allSessions).isNotEmpty();
        assertThat(allSessions).hasSize(202); // Expected total from KCDC data
        
        // Verify session structure
        var firstSession = allSessions.get(0);
        assertThat(firstSession.title()).isNotBlank();
        assertThat(firstSession.day()).isNotBlank();
        assertThat(firstSession.speakers()).isNotEmpty();
        assertThat(firstSession.room()).isNotBlank();
    }

    @Test
    void testSessionDataConsistency() {
        Map<String, Long> sessionsByDate = sessionTools.countSessionsByDate();
        var allSessions = sessionTools.findAllSessions();
        
        // Verify total counts match between both tools
        long expectedTotal = sessionsByDate.values().stream().mapToLong(Long::longValue).sum();
        assertThat(allSessions).hasSize((int) expectedTotal);
        
        // Verify each date has corresponding sessions
        for (String date : sessionsByDate.keySet()) {
            long sessionsForDate = allSessions.stream()
                    .filter(session -> date.equals(session.day()))
                    .count();
            assertThat(sessionsForDate).isEqualTo(sessionsByDate.get(date));
        }
    }
}