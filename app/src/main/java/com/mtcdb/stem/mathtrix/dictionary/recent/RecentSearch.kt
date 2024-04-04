package com.mtcdb.stem.mathtrix.dictionary.recent

data class RecentSearch(val query : String, val definition : String, val example : String) {

    override fun toString() : String {
        // Convert the RecentSearch object to a string representation
        return "$query|$definition|$example"
    }

    companion object {
        fun fromString(stringRepresentation : String) : RecentSearch {
            // Convert a string representation back to a RecentSearch object
            val parts = stringRepresentation.split("|")
            if (parts.size == 3) {
                return RecentSearch(parts[0], parts[1], parts[2])
            } else {
                // Handle invalid string representation
                throw IllegalArgumentException("Invalid string representation: $stringRepresentation")
            }
        }
    }
}
