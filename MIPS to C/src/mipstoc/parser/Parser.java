/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.parser;

import mipstoc.lexer.*;
import mipstoc.c.*;
import java.util.*;

/**
 * This class creates Parse Trees.
 * 
 * @author Alzer Casino
 *
 */
public class Parser {
	
	private static class JumpLabels {
		Word label;
		int line;
		
		public JumpLabels(Word l, int lin) {
			label = l;
			line = lin;
		}
	}
    
    public static String output = "";
    private Token look;
    private Lexer lex;
    private LinkedList<Word> labels = new LinkedList<>();
    private LinkedList<JumpLabels> jumpLabels = new LinkedList<>();
    private String[] registers = 
        {
            "$zero", "$t0", "$t1", "$t2", "$t3", "$t4",
            "$t5", "$t6", "$t7", "$t8", "$t9", "$f0",
            "$f1", "$f2", "$f3", "$f4", "$f5", "$f6",
            "$f7", "$f8", "$f9"
        };
    
    /**
     * Creates a new Parser.
     * @param l The Lexical Analyzer.
     */
    public Parser(Lexer l) {
        lex = l;
    }
    
    /**
     * Move to the next token.
     */
    private void move() {
        look = lex.scan();
    }
    
    /**
     * Emits string to a display.
     */
    private void emit(String s) {
        output += s + "\n";
        System.out.println(s);
    }
    
    /**
     * Generates error message.
     * @param s The error message.
     */
    private void error(String s) {
        throw new Error("near line " + lex.line + ": " +  s);
    }
    
    /**
     * Checks the string if its a register.
     */
    private void checkRegisters(String reg) {
        for(int i = 0; i < registers.length; i++) {
            if(registers[i].equals(reg))
                return;
        }
        error("unknown register");
    }
    
    /**
     * Checks if registers are valid for storage.
     */
    private void checkStorageReg(String reg) {
        for(int i = 1; i < registers.length; i++) {
            if(registers[i].equals(reg))
                return;
        }
        error("invalid register storage");
    }
    
    /**
     * Checks if the object is null.
     */
    private void checkNullObject() {
    	if(!(look instanceof Word)) {
    		error("syntax error");
    	}
    }
    
    /**
     * Checks if a jumping label is valid.
     */
    private void checklabels() {
    	for(int i = 0; i < jumpLabels.size(); i++) {
    		if(labels.isEmpty()) {
    			throw new Error("near line " + jumpLabels.get(i).line + ": unknown label.");
    		}
    		for(int j = 0; j < labels.size(); j++) {
    			if(jumpLabels.get(i).label.toString().equals(labels.get(j).toString()))
    				break;
    			else if(j == labels.size() - 1)
    				throw new Error("near line " + jumpLabels.get(i).line + ": unknown label.");
    		}
    	}
    }
    
    private void match(int tag) {
        if(look.tag == tag)
            move();
        else 
            error("syntax error");
    }
    
    /**
     * Starts the parser.
     */
    public void program() {
        move();
        Statement s = stmts();
        checklabels();
        emit("{");
        decls();
        s.gen();
        emit("}");
    }
    
    private void decls() {
        declRegisters();
    }
    
    private void declRegisters() {
        emit("\tlong $zero = 0, $t0, $t1, $t2, $t3, $t4;");
        emit("\tlong $t5, $t6, $t7, $t8, $t9;");
        emit("\tfloat $f0, $f1, $f2, $f3, $f4;");
        emit("\tfloat $f5, $f6, $f7, $f8, $f9;");
        emit("");
    }
    
    private Statement stmts() {
        if(look.tag == '\0')
            return Statement.Null;
        return new Statements(stmt(), stmts());
    }
    
    private Statement stmt() {
        switch(look.tag) {
            case Tag.LABEL:
                Word label = (Word)look;
                match(Tag.LABEL);
                labels.addLast(label);
                match(':');
                return new Label(label);
            case Tag.ADD:
                move();
                checkNullObject();
                Word output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                Word operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                Word operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Add(output, operand1, operand2);
            case Tag.ADDI:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                Token numoperand2 = look;
                if(numoperand2 instanceof Num)
                    match(Tag.NUM);
                else if(numoperand2 instanceof Real)
                    match(Tag.REAL);
                else
                    error("syntax error");
                return new AddImmediate(output, operand1, numoperand2);
            case Tag.SUB:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Sub(output, operand1, operand2);
            case Tag.MULT:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Mult(output, operand1, operand2);
            case Tag.DIV:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Div(output, operand1, operand2);
            case Tag.AND:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new And(output, operand1, operand2);
            case Tag.ANDI:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                numoperand2 = look;
                if(numoperand2 instanceof Num)
                    match(Tag.NUM);
                else if(numoperand2 instanceof Real)
                    match(Tag.REAL);
                else
                    error("syntax error");
                return new AndImmediate(output, operand1, numoperand2);
            case Tag.OR:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Or(output, operand1, operand2);
            case Tag.ORI:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                numoperand2 = look;
                if(numoperand2 instanceof Num)
                    match(Tag.NUM);
                else if(numoperand2 instanceof Real)
                    match(Tag.REAL);
                else
                    error("syntax error");
                return new OrImmediate(output, operand1, numoperand2);
            case Tag.XOR:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                checkNullObject();
                operand2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand2.toString());
                return new Xor(output, operand1, operand2);
            case Tag.XORI:
                move();
                checkNullObject();
                output = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(output.toString());
                match(',');
                checkNullObject();
                operand1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(operand1.toString());
                match(',');
                numoperand2 = look;
                if(numoperand2 instanceof Num)
                    match(Tag.NUM);
                else if(numoperand2 instanceof Real)
                    match(Tag.REAL);
                else
                    error("syntax error");
                return new XorImmediate(output, operand1, numoperand2);
            /*case Tag.LOAD:
                move();
                Word reg = (Word)look;
                match(Tag.REGISTER);
                match(',');
                Word id = (Word)look;
                match(Tag.ID);
                return new LoadWord(reg, id);*/
            case Tag.LOADI:
                move();
                checkNullObject();
                Word reg = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(reg.toString());
                match(',');
                Token num = look;
                if(num instanceof Num)
                    match(Tag.NUM);
                else if(num instanceof Real)
                    match(Tag.REAL);
                else
                    error("syntax error");
                return new LoadImmediate(reg, num);
            case Tag.MOVE:
                move();
                checkNullObject();
                Word store = (Word)look;
                match(Tag.REGISTER);
                checkStorageReg(store.toString());
                match(',');
                checkNullObject();
                reg = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg.toString());
                return new Move(store, reg);
            case Tag.BEQ:
                move();
                checkNullObject();
                Word reg1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg1.toString());
                match(',');
                checkNullObject();
                Word reg2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg2.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BEQ(reg1, reg2, label);
            case Tag.BEQZ:
                move();
                checkNullObject();
                reg = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BEQZ(reg, label);
            case Tag.BGT:
                move();
                checkNullObject();
                reg1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg1.toString());
                match(',');
                checkNullObject();
                reg2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg2.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BGT(reg1, reg2, label);
            case Tag.BGTZ:
                move();
                checkNullObject();
                reg = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BGTZ(reg, label);
            case Tag.BLT:
                move();
                checkNullObject();
                reg1 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg1.toString());
                match(',');
                checkNullObject();
                reg2 = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg2.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BLT(reg1, reg2, label);
            case Tag.BLTZ:
                move();
                checkNullObject();
                reg = (Word)look;
                match(Tag.REGISTER);
                checkRegisters(reg.toString());
                match(',');
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new BLTZ(reg, label);
            case Tag.JUMP:
                move();
                checkNullObject();
                label = (Word)look;
                match(Tag.LABEL);
                jumpLabels.addLast(new JumpLabels(label, lex.line));
                return new Jump(label);
            default:
                error("syntax error");
                break;
        }
        return Statement.Null;
    }
}
