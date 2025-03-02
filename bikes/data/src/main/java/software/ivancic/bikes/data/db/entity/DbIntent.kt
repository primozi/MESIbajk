package software.ivancic.bikes.data.db.entity

enum class DbIntent(val key: String) {
    PRIVATE("private"),
    BUSINESS("business"),
    ;

    override fun toString(): String {
        return key
    }
}
