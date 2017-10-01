/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;

import mipstoc.lexer.*;

public class BEQZ extends Statement {
    
    private Word reg, label;
    
    public BEQZ(Word r, Word l) {
        reg = r;
        label = l;
    }
    
    @Override
    public void gen() {
        emit("if" + "(" + reg.toString() + " == 0)");
        emit("\t" + "goto " + label.toString() + ";");
    }
}
