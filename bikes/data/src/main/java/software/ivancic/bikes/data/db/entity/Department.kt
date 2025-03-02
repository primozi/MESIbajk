package software.ivancic.bikes.data.db.entity

enum class DbDepartment(val key: String) {
    DEVELOPMENT("development"),
    SALES("sales"),
    MARKETING("marketing"),
    PRODUCTION("production"),
    ;

    override fun toString(): String {
        return key
    }
}
