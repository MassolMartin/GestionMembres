# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
FROM openjdk:8
ADD target/gestionmembres.jar gestionmembres.jar
ENTRYPOINT ["java","-jar","/gestionmembres.jar"]
