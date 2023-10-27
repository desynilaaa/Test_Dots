const request = require("supertest")("https://bookstore.toolsqa.com");
const expect = require("chai").expect;

describe("Flow that need credential", function () {
  before("Delete all books", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials);
    let param = '?UserId=78be09ba-fe70-4582-9146-d8c01f658bef';
    const response = await request
      .delete("/BookStore/v1/Books"+param)
      .set("Authorization","Basic "+encodedCredentialsAsString)

    console.log('Delete all books: '+ response.status);
    expect(response.status).to.eql(204);
  });

  it("Insert book", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials)
    const response = await request
      .post("/BookStore/v1/Books")
      .set("Authorization","Basic "+encodedCredentialsAsString)
      .send({
          "userId": "78be09ba-fe70-4582-9146-d8c01f658bef",
          "collectionOfIsbns": [
            {
              "isbn": "9781449331818"
            }
          ]
        });

    console.log('Insert book : '+ response.status);
    expect(response.status).to.eql(201);
    expect(response.body.books[0].isbn).to.eql("9781449331818");
  });

  it("Insert book that already exist in collection", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials)
    const response = await request
      .post("/BookStore/v1/Books")
      .set("Authorization","Basic "+encodedCredentialsAsString)
      .send({
          "userId": "78be09ba-fe70-4582-9146-d8c01f658bef",
          "collectionOfIsbns": [
            {
              "isbn": "9781449331818"
            }
          ]
        });
    console.log('Insert book that already exist in collection : '+ response.status);
    expect(response.status).to.eql(400);
    expect(response.body.code).to.eql('1210');
    expect(response.body.message).to.eql('ISBN already present in the User\'s Collection!');


  });

  it("Insert book that not available in books collection", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials)
    const response = await request
      .post("/BookStore/v1/Books")
      .set("Authorization","Basic "+encodedCredentialsAsString)
      .send({
          "userId": "78be09ba-fe70-4582-9146-d8c01f658bef",
          "collectionOfIsbns": [
            {
              "isbn": "978144933181800"
            }
          ]
        });

    console.log('Insert book that not available in books collection : '+ response.status);
    expect(response.status).to.eql(400);
    expect(response.body.code).to.eql("1205");
    expect(response.body.message).to.eql("ISBN supplied is not available in Books Collection!");
  });

  it("Insert book that already exist in collection", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials)
    const response = await request
      .post("/BookStore/v1/Books")
      .set("Authorization","Basic "+encodedCredentialsAsString)
      .send({
          "userId": "78be09ba-fe70-4582-9146-d8c01f658",
          "collectionOfIsbns": [
            {
              "isbn": "9781449331818"
            }
          ]
        });
    console.log('Insert book that already exist in collection : '+ response.status);
    expect(response.status).to.eql(401);
    expect(response.body.code).to.eql('1207');
    expect(response.body.message).to.eql('User Id not correct!');
  });

  it("Delete all books not authorized user", async function () {
    let credentials = "nila:123Qwerty";
    var encodedCredentialsAsString =btoa(credentials);
    let param = '?UserId=78be09ba-fe70-4582-9146-d8c01f658bef';
    const response = await request
      .delete("/BookStore/v1/Books"+param)
      .set("Authorization","Basic "+encodedCredentialsAsString)

    console.log('Delete all books not authorized user: '+ response.status);
    expect(response.status).to.eql(401);
      expect(response.body.code).to.eql('1200');
    expect(response.body.message).to.eql('User not authorized!');
  });

  it("Insert book with not authorized user", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials)
    const response = await request
      .post("/BookStore/v1/Books")
      .send({
          "userId": "78be09ba-fe70-4582-9146-d8c01f658",
          "collectionOfIsbns": [
            {
              "isbn": "9781449331818"
            }
          ]
        });
    console.log('Insert book that already exist in collection : '+ response.status);
    expect(response.status).to.eql(401);
    expect(response.body.code).to.eql('1200');
    expect(response.body.message).to.eql('User not authorized!');
  });

  it ("Delete all books", async function () {
    let credentials = "nila:123Qwerty*";
    var encodedCredentialsAsString =btoa(credentials);
    let param = '?UserId=78be09ba-fe70-4582-9146-d8c01f658bef';
    const response = await request
      .delete("/BookStore/v1/Books"+param)
      .set("Authorization","Basic "+encodedCredentialsAsString)

    console.log('Delete all books: '+ response.status);
    expect(response.status).to.eql(204);
  });
});