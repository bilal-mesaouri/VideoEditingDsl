# About the project :
This project uses Groovy for the internal DSL, to generate MoviePy script for video editing, it's designed for people with medium knowledge in Programming.

Implemented Features:
- All 2 basic scenarios

Optional Extensions:
- Set Audio
- Fade Transition

generated .ino code is in the root directory of each method 

# How to launch :
run : " mvn clean install " inside the kernel
- ### embeeded (groovy):
  -  mvn clean compile assembly:single
  - java -jar "target/dsl-groovy-1.0-jar-with-dependencies.jar" "scripts/YOUR_SCENARIO_HERE.groovy"
