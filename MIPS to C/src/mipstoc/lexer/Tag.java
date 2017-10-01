package mipstoc.lexer;

public class Tag {
    
    private Tag() {}
    
    public static final int
            ADD   = 256, ADDI    = 257, SUB      = 258, MULT = 259, DIV  = 260,
            AND   = 261, ANDI    = 262, OR       = 263, ORI  = 264, XOR  = 265,
            XORI  = 266, LOAD    = 267, LOADI    = 268, MOVE = 269, JUMP = 270,
            BLT   = 271, BLTZ    = 272, BGT      = 273, BGTZ = 274, BEQ  = 275,
            BEQZ  = 276, SYSCALL = 277, REGISTER = 278, NUM  = 279, REAL = 280,
            LABEL = 281, ID   = 282;
}
