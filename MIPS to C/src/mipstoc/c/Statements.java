/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipstoc.c;

public class Statements extends Statement {
    
    private Statement stmt1;
    private Statement stmt2;
    
    public Statements(Statement s1, Statement s2) {
        stmt1 = s1;
        stmt2 = s2;
    }
    
    @Override
    public void gen() {
        if(stmt1 == Statement.Null)
            stmt2.gen();
        else if(stmt2 == Statement.Null)
            stmt1.gen();
        else {
            stmt1.gen();
            stmt2.gen();
        }
    }
}
