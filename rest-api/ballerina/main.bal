import ballerina/http;

public type Country record {
    string name;
};

@http:ServiceConfig {
    auth: [
        {
            oauth2IntrospectionConfig: {
                url: "https://api.asgardeo.io/t/imeshaorg/oauth2/introspect",
                clientConfig: {
                    customHeaders: {
                        "Authorization": "Basic V3FjNmxnUEl6ZnN4d0ZhSkQ5S0p6NDcwV2I0YTp0T2dIdTAxWTFZWnVidGZVM0tlVDZBelZzaTRh"
                    }
                }
            }
        }
    ]
}
service / on new http:Listener(8080) {

    resource function get country/[int callingCode]() returns string|error {
        http:Client restCountriesEp = check new ("https://restcountries.com");
        Country[] countries = check restCountriesEp->get("/v2/callingcode/" + callingCode.toString());
        if countries.length() == 0 {
            return "Invalid Calling Code";
        }

        return countries[0].name;
    }
}
