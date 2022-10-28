import ballerina/http;

public type Country record {
    string name;
};

@http:ServiceConfig {
    auth: [
        {
            jwtValidatorConfig: {
                signatureConfig: {
                    jwksConfig: {
                        url: "https://api.asgardeo.io/t/imeshaorg/oauth2/jwks"
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
