package nl.abc.onboarding.framework;

import nl.abc.onboarding.customer.framework.DomainUtils;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DomainUtilsTest {

    @Test
    void generateExternalIdentifier_hasPrefixTimestampAndBase62Suffix_andSuffixLengthIs22() {
        String id = DomainUtils.generateExternalIdentifier();
        assertNotNull(id, "id should not be null");
        assertTrue(id.startsWith("CUST-"), "id must start with 'CUST-' prefix");

        String[] parts = id.split("-", 3);
        assertEquals(3, parts.length, "id must have three parts: prefix, tsBase36, randBase62");

        String prefix = parts[0];
        String tsPart = parts[1];
        String suffix = parts[2];

        assertEquals("CUST", prefix);

        // timestamp parseable from base36
        long ts = Long.parseLong(tsPart, 36);
        assertTrue(ts > 0, "timestamp must be positive");

        // suffix is base62 characters and expected length for 16 random bytes -> 22 chars
        assertTrue(suffix.matches("[0-9A-Za-z]+"), "suffix must be base62 alphanumeric");
        assertEquals(22, suffix.length(), "expected 22 chars for 16 random bytes encoded base62");
    }

    @Test
    void generateExternalIdentifier_isUniqueAcrossManyCalls() {
        final int N = 1000;
        Set<String> seen = new HashSet<>(N);
        for (int i = 0; i < N; i++) {
            String id = DomainUtils.generateExternalIdentifier();
            assertNotNull(id);
            boolean added = seen.add(id);
            assertTrue(added, "duplicate id generated at iteration " + i);
        }
        assertEquals(N, seen.size(), "all generated ids should be unique");
    }
}
