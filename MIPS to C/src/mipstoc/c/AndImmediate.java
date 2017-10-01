/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;

import mipstoc.lexer.Token;
import mipstoc.lexer.Word;

public class AndImmediate extends Statement {
    
    private Word output, operand1;
    private Token operand2;
    
    public AndImmediate(Word o, Word op1, Token op2) {
        output = o;
        operand1 = op1;
        operand2 = op2;
    }
    
    @Override
    public void gen() {
        emit(output.toString() + " = " + operand1.toString() + " & " +
                operand2.toString() + ";");
    }
}
