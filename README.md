# DIT094_MINIPROJECT_GROUP_3

#Eazy Banking

Gson:
To run our software you must utilize google Gson library. Found at Maven / Gradle / JAR download.
https://github.com/google/gson

Maven dependency: 
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.9</version>
</dependency>

Gradle dependency:

dependencies {
  implementation 'com.google.code.gson:gson:2.8.9'
}

Or JAR download:
https://search.maven.org/artifact/com.google.code.gson/gson/2.8.9/jar

Depending on PC/MAC: 
When loading the preloaded data from our json files you **MIGHT** have to change the file path on six methods.
The methods are called "jsonFrom" and "jsonTo". The **6** methods are located at the very bottom of "Menu.java" class.
Depending on if you are on a PC or a Mac you might have to **remove** or **add** this: 


"dit094_miniproject_group_3" +System.getProperty("file.separator") + 
**Or**
"dit094_miniproject_group_3" +System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + 
If you need to add it you simply add it before:

"src" + System.getProperty("file.separator") + "controller" + System.getProperty("file.separator") + "Customer.json"), Customer[].class);

in each of the six methods.


To login as an Employee you can use:
username: admin
password: admin

To login as a Customer with access to all banking services you can use:
personal number: 6410124540
password: chokladpralin

There are also more customers loaded from the json file.

##Contributors

- Adrian Niklasson gusnikada@student.gu.se
- Anna Törngren gusolsang@student.gu.se 
- Christopher Andersson gusandchbh@student.gu.se
- Faiza Amjad  gusamjfa@student.gu.se
- Marharyta Apanasevich  gusmarhaap@student.gu.se
- Pontus Norén Stomberg guspontno@student.gu.se
