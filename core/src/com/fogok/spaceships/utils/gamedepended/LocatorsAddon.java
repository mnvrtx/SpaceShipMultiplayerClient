package com.fogok.spaceships.utils.gamedepended;


public class LocatorsAddon {

    private float[] locators;

    public LocatorsAddon(float[] locators) {
        setLocators(locators);
    }

    public void setLocators(float[] locators) {
        this.locators = locators;
    }
    
    public void setLocator(float locate, int i){
        locators[i] = locate;
    }

    public float[] getLocators() {
        return locators;
    }

}
