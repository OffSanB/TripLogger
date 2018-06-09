package com.assignment.android.triplogger.database;

public class TripDbSchema {

    public static final class TripTable{
        public static final String NAME ="trips";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TYPE = "type";
            public static final String DEST = "destination";
            public static final String COMMENT = "comment";
            public static final String GPS = "location";

        }
    }
}
