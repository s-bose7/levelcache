package live.levelcache.storage;

import java.util.Map;

public class InMemoryLFUStorage implements StorageEngine {

	@Override
	public void createPair(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map.Entry<String, String>  getEvictedKeyIfAny() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSnapShort() {
		// TODO Auto-generated method stub
		return null;
	}

}
