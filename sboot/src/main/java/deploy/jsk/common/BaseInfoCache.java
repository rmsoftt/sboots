package deploy.jsk.common;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.hibernate.mapping.Collection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Multimap;

import deploy.jsk.baseInt.Constants;
import deploy.jsk.baseInt.DataBaseEntity;
import deploy.jsk.baseInt.DataBaseEntityInt;
import deploy.jsk.baseInt.LastQueryLastDeleteTs;
import deploy.jsk.entity.LastModifiedEntity;
import deploy.jsk.entity.Person;
import deploy.jsk.utility.MultimapCollector;
import deploy.repository.LastModifiedEntityDao;

public abstract class BaseInfoCache {

	@Autowired
	LastModifiedEntityDao lastModifiedDao;

	public Timestamp canUseCache(String dataFilter, Map<String, Multimap<String, DataBaseEntityInt>> cache,
			String tableName, Map<String, LastQueryLastDeleteTs> queryTimeCache) {

		if (cache.size() == 0)
			return Constants.beforeJskTime;

		LastModifiedEntity lastModEntity = lastModifiedDao.findByTableName(tableName.toUpperCase());
		LastQueryLastDeleteTs deleteQueryTs = queryTimeCache.get(dataFilter);

		if (lastModEntity == null || deleteQueryTs.getLastQueryTs() == null)
			return null;// use cache

		if (lastModEntity.getLastDeleteTs() != null && deleteQueryTs.getLastDeleteTs() != null
				&& lastModEntity.getLastDeleteTs().after(deleteQueryTs.getLastDeleteTs()))

			return Constants.beforeJskTime;// full reload in case delete

		if (lastModEntity.getLastQueryTs() != null
				&& lastModEntity.getLastQueryTs().after(deleteQueryTs.getLastQueryTs()))

			return deleteQueryTs.getLastQueryTs();

		if (deleteQueryTs.getLastQueryTs() != null && lastModEntity.getLastQueryTs() != null
				&& lastModEntity.getLastQueryTs().before(deleteQueryTs.getLastQueryTs()))

			return null;// use cache

		return null;
	}

	Multimap<String, DataBaseEntityInt> updateCache(String dataFilter, List<DataBaseEntityInt> results,
			Map<String, Multimap<String, DataBaseEntityInt>> cache,String className) {

		if(cache.size()==0 || !cache.containsKey(dataFilter))
		{
			Multimap<String, DataBaseEntityInt> resultMap = results.stream()
					.collect(MultimapCollector.toMultimap(DataBaseEntityInt::getId, x -> x));
			cache.put(dataFilter, resultMap);
			
			return cache.get(dataFilter);
			
		}
		
		
		Multimap<String, DataBaseEntityInt> cacheOfKey=cache.get(dataFilter);
		
		/*Map<String, DataBaseEntityInt> resultMap = cache.get(dataFilter).values().stream()
				.collect(Collectors.toMap(DataBaseEntityInt::getId, x -> x, (p1, p2) -> p1));
*/
		results.forEach(dbEntity -> {
			
			if(!cacheOfKey.containsKey(dbEntity.getId()))
			{
				cacheOfKey.put(dbEntity.getId(), dbEntity);
				
			}
			else
			{
			
			DataBaseEntityInt newDBEntity=null;
			
			try {
				newDBEntity= (DataBaseEntityInt) Class.forName(className).newInstance();
				//BeanUtils.copyProperties(dbEntity, newDBEntity);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			for(Field field:dbEntity.getClass().getDeclaredFields())
			{
				field.setAccessible(true);
				if(field.getAnnotation(Transient.class)!=null || Collection.class.isAssignableFrom(field.getType()))
				{
					continue;
				}
							
				
					try {
						field.set(newDBEntity, field.get(dbEntity));
					} catch (IllegalArgumentException |IllegalAccessException e) {
						try {
							throw e;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} 
				
			}
			
			for(  DataBaseEntityInt dbEnt: cacheOfKey.get(dbEntity.getId())){
				
				if(dbEnt.getId().equals(dbEntity.getId()))
				{
					BeanUtils.copyProperties(newDBEntity, dbEnt);
				}
			}
			
		}

		});

		 return cache.get(dataFilter);
		
		 
	}

	}


