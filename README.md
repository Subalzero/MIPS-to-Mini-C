# MIPS-to-Mini-C
A sub MIPS program to C
<p>
  
  Full program with GUI: https://github.com/Subalzero/Mini-C-to-MIPS-and-Reverse
  
  Grammar rules:
  
    program -> stmts
  
    stmts -> stmt stmts
  
    stmt -> label: stmts |
  
            li  reg, reg, num |
            
            addi  reg, reg, num |
            
            andi  reg, reg, num |
            
            ori  reg, reg, num |
            
            xori  reg, reg, num |
            
            add  reg, reg, reg |
            
            sub  reg, reg, reg |
            
            mult reg, reg, reg |
            
            div  reg, reg, reg |
            
            and  reg, reg, reg |
            
            or  reg, reg, reg |
            
            xor  reg, reg, reg |
            
            j label |
            
            beq  reg, reg, label |
            
            bgt  reg, reg, label |
            
            blt  reg, reg, label |
            
            beqz  reg, label |
            
            bgtz  reg, label |
            
            bltz  reg, label
