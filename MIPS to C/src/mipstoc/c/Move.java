/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;

import mipstoc.lexer.Word;

public class Move extends Statement {
    
    private Word id, reg;
    
    public Move(Word i, Word r) {
        id = i;
        reg = r;
    }
    
    @Override
    public void gen() {
        emit(id.toString() + " = " + reg.toString() + ";");
    }
}
