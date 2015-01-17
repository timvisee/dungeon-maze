package com.timvisee.dungeonmaze.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig extends YamlConfiguration{

	private File configFile;

	public CustomConfig(File file) {
		this.configFile = file;

		load();
	}

	public void load() {
		try {
			super.load(configFile);
			
		} catch (FileNotFoundException e) {
			reload();
			
		} catch (IOException ignored) {
		} catch (InvalidConfigurationException ignored) { }
	}

	public boolean reload() {
		boolean out = true;
		if (!configFile.exists())
			out = loadResource(configFile);
			
		if (out)
			load();
		
		return out;
	}

	public void save() {
	    try {
	        super.save(configFile);
	        
	    } catch (IOException ignored) { }
	}

	public boolean loadResource(File file) {
		boolean out = true;
		if (!file.exists()) {
			InputStream fis = getClass().getResourceAsStream("/" + file.getName());
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int i;
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
						
				} catch (Exception ignored) { }
			}
		}
		return out;
	}
}
