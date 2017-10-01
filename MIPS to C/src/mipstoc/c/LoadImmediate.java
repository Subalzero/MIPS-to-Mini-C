/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.lexer.Token;
import mipstoc.lexer.Word;

public class LoadImmediate extends Statement {
    
    private Word register;
    private Token number;
    public LoadImmediate(Word w, Token n) {
        register = w;
        number = n;
    }
    
    @Override
    public void gen() {
        emit(register.toString() + " = " + number.toString() + ";");
    }
}
