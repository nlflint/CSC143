package Tokenizer.Tokens;

/**
 * Represents any unidentifiable text in a statement
 */
public class UnknownToken extends Token {
    private String unkownTokenName;

    /**
     * Constructor. Saves the name of this unidentifiable token.
     * @param unknownToken the text of the unknown token
     */
    public UnknownToken(String unknownToken) {
        this.unkownTokenName = unknownToken;
    }

    /**
     * Gets the name of the unknonwn token
     * @return the text of the unknown token
     */
    public String getUnknownTokenName() { return unkownTokenName;}
}
