package live.levelcache.storage;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


public class InMemoryLRUStorage implements StorageEngine {
	
	private Map<String, Node> cacheMap;
    private Node MRUNode, LRUNode; 
    private final int MAX_CAPACITY;
    private int size;
    private Map.Entry<String, String> evictedPair;
    
	private static class Node {
		String key;
		String value;
        Node prev, next;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }
	
	public InMemoryLRUStorage(int capacity) {
        this.size = 0;
        this.MAX_CAPACITY = capacity;
        this.evictedPair = null;
        this.cacheMap = new HashMap<String, Node>();
        this.MRUNode = this.LRUNode = null;
    }

	@Override
	public void createPair(String key, String value) {
		if (cacheMap.containsKey(key)) {
            // Update the value for existing key
			Node node = cacheMap.get(key);
            node.value = value;
            updateCacheNode(node);
            return;
        }
        if (size == MAX_CAPACITY) {
            // Evict the least recently used key if capacity is reached
            evictKey();
        }
        // Add new key-value pair to the cache
        Node newNode = new Node(key, value);
        if (MRUNode == null) {
            // Cache is empty
            MRUNode = LRUNode = newNode;
        } else {
            newNode.next = MRUNode;
            MRUNode.prev = newNode;
            MRUNode = newNode;
        }
        cacheMap.put(key, newNode);
        ++size;
	}
	
	private void evictKey() {
        Node nodeToRemove = LRUNode;
        evictedPair = new AbstractMap.SimpleEntry<>(LRUNode.key, LRUNode.value);
        if (LRUNode == MRUNode) {
            // Only one node in the cache
            LRUNode = MRUNode = null;
        } else {
            // More than one node in the cache
            LRUNode = LRUNode.prev;
            LRUNode.next = null;
        }
        nodeToRemove.prev = null;
        cacheMap.remove(nodeToRemove.key);
        size = size - 1;
    }
	
	private void updateCacheNode(Node node) {
        if (node == MRUNode) {
            return;
        }
        if (node == LRUNode) {
            // Node is the least recently used
            LRUNode = LRUNode.prev;
            LRUNode.next = null;
        } else {
            // Node is in the middle of the cache
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = MRUNode.prev;
        node.next = MRUNode;
        MRUNode.prev = node;
        MRUNode = node;
    }

	@Override
	public String findByKey(String key) {
		if (!cacheMap.containsKey(key)) {
            return null;
        }
        updateCacheNode(cacheMap.get(key));
        return cacheMap.get(key).value;
	}

	@Override
	public Map.Entry<String, String> getEvictedKeyIfAny() {
		return evictedPair;
	}

	@Override
	public Map<String, String> getSnapShort() {
	    // Use the existing map size to avoid resizing during put operations
	    Map<String, String> snapshortMap = new HashMap<>(cacheMap.size());
	    
	    cacheMap.forEach((key, node) -> snapshortMap.put(key, node.value));
	    return snapshortMap;
	}
}
