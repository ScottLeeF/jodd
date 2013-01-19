// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.introspector;

import jodd.introspector.tst.Abean;
import jodd.introspector.tst.Ac;
import jodd.introspector.tst.Bbean;
import jodd.introspector.tst.Bc;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class IntrospectorTest {

	@Test
	public void testBasic() {
		ClassDescriptor cd = ClassIntrospector.lookup(Abean.class);
		assertNotNull(cd);
		Method[] getters = cd.getAllBeanGetters();
		assertEquals(2, getters.length);
		assertNotNull(cd.getBeanGetter("fooProp"));
		assertNotNull(cd.getBeanGetter("something"));
		assertNull(cd.getBeanGetter("FooProp"));
		assertNull(cd.getBeanGetter("Something"));
		assertNull(cd.getBeanGetter("notExisting"));

		Method[] setters = cd.getAllBeanSetters();
		assertEquals(1, setters.length);
	}

	@Test
	public void testExtends() {
		ClassDescriptor cd = ClassIntrospector.lookup(Bbean.class);
		assertNotNull(cd);

		Method[] getters = cd.getAllBeanGetters();
		assertEquals(2, getters.length);
		getters = cd.getAllBeanGetters(true);
		assertEquals(3, getters.length);
		assertNotNull(cd.getBeanGetter("fooProp"));
		assertNotNull(cd.getBeanGetter("something"));
		assertNull(cd.getBeanGetter("FooProp"));
		assertNull(cd.getBeanGetter("Something"));
		assertNull(cd.getBeanGetter("notExisting"));

		assertNotNull(cd.getBeanGetter("boo", true));
		assertNull(cd.getBeanGetter("boo"));

		Method[] setters = cd.getAllBeanSetters();
		assertEquals(1, setters.length);
		setters = cd.getAllBeanSetters(true);
		assertEquals(2, setters.length);
	}

	@Test
	public void testCtors() {
		ClassDescriptor cd = ClassIntrospector.lookup(Ac.class);
		Constructor[] ctors = cd.getAllCtors();
		assertEquals(1, ctors.length);
		ctors = cd.getAllCtors(true);
		assertEquals(2, ctors.length);
		assertNotNull(cd.getDefaultCtor(true));
		assertNull(cd.getDefaultCtor());

		Constructor ctor = cd.getCtor(new Class[]{Integer.class}, true);
		assertNotNull(ctor);

		cd = ClassIntrospector.lookup(Bc.class);
		ctors = cd.getAllCtors();
		assertEquals(1, ctors.length);
		ctors = cd.getAllCtors(true);
		assertEquals(1, ctors.length);
		assertNull(cd.getDefaultCtor());
		assertNull(cd.getDefaultCtor(true));

		ctor = cd.getCtor(new Class[]{Integer.class}, true);
		assertNull(ctor);
		ctor = cd.getCtor(new Class[]{String.class}, true);
		assertNotNull(ctor);
	}
}
