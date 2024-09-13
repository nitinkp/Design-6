import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class PhoneDirectory {
    Queue<Integer> q; //for assigning next number
    HashSet<Integer> set; //for lookup

    public PhoneDirectory(int maxNumbers) { //O(n) S.C where n = max numbers
        q = new LinkedList<>();
        set = new HashSet<>();
        for(int i=0; i<maxNumbers; i++) { //add all initially available numbers
            q.offer(i);
            set.add(i);
        }
    }

    public int get() { //O(1) T.C
        if(q.isEmpty()) return -1;
        int num = q.poll(); //take the early inserted number out
        set.remove(num); //remove it from the set too
        return num;
    }

    public boolean check(int number) { //O(1) T.C
        return set.contains(number); //check if given number is in set
    }

    public void release(int number) { //O(1) T.C
        if(set.contains(number)) return; //if the number is already not assigned to anyone, return
        q.offer(number); //else add it to both q
        set.add(number); //and set
    }

    public static void main(String[] args) {
        PhoneDirectory phoneDirectory = new PhoneDirectory(3);
        System.out.println("get " + phoneDirectory.get());
        System.out.println("check 0 " + phoneDirectory.check(0));
        System.out.println("get " + phoneDirectory.get());
        phoneDirectory.release(0);
        System.out.println("check 2 " + phoneDirectory.check(2));
        System.out.println("get " + phoneDirectory.get());
        System.out.println("get " + phoneDirectory.get());
        System.out.println("get " + phoneDirectory.get());
    }
}
