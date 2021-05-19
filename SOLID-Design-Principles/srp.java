import java.io.*;
import java.util.*;

// Journal class should have only one concern: being able to read/write the entries
// made by the user. Now if we were to add methods to store and read these entries 
// from a db or a URL then it would lead to Journal having a concern that it shouldn't 
// have been bothered with!

// Solution: To create a class Persistance whose only concern is to perform CRUD
// from any specified source!
class Journal {
    List<String> entries;
    int count;

    public Journal() {
        entries = new ArrayList<>();
        count = 0;
    }

    public void write(String entry) {
        entries.add((++count) + ": " + entry);
    }

    public String get(int index) {
        return entries.get(index);
    }

    public Iterator<String> iterator() {
        return entries.iterator();
    }
}

class Persistance {
    public void save(Journal journal, String filename, boolean overwrite) throws Exception {
        if (overwrite || new File(filename).exists()) {
            try {
                PrintStream ps = new PrintStream(new File(filename));
                Iterator<String> it = journal.iterator();
                while (it.hasNext()) {
                    ps.println(it.next());
                }
            } finally {
                // Do nothing
            }
        }
    }
}

class Driver {
    public static void main(String[] args) throws Exception {
        Journal journal = new Journal();
        journal.write("I had an amazing day today!");
        journal.write("I have to study a lot to crack this interview");

        System.out.println(journal.get(0));
        System.out.println(journal.get(1));

        Persistance p = new Persistance();
        p.save(journal, "output.txt", true);
    }
}