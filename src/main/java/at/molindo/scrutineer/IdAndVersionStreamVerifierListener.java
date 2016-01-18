package at.molindo.scrutineer;

public interface IdAndVersionStreamVerifierListener {

	void onMissingInSecondaryStream(IdAndVersion idAndVersion);

	void onMissingInPrimaryStream(IdAndVersion idAndVersion);

	void onVersionMisMatch(IdAndVersion primaryItem, IdAndVersion secondaryItem);
}
