package deploy.repository;

import org.springframework.data.repository.CrudRepository;

import deploy.jsk.entity.LastModifiedEntity;

public interface LastModifiedEntityDao extends CrudRepository<LastModifiedEntity, Long> {

	public LastModifiedEntity findByTableName(String tableName);

}
