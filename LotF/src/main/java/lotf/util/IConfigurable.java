package main.java.lotf.util;

import java.lang.reflect.Type;

public interface IConfigurable<T> {
	public abstract String getFileName();
	public abstract T getDefaultSave();
	public abstract T save();
	public abstract void load(Object obj);
	public abstract Type getType();
}
