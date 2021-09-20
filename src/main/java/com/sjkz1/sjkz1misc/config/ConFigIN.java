package com.sjkz1.sjkz1misc.config;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.utils.Text;
public class ConFigIN extends ConfigHandler{
	 private SJKZ1MiscConfig config;

	    public ConFigIN()
	    {
	        super(SJKZ1Misc.MOD_ID);
	    }

	    public SJKZ1MiscConfig getConfig()
	    {
	        if (this.config == null)
	        {
	            try
	            {
	                this.loadConfig();
	            }
	            catch (IOException e)
	            {
	                SJKZ1Misc.LOGGER.error("Failed to load config, using default.", e);
	                return new SJKZ1MiscConfig();
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
	        	SJKZ1Misc.LOGGER.error("Unable to find config file, creating new one.");
	            this.config = new SJKZ1MiscConfig();
	            this.saveConfig();
	        }
	        else
	        {
	            this.config = GSON.fromJson(ConfigHandler.readFile(this.configFile.toPath().toString(), Charset.defaultCharset()), SJKZ1MiscConfig.class);
	        }
	    }

	    @Override
	    public void saveConfig() throws IOException
	    {
	        this.configFile.getParentFile().mkdirs();
	        FileWriter writer = new FileWriter(this.configFile);
	        Text.toJson(this.config, writer);
	        writer.close();
	    }
}
