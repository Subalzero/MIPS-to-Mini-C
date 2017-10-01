# MIPS-to-Mini-C
A sub MIPS program to C
<p>
  Grammar rules:
  
  <program> -> <stmts>
  <stmts> -> <stmt> <stmts>
  <stmt> -> label: <stmts> |
            li  ,reg1, reg2, num |
            addi  ,reg1, reg2, num |
            andi  ,reg1, reg2, num |
            ori  ,reg1, reg2, num |
            xori  ,reg1, reg2, num |
            add  ,reg1, reg2, reg3 |
            sub  ,reg1, reg2, reg3 |
            mult ,reg1, reg2, reg3 |
            div  ,reg1, reg2, reg3 |
            and  ,reg1, reg2, reg3 |
            or  ,reg1, reg2, reg3 |
            xor  ,reg1, reg2, reg3 |
            j label
