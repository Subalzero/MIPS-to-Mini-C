/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.lexer.Word;

public class BGT extends Statement {
    
    private Word reg1, reg2, label;
    
    public BGT(Word r1, Word r2, Word l) {
        reg1 = r1;
        reg2 = r2;
        label = l;
    }
    
    @Override
    public void gen() {
        emit("if" + "(" + reg1.toString() + " > " + reg2.toString() + ")");
        emit("\t" + "goto " + label.toString() + ";");
    }
}
