package com.system.util.vehiclenumber;

import java.util.UUID;

public class CreateUniqueVehicleNumber {
    public String getUniqueVehicleNumber(){
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }
}
