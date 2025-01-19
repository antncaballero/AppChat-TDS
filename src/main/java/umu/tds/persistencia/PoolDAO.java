package umu.tds.persistencia;

import java.util.HashMap;
/**
 * Clase que implementa un pool de objetos
 */
public enum PoolDAO{
	INSTANCE;
	
	private HashMap<Integer,Object> pool;
	
	private PoolDAO(){
		pool=new HashMap<Integer,Object>();
	}
	
	public void addObject(int id,Object object){
		pool.put(id,object);
	}
	
	public Object getObject(int id){
		return pool.get(id);
	}
	
	public boolean contains(int id){
		return pool.containsKey(id);
	}
	
	public void removeObject(int id) {
		pool.remove(id);
	}
}
