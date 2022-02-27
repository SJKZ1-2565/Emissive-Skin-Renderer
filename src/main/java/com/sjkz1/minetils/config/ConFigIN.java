package com.sjkz1.minetils.config;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.TextUtils;
public class ConFigIN extends ConfigHandler{
	 private MinetilsConfig config;

	    public ConFigIN()
	    {
	        super(Minetils.MOD_ID);
	    }

	    public MinetilsConfig getConfig()
	    {
	        if (this.config == null)
	        {
	            try
	            {
	                this.loadConfig();
	            }
	            catch (IOException e)
	            {
	                Minetils.LOGGER.error("Failed to load config, using default.", e);
	                return new MinetilsConfig();
	            }
	        }
	        return this.config;
	    }

	    @Override
	    public void loadConfig() throws IOException
	    {
	        this.configFile.getParentFile().mkdirs();

	        if (!this.configFile.exists())
	        {
	        	Minetils.LOGGER.error("Unable to find config file, creating new one.");
	            this.config = new MinetilsConfig();
	            this.saveConfig();
	        }
	        else
	        {
	            this.config = GSON.fromJson(ConfigHandler.readFile(this.configFile.toPath().toString(), Charset.defaultCharset()), MinetilsConfig.class);
	        }
	    }

	    @Override
	    public void saveConfig() throws IOException
	    {
	        this.configFile.getParentFile().mkdirs();
	        FileWriter writer = new FileWriter(this.configFile);
	        TextUtils.toJson(this.config, writer);
	        writer.close();
	    }
}
