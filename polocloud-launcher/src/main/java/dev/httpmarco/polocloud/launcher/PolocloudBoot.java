package dev.httpmarco.polocloud.launcher;

import dev.httpmarco.polocloud.launcher.utils.ManifestReader;

public final class PolocloudBoot {

    public static void main(String[] args) {

        var version = ManifestReader.readProperty("version");

        // set manifest version to java property
        System.setProperty("version", version);


                //-Dmyproperty=myvalue
        new PolocloudLauncher(version);
    }
}
