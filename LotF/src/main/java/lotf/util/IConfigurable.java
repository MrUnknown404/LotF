package main.java.lotf.util;

import java.lang.reflect.Type;

public interface IConfigurable {
	public abstract String getFileName();
	public abstract Object getDefaultSave();
	public abstract Object save();
	public abstract void load(Object obj);
	public abstract Type getType();
}
