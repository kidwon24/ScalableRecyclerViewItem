package mom.example.scalablerecyclerviewitem.enums

enum class PokemonsType() {

    ICE,
    FIRE,
    WATER,
    GHOST,
    FAIRY,
    GROUND,
    FLYING,
    ELECTRIC;

    override fun toString() : String {
        return when(this) {
            ICE      -> "Ice"
            FIRE     -> "Fire"
            WATER    -> "Water"
            GHOST    -> "Ghost"
            FAIRY    -> "Fairy"
            GROUND   -> "Ground"
            FLYING   -> "Flying"
            ELECTRIC -> "Electric"
        }
    }
}