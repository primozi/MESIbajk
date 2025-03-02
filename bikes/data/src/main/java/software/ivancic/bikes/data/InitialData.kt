package software.ivancic.bikes.data

import software.ivancic.bikes.data.db.entity.BikeEntity


internal object InitialData {
    val initialBikesData = listOf(
        BikeEntity(id = 0, name = "Glavko", "glavko"),
        BikeEntity(id = 1, name = "Srečko", "srecko"),
        BikeEntity(id = 2, name = "Kihec", "kihec"),
        BikeEntity(id = 3, name = "Pikec", "pikec"),
        BikeEntity(id = 4, name = "Godrnjavko", "godrnjavko"),
        BikeEntity(id = 5, name = "Tepko", "tepko"),
        BikeEntity(id = 6, name = "Zaspanko", "zaspanko"),
    )
}
