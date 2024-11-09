package com.alexzforger.finalexam

data class StateFlag(
    val stateCode: String,
    val isUsStates: Boolean,
    val capital: String
) {
    fun getFlagUrl(imageSize: String) : String {

        // force to use US state flags
        if (stateCode == "DC") {
            return "https://flagpedia.net/data/flags/$imageSize/us.png"
        }

        if (isUsStates) {
            return "https://flagpedia.net/data/us/$imageSize/${stateCode.lowercase()}.png"
        }
        return "https://flagpedia.net/data/flags/$imageSize/${stateCode.lowercase()}.png"
    }
}

val mapOfStateSlugAndFlags = hashMapOf(
    "alabama" to StateFlag("AL", true, "Montgomery"),
    "alaska" to StateFlag("AK", true, "Juneau"),
    "arizona" to StateFlag("AZ", true, "Phoenix"),
    "arkansas" to StateFlag("AR", true, "Little Rock"),
    "california" to StateFlag("CA", true, "Sacramento"),
    "colorado" to StateFlag("CO", true, "Denver"),
    "connecticut" to StateFlag("CT", true, "Hartford"),
    "delaware" to StateFlag("DE", true, "Dover"),
    "florida" to StateFlag("FL", true, "Tallahassee"),
    "georgia" to StateFlag("GA", true, "Atlanta"),
    "hawaii" to StateFlag("HI", true, "Honolulu"),
    "idaho" to StateFlag("ID", true, "Boise"),
    "illinois" to StateFlag("IL", true, "Springfield"),
    "indiana" to StateFlag("IN", true, "Indianapolis"),
    "iowa" to StateFlag("IA", true, "Des Moines"),
    "kansas" to StateFlag("KS", true, "Topeka"),
    "kentucky" to StateFlag("KY", true, "Frankfort"),
    "louisiana" to StateFlag("LA", true, "Baton Rouge"),
    "maine" to StateFlag("ME", true, "Augusta"),
    "maryland" to StateFlag("MD", true, "Annapolis"),
    "massachusetts" to StateFlag("MA", true, "Boston"),
    "michigan" to StateFlag("MI", true, "Lansing"),
    "minnesota" to StateFlag("MN", true, "Saint Paul"),
    "mississippi" to StateFlag("MS", true, "Jackson"),
    "missouri" to StateFlag("MO", true, "Jefferson City"),
    "montana" to StateFlag("MT", true, "Helena"),
    "nebraska" to StateFlag("NE", true, "Lincoln"),
    "nevada" to StateFlag("NV", true, "Carson City"),
    "new-hampshire" to StateFlag("NH", true, "Concord"),
    "new-jersey" to StateFlag("NJ", true, "Trenton"),
    "new-mexico" to StateFlag("NM", true, "Santa Fe"),
    "new-york" to StateFlag("NY", true, "Albany"),
    "north-carolina" to StateFlag("NC", true, "Raleigh"),
    "north-dakota" to StateFlag("ND", true, "Bismarck"),
    "ohio" to StateFlag("OH", true, "Columbus"),
    "oklahoma" to StateFlag("OK", true, "Oklahoma City"),
    "oregon" to StateFlag("OR", true, "Salem"),
    "pennsylvania" to StateFlag("PA", true, "Harrisburg"),
    "rhode-island" to StateFlag("RI", true, "Providence"),
    "south-carolina" to StateFlag("SC", true, "Columbia"),
    "south-dakota" to StateFlag("SD", true, "Pierre"),
    "tennessee" to StateFlag("TN", true, "Nashville"),
    "texas" to StateFlag("TX", true, "Austin"),
    "utah" to StateFlag("UT", true, "Salt Lake City"),
    "vermont" to StateFlag("VT", true, "Montpelier"),
    "virginia" to StateFlag("VA", true, "Richmond"),
    "washington" to StateFlag("WA", true, "Olympia"),
    "west-virginia" to StateFlag("WV", true, "Charleston"),
    "wisconsin" to StateFlag("WI", true, "Madison"),
    "wyoming" to StateFlag("WY", true, "Cheyenne"),
    "puerto-rico" to StateFlag("PR", false, "San Juan"),
    "district-of-columbia" to StateFlag("DC", false, "Washington, D.C.")
)
