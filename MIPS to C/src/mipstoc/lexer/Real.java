package mipstoc.lexer;

public class Real extends Token {
    
    public float value;
    
    public Real(float value) {
        super(Tag.REAL);
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "" + value;
    }
}
