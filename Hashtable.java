import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Hashtable {
    class HashNode{
        String key;
        String value;
        HashNode next;
        public HashNode(String k, String v){
            key = k;
            value = v;
            next = null;
        }
    }
    ArrayList<HashNode> bucket;
    double LOAD_THRESHOLD = 0.75;
    int entries = 0;
    public Hashtable(){
        bucket = new ArrayList<HashNode>();
        for(int i = 0; i < 314521; i++)
            bucket.add(null);
    }

    public int getHead(String key){
        return Math.abs(key.hashCode()%bucket.size());
    }

    public boolean containsKey(String key){
        HashNode head = bucket.get(getHead(key));
        if(head == null)
        {
            return false;
        }

        while(head!=null)
        {
            if(head.key == key)
                return true;
            else
                head = head.next;
        }
        return false;
    }

    public String get(String key){
        HashNode head = bucket.get(getHead(key));
        if(head == null)
            return null;
        while(head!=null)
        {
            if(head.key == key)
                return head.value;
            else
                head = head.next;
        }
        return null;
    }

    public void put(String key, String value){
        HashNode head = bucket.get(getHead(key));
        if(head == null){
            bucket.set(getHead(key), new HashNode(key,value));
            entries++;
        }
        else{
            while(head.next != null && head.key != key)
                head = head.next;
            if(head.key == key)
                head.value = value;
            else{
                HashNode node = new HashNode(key,value);
                node.next = bucket.get(getHead(key));
                bucket.set(getHead(key), node);
                entries++;
            }
        }
        if((entries * 1.0)/bucket.size() == LOAD_THRESHOLD){
            ArrayList<HashNode>temp = bucket;
            int prev_size = bucket.size();
            bucket = new ArrayList<>(prev_size*2);
            for(int i = 0; i < prev_size*2; i++)
                bucket.add(null);
            for(int j = 0; j < temp.size(); j++){
                HashNode check = temp.get(j);
                while(check != null){
                    put(check.key, check.value);
                    check = check.next;
                }
            }
        }
    }

    public String remove(String key) throws Exception{
        HashNode head = bucket.get(getHead(key));
        if(head != null){
            if(head.key == key)
            {
                bucket.set(getHead(key), head.next);
                return head.value;
            }
            else{
                HashNode prev = head;
                HashNode current;
                while(prev!=null)
                {
                    current = prev.next;
                    if(current != null && current.key == key)
                    {
                        prev.next = current.next;
                        --entries;
                        return current.value;
                    }
                    prev = current;
                }
            }
        }
        throw new Exception();
    }
}
