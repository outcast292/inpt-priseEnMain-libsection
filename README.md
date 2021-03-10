# libsection : Prédiction des Concentrations des Éléments du Sol

Ce projet est le fruit de notre travail dans le cadre de la volonté de l’OCP d’optimiser les technologies que les agriculteurs adoptent pour effectuer des analyses de sol, et l’implémentation du projet LaserAg tout en adoptant la technologie Laser Induced Breakdown Spectroscopy (LIBS).

Afin de suivre l’évolution des technologies de l’information et l’émergence du monde, notre programme s’inscrit dans le cadre d’une digitalisation des activités de l’OCP. Notre travail consiste à étudier les différents modèles de Machine Learning existants et choisir le meilleur, et à réaliser un programme qui fait la prédiction de la concentration des éléments chimiques à partir des valeurs de spectre réalisé par un Laser LIBS.

Nous décrirons le fonctionnement de notre projet dans son ensemble ainsi que les éléments qui prouvent le bon fonctionnement de celui-ci. Nous présenterons également l’ensemble du projet d’un point de vue technique. Puis, nous montreront la manière dont nous avons géré le projet à savoir comment on a découpé le projet en plusieurs taches pour une meilleure organisation au sein de l’équipe.
	
## Used technologies
### Python
- python 3.6>
 - joblib  
- scikit-learn==0.24.1  
- matplotlib  
-  numpy  
- pandas

### Java

 -  https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc    
   group: 'org.xerial', name: 'sqlite-jdbc', version:   '3.34.0'   
  -    https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j    
   group: 'net.lingala.zip4j', name: 'zip4j', version:   '2.7.0'   
   - https://mvnrepository.com/artifact/com.opencsv/opencsv  
	   group: 'com.opencsv', name: 'opencsv', version: '5.3' 
   - https://mvnrepository.com/artifact/com.itextpdf/kernel     
	   group: 'com.itextpdf', name: 'kernel', version: '7.1.9'   
  - https://mvnrepository.com/artifact/com.itextpdf/layout     
   group: 'com.itextpdf', name: 'layout', version: '7.1.9'

### plugins gradle

 - id 'java'  
 -  id 'application'   
 - id 'org.openjfx.javafxplugin' version   '0.0.8'  
  - id "org.beryx.jlink" version "2.17.2"
