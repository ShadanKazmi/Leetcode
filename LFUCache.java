import java.util.*;

class LFUCache {

    int capacity;
    int size;
    int minFreq;
    Map<Integer, Node> cache;
    Map<Integer, Doubly> freqList;

    public LFUCache(int capacity) {
        
        this.capacity = capacity;
        size = 0;
        minFreq = 0;
        cache = new HashMap<>();
        freqList = new HashMap<>();
    }
    
    public int get(int key) {
        
        Node current = cache.get(key);

        if(current == null)
            return -1;

        updateFreq(current);

        return current.value;

    }
    
    public void put(int key, int value) {
        
        if(capacity == 0)
            return;
        
        if(cache.containsKey(key)){

            Node current = cache.get(key);
            current.value = value;
            updateFreq(current);            
        }
        else{
            size++;
            if(size > capacity){

                Doubly minFreqList = freqList.get(minFreq);
                cache.remove(minFreqList.tail.prev.key);
                minFreqList.remove(minFreqList.tail.prev);
                size--;
            }

            minFreq = 1;

            Node newNode = new Node(key, value);

            Doubly newList =  freqList.getOrDefault(1, new Doubly());
            newList.insert(newNode);

            freqList.put(1, newList);

            cache.put(key, newNode);

        }
        
    }

    public void updateFreq(Node current){

        int freq = current.freq;
        Doubly curFreqList = freqList.get(freq);

        curFreqList.remove(current);

        if(freq == minFreq && curFreqList.size == 0)
            minFreq++;

        current.freq++;

       Doubly newList =  freqList.getOrDefault(current.freq, new Doubly());
       newList.insert(current);

       freqList.put(current.freq, newList);
    }

    class Node{

        int key, value, freq;
        Node prev, next;

        public Node(int key, int value){

            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class Doubly{

        Node head, tail;
        int size;

        public Doubly(){

            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        void insert(Node newNode){
            
            Node temp = head.next;

            newNode.next = temp;
            newNode.prev = head;
            head.next = newNode;           
            temp.prev = newNode;

            size++;
        }

        void remove(Node current){

            Node tempPrev = current.prev;
            Node tempNext = current.next;
            tempPrev.next = tempNext;
            tempNext.prev = tempPrev;
            size--;
        }
    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
