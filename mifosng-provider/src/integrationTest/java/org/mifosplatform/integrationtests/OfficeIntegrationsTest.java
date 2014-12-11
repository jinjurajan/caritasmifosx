/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.integrationtests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mifosplatform.integrationtests.common.Utils;
import org.mifosplatform.integrationtests.common.office.OfficeHelper;
import org.mifosplatform.integrationtests.common.office.OfficeResourceHandler;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import java.util.*;

public class OfficeIntegrationsTest {

    private ResponseSpecification statusOkResponseSpec;
    private RequestSpecification requestSpec;

    @Before
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.statusOkResponseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test
    public void testCreateOffice() {
        int random_array = {1997, 26, 12};
        OfficeHelper oh = OfficeHelper                                            // oh ---> Office Helper
                .create(Utils.randomNameGenerator("", 5))
                .externalId(Utils.randomNameGenerator("offices-", 7))
                .nameDecorated(Utils.randomNameGenerator("", 8))
                .openingDate(random_array)
                .hierarchy(".")
                .build();
        String JsonData = oh.toJSON();
        final Long officeID = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
        Assert.assertNotNull(officeID);

    }

    private Long createOffice(final String officeJSON,
                              final RequestSpecification requestSpec,
                              final ResponseSpecification responseSpec) {
        String officeId = String.valueOf(OfficeResourceHandler.createOffice(officeJSON, requestSpec, responseSpec));
        if (officeId.equals("null")) {
            // Invalid JSON data parameters
            return null;
        }

        return new Long(officeId);

        @Test
        public void testCreateOfficeWithEmptyName ()
        {
            int random_array = {2000, 4, 8};
            OfficeHelper oh = new OfficeHelper
                    .create(null)
                    .externalId(Utils.randomNameGenerator("", 5))
                    .nameDecorated(Utils.randomNameGenerator("offices-", 6))
                    .openingDate(random_array)
                    .hierarchy(".")
                    .build();
            String JsonData = oh.toJSON();
            final Long officeID = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNull(officeID);
        }

        @Test
        public void testCreateOfficeWithEmptyExternalId ()
        {
            int random_array = {2005, 1, 1};
            OfficeHelper oh = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 3))
                    .externalId(null)
                    .nameDecorated(Utils.randomNameGenerator("offices-", 5))
                    .openingDate(random_array)
                    .hierarchy(".")
                    .build();
            String JsonData = oh.toJSON();
            final Long OfficeID = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNotNull(OfficeID);
        }

        @Test
        public void testCreateOfficeWithDuplicateName ()
        {
            int random_array1 = {1998, 12, 10};
            OfficeHelper oh1 = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 7))
                    .externalId(Utils.randomNameGenerator("Offices-", 4))
                    .nameDecorated(Utils.randomNameGenerator("", 10))
                    .openingDate(random_array1)
                    .hierarchy(".")
                    .build();
            String JsonData = oh1.toJSON();
            final Long OfficeID1 = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNotNull(OfficeID);

            int random_array2 = {2002, 10, 10};
            OfficeHelper oh2 = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 7))
                    .externalId(Utils.randomNameGenerator(oh.getName()))
                    .nameDecorated(Utils.randomNameGenerator("", 11))
                    .openingDate(random_array2)
                    .hierarchy(".")
                    .build();

            String JsonData = oh2.toJSON();
            ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(400).build(); // 400 if the test works
            final Long OfficeID2 = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNull(OfficeID2);
        }

        @Test
        public void testCreateOfficeWithDuplicateExternalId ()
        {
            int random_array1 = {2003, 2, 2};
            OfficeHelper oh1 = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 8))
                    .externalId(oh1.getExternalId())
                    .nameDecorated(Utils.randomNameGenerator("", 10))
                    .openingDate(random_array1)
                    .hierarchy(".")
                    .build();

            String JsonData = oh1.toJSON();
            final Long OfficeID1 = createOfficeID(JsonData1, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNotNull(OfficeID);

            int random_array2 = {2005, 5, 5};
            OfficeHelper oh2 = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 9))
                    .externalId(Utils.randomNameGenerator("Offices-", 5))
                    .nameDecorated(Utils.randomNameGenerator("", 11))
                    .openingDate(random_array2)
                    .hierarchy(".")
                    .build();

            String JsonData = oh2.toJSON();
            ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(402).build(); // 402 if the test works
            final Long OfficeID2 = createOfficeID(JsonData2, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNull(OfficeID2);


        }

        @Test
        public void testCreateOfficeWithEmptyOpeningDate ()
        {
            OfficeHelper oh = new OfficeHelper
                    .create(Utils.randomNameGenerator("", 9))
                    .externalId(Utils.randomNameGenerator("Offices-", 4))
                    .nameDecorated(Utils.randomNameGenerator("", 3))
                    .openingDate(null)
                    .hierarchy(".")
                    .build();

            String JsonData = oh.toJSON();
            final Long OfficeID = createOfficeID(JsonData, this.requestSpec, this.statusOkResponseSpec);
            Assert.assertNotNull(OfficeID);
        }

        @Test
        public void testNameUnknowForNow ()
        {
            OfficeHelper oh = new OfficeHelper
                    .create()
                    .externalId()
                    .nameDecorated()
                    .openingDate()
                    .hierarchy(".")
                    .build();

        }

    }
}