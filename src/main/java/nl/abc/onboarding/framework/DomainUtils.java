package nl.abc.onboarding.framework;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Framework-level service utilities.
 */
public final class DomainUtils {

    // cryptographically-strong random generator shared across calls
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // number of random bytes to include (16 bytes => 128 bits of randomness)
    private static final int RANDOM_BYTES = 16;

    // prefix to use for external identifiers
    private static final String PREFIX = "CUST";

    // base62 alphabet (0-9, A-Z, a-z)
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    // expected length for 16 random bytes encoded in base62: ceil(128 / log2(62)) = 22
    private static final int RANDOM_BASE62_LENGTH = 22;

    private DomainUtils() {
        // utility class
    }

    /**
     * Generate an external identifier with a custom prefix and extremely low collision probability.
     * Format: CUST-{timestamp_in_base36}-{random_base62_padded}
     *
     * Example: CUST-k9x3p1-0A1b2C... (timestamp for ordering + 128 bits random)
     *
     * @return a new external identifier
     */
    public static String generateExternalIdentifier() {
        long ts = System.currentTimeMillis();
        String tsBase36 = Long.toString(ts, 36);

        byte[] rnd = new byte[RANDOM_BYTES];
        SECURE_RANDOM.nextBytes(rnd);
        String randBase62 = toBase62(rnd, RANDOM_BASE62_LENGTH);

        return PREFIX + "-" + tsBase36 + "-" + randBase62;
    }

    // encode positive byte array to base62 string, pad with '0' to minLen if needed
    private static String toBase62(byte[] bytes, int minLen) {
        // ensure positive BigInteger
        BigInteger value = new BigInteger(1, bytes);
        if (value.equals(BigInteger.ZERO)) {
            // zero => return zeros of minLen
            return "0".repeat(Math.max(1, minLen));
        }
        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = value.divideAndRemainder(base);
            value = divRem[0];
            int rem = divRem[1].intValue();
            sb.append(BASE62[rem]);
        }
        // sb currently contains least-significant-first
        sb.reverse();
        // pad to minLen with leading '0' characters from BASE62[0]
        if (sb.length() < minLen) {
            StringBuilder pad = new StringBuilder();
            for (int i = 0; i < (minLen - sb.length()); i++) pad.append(BASE62[0]);
            pad.append(sb);
            return pad.toString();
        }
        return sb.toString();
    }
}
