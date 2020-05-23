package deploy.jsk.common;

import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import gnu.trove.map.hash.THashMap;
import deploy.jsk.baseInt.DataBaseEntityInt;
import deploy.jsk.baseInt.LastQueryLastDeleteTs;

public interface PersonInfoCacheInt {

	Map<String, Multimap<String, DataBaseEntityInt>> personCache = new THashMap<>(2);

	Map<String, LastQueryLastDeleteTs> personQueryTimeCache = new THashMap<>(2);
}
