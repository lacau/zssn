# zssn
## **What is it?**

This project is a solution to this [problem](https://gist.github.com/akitaonrails/711b5553533d1a14364907bbcdbee677)

## **Technologies and why**

* ***Java with spring(boot, data, web)*** - Reduce development and unit test time, scalable and realiable, large community, lots of good quality documentation, avoids write boilerplate code, provides embedded http servers. Actually it speaks for itself.
* ***Docker*** - Standardizing your environment, ensure consistency across multiple development/release cycles, eliminate the “it works on my machine” :), all major cloud computing providers support it(AWS/GCP).
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
1. Survivor **can not** report himself as infected.
2. Survivor **can not** report the same survivor **more than once**.
4. Survivor **can not trade with himself**.
4. Coordinates: **Latitude** has precision(8) and scale(6), **Longitude** has precision(9) and scale(6), it's enough to represent **any point on earth**.
5. If a survivor is created with **more than one resource** of the **same type**, its quantity will be **stacked**. Example: WATER(quantity 3), MEDICATION(quantity 5), WATER(quantity 6), this survivor will end up with **only one** register of **WATER(quantity 9(3+6))** on his inventory.

## **Run with Docker**
Theres two files in project root: **build-local-docker.sh** and **run-local.sh**
1. Enter project root folder
2. build image: ``./build-local-docker.sh`` it will build docker image, set archifact name, even remove old images.
3. run: ``./run-local.sh`` it will run image created at step 1. Bind container port to 8080 and map a volume for persistence.

## **Run without Docker**
1. Enter project root folder
2. run ``mvn clean install``
3. run ``mvn spring-boot:run``

## **API Docs**
You can found documentation through **swagger**.

With the system up and running, just open yout browser and go to **http://localhost:8080/swagger-ui.html**
