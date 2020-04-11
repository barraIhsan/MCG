package amerebagatelle.github.io.simplecoordinates;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class SimpleCoordinates implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        logger.info("Gathering your coordinates...");

        CoordinatesManager.initCoordinates();
        try {
            CoordinatesManager.writeToCoordinates("Mesa", 1, 3, 10);
            System.out.println(CoordinatesManager.loadCoordinates().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
