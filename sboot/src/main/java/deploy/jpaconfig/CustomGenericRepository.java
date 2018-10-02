package deploy.jpaconfig;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomGenericRepository<E extends Serializable, PK extends Serializable>
		extends JpaRepository<E, PK>, JpaSpecificationExecutor<E> {

	EntityManager getEntityManager();

}
