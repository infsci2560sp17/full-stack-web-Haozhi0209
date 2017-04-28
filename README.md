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

[![Build Status](https://travis-ci.org/infsci2560sp17/full-stack-web.svg?branch=master)](https://travis-ci.org/infsci2560sp17/full-stack-web)

## Web Site

https://github.com/Haozhi0209/full-stack-web-Haozhi0209

https://ancient-caverns-23519.herokuapp.com/


## Key Features

* Sign in to publish topics freely
* Follow topics to give comments
* Try to be the hottest person!

## Project Details

MVC frameworks are used to seperate the project into diffrent layers. Service and implentation funcitons are used to complete functions. Controllers are used to transfer requests and data. HTML/CSS and jQuery are used in views. Data is stored into different tables in MySQL. AJAX and ASP.NET are also used to make dynamic response to users' requests.


## API

I used MySQL to store data, and tables are called like this.

@Table(name = "t_topic", pk = "tid")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer tid;

    private Integer uid;

    private Integer nid;

    private String title;

    private String content;

    private Integer is_top;

    private Integer is_essence;

    private Double weight;

    private Integer create_time;

    private Integer update_time;

    private Integer status;

}

And different GET/POST functions are achieved in different controllers.

@Route(value = "/topic/add", method = HttpMethod.GET)

    public ModelAndView show_add_topic(Request request, Response response) {

        LoginUser user = SessionKit.getLoginUser();
        
        if (null == user) {
        
            response.go("/");
        
            return null;
        
        }

        Long pid = request.queryAsLong("pid");
  
        request.attribute("pid", pid);
  
        return this.getView("topic_add");
  
    }


#### Response

Different JSON data will be returned by service functions called.


## Technologies Used


- [Spring Boot](https://projects.spring.io/spring-boot/) - Takes an opinionated view of building production-ready Spring applications.
- [Thymleaf](http://www.thymeleaf.org/) - Thymeleaf is a modern server-side Java template engine for both web and standalone environments.
- [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool.
- [JDBC](http://www.oracle.com/technetwork/java/javase/jdbc/index.html) - Java Database Connectivity (JDBC) is an application programming interface (API) for the programming language Java, which defines how a client may access a database.
- [jQuery](https://jquery.com/) - JQuery is a fast, small, and feature-rich JavaScript library.
- [MySQL](https://www.mysql.com/) - MySQL is an open-source relational database management system (RDBMS) created by the Swedish company MySQL AB.
- [ASP.NET](https://www.asp.net/)  ASP.NET is an open-source server-side web application framework designed for web development to produce dynamic web pages.