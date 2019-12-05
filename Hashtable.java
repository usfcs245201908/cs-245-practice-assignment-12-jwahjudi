import java.util.ArrayList;

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
    double LOAD_THRESHOLD = 0.5;
    int entries = 0;
    public Hashtable(){
        bucket = new ArrayList<HashNode>();
        for(int i = 0; i < 5; i++)
            bucket.add(null);
    }

    public HashNode getHead(String key){
        return bucket.get(key.hashCode()%bucket.size());
    }

    public boolean containsKey(String key){
        HashNode head = getHead(key);
        if(head == null)
            return false;
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
        HashNode head = getHead(key);
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
        HashNode head = getHead(key);
        if(head == null)
        {
            bucket.set(key.hashCode()%bucket.size(), new HashNode(key,value));
            entries++;
        }
        else{
            while(head.key != key)
                head = head.next;
            if(head.key == key)
                head.value = value;
            else
            {
                HashNode node = new HashNode(key,value);
                node.next = getHead(key);
                bucket.set(key.hashCode()%bucket.size(), new HashNode(key,value));
                entries++;
            }
        }
        if((entries * 1.0)/bucket.size() == LOAD_THRESHOLD){
            ArrayList<HashNode>newBucket = new ArrayList<>(bucket.size()*2);
            bucket = newBucket;
        }
    }

    public String remove(String key) throws Exception{
        HashNode head = bucket.get(key.hashCode()%bucket.size());
        if(head != null){
            if(head.key == key)
            {
                bucket.set(key.hashCode()%bucket.size(), head.next);
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

    public static void main(String[] args){
        Hashtable table = new Hashtable();
        table.put("1", "hello");
        System.out.println(table.containsKey("1"));

    }

}
