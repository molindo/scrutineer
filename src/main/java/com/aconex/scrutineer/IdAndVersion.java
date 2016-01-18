package com.aconex.scrutineer;

public interface IdAndVersion extends Comparable<IdAndVersion> {

	String getId();

	long getVersion();

}
