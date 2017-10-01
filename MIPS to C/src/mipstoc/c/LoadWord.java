/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.lexer.Word;

public class LoadWord extends Statement {
    
    private Word register1, register2;
    
    public LoadWord(Word reg1, Word reg2) {
        register1 = reg1;
        register2 = reg2;
    }
    
    @Override
    public void gen() {
        emit(register1.toString() + " = " + register2.toString() + ";");
    }
}
