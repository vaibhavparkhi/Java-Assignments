# Java-Assignments --> Threat Detection

Technologies Used: Java 8, Spring Boot, Spring Data, Hibernate, H2 Database

## Solution Description:
#### The solution is belonging to entity relationship using Spring Data JPA which has to entity classes Device and Detection. The device has the @OnetoMany relationship with Detection.
And Detection entity has a relationship @ManyToOne with Device.
To remove detection for the particular device, I used the soft deletion strategy of Hibernate which could be used for further analytics of 
