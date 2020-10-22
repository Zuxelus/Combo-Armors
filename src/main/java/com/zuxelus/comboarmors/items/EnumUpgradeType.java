package com.zuxelus.comboarmors.items;

public enum EnumUpgradeType {
	JETPACKS("info.jet_packs"),
	SOLARS("info.solar_helmets"),
	STATICS("info.static_boots"),
	CHESTS("info.chest_plates"),
	HELMETS("info.helmets"),
	ELECTRICS("info.electrical_items"),
	TANKS("info.tanks");

	public String name;

	private EnumUpgradeType(String s) {
		name = s;
	}
}
