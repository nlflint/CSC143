package Tokenizer.Tokens;

/**
 * Created by nate on 6/14/14.
 */
public class UnknownToken extends Token {
    private String unkownTokenName;

    public UnknownToken(String unknownToken) {
        this.unkownTokenName = unknownToken;
    }

    public String getUnkownTokenName() { return unkownTokenName;}
}
