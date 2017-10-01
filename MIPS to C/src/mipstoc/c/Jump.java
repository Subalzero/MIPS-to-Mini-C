/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;

import mipstoc.lexer.Word;

public class Jump extends Statement {
    
    private Word label;
    
    public Jump(Word l) {
        label = l;
    }
    
    @Override
    public void gen() {
        emit("goto " + label.toString() + ";");
    }
}
