package net.lotte.chamomile.admin.common.validation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCollection {

    @NotNull(message = "must not be null")
    private String value;

    @NotNull(message = "must not be null")
    @NotEmpty
    private String[] array;

    @NotNull
    @NotEmpty
    private ArrayList<String> list;

    @NotNull
    @NotEmpty
    private LinkedList<String> linkedList;

    @NotNull
    @NotEmpty
    private HashSet<String> hashSet;

    @NotNull
    @NotEmpty
    private LinkedHashSet<String> linkedHashSet;

    @NotEmpty
    private TreeSet<String> treeSet;

    @NotNull
    @NotEmpty
    private PriorityQueue<String> priorityQueue;

    @NotNull
    @NotEmpty
    private ArrayDeque<String> arrayDeque;

    @NotNull
    @NotEmpty
    private HashMap<String, String> hashMap;

    @NotNull
    @NotEmpty
    private LinkedHashMap<String, String> linkedHashMap;

    @NotNull
    @NotEmpty
    private TreeMap<String, String> treeMap;

    @NotNull
    @NotEmpty
    private Hashtable<String, String> hashtable;

    @NotNull
    @NotEmpty
    private ConcurrentHashMap<String, String> concurrentHashMap;

    public static TestCollection validCollection() {
        return TestCollection.builder()
                .value("value")
                .array(new String[] { "test@example.com" })
                .list(new ArrayList<String>() {{
                    add("Item1");
                    add("Item2");
                }})
                .linkedList(new LinkedList<String>() {{
                    add("LinkedItem1");
                    add("LinkedItem2");
                }})
                .hashSet(new HashSet<String>() {{
                    add("HashSetItem1");
                    add("HashSetItem2");
                }})
                .linkedHashSet(new LinkedHashSet<String>() {{
                    add("LinkedHashSetItem1");
                    add("LinkedHashSetItem2");
                }})
                .treeSet(new TreeSet<String>() {{
                    add("TreeSetItem1");
                    add("TreeSetItem2");
                }})
                .priorityQueue(new PriorityQueue<String>() {{
                    add("PriorityQueueItem1");
                    add("PriorityQueueItem2");
                }})
                .arrayDeque(new ArrayDeque<String>() {{
                    add("ArrayDequeItem1");
                    add("ArrayDequeItem2");
                }})
                .hashMap(new HashMap<String, String>() {{
                    put("key1", "HashMapValue1");
                    put("key2", "HashMapValue2");
                }})
                .linkedHashMap(new LinkedHashMap<String, String>() {{
                    put("key1", "LinkedHashMapValue1");
                    put("key2", "LinkedHashMapValue2");
                }})
                .treeMap(new TreeMap<String, String>() {{
                    put("key1", "TreeMapValue1");
                    put("key2", "TreeMapValue2");
                }})
                .hashtable(new Hashtable<String, String>() {{
                    put("key1", "HashtableValue1");
                    put("key2", "HashtableValue2");
                }})
                .concurrentHashMap(new ConcurrentHashMap<String, String>() {{
                    put("key1", "ConcurrentHashMapValue1");
                    put("key2", "ConcurrentHashMapValue2");
                }})
                .build();
    }
}
