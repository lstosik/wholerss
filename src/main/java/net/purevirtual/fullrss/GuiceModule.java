package net.purevirtual.fullrss;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class GuiceModule extends AbstractModule {
	/**
	 * @see AbstractModule#configure() 
	 */
	@Override
	protected void configure() {
		//bind(javax.persistence.EntityManager.class).toInstance(this.provideEntityManager());
		//bind(.class).toInstance(null);
	}

	@Provides
	@Inject
	javax.persistence.EntityManager provideEntityManager(EntityManagerFactory emfInstance) {
		return emfInstance.createEntityManager();
	}

	@Singleton
	@Provides
	javax.persistence.EntityManagerFactory provideEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("transactions-optional");
	}
}
