package net.skytreader.battleship.utils;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

/**
Value object representing a configuration file for Battleship.

We look for the following config values:
  server-host
  inbound-port
  outbound-port

@author Chad Estioco
*/
public class ConfigReader{
    
    private Properties p;
    
    public ConfigReader(String filename) throws IOException{
        p = new Properties();
        p.load(new FileInputStream(filename));
    }

    public String getServerHost(){
        return p.getProperty("server-host");
    }

    public int getInboundPort(){
        return Integer.parseInt(p.getProperty("inbound-port"));
    }

    public int getOutboundPort(){
        return Integer.parseInt(p.getProperty("outbound-port"));
    }

}
