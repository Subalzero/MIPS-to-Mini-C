/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.lexer.Word;

public class Or extends Statement {
    
    private Word output, operand1, operand2;
    
    public Or(Word o, Word op1, Word op2) {
        output = o;
        operand1 = op1;
        operand2 = op2;
    }
    
    @Override
    public void gen() {
        emit(output.toString() + " = " + operand1.toString() + " | " +
                operand2.toString() + ";");
    }
}
