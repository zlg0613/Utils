//: net/mindview/util/New.java
// Utilities to simplify generic container creation
// by using type argument inference.
package cn.zlg.util;
import java.util.*;

public class New {
  public static <K,V> HashMap<K,V> hashMap() {
    return new HashMap<K,V>();
  }
  
  public static <K,V> HashMap<K,V> hashMap(int initCapacity) {
	  return new HashMap<K,V>(initCapacity);
  }
  
  public static <K,V> HashMap<K,V> hashMap(int initCapacity,float loadFactor) {
	  return new HashMap<K,V>(initCapacity,loadFactor);
  }
  
  public static <K,V> TreeMap<K,V> treeMap() {
	    return new TreeMap<K,V>();
  }
  
  
  public static <T> ArrayList<T> arraylist() {
    return new ArrayList<T>();
  }
  
  public static <T> ArrayList<T> arraylist(int initCapacity) {
	  return new ArrayList<T>(initCapacity);
  }
  
  public static <T> LinkedList<T> linkedlist() {
    return new LinkedList<T>();
  }
  
  public static <T> Set<T> hashset() {
    return new HashSet<T>();
  }	
  
  public static <T> Queue<T> queue() {
    return new LinkedList<T>();
  }
  
  // Examples:
  public static void main(String[] args) {
    Map<String, List<String>> sls = New.hashMap();
    List<String> ls = New.arraylist();
    Queue<String> qs = New.queue();
  }
} ///:~
