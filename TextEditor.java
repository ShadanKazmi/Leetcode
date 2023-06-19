import java.util.*;
class TextEditor {

    ArrayDeque<Character> left, right;
    public TextEditor() {
        left = new ArrayDeque<Character>();
        right = new ArrayDeque<Character>();
    }
    
    public void addText(String text) {
        
        for(char ch : text.toCharArray())//lee
            left.addLast(ch);
    }
    
    public int deleteText(int k) {//4
        int count=0, temp=k;
        
        while(!left.isEmpty() && k-->0){
            left.removeLast();
            count++;
        }

        return Math.min(count, temp);
    }
    
    public String cursorLeft(int k) {
        
        while(!left.isEmpty() && k-->0){

           right.addFirst(left.removeLast());
        }

        k = Math.min(10, left.size());

        Iterator iter = left.descendingIterator();
        
        String res = "";
        while(k-->0) {                  
            res =  iter.next() + res;           
        }

        return res;
    }
    
    public String cursorRight(int k) {
        while(!right.isEmpty() && k-->0){

           left.addLast(right.removeFirst());
        }

        k = Math.min(10, left.size());

        Iterator iter = left.descendingIterator();
        
        String res = "";
        while(k-->0) {                  
            res =  iter.next() + res;           
        }

        return res;

    }
}

/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */