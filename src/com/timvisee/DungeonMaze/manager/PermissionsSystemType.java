package com.timvisee.DungeonMaze.manager;

public enum PermissionsSystemType {
	NONE("None"),
	PERMISSIONS_EX("Permissions Ex"),
	PERMISSIONS_BUKKIT("Permissions Bukkit"),
	B_PERMISSIONS("bPermissions"),
	ESSENTIALS_GROUP_MANAGER("Essentials Group Manager"),
	Z_PERMISSIONS("zPermissions"),
	VAULT("Vault"),
	PERMISSIONS("Permissions");
	
	public String name;
	
	PermissionsSystemType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
