# zssn
## **What is it?**

This project is a solution to this [problem](https://gist.github.com/akitaonrails/711b5553533d1a14364907bbcdbee677)

## **Technologies and why**

* ***Java with spring(boot, data, web)*** - Reduce development and unit test time, scalable and realiable, large community, lots of good quality documentation, avoids write boilerplate code, provides embedded http servers. Actually it speaks for itself.
* ***HSQLDB*** - Lightweight embedded database, easy to migrate, accept in memory manipulation to integration tests. Do not require a local installed database system to easy up and running.
* ***Flyway*** - Powerful tool to migrate and assure database consistency on a continuous deployment/delivery.
* ***Actuator*** - It's always crucial to have a good monitoring mechanism with easy access, has a very simple integration with grafana and prometheus.
* ***Swagger*** - Very straightforward way to provide a good quality api documentation to developers.
* ***Lombok*** - Annotation processor to avoids repetitive code, makes code cleaner, speeds up development.
* ***Junit/Mockito*** - Code without tests are difficult to maintain, add new features, scale, keep track of what is running as expected. Most common tools for testing with java.

## **Database Diagram**
![Alt text](https://github.com/lacau/zssn/blob/master/database/database_diagram.png?raw=true)

