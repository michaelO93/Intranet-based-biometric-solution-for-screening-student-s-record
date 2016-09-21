/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.database;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author michael-prime
 */
public abstract class Database {
	private static Database INSTANCE = null;
    public static boolean DEBUGGING = false;

	protected abstract List<Map> getAll(String table) throws IOException;
	protected abstract List<Map> select(String table, Map filter) throws IOException;
	protected abstract void delete(String table, Map filter) throws IOException;
	protected abstract void insert(String table, Map values) throws IOException;
	protected abstract void update(String table, Map filter, Map values) throws IOException;

	public static <T> Helper<T> helper(Class<? extends T> clazz) {
        if (INSTANCE == null) INSTANCE = new JdbcDatabase();
		return new Helper<>(Factory.get(clazz));
	}

	public static final class Helper<T> {
		private final Factory<T> factory;

		private Helper(Factory<T> factory) {
			this.factory = factory;
		}

		public List<T> getAll() throws IOException {
			List<Map> maps = INSTANCE.getAll(factory.className());
			return factory.fromMaps(maps);
		}

		public List<T> getAll(Map filter) throws IOException {
			List<Map> maps = INSTANCE.select(factory.className(), filter);
			return factory.fromMaps(maps);
		}

		public List<T> getAll(Object primaryValue) throws IOException {
			return getAll(new Map(factory.primary(), primaryValue));
		}
        
        public T get(Map filter) throws IOException {
			List<T> list = getAll(filter);
            return list.isEmpty() ? null : list.get(0);
		}

		public T get(Object primaryValue) throws IOException {
			return get(new Map(factory.primary(), primaryValue));
		}

		public Helper<T> insert(T t) throws IOException {
			Map values = factory.map(t);
			INSTANCE.insert(factory.className(), values);
            return this;
		}
        

		public Helper<T> update(T t, Collection<String> keys) throws IOException {
			Map values = factory.map(t);
            Map filter = values.subset(keys);
			INSTANCE.update(factory.className(), filter, values);
            return this;
		}
        
        public Helper<T> update(T t, String... keys) throws IOException {
            return update(t, Arrays.asList(keys));
        }

		public Helper<T> update(T t) throws IOException {
			return update(t, factory.primary());
		}
        

		public void delete(T t, Collection<String> keys) throws IOException {
			Map filter = factory.map(t, keys);
			INSTANCE.delete(factory.className(), filter);
		}
        
        public void delete(T t, String... keys) throws IOException {
            delete(t, Arrays.asList(keys));
        }

		public void delete(T t) throws IOException {
			delete(t, factory.primary());
		}
        
        public void remove(Object primaryValue) throws IOException {
            Map filter = new Map(factory.primary(), primaryValue);
			INSTANCE.delete(factory.className(), filter);
        }
	}
}
