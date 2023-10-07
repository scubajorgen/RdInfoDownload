/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.studioblueplanet.rdinfodownload;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author jorgen
 */
public class Main
{
    private final static Logger LOGGER      = LogManager.getLogger(Main.class); 

    public static void main(String[] args)
    {
        LOGGER.info("Starting RdInfoDownload");
        RdInfoParser parser=new RdInfoParser();
        parser.downloadAndParse();
    }
}
