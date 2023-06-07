 package com.example.demo.integrationtests.controller.withxml;

 import com.example.demo.configs.TestConfigs;
 import com.example.demo.integrationtests.testcontainers.AbstractIntegrationTest;
 import com.example.demo.integrationtests.vo.AccountCredentialsVO;
 import com.example.demo.integrationtests.vo.PersonVO;
 import com.example.demo.integrationtests.vo.TokenVO;
 import com.example.demo.integrationtests.vo.pagedmodels.PagedModelPerson;
 import com.example.demo.integrationtests.vo.wrappers.WrapperPersonVO;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.DeserializationFeature;
 import com.fasterxml.jackson.dataformat.xml.XmlMapper;
 import io.restassured.builder.RequestSpecBuilder;
 import io.restassured.filter.log.LogDetail;
 import io.restassured.filter.log.RequestLoggingFilter;
 import io.restassured.filter.log.ResponseLoggingFilter;
 import io.restassured.specification.RequestSpecification;
 import org.junit.jupiter.api.*;
 import org.springframework.boot.test.context.SpringBootTest;

 import static io.restassured.RestAssured.given;
 import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification requestSpecification;
	private static XmlMapper objectMapper;
	private static PersonVO person;

	@BeforeAll
	public static void setUp() {
		objectMapper = new XmlMapper();
		objectMapper. disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(0)
	public void authorization() {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class).getAccessToken();

		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonProcessingException {
		mockPerson();

		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.body(person)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
		person = createdPerson;

		assertNotNull(createdPerson);
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());

		assertTrue(createdPerson.getId() > 0);

		assertEquals("Richard", createdPerson.getFirstName());
		assertEquals("Stallman", createdPerson.getLastName());
		assertEquals("New York City, US", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}

	@Test
	@Order(2)
	public void testDisableById() throws JsonProcessingException {
		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.pathParam("id", person.getId())
				.when()
				.patch("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertTrue(persistedPerson.getId() > 0);

		assertEquals("Richard", persistedPerson.getFirstName());
		assertEquals("Stallman", persistedPerson.getLastName());
		assertEquals("New York City, US", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonProcessingException {
		mockPerson();

		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.pathParam("id", person.getId())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertTrue(persistedPerson.getId() > 0);

		assertEquals("Richard", persistedPerson.getFirstName());
		assertEquals("Stallman", persistedPerson.getLastName());
		assertEquals("New York City, US", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(4)
	public void testUpdate() throws JsonProcessingException {
		person.setLastName("Piquet");

		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.body(person)
				.when()
				.put()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
		person = createdPerson;

		assertNotNull(createdPerson);
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		assertFalse(createdPerson.getEnabled());

		assertEquals(person.getId(), createdPerson.getId());

		assertEquals("Richard", createdPerson.getFirstName());
		assertEquals("Piquet", createdPerson.getLastName());
		assertEquals("New York City, US", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}

	@Test
	@Order(5)
	public void testDelete() {
		var content = given()
				.spec(requestSpecification)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
				.then()
				.statusCode(204);
	}

	@Test
	@Order(6)
	public void testFindAll() throws JsonProcessingException {
		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 2, "size", 10, "direction", "asc")
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO).body(person)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PagedModelPerson pagedModel = objectMapper.readValue(content, PagedModelPerson.class);
		var people = pagedModel.getContent();

		PersonVO foundPersonOne = people.get(0);

		assertNotNull(foundPersonOne);
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertTrue(!foundPersonOne.getEnabled());

		assertEquals(725,foundPersonOne.getId());
		assertEquals("Alberto", foundPersonOne.getFirstName());
		assertEquals("Chazotte", foundPersonOne.getLastName());
		assertEquals("384 Maple Place", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());

		PersonVO foundPersonTwo = people.get(1);

		assertNotNull(foundPersonTwo);
		assertNotNull(foundPersonTwo.getId());
		assertNotNull(foundPersonTwo.getFirstName());
		assertNotNull(foundPersonTwo.getLastName());
		assertNotNull(foundPersonTwo.getAddress());
		assertNotNull(foundPersonTwo.getGender());

		assertTrue(foundPersonTwo.getEnabled());

		assertEquals(915,foundPersonTwo.getId());
		assertEquals("Albrecht", foundPersonTwo.getFirstName());
		assertEquals("Corsor", foundPersonTwo.getLastName());
		assertEquals("36 Kipling Hill", foundPersonTwo.getAddress());
		assertEquals("Male", foundPersonTwo.getGender());
	}

	@Test
	@Order(7)
	public void testFindAllWithoutToken() {
		RequestSpecification requestSpecificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		given()
				.spec(requestSpecificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO).body(person)
				.when()
				.get()
				.then()
				.statusCode(403);
	}

	@Test
	@Order(8)
	public void testFindPeopleByName() throws JsonProcessingException {
		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("firstName", "be")
				.queryParams("page", 2, "size", 10, "direction", "asc")
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO).body(person)
				.when()
				.get("findPeopleByName/{firstName}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		/*WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPeople();

		PersonVO foundPersonOne = people.get(0);

		assertNotNull(foundPersonOne);
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertTrue(!foundPersonOne.getEnabled());

		assertEquals(346,foundPersonOne.getId());
		assertEquals("Berte", foundPersonOne.getFirstName());
		assertEquals("Moukes", foundPersonOne.getLastName());
		assertEquals("01913 Chinook Hill", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());*/

	}

	@Test
	@Order(9)
	void testHATEOAS() {
		var content = given()
				.spec(requestSpecification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 2, "size", 10, "direction", "asc")
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO).body(person)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();



		assertTrue(content.contains("links><rel>self</rel><href>http://localhost:8888/api/person/v1/725</href></links>"));
		assertTrue(content.contains("links><rel>self</rel><href>http://localhost:8888/api/person/v1/796</href></links>"));
		assertTrue(content.contains("links><rel>self</rel><href>http://localhost:8888/api/person/v1/969</href></links>"));

		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=0&amp;size=10&amp;sort=firstName,asc</href></links>"));
		assertTrue(content.contains("<links><rel>prev</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=1&amp;size=10&amp;sort=firstName,asc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1?page=2&amp;size=10&amp;direction=asc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=3&amp;size=10&amp;sort=firstName,asc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=100&amp;size=10&amp;sort=firstName,asc</href></links>"));

		assertTrue(content.contains("<page><size>10</size><totalElements>1002</totalElements><totalPages>101</totalPages><number>2</number></page>"));
	}

	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City, US");
		person.setGender("Male");
		person.setEnabled(true);
	}
}
