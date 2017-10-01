package mipstoc.lexer;

import mipstoc.read.CodeReader;
import java.util.*;

/**
 * <b>The Lexical Analyzer.</b>
 * For MIPS source code.
 * Analyzes source codes then turn them into
 * tokens.
 */
public class Lexer {
    
    public int line = 1;
    private char peek = ' ';
    private HashMap<String, Word> words = new HashMap<>();
    private CodeReader c;
    private int counter = -1;
    private int counterMark = 0;
    
    private void reserve(Word w) {
        words.put(w.toString(), w);
    }
    
    public Lexer(CodeReader reader) {
        reserve(new Word("add",        Tag.ADD));
        reserve(new Word("addi",       Tag.ADDI));
        reserve(new Word("sub",        Tag.SUB));
        reserve(new Word("mult",       Tag.MULT));
        reserve(new Word("div",        Tag.DIV));
        reserve(new Word("move",       Tag.MOVE));
        reserve(new Word("lw",         Tag.LOAD));
        reserve(new Word("li",         Tag.LOADI));
        reserve(new Word("and",        Tag.AND));
        reserve(new Word("andi",       Tag.ANDI));
        reserve(new Word("or",         Tag.OR));
        reserve(new Word("ori",        Tag.ORI));
        reserve(new Word("xor",        Tag.XOR));
        reserve(new Word("xori",       Tag.XORI));
        reserve(new Word("blt",        Tag.BLT));
        reserve(new Word("bltz",       Tag.BLTZ));
        reserve(new Word("bgt",        Tag.BGT));
        reserve(new Word("bgtz",       Tag.BGTZ));
        reserve(new Word("beq",        Tag.BEQ));
        reserve(new Word("beqz",       Tag.BEQZ));
        reserve(new Word("j",          Tag.JUMP));
        reserve(new Word("syscall",    Tag.SYSCALL));
        
        c = reader;
    }
    
    private void readch() {
        peek = c.nextCharacter();
    }
    
    /*private boolean readch(char ch) {
        readch();
        if(peek != ch)
            return false;
        peek = ' ';
        return true;
    }*/
    
    public Token scan() {
        for(; ; readch()) {
            if(counter != -1) {
                if(counter < counterMark)
                    counter++;
            }
            if(peek == ' ' || peek == '\t')
                continue;
            else if(peek == '\n')
                line++;
            if(Character.isDigit(peek)) {
                int v = 0;
                do {
                    v = v * 10 + Character.digit(peek, 10);
                    readch();
                }
                while(Character.isDigit(peek));
                if(peek != '.')
                    return new Num(v);
                float x = v; float d = 10;
                for(; ;) {
                    readch();
                    if(!Character.isDigit(peek))
                        break;
                    x = x + Character.digit(peek, 10) / d;
                    d *= 10;
                }
                return new Real(x);
            }
            if(peek == '$') {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(peek);
                    readch();
                }
                while(Character.isLetterOrDigit(peek));
                String s = sb.toString();
                return new Word(s, Tag.REGISTER);
            }
            if(Character.isLetter(peek)) {
                StringBuilder b = new StringBuilder();
                do {
                    b.append(peek);
                    readch();
                }
                while(Character.isLetterOrDigit(peek));
                String s = b.toString();
                if(peek == ':') {
                    return new Word(s, Tag.LABEL);
                }
                Word w = (Word)words.get(s);
                if(w != null) {
                    switch(w.tag) {
                        case Tag.BEQ:
                        case Tag.BGT:
                        case Tag.BLT:
                            counter++;
                            counterMark = 3;
                            break;
                        case Tag.BEQZ:
                        case Tag.BGTZ:
                        case Tag.BLTZ:
                            counter++;
                            counterMark = 2;
                            break;
                        case Tag.JUMP:
                            counter++;
                            counterMark = 1;
                            break;
                    }
                    return w;
                }
                if(counter == counterMark) {
                    counter = -1;
                    return new Word(s, Tag.LABEL);
                }
                return new Word(s, Tag.ID);
            }
            if(peek == ' ' || peek == '\t')
                /*do nothing*/;
            else if(peek == '\n')
                /*do nothing*/;
            else {
                Token tok = new Token(peek);
                peek = ' ';
                return tok;
            }
        }
    }
}
