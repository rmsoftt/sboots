package deploy.jpaconfig;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional(readOnly = true)
public class JskCustomRepositoryImpl<E extends Serializable, I extends Serializable> extends SimpleJpaRepository<E, I> {

	private final EntityManager entityManager;
	private final JpaEntityInformation<E, ?> entityInformation;

	public JskCustomRepositoryImpl(final JpaEntityInformation entityInformation, final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = entityInformation;
	}

	@Override
	public List<E> findAll() {

		/*
		 * System.out.println("Rashmi Spring boot practice1SSS!!!!!!!!!!!!!");
		 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		return super.findAll();
	}

	@Override
	public <S extends E> S save(S entity) {

		/*
		 * System.out.println("Rashmi Spring boot practice!!!!!!!!!!!!!"); try {
		 * Thread.sleep(10000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return super.save(entity);
	}

	protected JpaEntityInformation<E, ?> getEntityInformation() {
		return this.entityInformation;
	}

}