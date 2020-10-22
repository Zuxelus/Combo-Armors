package com.zuxelus.comboarmors.items;

public abstract interface IItemUpgradeable {

	public abstract int getDefaultMaxCharge();

	public abstract int getDefaultTier();

	public abstract int getDefaultTransferLimit();

	public abstract int getItemTier();

	public abstract int getMaxUpgradeableCharge();

	public abstract int getMaxUpgradeableTransfer();
}
