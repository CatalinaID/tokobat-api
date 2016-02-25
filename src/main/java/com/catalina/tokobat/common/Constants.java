package com.catalina.tokobat.common;

/**
 * Created by Alifa on 3/14/2015.
 */
public final class Constants {
    public static final String ERROR_MESSAGE = "Fail";
    public static final Long ERROR_INDEX = 0L;
    public static final Long SUCCESS_INDEX = 1L;

    public static final String DEFAULT_SUCCESS = "OK";
    public static final String DEFAULT_FAIL = "FAIL";

    public static final String IPG_URI = "http://128.199.115.34:8080/ecommgateway";
    public static final String TICKET_URI = "http://128.199.115.34:6557/ipg/ticket";

    public static final String ENV_VAR = "{\n"+
            "  \"elephantsql\" : [ {\n"+
            "    \"name\" : \"ElephantSQL\",\n"+
            "    \"label\" : \"elephantsql\",\n"+
            "    \"plan\" : \"turtle\",\n"+
            "    \"credentials\" : {\n"+
            "      \"uri\" : \"postgres://woknocen:p9MLt7sQ9XCvkxnLZHwPN_GgfVKIPFZi@jumbo.db.elephantsql.com:5432/woknocen\",\n"+
            "      \"max_conns\" : \"5\"\n"+
            "    }\n"+
            "  } ],\n"+
            "  \"Object-Storage\" : [ {\n"+
            "    \"name\" : \"tokobat-img\",\n"+
            "    \"label\" : \"Object-Storage\",\n"+
            "    \"plan\" : \"Free\",\n"+
            "    \"credentials\" : {\n"+
            "      \"auth_url\" : \"https://identity.open.softlayer.com\",\n"+
            "      \"project\" : \"object_storage_3e2ce74c_b529_49cb_a5cb_e72954e9876e\",\n"+
            "      \"projectId\" : \"57557560a4cf43e69b07540ec4a9e5ee\",\n"+
            "      \"region\" : \"dallas\",\n"+
            "      \"userId\" : \"6907b89a637443778e4b80ba4d938f07\",\n"+
            "      \"username\" : \"Admin_87a7d6b2b7d22471251258090745a2e521482056\",\n"+
            "      \"password\" : \"hl0L?y8wu]inJ.Xs\",\n"+
            "      \"domainId\" : \"ea999ced620c4fc7a692d6ee514e84af\",\n"+
            "      \"domainName\" : \"913513\"\n"+
            "    }\n"+
            "  } ]\n"+
            "}";
}
