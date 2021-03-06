package fi.akisaarinen

import org.scalatra._
import org.scalatra.test.scalatest._
import org.scalatest.matchers._

class AlgoholismFilterSuite extends ScalatraFunSuite with ShouldMatchers {
  addFilter(classOf[AlgoholismFilter], "/*")

  test("GET / returns status 200") {
    get("/") { 
      status should equal (200)
    }
  }

  test("GET /foo returns status 404") {
    get("/foo") {
      status should equal (404)
    }
  }

  test("POST / returns status 200 with empty body") {
    post("/") { 
      status should equal (200)
      body should equal("")
    }
  }

  test("POST / returns fake algorithm reply with correct magic value of 'a'") {
    post("/", """{ "a" : "lol" }""", Map[String,String]()) { 
      status should equal (200)
      body should equal("[1,3]\n")
    }
  }
  
  test("POST / returns compacted JSON with line change with wrong value of 'a'") {
    val testJson = """{ "a" : "not_funny" }"""
    post("/", testJson, Map[String,String]()) { 
      status should equal (200)
      body should equal("""{"a":"not_funny"}""" + "\n")
    }
  }

  test("POST / returns compacted JSON with line change for random json") {
    val testJson = """{ "foo" : "bar" }"""
    post("/", testJson, Map[String,String]()) { 
      status should equal (200)
      body should equal("""{"foo":"bar"}""" + "\n")
    }
  }

  test("POST /foo returns status 404") {
    post("/foo") {
      status should equal (404)
    }
  }
}
