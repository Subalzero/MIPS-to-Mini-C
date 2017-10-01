package mipstoc.lexer;

public class Token {
    
    public int tag;
    
    public Token(int tag) {
        this.tag = tag;
    }
    
    @Override
    public String toString() {
        return "" + (char)tag;
    }
}
