package com.timvisee.dungeonmaze.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class DMCustomConfig extends YamlConfiguration{

	private File configFile;

	public DMCustomConfig(File file) {
		this.configFile = file;

		load();
	}

	public void load() {
		try {
			super.load(configFile);
			
		} catch (FileNotFoundException e) {
			reload();
			
		} catch (IOException e) {
		} catch (InvalidConfigurationException e) { }
	}

	public boolean reload() {
		boolean out = true;
		if (!configFile.exists())
			out = loadRessource(configFile);
			
		if (out)
			load();
		
		return out;
	}

	public void save() {
	    try {
	        super.save(configFile);
	        
	    } catch (IOException ex) { }
	}

	public boolean loadRessource(File file) {
		boolean out = true;
		if (!file.exists()) {
			InputStream fis = getClass().getResourceAsStream("/" + file.getName());
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1)
					fos.write(buf, 0, i);
				
			} catch (Exception e) {
				out = false;
				
			} finally {
				try {
					if (fis != null)
						fis.close();
						
					if (fos != null)
						fos.close();
						
				} catch (Exception e) { }
			}
		}
		return out;
	}
}
