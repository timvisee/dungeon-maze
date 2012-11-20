package com.timvisee.DungeonMaze.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.milkbowl.vault.permission.Permission;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsService;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;

public class PermissionsManager {
	
	private PermissionsSystemType permsType = PermissionsSystemType.NONE;
	private Server s;
	private Plugin p;
	
	// Permissions Ex
	private PermissionManager pexPerms;
	
	// Group manager essentials
	private GroupManager groupManagerPerms;
	
	// Permissions (the default old permissions system by nijiko)
	private PermissionHandler defaultPerms;
	
	// zPermissions
	private ZPermissionsService zPermissionsService;
	
	// Vault
	public Permission vaultPerms = null;
	
	/**
	 * Constructor
	 * @param s server
	 * @param logPrefix log prefix (plugin name)
	 */
	public PermissionsManager(Server s, Plugin p) {
		this.s = s;
		this.p = p;
	}
	
	/**
	 * Return the permissions system where the permissions manager is currently hooked into
	 * @return permissions system type
	 */
	public PermissionsSystemType getUsedPermissionsSystemType() {
		return this.permsType;
	}
	
	/**
	 * Check if the permissions manager is currently hooked into any of the supported permissions systems
	 * @return false if there isn't any permissions system used
	 */
	public boolean isEnabled() {
		return !permsType.equals(PermissionsSystemType.NONE);
	}
	
	/**
	 * Setup and hook into the permissions systems
	 * @return the detected permissions system
	 */
	public PermissionsSystemType setup() {
		// Define the plugin manager
		final PluginManager pm = this.s.getPluginManager();
		
		// Reset used permissions system type
		permsType = PermissionsSystemType.NONE;
		
		// Check if PermissionsEx is available
		Plugin pex = pm.getPlugin("PermissionsEx");
		if(pex != null) {
			pexPerms = PermissionsEx.getPermissionManager();
			if(pexPerms != null) {
				permsType = PermissionsSystemType.PERMISSIONS_EX;
				
				System.out.println("[" + p.getName() + "] Hooked into PermissionsEx!");
				return permsType;
			}
		}
		
		// Check if PermissionsBukkit is available
		Plugin bukkitPerms = pm.getPlugin("PermissionsBukkit");
		if(bukkitPerms != null) {
			permsType = PermissionsSystemType.PERMISSIONS_BUKKIT;
			System.out.println("[" + p.getName() + "] Hooked into PermissionsBukkit!");
			return permsType;
		}
		
		// Check if bPermissions is available
		Plugin testBPermissions = pm.getPlugin("bPermissions");
		if(testBPermissions != null) {
			permsType = PermissionsSystemType.B_PERMISSIONS;
			System.out.println("[" + p.getName() + "] Hooked into bPermissions!");
			return permsType;
		}
		
		// Check if Essentials Group Manager is available
		final Plugin GMplugin = pm.getPlugin("GroupManager");
		if (GMplugin != null && GMplugin.isEnabled()) {
			permsType = PermissionsSystemType.ESSENTIALS_GROUP_MANAGER;
			groupManagerPerms = (GroupManager)GMplugin;
            System.out.println("[" + p.getName() + "] Hooked into Essentials Group Manager!");
            return permsType;
		}
		
		// Check if zPermissions is available
		Plugin testzPermissions = pm.getPlugin("zPermissions");
		if(testzPermissions != null){
			zPermissionsService = Bukkit.getServicesManager().load(ZPermissionsService.class);
			if(zPermissionsService != null){
				permsType = PermissionsSystemType.Z_PERMISSIONS;
				System.out.println("[" + p.getName() + "] Hooked into zPermissions!");
				return permsType;
			}
		}
		
		// VAULT PERMISSIONS
		final Plugin vaultPlugin = pm.getPlugin("Vault");
		if (vaultPlugin != null && vaultPlugin.isEnabled()) {
			RegisteredServiceProvider<Permission> permissionProvider = this.s.getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            vaultPerms = permissionProvider.getProvider();
	            if(vaultPerms.isEnabled()) {
	            	permsType = PermissionsSystemType.VAULT;
	            	System.out.println("[" + p.getName() + "] Hooked into Vault Permissions!");
	    		    return permsType;
	            } else {
	            	System.out.println("[" + p.getName() + "] Not using Vault Permissions, Vault Permissions is disabled!");
	            }
	        }
		}
        
		// Check if Permissions is available
	    Plugin testPerms = pm.getPlugin("Permissions");
        if (testPerms != null) {
        	permsType = PermissionsSystemType.PERMISSIONS;
            this.defaultPerms = ((Permissions) testPerms).getHandler();
            System.out.println("[" + p.getName() + "] Hooked into Permissions!");
            return PermissionsSystemType.PERMISSIONS;
        }
	    
	    // No recognized permissions system found
	    permsType = PermissionsSystemType.NONE;
	    System.out.println("[" + p.getName() + "] No supported permissions system found! Permissions disabled!");
	    
	    return PermissionsSystemType.NONE;
    }
	
	/**
	 * Check if the player has permission. If no permissions system is used, the player has to be OP
	 * @param p player
	 * @param permsNode permissions node
	 * @return true if the player is permitted
	 */
	public boolean hasPermission(Player p, String permsNode) {
		return hasPermission(p, permsNode, p.isOp());
	}
	
	/**
	 * Check if a player has permission
	 * @param player player
	 * @param permissionNode permission node
	 * @param def default if no permissions system is used
	 * @return true if the player is permitted
	 */
	public boolean hasPermission(Player p, String permsNode, boolean def) {
		if(!isEnabled()) {
			// No permissions system is used, return default
			return def;
		}
		
		switch (this.permsType) {
		case PERMISSIONS_EX:
			// Permissions Ex
			PermissionUser user  = PermissionsEx.getUser(p);
			return user.has(permsNode);
			
		case PERMISSIONS_BUKKIT:
			// Permissions Bukkit
			return p.hasPermission(permsNode);
			
		case B_PERMISSIONS:
			// bPermissions
			return ApiLayer.hasPermission(p.getWorld().getName(), CalculableType.USER, p.getName(), permsNode);
			
		case ESSENTIALS_GROUP_MANAGER:
			// Essentials Group Manager
			final AnjoPermissionsHandler handler = groupManagerPerms.getWorldsHolder().getWorldPermissions(p);
			if (handler == null)
				return false;
			return handler.has(p, permsNode);
		case Z_PERMISSIONS:
			// zPermissions
			Map<String, Boolean> perms = zPermissionsService.getPlayerPermissions(p.getWorld().getName(), null, p.getName());
			if(perms.containsKey(permsNode)){
				return perms.get(permsNode);
			} else {
				return def;
			}
		case VAULT:
			// Vault
			return vaultPerms.has(p, permsNode);
			
		case PERMISSIONS:
			// Permissions by nijiko
			return this.defaultPerms.has(p, permsNode);
			
		case NONE:
			// Not hooked into any permissions system, return default
			return def;
			
		default:
			// Something went wrong, return false to prevent problems
			return false;
		}
	}
	
	public List<String> getGroups(Player p) {
		if(!isEnabled()) {
			// No permissions system is used, return an empty list
			return new ArrayList<String>();
		}
		
		switch (this.permsType) {
		case PERMISSIONS_EX:
			// Permissions Ex
			PermissionUser user  = PermissionsEx.getUser(p);
			return Arrays.asList(user.getGroupsNames());
			
		case PERMISSIONS_BUKKIT:
			// Permissions Bukkit
			// Permissions Bukkit doesn't support group, return an empty list
			return new ArrayList<String>();
			
		case B_PERMISSIONS:
			// bPermissions
			return Arrays.asList(ApiLayer.getGroups(p.getName(), CalculableType.USER, p.getName()));
			
		case ESSENTIALS_GROUP_MANAGER:
			// Essentials Group Manager
			final AnjoPermissionsHandler handler = groupManagerPerms.getWorldsHolder().getWorldPermissions(p);
			if (handler == null)
				return new ArrayList<String>();
			return Arrays.asList(handler.getGroups(p.getName()));
			
		case Z_PERMISSIONS:
			//zPermissions
			return new ArrayList<String>(zPermissionsService.getPlayerGroups(p.getName()));
			
		case VAULT:
			// Vault
			return Arrays.asList(vaultPerms.getPlayerGroups(p));
			
		case PERMISSIONS:
			// Permissions by nijiko
			return new ArrayList(this.defaultPerms.getGroups(p.getName()));
			
		case NONE:
			// Not hooked into any permissions system, return an empty list
			return new ArrayList<String>();
			
		default:
			// Something went wrong, return an empty list to prevent problems
			return new ArrayList<String>();
		}
	}
}
