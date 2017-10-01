/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;
import mipstoc.parser.*;

public abstract class Node {
    
    public Node() {}
    
    public void emit(String s) {
        Parser.output += "\t" + s + "\n";
        System.out.println("\t" + s);
    }
    
    public abstract void gen();
}
