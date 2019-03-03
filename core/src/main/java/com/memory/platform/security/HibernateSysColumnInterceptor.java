package com.memory.platform.security;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

@Component
public class HibernateSysColumnInterceptor extends EmptyInterceptor {

	static final String SYS_CREATE_TIME = "create_time";
	static final String SYS_UPDATE_TIME = "update_time";

	protected void UpdateEntity(Object entity, String cmd) {

		try {

			PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(entity, cmd);
			if (descriptor != null) {
				PropertyUtils.setProperty(entity, cmd, ConvertUtils.convert(new Date(), descriptor.getPropertyType()));
			}

		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		}

	}

	protected void updateState(String cmd, Object[] state, String[] propertyNames) {
		Date now = new Date();
		for (int i = 0; i < propertyNames.length; i++) {
			if (cmd.equals(propertyNames[i])) {
				if (cmd.equals(SYS_CREATE_TIME) && state[i] == null) {
					state[i] = now;
				} else
					state[i] = now;
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		updateState(SYS_UPDATE_TIME, state, propertyNames);
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		updateState(SYS_UPDATE_TIME, currentState, propertyNames);
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

		updateState(SYS_CREATE_TIME, state, propertyNames);
		updateState(SYS_UPDATE_TIME, state, propertyNames);
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {

	//	System.out.println("recreate..............");
		super.onCollectionRecreate(collection, key);
	}

	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {

		super.onCollectionRemove(collection, key);
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {

		super.onCollectionUpdate(collection, key);
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@Override
	public void postFlush(Iterator entities) {

		super.postFlush(entities);
	}

	@Override
	public String onPrepareStatement(String sql) {

		return super.onPrepareStatement(sql);
	}

	@Override
	public void preFlush(Iterator entities) {

		super.preFlush(entities);
	}

}
