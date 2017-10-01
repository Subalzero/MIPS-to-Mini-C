/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.lexer.Word;

public class Label extends Statement {
    
    private Word label;
            
    public Label(Word w) {
        label = w;
    }
    
    @Override
    public void gen() {
        emit(label.toString() + ":");
    }
}
