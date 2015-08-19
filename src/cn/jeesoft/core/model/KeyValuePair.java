package cn.jeesoft.core.model;

import java.io.Serializable;

/**
 * 键值对
 * @author king
 */
public class KeyValuePair<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private K key;
	private V value;
	
	

	public KeyValuePair() {
		super();
	}
	public KeyValuePair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	
	
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
}
