package mipstoc.read;

import java.io.*;

public class CodeReader {
    
    private String output;
    private FileReader f;
    private BufferedReader b;
    private int next;
    
    public CodeReader(File file) {
        try {
            f = new FileReader(file);
            output = "";
            b = new BufferedReader(f);
            next = 0;
            read();
        }
        catch(IOException e) {
            e.printStackTrace();
        }      
    }
    
    public CodeReader(String code) {
        output = code;
    }
    
    private void read() {
        try {
            String currentString;
            for(; (currentString = b.readLine()) != null; output += "\n") {
                output += currentString;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getString() {
        return output;
    }
    
    public char nextCharacter() {
        if(next >= output.length())
            return '\0';
        return output.charAt(next++);
    }
}
