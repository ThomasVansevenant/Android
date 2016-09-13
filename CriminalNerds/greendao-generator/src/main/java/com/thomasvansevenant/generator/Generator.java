package com.thomasvansevenant.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.thomasvansevenant.daoModels");

        schema.enableKeepSectionsByDefault();
        Entity entity = schema.addEntity("Crime");
        entity.addStringProperty("id")
                .customType("java.util.UUID", "com.thomasvansevenant.criminalnerds.Utils.UuidConverter")
                .primaryKey();
        entity.addDateProperty("date");
        entity.addStringProperty("photopath");
        entity.addBooleanProperty("solved");
        entity.addStringProperty("suspect");
        entity.addStringProperty("title");


        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}