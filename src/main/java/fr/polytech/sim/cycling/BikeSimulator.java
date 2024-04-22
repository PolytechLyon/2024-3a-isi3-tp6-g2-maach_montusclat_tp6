package fr.polytech.sim.cycling;

import fr.polytech.sim.Simulation;
import fr.polytech.sim.log.FactoryLogger;
import fr.polytech.sim.log.Logger;
import fr.polytech.sim.utils.Context;

import java.util.Iterator;

/**
 * Bike simulation.
 */
public class BikeSimulator implements Simulation {
    private final Logger logger = FactoryLogger.getLogger("BikeSimulator");

    public void run() {
        Bike bike;

        Iterator<Bike> bikes = Context.injectAll(Bike.class);

        while (bikes.hasNext()) {
            bike = bikes.next();

            this.logger.log(bike.getClass().getSimpleName() + "'s speed %.2f Km/h.", bike.getVelocity());
            this.logger.log(bike.getClass().getSimpleName() + "'s mass %.2f Kg.", bike.getMass());
        }
    }
}
