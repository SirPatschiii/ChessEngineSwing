/**
 * <p>
 *  Utility class for bitwise operations on long bitstreams. This class provides methods to
 *  set, clear, check, and count bits, as well as perform bitwise operations and shifts.
 *  It is implemented using the singleton pattern to prevent instantiation.
 * </p>
 *
 * @author SirPatschiii
 * @version 08.07.2024
 */
public class BitHelper {
    private BitHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     *  Sets a bit at a specified position in the given bitstream.
     * </p>
     *
     * @param bitstream The original bitstream.
     * @param position The position (0 to 63) where the bit should be set.
     * @return The bitstream with the specified bit set.
     */
    public static long setBit(long bitstream, int position) {
        return bitstream | (1L << position);
    }

    /**
     * <p>
     *  Clears a bit at a specified position in the given bitstream.
     * </p>
     *
     * @param bitstream The original bitstream.
     * @param position The position (0 to 63) where the bit should be cleared.
     * @return The bitstream with the specified bit cleared.
     */
    public static long clearBit(long bitstream, int position) {
        return bitstream & ~(1L << position);
    }

    /**
     * <p>
     *  Checks if a bit is set at a specified position in the given bitstream.
     * </p>
     *
     * @param bitstream The bitstream to check.
     * @param position The position (0 to 63) of the bit to check.
     * @return {@code true} if the bit is set (1), {@code false} otherwise (0).
     */
    public static boolean isBitSet(long bitstream, int position) {
        return (bitstream & (1L << position)) != 0;
    }

    /**
     * <p>
     *  Counts the number of bits set to {@code 1} in the given bitstream.
     * </p>
     *
     * @param bitstream The bitstream to count bits from.
     * @return The number of bits set to {@code 1} in the bitstream.
     */
    public static int countBits(long bitstream) {
        return Long.bitCount(bitstream);
    }

    /**
     * <p>
     *  Performs a bitwise AND operation on two bitstreams.
     * </p>
     *
     * @param bitstreamA The first bitstream.
     * @param bitstreamB The second bitstream.
     * @return The result of the bitwise AND operation.
     */
    public static long bitwiseAND(long bitstreamA, long bitstreamB) {
        return bitstreamA & bitstreamB;
    }

    /**
     * <p>
     *  Performs a bitwise OR operation on two bitstreams.
     * </p>
     *
     * @param bitstreamA The first bitstream.
     * @param bitstreamB The second bitstream.
     * @return The result of the bitwise OR operation.
     */
    public static long bitwiseOR(long bitstreamA, long bitstreamB) {
        return bitstreamA | bitstreamB;
    }

    /**
     * <p>
     *  Performs a bitwise XOR operation on two bitstreams.
     * </p>
     *
     * @param bitstreamA The first bitstream.
     * @param bitstreamB The second bitstream.
     * @return The result of the bitwise XOR operation.
     */
    public static long bitwiseXOR(long bitstreamA, long bitstreamB) {
        return bitstreamA ^ bitstreamB;
    }

    /**
     * <p>
     *  Performs a bitwise NOT operation on a bitstream.
     * </p>
     *
     * @param bitstream The bitstream to invert.
     * @return The result of the bitwise NOT operation.
     */
    public static long bitwiseNOT(long bitstream) {
        return ~bitstream;
    }

    /**
     * <p>
     *  Shifts the bits in the bitstream to the left by the specified amount.
     * </p>
     *
     * @param bitstream The bitstream to shift.
     * @param amount The number of positions to shift.
     * @return The shifted bitstream.
     */
    public static long shiftLeft(long bitstream, byte amount) {
        return bitstream << amount;
    }

    /**
     * <p>
     *  Shifts the bits in the bitstream to the right by the specified amount, preserving the sign.
     * </p>
     *
     * @param bitstream The bitstream to shift.
     * @param amount The number of positions to shift.
     * @return The shifted bitstream.
     */
    public static long shiftRight(long bitstream, byte amount) {
        return bitstream >> amount;
    }

    /**
     * <p>
     *  Shifts the bits in the bitstream to the right by the specified amount, without preserving the sign.
     * </p>
     *
     * @param bitstream The bitstream to shift.
     * @param amount The number of positions to shift.
     * @return The shifted bitstream.
     */
    public static long shiftRightWithoutPreservingSign(long bitstream, byte amount) {
        return bitstream >>> amount;
    }

    /**
     * <p>
     *  Prints the binary representation of the given bitstream in a single line.
     * </p>
     *
     * @param bitstream The bitstream to print.
     */
    public static void printBitstreamInLine(long bitstream) {
        for (int i = 0; i < 64; i++) {
            System.out.print(isBitSet(bitstream, i) ? "1" : "0");
        }
    }

    /**
     * <p>
     *  Prints the binary representation of the given bitstream in an 8x8 matrix.
     * </p>
     *
     * @param bitstream The bitstream to print.
     */
    public static void printBitstreamInMatrix(long bitstream) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                long mask = 1L << (i * 8 + j);
                System.out.print((bitstream & mask) != 0 ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
