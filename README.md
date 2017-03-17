# LawAdvice

1. What is the proposed name for your Web application?

  LawAdvice

  It is a website of legal consultation and knowledge share. Allowing users to upload their legal questions and interesting legal knowledges. Each question or legal related article can be seen and reviewed by anyone.  

2. Who is the target audience for your Web application?

  Anyone who meet a legal problem and want to ask some help; Anyone who is good at law and want to help others and try to popularize law; Anyone who is interested in legal articles and cases. Any lawyer who want to increase his/her popularity (through publishing some high-quality comments and interesting legal articles).

3. What problem is it intended to solve for the target audience?

  The major aim for this website is to build a platform for legal lovers, people who are facing legal questions and lawyers. They can publish legal questions or share interesting legal articles, and give comments to others. They can register their own accounts, publish legal articles/questions, and freely give comments to others if they like. Some classical legal cases and acknowledge will be prepared by the administrators.

4. How will it meet the minimum project requirements?

  For all users, they can create their accounts to save data and give comments to anyone. Data will be stored in a database. Users can upload any text content (posting or comment) and update or delete them in any time they like. And they also can delete their account. Ajax will be used to give and respond requests between clients and server.

5. Why is your proposed Web application unique or creative beyond simply meeting the minimum requirements?

  It looks like a Bulletin board system. The contents can be searched by catalog. I will use HTML5 and CSS3 to fulfill the main structure of the front end pages, and use javascript to achieve the dynamic functions. Data is transmitted by JSON, and back end will be written by JAVA. Ajax and jQuery will be used to improve the websites. And I will use frameworks like Bootstrap to make the developing easier and more clear. A suitable database will also be needed to satisfy the job of data storage. The whole development will be tested by a browser like Chrome. Standards and requests of Heroku and github will be met.

## Build status

TODO : Modify to match your project specific Travis Build
[![Build Status](https://travis-ci.org/infsci2560sp17/full-stack-web.svg?branch=master)](https://travis-ci.org/infsci2560sp17/full-stack-web)

## Web Site

https://github.com/Haozhi0209/full-stack-web-Haozhi0209

https://guarded-peak-94065.herokuapp.com/


## Key Features

TODO : Please list key features of your project.

* Key Feature 1
* Key Feature 2
* Key Feature N

## Project Details

### Landing Page

TODO : please provide a description of your landing page inluding a screen shot ![](https://.../image.JPG)

### User Input Form

TODO : please provide a description of at least 1 user input form including a screen shot ![](https://.../image.jpg)

## API

TODO : please provide a description of at least 1 API including a sample of request data and response data in both XML and JSON format.

### API Method 1

    POST photos/:id/tags

#### Parameters

- **id** _(required)_ — The Photo ID to add tags for.
- **tags** _(required)_ — Comma separated tags.

#### Response

A JSON or XMLobject containing the PhotoID and list of tags accepted.

#### Errors

All known errors cause the resource to return HTTP error code header together with a JSON array containing at least 'status' and 'error' keys describing the source of error.

- **404 Not Found** — The photo was not found.

#### Example

##### Request

    POST /v1/photos/123456/tags

##### Body

    tags=cute,puppy


##### JSON Response

```json
{
    "photoId": 123456,
    "tags": ["cute", "puppy"]
}
```

##### XML Response

```xml
<?xml version="1.0" encoding="UTF-8"?>
<PhotoTags>
    <photoId>123456</PhotoId>
        <tags>
            <tag>cute</tag>
            <tag>puppy</tag>
        </tags>
</PhotoTags>
```

## Technologies Used

TODO : List all technologies used in your project

- [Spring Boot](https://projects.spring.io/spring-boot/) - Takes an opinionated view of building production-ready Spring applications.
- [Thymleaf](http://www.thymeleaf.org/) - Thymeleaf is a modern server-side Java template engine for both web and standalone environments.
- [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool.

