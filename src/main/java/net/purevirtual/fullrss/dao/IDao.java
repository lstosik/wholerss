package net.purevirtual.fullrss.dao;

public interface IDao<Entity, Key> {

	Entity merge(Entity entity);

	void persist(Entity entity);

	void remove(Entity entity);

	Entity findById(Key id);
}


