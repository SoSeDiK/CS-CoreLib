package me.mrCookieSlime.CSCoreLibSetup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CSCoreLibLoader {
	
	Plugin plugin;
	URL url;
	URL download;
	File file;
	
	public CSCoreLibLoader(Plugin plugin) {
		this.plugin = plugin;
		try {
			this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=88802");
		} catch (MalformedURLException e) {
		}
	}
	
	public boolean load() {
		if (plugin.getServer().getPluginManager().isPluginEnabled("CS-CoreLib")) return true;
		else {
			System.err.println(" ");
			System.err.println("################## - ИНФОРМАЦИЯ - ##################");
			System.err.println(" ");
			System.err.println(plugin.getName() + " не может быть включен!");
			System.err.println("Это случается, если CS-CoreLib не установлен");
			System.err.println("Ваш сервер попробует загрузить и установить его.");
			System.err.println("автоматически.");
			System.err.println("После того, как процесс установки завершится,");
			System.err.println("Вам будет предложено перезагрузить сервер.");
			System.err.println("Если что-то пойдёт не так, установите CS-CoreLib вручную:");
			System.err.println("https://dev.bukkit.org/projects/cs-corelib");
			System.err.println(" ");
			System.err.println("################## - ИНФОРМАЦИЯ - ##################");
			System.err.println(" ");
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					if (connect()) install();
				}
			}, 10L);
			return false;
		}
	}
	
	private boolean connect() {
        try {
            final URLConnection connection = this.url.openConnection();
            connection.setConnectTimeout(5000);
            connection.addRequestProperty("User-Agent", "CS-CoreLib Loader (by mrCookieSlime)");
            connection.setDoOutput(true);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final JSONArray array = (JSONArray) JSONValue.parse(reader.readLine());
            download = traceURL(((String) ((JSONObject) array.get(array.size() - 1)).get("downloadUrl")).replace("https:", "http:"));
            file = new File("plugins/" + (String) ((JSONObject) array.get(array.size() - 1)).get("name") + ".jar");
            
            return true;
        } catch (IOException e) {
        	System.err.println(" ");
        	System.err.println("################## - ПРЕДУПРЕЖДЕНИЕ - ##################");
			System.err.println(" ");
			System.err.println("Не удалось подключиться к BukkitDev.");
			System.err.println("Пожалуйста, установите CS-CoreLib вручную:");
			System.err.println("https://dev.bukkit.org/projects/cs-corelib");
			System.err.println(" ");
			System.err.println("################## - ПРЕДУПРЕЖДЕНИЕ - ##################");
			System.err.println(" ");
            return false;
        }
    }
	
	private URL traceURL(String location) throws IOException {
	    	HttpURLConnection connection = null;
	    	
	        while (true) {
	            URL url = new URL(location);
	            connection = (HttpURLConnection) url.openConnection();

	            connection.setInstanceFollowRedirects(false);
	            connection.setConnectTimeout(5000);
	            connection.addRequestProperty("User-Agent", "Auto Updater (by mrCookieSlime)");

	            switch (connection.getResponseCode()) {
	                case HttpURLConnection.HTTP_MOVED_PERM:
	                case HttpURLConnection.HTTP_MOVED_TEMP:
	                    String loc = connection.getHeaderField("Location");
	                    location = new URL(new URL(location), loc).toExternalForm();
	                    continue;
	            }
	            break;
	        }
	        
	        return new URL(connection.getURL().toString().replaceAll(" ", "%20"));
	}
	
	private void install() {
		BufferedInputStream input = null;
		FileOutputStream output = null;
        try {
            input = new BufferedInputStream(download.openStream());
            output = new FileOutputStream(file);

            final byte[] data = new byte[1024];
            int read;
            while ((read = input.read(data, 0, 1024)) != -1) {
                output.write(data, 0, read);
            }
        } catch (Exception ex) {
        	System.err.println(" ");
        	System.err.println("################## - ПРЕДУПРЕЖДЕНИЕ - ##################");
			System.err.println(" ");
			System.err.println("Не удалось загрузить CS-CoreLib");
			System.err.println("Пожалуйста, установите CS-CoreLib вручную:");
			System.err.println("https://dev.bukkit.org/projects/cs-corelib");
			System.err.println(" ");
			System.err.println("################## - ПРЕДУПРЕЖДЕНИЕ - ##################");
			System.err.println(" ");
        } finally {
            try {
                if (input != null) input.close();
                if (output != null) output.close();
                System.err.println(" ");
                System.err.println("################## - ИНФОРМАЦИЯ - ##################");
                System.err.println(" ");
                System.err.println("Пожалуйста, перезагрузите сервер, чтобы завершить установку");
                System.err.println("плагина " + plugin.getName() + " и CS-CoreLib");
                System.err.println(" ");
                System.err.println("################## - ИНФОРМАЦИЯ - ##################");
                System.err.println(" ");
            } catch (IOException e) {
            	e.printStackTrace();
            } 
        }
	}

}
