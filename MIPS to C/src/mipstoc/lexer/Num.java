package mipstoc.lexer;

public class Num extends Token {
    
    public int value;
    
    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "" + value;
    }
}
