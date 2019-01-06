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

## **Code structure**
* ***com.zssn*** - Entry point and config classes.
* ***com.zssn.controller*** - All controller that expose api features.
* ***com.zssn.exeptions*** - Exception handler and api exceptions that maps http status.
* ***com.zssn.model*** - Contains entity mappings and spring data repository classes.
* ***com.zssn.service*** - All business rules are implemented in this classes.
* ***com.zssn.vo*** - Mappers and View Objects to map requests/resposes.

## **Additional rules**
1. Survivor **can not** report him self as infected.
2. Survivor **can not** report the same survivor **more than once**.
3. Coordinates: **Latitude** has precision(8) and scale(6), **Longitude** has precision(9) and scale(6), it's enough to represent **any point on earth**.
4. If a survivor is created with **more than one resource** of the **same type**, its quantity will be **stacked**. Example: WATER(quantity 3), MEDICATION(quantity 5), WATER(quantity 6), this survivor will end up with **only one** register of **WATER(quantity 9(3+6))** on his inventory.
