package com.timvisee.dungeonmaze;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.bukkit.Bukkit;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObjectFromUrl;

public class DMUpdateChecker {
	
	// Constants
	private final String APP_NAME = "DungeonMaze";
	private final String CHECKER_URL = "http://updates.timvisee.com/check.php?app=3";
	private final List<String> ALLOWED_DOWNLOAD_HOSTS = Arrays.asList(new String[]{"dev.bukkit.org", "bukkit.org"});
	
	private JSONObject updatesData = null;
	
	/**
	 * Constructor
	 */
	public DMUpdateChecker() {
		// Immediately refresh updates data if the Update Checker has been enabled
		if(DungeonMaze.instance.getConfig().getBoolean("updateChecker.enabled", true))
			refreshUpdatesData();
	}
	
	/**
	 * Constructor
	 * @param refreshUpdatesData immediately refresh updates data
	 */
	public DMUpdateChecker(boolean refreshUpdatesData) {
		// Immediately refresh updates data
		if(refreshUpdatesData)
			refreshUpdatesData();
	}
	
	/**
	 * Refresh all updates data
	 */
	public void refreshUpdatesData() {
		// Show a status message
		System.out.println("[" + APP_NAME + "] Checking for updates...");
		
		try {
			// Recieve the updates data
			this.updatesData = JSONObjectFromUrl.readJsonFromUrl(CHECKER_URL);
			
			// Make sure the updates data is not null (cause of any error)
			if(this.updatesData == null) {
				System.out.println("[" + APP_NAME + "] Failed checking for updates!");
				return;
			}
			
			// Return an error if any error occurred
			if(this.updatesData.isSet("error")) {
				String errMsg = this.updatesData.getString("error");
				System.out.println("[" + APP_NAME + "] Error occured while receiving updates data!");
				System.out.println("[" + APP_NAME + "] [ERROR] " + errMsg);
				return;
			}
			
			// Updates data received, show status message
			System.out.println("[" + APP_NAME + "] Updates data received!");
			
		} catch (NoSuchMethodError e) {
			System.out.println("[" + APP_NAME + "] [ERROR] Error occured while checking for updates!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the current running Safe Creeper version
	 * @return current Safe Creeper version
	 */
	public String getCurrentVersion() {
		return DungeonMaze.instance.getVersion();
	}
	
	/**
	 * Get the current running (Craft)Bukkit version
	 * @return current (Craft)Bukkit version
	 */
	public String getBukkitVersion() {
		return Bukkit.getBukkitVersion();
	}
	
	/**
	 * Get the current Minecraft version the server is running on
	 * @return current Minecraft version
	 */
	public String getMinecraftVersion() {
		final String bukkitVer = Bukkit.getBukkitVersion().trim();
		final String mcVer = bukkitVer.split("-")[0];
		return mcVer;
	}
	
	/**
	 * Check if the updates data is loaded
	 * @return
	 */
	public boolean isUpdatesDataLoaded() {
		return this.updatesData != null;
	}
	
	/**
	 * Get the newest version available
	 * @return
	 */
	public String getNewestVersion() {
		// Make sure the updates data is loaded, if not return an emtpy string
		if(!isUpdatesDataLoaded())
			return "";
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return "";
		
		// Get and return the newest version
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			return app.getString("version");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred return an empty string
		return "";
	}
	
	/**
	 * Check if there's any newer version available
	 * @return true if newer version is available
	 */
	public boolean isNewVersionAvailable() {
		String newestVer = getNewestVersion();
		String curVer = getCurrentVersion();
		
		if(newestVer.equals("") || curVer.equals(""))
			return false;
		
		return isNewerVersion(curVer, newestVer);
	}
	
	public boolean isNewVersionCompatibleWithCurrentBukkit() {
		if(!isRequiredBukkitVersionSet())
			return true;
		
		final String recBukkitVer = getRequiredBukkitVersion();
		final String curBukkitVer = getBukkitVersion().replace("-SNAPSHOT", "");
		
		if(recBukkitVer.equals(""))
			return false;
		
		return !isOlderVersion(recBukkitVer, curBukkitVer);
	}
	
	/**
	 * Check if a required bukkit version is set
	 * @return true if set
	 */
	public boolean isRequiredBukkitVersionSet() {
		// Make sure the updates data is loaded, if not return false
		if(!isUpdatesDataLoaded())
			return false;
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return false;
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Check if the downloadUrl key is set
			return app.isString("requiredBukkitVersion");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return false
		return false;
	}
	
	/**
	 * Get the required bukkit version
	 * @return required bukkit version
	 */
	public String getRequiredBukkitVersion() {
		// Make sure the updates data is loaded, if not return empty string
		if(!isUpdatesDataLoaded())
			return "";
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return "";
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Make sure the downloadUrl key is set
			if(!app.isString("requiredBukkitVersion"))
				return "";
			
			// Return the value from the important key
			return app.getString("requiredBukkitVersion");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return empty string
		return "";
	}
	
	
	/**
	 * Check if the newest version is important to install
	 * @return true if newest update is important
	 */
	public boolean isImportantUpdateAvailable() {
		// Make sure the updates data is loaded, if not return false
		if(!isUpdatesDataLoaded())
			return false;
		
		// There must be a newer version available, if not return false
		if(!isNewVersionAvailable())
			return false;
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return false;
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Make sure the important key is set
			if(!app.isBoolean("importantUpdate"))
				return false;
			
			// Return the value from the important key
			return app.getBoolean("importantUpdate");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return false
		return false;
	}
	
	/**
	 * Check if the download URL is set
	 * @return true if set
	 */
	public boolean isDownloadUrlSet() {
		// Make sure the updates data is loaded, if not return false
		if(!isUpdatesDataLoaded())
			return false;
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return false;
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Check if the downloadUrl key is set
			return app.isString("downloadUrl");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return false
		return false;
	}
	
	/**
	 * Get the download url
	 * @return download url
	 */
	public String getDownloadUrl() {
		// Make sure the updates data is loaded, if not return empty string
		if(!isUpdatesDataLoaded())
			return "";
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return "";
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Make sure the downloadUrl key is set
			if(!app.isString("downloadUrl"))
				return "";
			
			// Return the value from the important key
			return app.getString("downloadUrl");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return empty string
		return "";
	}
	
	/**
	 * Check if the download URL is set
	 * @return true if set
	 */
	public boolean isManualDownloadUrlSet() {
		// Make sure the updates data is loaded, if not return false
		if(!isUpdatesDataLoaded())
			return false;
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return false;
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Check if the downloadUrl key is set
			return app.isString("manualDownloadUrl");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return false
		return false;
	}
	
	/**
	 * Get the manual download URL
	 * @return manual download URL
	 */
	public String getManualDownloadUrl() {
		// Make sure the updates data is loaded, if not return empty string
		if(!isUpdatesDataLoaded())
			return "";
		
		// Make sure the app entry exists
		if(this.updatesData.isNull("app"))
			return "";
		
		// Is the newest version important
		try {
			JSONObject app = this.updatesData.getJSONObject("app");
			
			// Make sure the downloadUrl key is set
			if(!app.isString("manualDownloadUrl"))
				return "";
			
			// Return the value from the important key
			return app.getString("manualDownloadUrl");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// An error occurred, return empty string
		return "";
	}
	
	private static String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	/**
	 * Download the newest update available
	 */
	public void downloadUpdate() {
		// Make sure any URL for the update is available
		if(!isDownloadUrlSet())
			return;
		
		// Get the download URL to download the update from
		final String downloadUrl = getDownloadUrl();
		
		// The download URL may not be an empty string
		if(downloadUrl.equals(""))
			return;
		
		// Define the file path to save the update to and get the logger
		String fileExtention = getUrlExtention(downloadUrl);
		
		// Make sure any file extention is set
		if(fileExtention.equals(""))
			fileExtention = ".jar";
		
		File updatedFilePath = new File("plugins/DungeonMaze/updates/DungeonMaze" + fileExtention);
		Logger log = DungeonMaze.instance.getServer().getLogger();
		
		// Download the update
		try {
			
			// Make sure the download is hosted on a allowed host (Required cause of Bukkit rules)
			if(!ALLOWED_DOWNLOAD_HOSTS.contains(getDomainName(downloadUrl))) {
				log.info("[" + APP_NAME + "] The host the update is hosted on is not allowed, can't download update!");
				return;
			}
			
			downloadFile(log, downloadUrl, updatedFilePath);
			
		} catch (IOException e) {
			log.info("[" + APP_NAME + "] An error occured while downloading update!");
			e.printStackTrace();
			
		} catch (URISyntaxException e) {
			log.info("[" + APP_NAME + "] An error occured while downloading update!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if any update is already downloaded
	 * @return false if not
	 */
	public boolean isUpdateDownloaded() {
		File updatedFilePath = new File("plugins/DungeonMaze/updates/DungeonMaze.jar");
		File updatedFilePathZip = new File("plugins/DungeonMaze/updates/DungeonMaze.zip");
		
		return (updatedFilePath.exists() || updatedFilePathZip.exists());
	}
	
	/**
	 * Install a downloaded update
	 */
	public void installUpdate() {
		// Any update has to be downloaded first, if not return error
		if(!isUpdateDownloaded()) {
			System.out.println("[" + APP_NAME + "] No update has been downloaded to install!");
			return;
		}
		
		long t = System.currentTimeMillis();
		
		// Show a status message
		System.out.println("[" + APP_NAME + "] Installing update...");

		File pluginFile = new File("plugins/DungeonMaze.jar");
		File updatedFilePath = new File("plugins/DungeonMaze/updates/DungeonMaze.jar");
		File updatedFilePathZip = new File("plugins/DungeonMaze/updates/DungeonMaze.zip");
		
		if(updatedFilePath.exists()) {
			// Copy the update
			copyFile(updatedFilePath, pluginFile);
			
			// Calculate installation duration
			long duration = System.currentTimeMillis() - t;
			
			// Show a status message
			System.out.println("[" + APP_NAME + "] Update installed, took " + String.valueOf(duration) + " ms!");
			System.out.println("[" + APP_NAME + "] Server reload required!");
			
		} else if(updatedFilePathZip.exists()) {
			try {
				ZipFile updatedFileZip = new ZipFile(updatedFilePathZip);
				InputStream fileFromZip = updatedFileZip.getInputStream(updatedFileZip.getEntry("DungeonMaze.jar"));
				
				// Copy the update
				copyFile(fileFromZip, pluginFile);
				
				// Close the zip file
				updatedFileZip.close();
				
				// Calculate installation duration
				long duration = System.currentTimeMillis() - t;
				
				// Show a status message
				System.out.println("[" + APP_NAME + "] Update installed, took " + String.valueOf(duration) + " ms!");
				System.out.println("[" + APP_NAME + "] Server reload required!");
				
			} catch (ZipException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Remove all update files
	 */
	public void removeUpdateFiles() {
		File updatesFolder = new File("plugins/DungeonMaze/updates");
		
		// Remove all files
		try {
			delete(updatesFolder);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Download a file from an URL
	 * @param log logger to log to
	 * @param url URL to download the file from
	 * @param f file path to download the file to
	 * @throws IOException
	 */
	private void downloadFile(Logger log, String url, File f) throws IOException {
		downloadFile(log, new URL(url), f);
	}
	
	/**
	 * Download a file from an URL
	 * @param log logger to log to
	 * @param url URL to download the file from
	 * @param f file path to download the file to
	 * @throws IOException
	 */
	private void downloadFile(Logger log, URL url, File f) throws IOException {
		long t = System.currentTimeMillis();
		
		// Make sure the parent folder does exist
		if (!f.getParentFile().exists())
			f.getParentFile().mkdir();
		
		// Delete previous versions of the file
		if (f.exists())
			f.delete();
		
		// Create a new file
		f.createNewFile();
		
		// Show a status message
		final int size = url.openConnection().getContentLength();
		log.info("[" + APP_NAME + "] Downloading " + f.getName() + " (" + size / 1024 + "kb) ...");
		
		// Download the file
		final InputStream in = url.openStream();
		final OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
		
		final byte[] buffer = new byte[1024];
		int len, downloaded = 0, msgs = 0;
		final long start = System.currentTimeMillis();
		
		// Download the file
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
			downloaded += len;
			
			// Show downloading process
			if ((int) ((System.currentTimeMillis() - start) / 500) > msgs) {
				log.info("[" + APP_NAME + "] Downloading... " + ((int) ((double) downloaded / (double) size * 100d) + "%"));
				msgs++;
			}
		}
		
		// Close the streams
		in.close();
		out.close();

		// Calculate download duration
		long duration = System.currentTimeMillis() - t;
		
		// Show a status message
		if(duration >= 1000)
			log.info("[" + APP_NAME + "] " + f.getName() + " succesfully downloaded, took " + String.valueOf((double) (duration) / 1000) + " s!");
		else
			log.info("[" + APP_NAME + "] " + f.getName() + " succesfully downloaded, took " + String.valueOf(duration) + " ms!");
	}

	/**
	 * Copy a file
	 * @param from file to copy
	 * @param to path to copy file to
	 */
	private void copyFile(File from, File to) {
		InputStream in = null;
		try {
			in = new FileInputStream(from);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		copyFile(in, to);
	}

	/**
	 * Copy an input stream into a file
	 * @param in input stream to copy from
	 * @param file path to copy input stream to
	 */
	private void copyFile(InputStream in, File to) {
		// Make sure the input isn't null
		if(in == null)
			return;
		
	    try {
	        OutputStream out = new FileOutputStream(to);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Delete a file or folder
	 * @param file file or folder to delete
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void delete(File file) throws FileNotFoundException, IOException {
		if(!file.exists())
			return;
		
		// Checks if file is a directory
		if (file.isDirectory()) {
		   // Gathers files in directory
		   List<File> b = Arrays.asList(file.listFiles());
		   
		   // Recursively deletes all files and sub-directories
		   for (File entry : b)
			   delete(entry);
		   
		   // Deletes original sub-directory file
		   file.delete();
		} else
		   file.delete();
	}
	
	/**
	 * Get the extention of an URL
	 * @return URL Extention
	 */
	private String getUrlExtention(String url) {
		return url.substring(url.lastIndexOf("."));
	}
	
	/**
	 * Compare two version numbers
	 * @param current first version number
	 * @param lastCheck second version number
	 * @return true if second version number is larger
	 */
	private boolean isNewerVersion(String current, String lastCheck) {
        String s1 = normalisedVersion(current);
        String s2 = normalisedVersion(lastCheck);
        int cmp = s1.compareTo(s2);
        return (cmp < 0);
    }
	
	/**
	 * Compare two version numbers
	 * @param current first version number
	 * @param lastCheck second version number
	 * @return true if first version number is larger
	 */
	private boolean isOlderVersion(String current, String lastCheck) {
		return isNewerVersion(lastCheck, current);
	}

	/**
	 * Compare two version numbers
	 * @param current first version number
	 * @param lastCheck second version number
	 * @return true if server versions equals
	 */
	public boolean isSameVersion(String current, String lastCheck) {
		String s1 = normalisedVersion(current);
        String s2 = normalisedVersion(lastCheck);
        int cmp = s1.compareTo(s2);
        return (cmp == 0);
	}
	
	/**
	 * Normalize version number
	 * @param version version number to normalize
	 * @return normalized version number
	 */
	private String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

	/**
	 * Normalize version number
	 * @param version version number to normalize
	 * @param sep seperation char
	 * @param maxWidth max width
	 * @return normalized version number
	 */
	private String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split)
            sb.append(String.format("%" + maxWidth + 's', s));
        return sb.toString();
    }
}
