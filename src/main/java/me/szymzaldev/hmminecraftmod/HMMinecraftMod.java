package me.szymzaldev.hmminecraftmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HMMinecraftMod implements ModInitializer {

    public static final String MOD_ID = "hmminecraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized!");
    }
}
