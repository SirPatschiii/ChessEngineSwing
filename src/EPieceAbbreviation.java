/**
 * Enumeration representing abbreviations for chess piece types.
 * Each enumeration value corresponds to a specific type of chess piece,
 * differentiated by color (W for White, B for Black) and piece type abbreviation.
 *
 * <p>
 * Piece Abbreviations:
 * <ul>
 *     <li>{@code WP} - White Pawn</li>
 *     <li>{@code WN} - White Knight</li>
 *     <li>{@code WB} - White Bishop</li>
 *     <li>{@code WR} - White Rook</li>
 *     <li>{@code WQ} - White Queen</li>
 *     <li>{@code WK} - White King</li>
 *     <li>{@code BP} - Black Pawn</li>
 *     <li>{@code BN} - Black Knight</li>
 *     <li>{@code BB} - Black Bishop</li>
 *     <li>{@code BR} - Black Rook</li>
 *     <li>{@code BQ} - Black Queen</li>
 *     <li>{@code BK} - Black King</li>
 * </ul>
 * </p>
 *
 * <p>
 * The enumeration provides a standardized set of abbreviations for each chess piece type,
 * facilitating easy identification and representation of pieces in chess engine logic.
 * </p>
 *
 * <p>
 * Note: Each abbreviation follows the convention of using a color prefix (W for White, B for Black)
 * followed by a single character representing the piece type.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public enum EPieceAbbreviation {
    WP, WN, WB, WR, WQ, WK,
    BP, BN, BB, BR, BQ, BK
}
